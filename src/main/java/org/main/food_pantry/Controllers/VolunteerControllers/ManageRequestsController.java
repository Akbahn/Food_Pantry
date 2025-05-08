package org.main.food_pantry.Controllers.VolunteerControllers;

//package org.main.food_pantry.Controllers.VolunteerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Databases.FoodDAO;
import org.main.food_pantry.Models.RequestRow;
import org.main.food_pantry.Databases.RequestDAO;

public class ManageRequestsController
{

    @FXML private TableView<RequestRow> requestTable;
    @FXML private TableColumn<RequestRow, String> studentColumn;
    @FXML private TableColumn<RequestRow, String> foodColumn;
    @FXML private TableColumn<RequestRow, Integer> quantityColumn;
    @FXML private TableColumn<RequestRow, String> statusColumn;
    @FXML private TableColumn<RequestRow, java.time.LocalDate> dateColumn;
    @FXML private TableColumn<RequestRow, Void> actionColumn;

    private final ObservableList<RequestRow> requests = FXCollections.observableArrayList();

    @FXML
    public void initialize()
    {
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        loadRequests();
        addActionButtons();
    }

    private void loadRequests()
    {
        requests.setAll(RequestDAO.getPendingRequestRows());
        requestTable.setItems(requests);
    }

    private void addActionButtons()
    {
        actionColumn.setCellFactory(col -> new TableCell<>()
        {
            private final Button approveBtn = new Button("Approve");
            private final Button denyBtn = new Button("Deny");
            private final HBox btnBox = new HBox(5, approveBtn, denyBtn);

            {
                approveBtn.setOnAction(e -> {
                    RequestRow row = getTableView().getItems().get(getIndex());
                    boolean success = RequestDAO.approveRequest(row.getRequestId(), row.getQuantity(), row.getFoodName());
                    if (success) {
                        showAlert("Request approved and inventory updated.");
                        loadRequests(); // refresh
                    }
                });

                denyBtn.setOnAction(e -> {
                    RequestRow row = getTableView().getItems().get(getIndex());
                    RequestDAO.denyRequest(row.getRequestId());
                    showAlert("Request denied.");
                    loadRequests(); // refresh
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnBox);
            }
        });
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
