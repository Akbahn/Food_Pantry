package org.main.food_pantry.Controllers;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.main.food_pantry.Databases.FoodDAO;
import org.main.food_pantry.Items.Food;
import javafx.event.ActionEvent;
import org.main.food_pantry.Databases.CurrentUser;

//import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SplashPageController {

    @FXML
    private Button LoginBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    void goToLoginPage(ActionEvent event) {
        Stage stage = (Stage) LoginBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/login-page.fxml");

    }

    @FXML
    void goToSignUpPage(ActionEvent event) {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/create-account-page.fxml");
    }

 /*   @FXML
    private ListView<String> requestedItemList;

    //@FXML
    //private Button loginBtn;
    //@FXML
    //private Button signUpBtn;

    // Threshold for low stock
    private static final int LOW_STOCK_THRESHOLD = 2;

    @FXML //will need this for the new main page
    public void initialize() {
        loadLowStockItems();
    }

    private void loadLowStockItems() {
        List<Food> allFoods = FoodDAO.getAvailableFoods(); // You should already have this DAO method

        List<String> lowStockItems = allFoods.stream()
                .filter(food -> food.getQuantity() <= LOW_STOCK_THRESHOLD)
                .map(food -> food.getName() + " — only " + food.getQuantity() + " left")
                .collect(Collectors.toList());

        if (lowStockItems.isEmpty()) {
            requestedItemList.getItems().add(" All items are well-stocked!");
        } else {
            requestedItemList.getItems().addAll(lowStockItems);
        }
    }

    @FXML
    private void goToLoginPage() {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/login-page.fxml");
    }

    @FXML
    private void goToSignUpPage() {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/create-account-page.fxml");
    }

 ----- Nia's code ----

@FXML
private void handleStudent(ActionEvent event) throws IOException {
    //
    Parent StudentPage = FXMLLoader.load(getClass().getResource("splash-page.fxml"));
    Scene studentScene = new Scene(StudentPage);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(studentScene);
    stage.show();
     //
    CurrentUser.setTempRole("student");
    Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.switchScene(stage,"/org/main/food_pantry/login-page.fxml");
}

@FXML
    private void handleAdmin(ActionEvent event) throws IOException  {
    CurrentUser.setTempRole("admin");
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    SceneManager.switchScene(stage,"/org/main/food_pantry/login-page.fxml");

}
    */


}


