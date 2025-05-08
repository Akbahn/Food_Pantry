package org.main.food_pantry.Controllers.VolunteerControllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.main.food_pantry.Controllers.SceneManager;
import org.main.food_pantry.Databases.RequestDAO;
import org.main.food_pantry.Models.RequestRow;
import org.main.food_pantry.Databases.StudentRequestGroup;

import java.time.LocalDate;

public class ManageRequestsController {

    @FXML private TableView<StudentRequestGroup> requestTable;
    @FXML private TableColumn<StudentRequestGroup, Integer> studentIdColumn;
    @FXML private TableColumn<StudentRequestGroup, String> studentNameColumn;
    @FXML private TableColumn<StudentRequestGroup, LocalDate> dateColumn;
    @FXML private TableColumn<StudentRequestGroup, Void> itemsColumn;
    @FXML private TableColumn<StudentRequestGroup, Void> actionColumn;

    private final ObservableList<StudentRequestGroup> groupedRequests = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        studentIdColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getStudentId()));
        studentNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStudentName()));
        dateColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getRequestDate()));

        // View Items button
        itemsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button viewBtn = new Button("View");

            {
                viewBtn.setOnAction(e -> {
                    StudentRequestGroup group = getTableView().getItems().get(getIndex());
                    showRequestedItemsDialog(group);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : viewBtn);
            }
        });

        // Approve / Deny buttons
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button approveBtn = new Button("Approve");
            private final Button denyBtn = new Button("Deny");
            private final HBox buttonBox = new HBox(10, approveBtn, denyBtn);

            {
                approveBtn.setStyle("-fx-background-color: #6fb98f; -fx-text-fill: white;");
                denyBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

                approveBtn.setOnAction(e -> {
                    StudentRequestGroup group = getTableView().getItems().get(getIndex());
                    boolean allApproved = group.getRequests().stream().allMatch(req ->
                            RequestDAO.approveRequest(req.getRequestId(), req.getQuantity(), req.getFoodName())
                    );
                    showAlert(allApproved ? "All items approved." : "Some items may not have been approved.");
                    loadGroupedRequests();
                });

                denyBtn.setOnAction(e -> {
                    StudentRequestGroup group = getTableView().getItems().get(getIndex());
                    group.getRequests().forEach(req -> RequestDAO.denyRequest(req.getRequestId()));
                    showAlert("Request denied.");
                    loadGroupedRequests();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttonBox);
            }
        });

        loadGroupedRequests();
    }

    private void loadGroupedRequests() {
        groupedRequests.setAll(RequestDAO.getGroupedPendingRequests());
        requestTable.setItems(groupedRequests);
    }

    private void showRequestedItemsDialog(StudentRequestGroup group) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Requested Items - " + group.getStudentName());

        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 15;");

        for (RequestRow item : group.getRequests()) {
            CheckBox cb = new CheckBox(item.getFoodName() + " (Qty: " + item.getQuantity() + ")");
            cb.selectedProperty().bindBidirectional(item.fulfilledProperty()); // 👈 allow interaction
            content.getChildren().add(cb);
        }

        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(content);

        dialog.setResultConverter(button -> {
            if (button == saveBtn) {
                System.out.println("Updated fulfillment:");
                for (RequestRow item : group.getRequests()) {
                    System.out.println(item.getFoodName() + " = " + item.isFulfilled());
                    // optionally update DB here
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    @FXML
    private void goBack(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/org/main/food_pantry/VolunteerPages/volunteer-page.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
