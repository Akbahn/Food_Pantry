package org.main.food_pantry;

import java.sql.*;

public class Database {

    private static final String MYSQL_SERVER_URL = "jdbc:mysql://food-pantry-db.mysql.database.azure.com:3306/";
    private static final String DB_NAME = "food_pantry";
    private static final String DB_URL = MYSQL_SERVER_URL + DB_NAME + "?useSSL=true";
    private static final String USERNAME = "akbahn";
    private static final String PASSWORD = "Hyder1000";

    // Global connection method used in all other classes
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    // Initial setup method called once at app start
    public static boolean initializeDatabase() {
        boolean hasUsers = false;

        try {
            // Step 1: Connect to server and create database if not exists
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL + "?useSSL=true", USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            statement.close();
            conn.close();
            System.out.println("✅ Database created or already exists.");

            // Step 2: Connect to the new DB
            conn = getConnection();
            statement = conn.createStatement();

            // Step 3: Create tables
            String createUsers = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(100) NOT NULL,"
                    + "username VARCHAR(100) NOT NULL UNIQUE,"
                    + "password VARCHAR(100) NOT NULL,"
                    + "role ENUM('Student','Volunteer','Admin') NOT NULL"
                    + ")";
            statement.executeUpdate(createUsers);

            String createFoodItems = "CREATE TABLE IF NOT EXISTS food_items ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(100) NOT NULL,"
                    + "category VARCHAR(50),"
                    + "quantity INT NOT NULL,"
                    + "expiration_date DATE,"
                    + "description VARCHAR(255)"
                    + ")";
            statement.executeUpdate(createFoodItems);

            String createRequests = "CREATE TABLE IF NOT EXISTS requests ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "user_id INT NOT NULL,"
                    + "food_id INT NOT NULL,"
                    + "quantity_requested INT DEFAULT 1,"
                    + "status ENUM('Pending','Approved','Denied') DEFAULT 'Pending',"
                    + "request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,"
                    + "FOREIGN KEY (food_id) REFERENCES food_items(id) ON DELETE CASCADE"
                    + ")";
            statement.executeUpdate(createRequests);

            System.out.println("✅ All tables created or already exist.");

            // Step 4: Check if users exist
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            if (resultSet.next()) {
                hasUsers = resultSet.getInt(1) > 0;
            }

            resultSet.close();
            statement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Setup failed:");
            e.printStackTrace();
        }

        return hasUsers;
    }

    // Optional main method to trigger setup manually
    public static void main(String[] args) {
        boolean hasUsers = initializeDatabase();
        System.out.println("✅ Setup complete. Users exist? " + hasUsers);
    }
}
