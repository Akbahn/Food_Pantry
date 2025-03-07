package org.main.food_pantry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private Button backBtn;

    @FXML
    private TextField emailTxtF;

    @FXML
    private Hyperlink forgotPasswordBtn;

    @FXML
    private TextField passwordTxtF;

    @FXML
    private Button signInBtn;

    @FXML
    void goChangePasswordPage(ActionEvent event) {

    }

    @FXML
    void goToPage(ActionEvent event) {

    }

    @FXML
    void goBackPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FoodPantryApplication.class.getResource("splash-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
