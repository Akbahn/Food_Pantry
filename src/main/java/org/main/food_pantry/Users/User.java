package org.main.food_pantry.Users;

import org.main.food_pantry.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class User {
    protected String name, username, password, role;
    protected int id;

    public User(String name, String username, String password, String role) {
        this.id = generateUniqueID(); // Automatically generates ID
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    private int generateUniqueID() {
        int newID = 1;
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM users")) {
            if (rs.next()) {
                newID = rs.getInt(1) + 1; // Increment max ID by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public abstract void displayMenu();
}
