package org.main.food_pantry.Controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Databases.CurrentUser;
import org.main.food_pantry.Databases.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    private Button backBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/splash-page.fxml");
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String usernameInput = usernameTxt.getText().trim();
        String passwordInput = passwordTxt.getText().trim();

        System.out.println("[DEBUG] Login button clicked with username: " + usernameInput);

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            System.out.println("[DEBUG] Username or password is empty");
            showAlert("Input Required", "Please enter both username and password.");
            return;
        }

        loginBtn.setDisable(true);
        System.out.println("[DEBUG] Starting login task...");

        Task<Void> loginTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

                try (Connection conn = Database.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    System.out.println("[DEBUG] Connected to database successfully.");

                    stmt.setString(1, usernameInput);
                    stmt.setString(2, passwordInput);

                    System.out.println("[DEBUG] Executing query with parameters: " + usernameInput + " / " + passwordInput);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String name = rs.getString("name");
                        String username = rs.getString("username");
                        String role = rs.getString("role").toLowerCase();

                        System.out.println("[DEBUG] Login successful. User: " + name + " | Role: " + role);

                        CurrentUser.setUser(userId, name, username, role);

                        javafx.application.Platform.runLater(() -> {
                            Stage stage = (Stage) usernameTxt.getScene().getWindow();
                            switch (role) {
                                case "student" -> {
                                    System.out.println("[DEBUG] Redirecting to student page...");
                                    SceneManager.switchScene(stage, "/org/main/food_pantry/StudentPages/student-page.fxml");
                                }
                                case "volunteer" -> {
                                    System.out.println("[DEBUG] Redirecting to volunteer page...");
                                    SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/volunteer-page.fxml");
                                }
                                case "admin" -> {
                                    System.out.println("[DEBUG] Redirecting to admin page...");
                                    SceneManager.switchScene(stage, "/org/main/food_pantry/AdminPages/admin-page.fxml");
                                }
                                default -> {
                                    System.out.println("[DEBUG] Unknown role encountered.");
                                    showAlert("Unknown role", "Your account has no valid role assigned.");
                                }
                            }
                        });

                    } else {
                        System.out.println("[DEBUG] Login failed — invalid credentials.");
                        javafx.application.Platform.runLater(() ->
                                showAlert("Login failed", "Invalid username or password.")
                        );
                    }

                } catch (SQLException e) {
                    System.out.println("[DEBUG] SQLException occurred during login:");
                    e.printStackTrace();
                    javafx.application.Platform.runLater(() ->
                            showAlert("Database error", "Could not connect to the database.")
                    );
                }

                return null;
            }
        };

        new Thread(loginTask).start();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    /*
    //nia

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button signUpBtn;

    @FXML
    private void handleLogin() {
        String usernameInput = usernameField.getText().trim();
        String passwordInput = passwordField.getText().trim();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usernameInput);
            stmt.setString(2, passwordInput);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extract user info from DB
                int userId = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                //String role = rs.getString("role");
                String role=rs.getString("role").toLowerCase();

                //String selectedRole=CurrentUser.getTempRole();



                // Store logged-in user globally
                CurrentUser.setUser(userId, name, username, role);

                // Navigate to appropriate page
                Stage stage = (Stage) usernameField.getScene().getWindow();
                //scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); for the font


                switch (role) {
                    case "student" -> SceneManager.switchScene(stage, "/org/main/food_pantry/mainpage/student-page.fxml");
                    case "volunteer" -> SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/volunteer-page.fxml");
                    case "admin" -> SceneManager.switchScene(stage, "/org/main/food_pantry/AdminPages/admin-page.fxml"); // Adjust if needed
                    default -> showAlert("Unknown role", "Your account has no valid role assigned.");
                }
            } else {
                statusLabel.setText("Invalid username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error. Try again later.");
        }
    }

    @FXML
    void handleSignUp(ActionEvent event) {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/create-account-page.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    SceneManager.switchScene(stage, "/org/main/food_pantry/splash-page.fxml");
    }

     */


}
