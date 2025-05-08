package org.main.food_pantry.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.main.food_pantry.Databases.FoodDAO;
import org.main.food_pantry.Items.Food;

import java.util.List;
import java.util.stream.Collectors;

public class SplashPageController
{

    @FXML
    private ListView<String> requestedItemList;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signUpBtn;

    // Threshold for low stock
    private static final int LOW_STOCK_THRESHOLD = 2;

    @FXML
    public void initialize() {
        loadLowStockItems();
    }

    private void loadLowStockItems()
    {
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
    private void goToLoginPage()
    {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/login-page.fxml");
    }

    @FXML
    private void goToSignUpPage()
    {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/create-account-page.fxml");
    }
}
