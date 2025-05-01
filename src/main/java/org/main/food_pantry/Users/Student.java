package org.main.food_pantry.Users;

import org.main.food_pantry.Databases.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student extends User {
    public Student(int id, String name, String username, String password) {
        super(name, username, password, "Student");
        this.id = id;
    }

    public void requestDonation(String foodItem) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO requests (student_id, food_item) VALUES (?, ?)")) {
            stmt.setInt(1, id);
            stmt.setString(2, foodItem);
            stmt.executeUpdate();
            System.out.println("Request for " + foodItem + " submitted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMenu() {
        System.out.println("1. Request Donation\n2. View Available Items");
    }
}
