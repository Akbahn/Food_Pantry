package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.main.food_pantry.FoodPantryApplication;

import java.io.IOException;

public class SplashPageController {

    @FXML
    private Button loginBtn;

    @FXML
    private ListView<?> requestedItemList;

    @FXML
    private Button signUpBtn;

    @FXML
    void goToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FoodPantryApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToSignUpPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FoodPantryApplication.class.getResource("create-account-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
