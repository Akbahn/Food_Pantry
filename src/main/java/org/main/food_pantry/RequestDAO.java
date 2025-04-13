package org.main.food_pantry;



import org.main.food_pantry.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestDAO {

    public static boolean submitRequest(int userId, int foodId, int quantity) {
        String sql = "INSERT INTO requests (user_id, food_id, quantity_requested) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, foodId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
