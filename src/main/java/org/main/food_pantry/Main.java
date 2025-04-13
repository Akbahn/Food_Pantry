package org.main.food_pantry;

import org.main.food_pantry.Users.Student;
import org.main.food_pantry.Users.User;
import org.main.food_pantry.Users.UserDAO;

public class Main {
    public static void main(String[] args) {
        User newStudent = new Student(0, "Alice Johnson", "alice123", "alicepass");
        int id = UserDAO.registerUser(newStudent);
        System.out.println("Student registered with ID: " + id);

        User loggedIn = UserDAO.loginUser("alice123", "alicepass");
        if (loggedIn != null) {
            System.out.println("Logged in as " + loggedIn.getRole() + ": " + loggedIn.getName());
        } else {
            System.out.println("Login failed.");
        }
    }
}
