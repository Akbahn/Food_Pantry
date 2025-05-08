package org.main.food_pantry.Controllers.StudentControllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.main.food_pantry.Databases.CurrentUser;
import org.main.food_pantry.Databases.RequestDAO;
import org.main.food_pantry.Items.Food;

import java.util.ArrayList;
import java.util.List;


public class CartCheckoutPageController {

    @FXML
    private Label totalLabel;


    @FXML
    private VBox cartItemsBox;

    @FXML
    private Button submitBtn;

    @FXML
    private Button clearBtn;

    private List<Food> cart = new ArrayList<>();

    // Called by StudentPageController
    public void setCart(List<Food> cart) {
        this.cart = cart;
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        cartItemsBox.getChildren().clear();

        if (cart.isEmpty()) {
            cartItemsBox.getChildren().add(new Label("Your cart is empty."));
            totalLabel.setText("Total Items: 0");
            submitBtn.setDisable(true);
            clearBtn.setDisable(true);
            return;
        }

        submitBtn.setDisable(false);
        clearBtn.setDisable(false);

        int totalItems = cart.size();

        for (int i = 0; i < cart.size(); i++) {
            Food food = cart.get(i);

            HBox itemRow = new HBox(10);
            itemRow.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-background-color: white;");
            itemRow.setAlignment(Pos.CENTER_LEFT);

            Label nameLabel = new Label(food.getName());
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label qtyLabel = new Label("Qty: 1");

            Button removeBtn = new Button("Remove");
            removeBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
            int index = i; // capture index for lambda
            removeBtn.setOnAction(e -> {
                cart.remove(index);
                updateCartDisplay();
            });

            itemRow.getChildren().addAll(nameLabel, qtyLabel, removeBtn);
            cartItemsBox.getChildren().add(itemRow);
        }

        totalLabel.setText("Total Items: " + totalItems);
    }


    @FXML
    private void handleSubmitCart() {
        if (cart.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cart is empty", "Please add items to cart before checking out.");
            return;
        }

        int studentId = CurrentUser.getId();

        boolean allSuccessful = true;
        for (Food food : cart) {
            boolean success = RequestDAO.insertRequest(studentId, food.getId(), 1);
            if (!success) {
                allSuccessful = false;
                showAlert(Alert.AlertType.ERROR, "Request Failed", "Could not submit request for: " + food.getName());
            }
        }

        if (allSuccessful) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Your request has been submitted!");
            cart.clear();
            updateCartDisplay();
        }
    }

    @FXML
    private void handleClearCart() {
        cart.clear();
        updateCartDisplay();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
