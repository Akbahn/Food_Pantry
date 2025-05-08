package org.main.food_pantry.Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.main.food_pantry.Databases.UserDAO;

public class AccountManagerController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleCreateAccount() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleCombo.getValue();

        // Validate inputs
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("Please fill in all fields.");
            statusLabel.setStyle("-fx-text-fill: #cc0000;");
            return;
        }

        boolean success = UserDAO.createUser(name, username, password, role);

        if (success) {
            statusLabel.setText("✅ Account created successfully!");
            statusLabel.setStyle("-fx-text-fill: #2e8b57;");
            clearForm();
        } else {
            statusLabel.setText("⚠ Failed to create account. Username may already exist.");
            statusLabel.setStyle("-fx-text-fill: #cc0000;");
        }
    }

    private void clearForm() {
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
        roleCombo.setValue(null);
    }
}
