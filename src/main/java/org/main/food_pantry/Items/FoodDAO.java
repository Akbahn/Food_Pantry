package org.main.food_pantry.Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.main.food_pantry.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FoodDAO {
    public static ObservableList<Food> getAllFoods() {
        ObservableList<Food> foodList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM food_items";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Food food = new Food(
                        rs.getInt("food_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDate("expiration_date").toLocalDate(),
                        rs.getString("description")
                );
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }
}
