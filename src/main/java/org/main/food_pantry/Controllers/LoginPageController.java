package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Users.*;

import java.net.URL;

public class LoginPageController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button signUpBtn;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        User user = UserDAO.loginUser(username, password);
        if (user != null) {

            Stage stage = (Stage) usernameField.getScene().getWindow();
            URL url = SceneManager.class.getResource("/org/main/food_pantry/student-page.fxml");
            System.out.println("FXML URL: " + url);



            switch (user.getRole()) {
                case "Student":
                    SceneManager.switchScene(stage, "/org/main/food_pantry/student-page.fxml");
                    break;
                case "Volunteer":
                    SceneManager.switchScene(stage, "/org/main/food_pantry/volunteer-page.fxml");
                    break;
                case "Admin":
                    SceneManager.switchScene(stage, "/org/main/food_pantry/admin-page.fxml");
                    break;
            }
        } else {
            statusLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    void handleSignup(ActionEvent event) {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/create-account-page.fxml");
    }
}
