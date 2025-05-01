package org.main.food_pantry.Controllers.VolunteerControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Items.FoodCategory;
import org.main.food_pantry.Databases.FoodDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.nio.file.*;

public class AddFoodController {
    private boolean isUpdateMode = false;
    private int foodIdToUpdate = -1;

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField quantityField;
    @FXML private DatePicker expirationDatePicker;
    @FXML private TextArea descriptionArea;
    @FXML private Button uploadImageBtn;
    @FXML private Label imageFileNameLabel;

    private String selectedImageFileName = null;

    @FXML
    public void initialize() {
        categoryComboBox.setItems(FXCollections.observableArrayList(
                Arrays.stream(FoodCategory.values())
                        .map(Enum::name)
                        .toList()
        ));

    }

    @FXML
    void handleSubmit() {
        String name = nameField.getText();
        String selectedCategory = categoryComboBox.getValue();
        String quantityText = quantityField.getText();
        LocalDate expirationDate = expirationDatePicker.getValue();
        String description = descriptionArea.getText();

        if (name.isEmpty() || selectedCategory == null || quantityText.isEmpty() || expirationDate == null) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill out all fields!");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be a positive number!");
            return;
        }

        FoodCategory category = FoodCategory.valueOf(selectedCategory);

        Food newFood = new Food(0, name, category, quantity, expirationDate, description, selectedImageFileName);
        boolean success;
        if (isUpdateMode) {
            newFood.setId(foodIdToUpdate); // make sure the ID is included
            success = FoodDAO.updateFood(newFood);
        } else {
            System.out.println("Image to save: " + selectedImageFileName);
            success = FoodDAO.insertFood(newFood);
        }
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Food added successfully!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add food. Try again.");
        }
    }

    @FXML
    private void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Food Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(uploadImageBtn.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Clean up filename to remove spaces
                String fileName = selectedFile.getName().replaceAll(" ", "_");
                Path targetPath = Paths.get("src/main/resources/org/main/food_pantry/Images/FoodItems/" + fileName);

                // 👇 Ensure the directory exists
                Files.createDirectories(targetPath.getParent());

                // Only copy if file doesn't already exist
                if (!Files.exists(targetPath)) {
                    Files.copy(selectedFile.toPath(), targetPath);
                }

                selectedImageFileName = fileName;
                imageFileNameLabel.setText(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Upload Failed", "Failed to upload image.");
            }
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }


    public void saveImageToClassesDirectory(File selectedFile) {
        try {
            // Target directory inside target/classes
            Path targetPath = Paths.get("target/classes/org/main/food_pantry/Images/FoodItems/" + selectedFile.getName());

            // Ensure the directory exists
            Files.createDirectories(targetPath.getParent());

            // Copy the selected image to the target directory
            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Image saved to: " + targetPath.toString());
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }



    public void prefillFood(Food food) {
        nameField.setText(food.getName());
        categoryComboBox.setValue(food.getCategory().name());
        quantityField.setText(String.valueOf(food.getQuantity()));
        expirationDatePicker.setValue(food.getExpirationDate());
        descriptionArea.setText(food.getDescription());

        if (food.getImagePath() != null) {
            selectedImageFileName = food.getImagePath();
            imageFileNameLabel.setText(selectedImageFileName);
        }

        isUpdateMode = true;
        foodIdToUpdate = food.getId();
    }

}
