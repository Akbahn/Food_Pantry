package org.main.food_pantry.Controllers.StudentControllers;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import org.main.food_pantry.Databases.CurrentUser;
import org.main.food_pantry.Databases.RequestDAO;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Items.FoodCategory;
import org.main.food_pantry.Databases.FoodDAO;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodListPageController {

    @FXML
    private FlowPane foodFlowPane;

    @FXML
    private Label categoryLabel;

    @FXML
    private Button checkoutBtn;

    private FoodCategory selectedCategory;
    private List<Food> cart = new ArrayList<>();



    public void setCategory(FoodCategory category) {
        this.selectedCategory = category;
        categoryLabel.setText(category.name() + " Items");

        loadFoodItems();
    }



    private Image getCategoryImage(FoodCategory category) {
        String path;
        switch (category) {
            case DAIRY -> path = "/food_pantry/Images/FoodCategory/icons8-dairy-50.png";
            case MEAT -> path = "/food_pantry/Images/FoodCategory/icons8-meat-50.png";
            case VEGETABLES -> path = "/food_pantry/Images/FoodCategory/icons8-vegetables-48.png";
            case FRUITS -> path = "/food_pantry/Images/FoodCategory/icons8-fruit-50.png";
            case BEVERAGES -> path = "/food_pantry/Images/FoodCategory/icons8-drink-50.png";
            case GRAINS -> path = "/food_pantry/Images/FoodCategory/icons8-grain-50.png";
            case SNACKS -> path = "/food_pantry/Images/FoodCategory/icons8-snack-50.png";
            case OTHER -> path = "/food_pantry/Images/FoodCategory/icons8-other-50.png";
            default -> path = "/food_pantry/Images/FoodCategory/icons8-other-50.png";
        }

        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }




    private void loadFoodItems() {
        foodFlowPane.getChildren().clear();

        List<Food> foods = FoodDAO.getAvailableFoods(); // Only available foods
        List<Food> filteredFoods = foods.stream()
                .filter(food -> food.getCategory() == selectedCategory)
                .toList();

        for (Food food : filteredFoods) {
            VBox card = createFoodCard(food);
            foodFlowPane.getChildren().add(card);
        }
    }


    private VBox createFoodCard(Food food) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setPrefWidth(150);

        ImageView foodImage = new ImageView();
        foodImage.setFitHeight(100);
        foodImage.setFitWidth(100);

        String imagePath = food.getImagePath();
        Image image = null;

        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Ensure that the image path is correctly passed relative to resources
                image = new Image(getClass().getResourceAsStream("/food_pantry/Images/FoodItems/" + imagePath));
                if (image.isError()) throw new Exception("Image failed to load.");
            } catch (Exception e) {
                System.out.println("⚠ Could not load food image: " + imagePath + " → using default.");
                image = getCategoryImage(food.getCategory());
            }
        } else {
            image = getCategoryImage(food.getCategory());
        }

        foodImage.setImage(image);
        Label foodName = new Label(food.getName());
        foodName.setStyle("-fx-font-weight: bold;");

        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setOnAction(e -> {
            cart.add(food);
            updateCheckoutButton();
        });

        card.getChildren().addAll(foodImage, foodName, addToCartBtn);
        return card;
    }




    private void updateCheckoutButton() {
        checkoutBtn.setText("Checkout (" + cart.size() + ")");
    }

    @FXML
    private void handleCheckout() {
        if (cart.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cart is empty", "Please add items to cart before checkout.");
            return;
        }

        int studentId = CurrentUser.getId(); // 👈 You must be storing this somewhere when the student logs in
        System.out.println("User ID used for checkout: " + studentId);

        for (Food food : cart) {
            boolean success = RequestDAO.insertRequest(studentId, food.getId(), 1);

            if (!success) {
                showAlert(Alert.AlertType.ERROR, "Request Failed", "Could not submit request for: " + food.getName());
                return;
            }
        }

        cart.clear();
        updateCheckoutButton();
        loadFoodItems(); // Refreshes UI
        showAlert(Alert.AlertType.INFORMATION, "Success", "Request submitted successfully!");
    }



    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
