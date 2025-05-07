package org.main.food_pantry.Controllers.StudentControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.main.food_pantry.Controllers.SceneManager;
import org.main.food_pantry.Databases.CurrentUser;
import org.main.food_pantry.Items.FoodCategory;

import java.io.IOException;

public class StudentPageController {

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
}
