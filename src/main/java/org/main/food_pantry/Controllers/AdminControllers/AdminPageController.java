package org.main.food_pantry.Controllers.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPageController {

    @FXML
    private void goToUserSearch(ActionEvent event) {
        openWindow("/org/main/food_pantry/AdminPages/UserSearchPage.fxml", "Manage Users");
    }

    @FXML
    private void goToAnalytics(ActionEvent event) {
        openWindow("/org/main/food_pantry/AdminPages/AdminAnalyticsPage.fxml", "Analytics");
    }

    @FXML
    private void goToAccountManager(ActionEvent event) {
        openWindow("/org/main/food_pantry/AdminPages/CreateAccountPage.fxml", "Create Account");
    }

    @FXML
    private void goToInfoEditor(ActionEvent event) {
        openWindow("/org/main/food_pantry/AdminPages/InfoBoardEditor.fxml", "Edit Info Board");
    }

    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
