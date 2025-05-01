package org.main.food_pantry.Users;


/**
 * Utility class to store the currently logged-in user's data.
 * This data can be accessed from anywhere in the app.
 */
public class CurrentUser {
    private static int id;
    private static String name;
    private static String username;
    private static String role; // Optional: "Student", "Volunteer", "Admin"

    // Setters
    public static void setUser(int userId, String userName, String userUsername, String userRole) {
        id = userId;
        name = userName;
        username = userUsername;
        role = userRole;
    }

    public static void clearUser() {
        id = 0;
        name = null;
        username = null;
        role = null;
    }

    // Getters
    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return id > 0;
    }
}
