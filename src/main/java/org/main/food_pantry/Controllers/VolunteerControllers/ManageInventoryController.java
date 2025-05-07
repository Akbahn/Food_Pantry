package org.main.food_pantry.Controllers.VolunteerControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.main.food_pantry.Controllers.SceneManager;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Databases.FoodDAO;

import java.io.IOException;
import java.time.LocalDate;

public class ManageInventoryController {

    @FXML
    private TableColumn<Food, String> nameColumn;
    @FXML
    private TableColumn<Food, String> categoryColumn;
    @FXML
    private TableColumn<Food, Integer> quantityColumn;
    @FXML
    private TableColumn<Food, LocalDate> expirationColumn;
    @FXML
    private TableColumn<Food, String> descriptionColumn;
    @FXML
    private TableColumn<Food, ImageView> imageColumn;
    @FXML
    private Button backBtn;


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        categoryColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCategory().toString()));
        quantityColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());
        expirationColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getExpirationDate()));
        descriptionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        imageColumn.setCellValueFactory(cellData -> {
            String imagePath = cellData.getValue().getImagePath();
            ImageView imgView = new ImageView();

            try {
                if (imagePath != null && !imagePath.isEmpty()) {
                    Image image = new Image(getClass().getResourceAsStream(
                            "/food_pantry/Images/FoodItems/" + imagePath));
                    imgView.setImage(image);
                }
            } catch (Exception e) {
                // Optionally log or load a default image
            }

            imgView.setFitHeight(40);
            imgView.setFitWidth(40);
            imgView.setPreserveRatio(true);

            return new javafx.beans.property.SimpleObjectProperty<>(imgView);
        });
        refreshFoodTable();
    }


    @FXML
    private TableView<Food> foodTable; // make sure your fx:id matches!

    @FXML
    void handleUpdateQuantity() {
        Food selectedFood = foodTable.getSelectionModel().getSelectedItem();

        if (selectedFood == null) {
            showAlert(Alert.AlertType.ERROR, "Please select a food item to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/VolunteerPages/UpdateQuantityPage.fxml"));
            Parent root = loader.load();

            // Pass the selected food into the controller
            UpdateQuantityController controller = loader.getController();
            controller.setFood(selectedFood);

            // Open as a small popup window
            Stage stage = new Stage();
            stage.setTitle("Update Quantity");
            stage.initModality(Modality.APPLICATION_MODAL); // Blocks parent window
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Optional: after closing popup, you can refresh table if needed
            refreshFoodTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddFood() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/VolunteerPages/AddFoodPage.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Food");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // 🔁 Refresh table AFTER window closes
            refreshFoodTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleUpdateFood() {
        Food selectedFood = foodTable.getSelectionModel().getSelectedItem();

        if (selectedFood == null) {
            showAlert(Alert.AlertType.ERROR, "Please select a food item to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/VolunteerPages/AddFoodPage.fxml"));
            Parent root = loader.load();

            AddFoodController controller = loader.getController();
            controller.prefillFood(selectedFood); // You need to implement this method

            Stage stage = new Stage();
            stage.setTitle("Update Food");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshFoodTable();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteFood() {
        Food selectedFood = foodTable.getSelectionModel().getSelectedItem();

        if (selectedFood == null) {
            showAlert(Alert.AlertType.ERROR, "Please select a food item to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete " + selectedFood.getName() + "?",
                ButtonType.YES, ButtonType.NO);

        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            boolean success = FoodDAO.deleteFoodById(selectedFood.getId()); // implement this in DAO
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Food deleted successfully.");
                refreshFoodTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete food.");
            }
        }
    }


    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshFoodTable() {
        var foods = FoodDAO.getAllFoods();
        System.out.println("Foods loaded: " + foods.size()); // Debug log
        foodTable.setItems(foods);
    }

    @FXML
    void goBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/volunteer-page.fxml");
    }

}
