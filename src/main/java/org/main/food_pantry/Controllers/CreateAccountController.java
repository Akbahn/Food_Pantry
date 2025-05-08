package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Databases.UserDAO;

public class CreateAccountController {

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button backBtn;
    @FXML private PasswordField confirmPasswordField;

    @FXML
    private void handleCreateAccount() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = "Student";

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }

        boolean success = UserDAO.createUser(name, username, password, role);

        if (success) {
            statusLabel.setStyle("-fx-text-fill: #2e8b57;");
            statusLabel.setText("✅ Account created successfully!");
            nameField.clear();
            usernameField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
        } else {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("⚠ Username may already exist.");
        }
    }




    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/splash-screen.fxml");
    }

}
