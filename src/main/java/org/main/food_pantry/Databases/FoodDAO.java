package org.main.food_pantry.Databases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.main.food_pantry.Items.Food;
import org.main.food_pantry.Items.FoodCategory;

import java.sql.*;

public class FoodDAO {

    public static ObservableList<Food> getAllFoods() {
        ObservableList<Food> foodList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM food_items";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Food food = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        FoodCategory.valueOf(rs.getString("category")),
                        rs.getInt("quantity"),
                        rs.getDate("expiration_date").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("image_path") // ✅
                );

                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    public static boolean updateFood(Food food) {
        String sql = "UPDATE food_items SET name = ?, category = ?, quantity = ?, expiration_date = ?, description = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, food.getName());
            pstmt.setString(2, food.getCategory().toString()); // FoodCategory enum to String
            pstmt.setInt(3, food.getQuantity());
            pstmt.setDate(4, java.sql.Date.valueOf(food.getExpiration_date()));
            pstmt.setString(5, food.getDescription());
            pstmt.setInt(6, food.getId());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Food> getAvailableFoods() {
        ObservableList<Food> foods = FXCollections.observableArrayList();
        String sql = "SELECT * FROM food_items WHERE quantity > 0";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Food food = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        FoodCategory.valueOf(rs.getString("category")), // be sure this matches your enum!
                        rs.getInt("quantity"),
                        rs.getDate("expiration_date").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("image_path")
                );
                foods.add(food);
                System.out.println("Loaded food: " + food.getName() + " (ID: " + food.getId() + ")");
                System.out.println("Checking out: Food ID = " + food.getId());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foods;
    }




    public static boolean insertFood(Food food) {
        String sql = "INSERT INTO food_items (name, category, quantity, expiration_date, description, image_path) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, food.getName());
            pstmt.setString(2, food.getCategory().toString());
            pstmt.setInt(3, food.getQuantity());
            pstmt.setDate(4, Date.valueOf(food.getExpiration_date()));
            pstmt.setString(5, food.getDescription());
            pstmt.setString(6, food.getImagePath());

            pstmt.executeUpdate();
            System.out.println("INSERTING: " + food.getName() + ", " + food.getImagePath());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFoodById(int id) {
        String sql = "DELETE FROM food_items WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
