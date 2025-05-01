package org.main.food_pantry.Controllers.VolunteerControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Databases.FoodDAO;

import java.time.LocalDate;

public class UpdateQuantityController {

    @FXML
    private TextField quantityField;

    @FXML
    private DatePicker expirationDatePicker;

    private Food foodToUpdate;

    public void setFood(Food food) {
        this.foodToUpdate = food;
    }

    @FXML
    void handleUpdate() {
        String quantityText = quantityField.getText();

        if (quantityText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter a quantity.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Quantity must be a positive number!");
            return;
        }

        // Update only quantity and optional expiration date
        foodToUpdate.setQuantity(quantity);

        LocalDate newExpiration = expirationDatePicker.getValue();
        if (newExpiration != null) {
            foodToUpdate.setExpiration_date(newExpiration);
        }

        boolean success = FoodDAO.updateFood(foodToUpdate);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Quantity updated successfully!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update quantity. Try again.");
        }
    }

    @FXML
    void handleCancel() {
        closeWindow();
    }



    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) quantityField.getScene().getWindow();
        stage.close();
    }
}
