package org.main.food_pantry.Users;

import org.main.food_pantry.Database;

import java.sql.*;

public class UserDAO {

    public static int registerUser(User user) {
        String sql = "INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.name);
            stmt.setString(2, user.username);
            stmt.setString(3, user.password);
            stmt.setString(4, user.role);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int newId = keys.getInt(1);
                user.id = newId; // set ID in object
                return newId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String role = rs.getString("role");

                switch (role) {
                    case "Student":
                        return new Student(id, name, username, password);
                    case "Admin":
                        return new Admin(id, name, username, password);
                    // Add Volunteer later
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // login failed
    }

    public static boolean createUser(String name, String username, String password, String role) {
        String sql = "INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
