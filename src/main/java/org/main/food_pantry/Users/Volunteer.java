package org.main.food_pantry.Users;

import org.main.food_pantry.Databases.Database;
import org.main.food_pantry.Items.Food;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Volunteer extends User {
    public Volunteer(int id, String name, String username, String password) {
        super(name, username, password, "Volunteer");
        this.id = id;
    }

    public void approveRequest(int requestId) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM requests WHERE id = ?")) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
            System.out.println("Request " + requestId + " approved.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(Food food) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO inventory (name, quantity) VALUES (?, ?)")) {
            stmt.setString(1, food.getName());
            stmt.setInt(2, food.getQuantity());
            stmt.executeUpdate();
            System.out.println("Added " + food.getQuantity() + " of " + food.getName() + " to inventory.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMenu() {
        System.out.println("1. Approve Requests\n2. Add Items");
    }
}
