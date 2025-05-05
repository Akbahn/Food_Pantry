package org.main.food_pantry.Controllers;

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
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button signUpBtn;
    @FXML private Button backBtn;

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
                String role = rs.getString("role");

                // Store logged-in user globally
                CurrentUser.setUser(userId, name, username, role);

                // Navigate to appropriate page
                Stage stage = (Stage) usernameField.getScene().getWindow();

                switch (role.toLowerCase()) {
                    case "student" -> SceneManager.switchScene(stage, "/org/main/food_pantry/StudentPages/student-page.fxml");
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
    void handleSignup(ActionEvent event) {
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
    void goBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/splash-page.fxml");

    }
}
