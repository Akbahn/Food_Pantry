package org.main.food_pantry.Databases;

/**
 * Singleton-style utility to store the currently logged-in user's data.
 */
public class CurrentUser {
    private static String tempRole;

    public static void setTempRole(String selectedRole) {
        tempRole = selectedRole;
    }
    public static String getTempRole(){
        return tempRole;
    }
    public static void clearTempRole(){
        tempRole=null;
    }


    private static int id;
    private static String name;
    private static String username;
    private static String role;

    // Full setter for all fields
    public static void setUser(int userId, String userName, String userUsername, String userRole) {
        id = userId;
        name = userName;
        username = userUsername;
        role = userRole;
    }

    // Optional: setters for individual fields if needed
    public static void setId(int userId) {
        id = userId;
    }

    public static void setName(String userName) {
        name = userName;
    }

    public static void setUsername(String userUsername) {
        username = userUsername;
    }

    public static void setRole(String userRole) {
        role = userRole;
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

    // Optional: clear user on logout
    public static void clear() {
        id = 0;
        name = null;
        username = null;
        role = null;
    }
}
