package org.main.food_pantry.Controllers.StudentControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.main.food_pantry.Models.Request;
import org.main.food_pantry.Databases.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRequestPageController
{

    @FXML
    private Label pageTitle;

    @FXML
    private TableView<Request> requestsTable;

    @FXML
    private TableColumn<Request, String> foodNameColumn;

    @FXML
    private TableColumn<Request, Integer> quantityColumn;

    @FXML
    private TableColumn<Request, String> statusColumn;

    @FXML
    private TableColumn<Request, String> dateColumn;

    private boolean showPendingOnly;
    private int userId;

    public void setData(boolean showPendingOnly, int userId)
    {
        this.showPendingOnly = showPendingOnly;
        this.userId = userId;
        pageTitle.setText(showPendingOnly ? "Pending Requests" : "Request History");

        setupTable();
        loadRequests();
    }

    private void setupTable()
    {
        foodNameColumn.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityRequested"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    }

    private void loadRequests()
    {
        requestsTable.getItems().clear();
        List<Request> requests = new ArrayList<>();

        String sql = "SELECT f.name, r.quantity_requested, r.status, r.request_date " +
                "FROM requests r " +
                "JOIN food_items f ON r.food_id = f.id " +
                "WHERE r.user_id = ?";

        if (showPendingOnly)
        {
            sql += " AND r.status = 'Pending'";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Request req = new Request(
                        rs.getString("name"),
                        rs.getInt("quantity_requested"),
                        rs.getString("status"),
                        rs.getString("request_date")
                );
                requests.add(req);
            }

            requestsTable.getItems().addAll(requests);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
