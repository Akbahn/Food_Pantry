package org.main.food_pantry.Controllers.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.main.food_pantry.Controllers.SceneManager;

public class AdminPageController
{

    @FXML
    private Button createUserBtn;

    @FXML
    private Button Alerts;

    @FXML
    private Button inventoryBtn;

    @FXML
    private Button manageUserBtn;

    @FXML
    private Button reportBtn;

    @FXML
    void showCreateUsers(ActionEvent event)
    {
        Stage stage = (Stage) createUserBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/create-account-page.fxml");
    }
    @FXML
    void showAlerts(ActionEvent event)
    {
        Stage stage = (Stage) Alerts.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/AdminPages/Alerts_Page.fxml");
    }

    @FXML
    void showInventory(ActionEvent event)
    {
        Stage stage = (Stage) inventoryBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/ManageInventoryPage.fxml");
    }

    @FXML
    void showManageUsers(ActionEvent event)
    {
        Stage stage = (Stage) manageUserBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/AdminPages/Manage_Users_Page.fxml");
    }

    @FXML
    void showReports(ActionEvent event)
    {
        Stage stage = (Stage) createUserBtn.getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/AdminPages/Reports_Page.fxml");
    }

}
