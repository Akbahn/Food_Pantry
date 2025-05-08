package org.main.food_pantry.Controllers.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.main.food_pantry.Databases.UserDAO;
import org.main.food_pantry.Users.User;

import java.util.List;

public class UserSearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> roleFilter;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    private final ObservableList<User> allUsers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up ComboBox options
        roleFilter.getItems().addAll("All", "Student", "Volunteer", "Admin");
        roleFilter.setValue("All");

        // Set up columns
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        usernameColumn.setCellValueFactory(data -> data.getValue().usernameProperty());
        roleColumn.setCellValueFactory(data -> data.getValue().roleProperty());

        // Load users from database (mocked for now)
        loadUsers();
    }

    private void loadUsers() {
        List<User> fetchedUsers = UserDAO.getAllUsers();
        allUsers.setAll(fetchedUsers);
        userTable.setItems(allUsers);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase().trim();
        String selectedRole = roleFilter.getValue();

        ObservableList<User> filtered = FXCollections.observableArrayList();

        for (User user : allUsers) {
            boolean matchesName = user.getName().toLowerCase().contains(searchText)
                    || user.getUsername().toLowerCase().contains(searchText);

            boolean matchesRole = selectedRole.equals("All") || user.getRole().equalsIgnoreCase(selectedRole);

            if (matchesName && matchesRole) {
                filtered.add(user);
            }
        }

        userTable.setItems(filtered);
    }
}
