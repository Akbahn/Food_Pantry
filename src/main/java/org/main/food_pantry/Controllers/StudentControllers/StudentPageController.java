package org.main.food_pantry.Controllers.StudentControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.main.food_pantry.Controllers.SceneManager;
import org.main.food_pantry.Databases.CurrentUser;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Items.FoodCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentPageController {

    private final List<Food> cart = new ArrayList<>();

    @FXML
    private Button history;

    @FXML
    private Button signOutBtn;


    @FXML
    private Button cartBtn;

    @FXML
    private Button dairyBtn;

    @FXML
    private Button drinkBtn;

    @FXML
    private Button fruitBtn;

    @FXML
    private Button grainBtn;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button meatBtn;

    @FXML
    private Button otherBtn;

    @FXML
    private Button snackBtn;

    @FXML
    private Button vegBtn;

    @FXML
    private void handleCategoryClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        FoodCategory category = null;

        // Match button to category
        if (clickedButton == dairyBtn) {
            category = FoodCategory.DAIRY;
        } else if (clickedButton == drinkBtn) {
            category = FoodCategory.BEVERAGES;
        } else if (clickedButton == fruitBtn) {
            category = FoodCategory.FRUITS;
        } else if (clickedButton == grainBtn) {
            category = FoodCategory.GRAINS;
        } else if (clickedButton == meatBtn) {
            category = FoodCategory.MEAT;
        } else if (clickedButton == vegBtn) {
            category = FoodCategory.VEGETABLES;
        } else if (clickedButton == snackBtn) {
            category = FoodCategory.SNACKS;
        } else if (clickedButton == otherBtn) {
            category = FoodCategory.OTHER;
        }

        if (category != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/StudentPages/FoodListPage.fxml"));
                Parent root = loader.load();

                FoodListPageController controller = loader.getController();
                controller.setCategory(category);
                controller.setParentController(this);

                Stage newStage = new Stage();
                newStage.setTitle(category.name() + " Items");
                newStage.setScene(new Scene(root));
                newStage.show(); // not modal — allows both windows to be used independently

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void goToCartPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/StudentPages/CartCheckoutPage.fxml"));
            Parent root = loader.load();

            // Pass cart to the controller
            CartCheckoutPageController controller = loader.getController();
            controller.setCart(cart);

            Stage stage = new Stage();
            stage.setTitle("Cart Checkout");
            stage.setScene(new Scene(root));
            stage.show(); // non-modal

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addToCart(Food food) {
        cart.add(food);
        System.out.println("[DEBUG] Added to cart: " + food.getName() + " | Total: " + cart.size());
        cartBtn.setText("Cart (" + cart.size() + ")");
    }

    @FXML
    void goHome(ActionEvent event) {
        // Clear the logged-in user data
        CurrentUser.clear();

        // Switch to the splash screen
        Stage stage = (Stage) mainPane.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/splash-page.fxml");

    }

    @FXML
    void goToHistory(ActionEvent event) {
        openRequestPage(false);

    }

    private int getLoggedInUserId() {
        return CurrentUser.getId();
    }

    private void openRequestPage(boolean pendingOnly) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/StudentPages/StudentRequestPage.fxml"));
            Parent root = loader.load();

            StudentRequestPageController controller = loader.getController();
            controller.setData(pendingOnly, getLoggedInUserId());

            Stage stage = new Stage();
            stage.setTitle(pendingOnly ? "Pending Requests" : "Request History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




/*
    @FXML private ImageView fruitImage;
    @FXML private ImageView cartImage;
    @FXML private ImageView dairyImage;
    @FXML private ImageView drinkImage;
    @FXML private ImageView grainImage;
    @FXML private ImageView meatImage;
    @FXML private ImageView otherImage;
    @FXML private ImageView snackImage;
    @FXML private ImageView vegImage;
    @FXML private ImageView profileImageView;

    @FXML private Button historyBtn;
    @FXML private Button pendingBtn;
    @FXML private Button searchBtn;
    @FXML private MenuItem aboutMenu;
    @FXML private MenuItem closeMenu;
    @FXML private MenuItem signOutMenu;
    @FXML private BorderPane rootPane;


    @FXML
    void goToPending(ActionEvent event) {
        openRequestPage(true);
    }

    @FXML
    void goToHistory(ActionEvent event) {
        openRequestPage(false);
    }

    @FXML
    void closeApp(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    void goHome(ActionEvent event) {
        // Clear the logged-in user data
        CurrentUser.clear();

        // Switch to the splash screen
        Stage stage = (Stage) rootPane.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/splash-page.fxml");
    }



    @FXML
    void showProfile(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/student-profile.fxml"); // TODO: Need to add student profile
    }


    private void openRequestPage(boolean pendingOnly) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/mainpage/StudentRequestPage.fxml"));
            Parent root = loader.load();

            StudentRequestPageController controller = loader.getController();
            controller.setData(pendingOnly, getLoggedInUserId());

            Stage stage = new Stage();
            stage.setTitle(pendingOnly ? "Pending Requests" : "Request History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getLoggedInUserId() {
        return CurrentUser.getId();
    }

    @FXML
    void goToSearch(ActionEvent event) {
        // Placeholder for future search feature
    }

    @FXML
    public void initialize() {
        if (dairyImage != null) dairyImage.setOnMouseClicked(e -> openFoodList(FoodCategory.DAIRY));
        if (drinkImage != null) drinkImage.setOnMouseClicked(e -> openFoodList(FoodCategory.BEVERAGES));
        if (fruitImage != null) fruitImage.setOnMouseClicked(e -> openFoodList(FoodCategory.FRUITS));
        if (grainImage != null) grainImage.setOnMouseClicked(e -> openFoodList(FoodCategory.GRAINS));
        if (meatImage != null) meatImage.setOnMouseClicked(e -> openFoodList(FoodCategory.MEAT));
        if (snackImage != null) snackImage.setOnMouseClicked(e -> openFoodList(FoodCategory.SNACKS));
        if (vegImage != null) vegImage.setOnMouseClicked(e -> openFoodList(FoodCategory.VEGETABLES));
        if (otherImage != null) otherImage.setOnMouseClicked(e -> openFoodList(FoodCategory.OTHER));
    }

    private void openFoodList(FoodCategory category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/mainpage/FoodListPage.fxml"));
            Parent root = loader.load();

            FoodListPageController controller = loader.getController();
            controller.setCategory(category);

            Stage stage = new Stage();
            stage.setTitle(category.name() + " Items");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

}
