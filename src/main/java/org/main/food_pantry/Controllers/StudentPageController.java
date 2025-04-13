package org.main.food_pantry.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Items.FoodDAO;
import org.main.food_pantry.RequestDAO;
import org.main.food_pantry.Users.Student;


public class StudentPageController {
    private Student currentStudent;

    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
    }

    @FXML private TableView<Food> tableView;
    @FXML private TableColumn<Food, String> nameColumn;
    @FXML private TableColumn<Food, String> categoryColumn;
    @FXML private TableColumn<Food, Integer> quantityColumn;
    @FXML private TableColumn<Food, String> expiryColumn;
    @FXML private TableColumn<Food, String> descriptionColumn;
    @FXML private Label statusLabel;


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        expiryColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        ObservableList<Food> foodList = FoodDAO.getAllFoods();
        tableView.setItems(foodList);
    }


    @FXML
    private void handleRequestItem() {
        Food selectedFood = tableView.getSelectionModel().getSelectedItem();

        if (selectedFood != null && currentStudent != null) {
            boolean success = RequestDAO.submitRequest(currentStudent.getId(), selectedFood.getId(), 1);

            if (success) {
                statusLabel.setText("Requested: " + selectedFood.getName());
            } else {
                statusLabel.setText("Failed to submit request.");
            }
        } else {
            statusLabel.setText("Please select an item.");
        }
    }




}



