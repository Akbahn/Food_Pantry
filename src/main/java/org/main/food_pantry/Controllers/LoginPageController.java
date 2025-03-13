package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Database;
import org.main.food_pantry.FoodPantryApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {

    @FXML
    private Button backBtn;

    @FXML
    private TextField usernameTxtF;

    @FXML
    private Hyperlink forgotPasswordBtn;

    @FXML
    private TextField passwordTxtF;

    @FXML
    private Button signInBtn;

    @FXML
    private Label errorLabel;

    @FXML
    void goToPage(ActionEvent event) {
        String email = usernameTxtF.getText();
        String password = passwordTxtF.getText();

        String role = authenticateUser(email, password);
        if (role != null) {
            showAlert("Login Successful", "Welcome, " + role + "!", Alert.AlertType.INFORMATION);
            navigateToPage(role);
        } else {
            showAlert("Login Failed", "Invalid credentials. Please try again.", Alert.AlertType.ERROR);
        }
    }

    private String authenticateUser(String email, String password) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT role FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void navigateToPage(String role) {
        String page = switch (role) {
            case "Student" -> "/org/main/food_pantry/student-page.fxml";
            case "Volunteer" -> "/org/main/food_pantry/volunteer-page.fxml";
            default -> null;
        };

        if (page != null) {
            try {
                URL fxmlLocation = getClass().getResource(page);
                if (fxmlLocation == null) {
                    throw new IOException("FXML file not found: " + page);
                }

                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) signInBtn.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load " + page + "\n" + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Invalid role: " + role, Alert.AlertType.WARNING);
        }
    }



    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void goBackPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FoodPantryApplication.class.getResource("splash-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goChangePasswordPage(ActionEvent actionEvent) {
    }
}
