package org.main.food_pantry.Databases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.main.food_pantry.Models.RequestRow;

import java.sql.*;

public class RequestDAO {
    public static boolean insertRequest(int userId, int foodId, int quantity) {
        String sql = "INSERT INTO requests (user_id, food_id, quantity_requested) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, foodId);
            pstmt.setInt(3, quantity);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<RequestRow> getPendingRequestRows() {
        ObservableList<RequestRow> rows = FXCollections.observableArrayList();

        String sql = "SELECT r.id, u.name AS student, f.name AS food, r.quantity_requested, r.request_date, r.status " +
                "FROM requests r JOIN users u ON r.user_id = u.id " +
                "JOIN food_items f ON r.food_id = f.id " +
                "WHERE r.status = 'Pending'";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rows.add(new RequestRow(
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getString("food"),
                        rs.getInt("quantity_requested"),
                        rs.getDate("request_date").toLocalDate(),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    public static void denyRequest(int requestId) {
        String sql = "UPDATE requests SET status = 'Denied' WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, requestId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean approveRequest(int requestId, int quantity, String foodName) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false); // transaction start

            // 1. Update request status
            String updateRequest = "UPDATE requests SET status = 'Approved' WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateRequest)) {
                pstmt.setInt(1, requestId);
                pstmt.executeUpdate();
            }

            // 2. Reduce food quantity
            String updateFood = "UPDATE food_items SET quantity = quantity - ? WHERE name = ? AND quantity >= ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateFood)) {
                pstmt.setInt(1, quantity);
                pstmt.setString(2, foodName);
                pstmt.setInt(3, quantity);
                int updated = pstmt.executeUpdate();

                if (updated == 0) {
                    conn.rollback();
                    return false;
                }
            }

            conn.commit(); // ✅ success
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
