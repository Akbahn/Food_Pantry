package org.main.food_pantry.Databases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.main.food_pantry.Models.RequestRow;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public static ObservableList<StudentRequestGroup> getGroupedPendingRequests() {
        ObservableList<RequestRow> flatList = getPendingRequestRows();
        Map<Integer, StudentRequestGroup> grouped = new LinkedHashMap<>();

        for (RequestRow row : flatList) {
            int studentId = row.getStudentId();  // ✅ MUST be from RequestRow.getStudentId()

            grouped.computeIfAbsent(studentId, id -> new StudentRequestGroup(
                    id,
                    row.getStudent(),
                    row.getRequestDate(),
                    row.getStatus()
            )).addRequest(row);

        }

        return FXCollections.observableArrayList(grouped.values());
    }


    public static ObservableList<RequestRow> getPendingRequestRows() {
        ObservableList<RequestRow> rows = FXCollections.observableArrayList();

        String sql = "SELECT r.id, u.id AS student_id, u.name AS student, f.name AS food, r.quantity_requested, r.request_date, r.status " +
                "FROM requests r JOIN users u ON r.user_id = u.id " +
                "JOIN food_items f ON r.food_id = f.id " +
                "WHERE r.status = 'Pending'";


        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rows.add(new RequestRow(
                        rs.getInt("id"),
                        rs.getInt("student_id"),          // 👈 studentId from users table
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

    public static Map<String, Integer> getMostRequestedFoods() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = """
        SELECT f.name, COUNT(*) as count
        FROM requests r
        JOIN food_items f ON r.food_id = f.id
        GROUP BY r.food_id
        ORDER BY count DESC
        LIMIT 10
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                map.put(rs.getString("name"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, Integer> getTopRequestingStudents() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = """
        SELECT u.username, COUNT(*) as count
        FROM requests r
        JOIN users u ON r.user_id = u.id
        WHERE u.role = 'Student'
        GROUP BY r.user_id
        ORDER BY count DESC
        LIMIT 10
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                map.put(rs.getString("username"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void setRequestFulfilled(int requestId, boolean fulfilled) {
        String sql = "UPDATE requests SET fulfilled = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, fulfilled);
            stmt.setInt(2, requestId);
            stmt.executeUpdate();
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
