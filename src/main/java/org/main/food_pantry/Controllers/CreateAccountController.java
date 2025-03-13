package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Database;
import org.main.food_pantry.FoodPantryApplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccountController {

    @FXML
    private Button backBtn;

    @FXML
    private TextField usernameTxtF;

    @FXML
    private TextField passwordTxtF;

    @FXML
    private TextField nameTxtF;

    @FXML
    private Button registerBtn;

    @FXML
    private Label successLabel;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Student", "Volunteer");
        roleComboBox.setValue("Student"); // Default selection
    }

    @FXML
    void createAccount(ActionEvent event) {
        String name = nameTxtF.getText();
        String email = usernameTxtF.getText();
        String password = passwordTxtF.getText();
        String role = roleComboBox.getValue();

        if (registerUser(name, email, password, role)) {
            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");

            // Navigate to the appropriate screen based on role
            switchToRoleView(role);
        } else {
            // Show error alert
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account. Try again.");
        }
    }

    private boolean registerUser(String name, String email, String password, String role) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    void goToPrevoiusPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FoodPantryApplication.class.getResource("splash-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToRoleView(String role) {
        String fxmlFile = role.equals("Student") ? "student-page.fxml" : "volunteer-page.fxml";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FoodPantryApplication.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the next screen.");
        }
    }
}
