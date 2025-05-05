package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Databases.UserDAO;

public class CreateAccountController {

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label statusLabel;
    @FXML private Button backBtn;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Student", "Volunteer", "Admin");
        roleComboBox.setValue("Student");
    }

    @FXML
    public void handleCreateAccount() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill out all fields.");
            return;
        }

        boolean success = UserDAO.createUser(name, username, password, role);
        if (success) {
            statusLabel.setText("✅ Account created successfully!");
            Stage stage = (Stage) nameField.getScene().getWindow();
            SceneManager.switchScene(stage, "/org/main/food_pantry/login-page.fxml");
        } else {
            statusLabel.setText("❌ Username already exists or error occurred.");
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/splash-screen.fxml");
    }

}
