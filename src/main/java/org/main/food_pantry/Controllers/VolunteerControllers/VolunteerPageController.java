package org.main.food_pantry.Controllers.VolunteerControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.main.food_pantry.Controllers.SceneManager;
import org.main.food_pantry.Databases.CurrentUser;

public class VolunteerPageController {

    @FXML
    private ImageView cartImage;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private Button manageInventoryBtn;

    @FXML
    private Button manageRequestsBtn;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button scheduleBtn;

    @FXML
    private BorderPane rootPane;

    @FXML
    private MenuItem signOutMenu;

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
    void goToManageInventory(ActionEvent event) {
        Stage stage = (Stage) manageInventoryBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/ManageInventoryPage.fxml");
    }

    @FXML
    void goToManageRequests(ActionEvent event) {
        Stage stage = (Stage) manageRequestsBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/ManageRequestsPage.fxml");
    }

    @FXML
    void goToSchedule(ActionEvent event) {
        Stage stage = (Stage) scheduleBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/VolunteerSchedulePage.fxml");
    }
}
