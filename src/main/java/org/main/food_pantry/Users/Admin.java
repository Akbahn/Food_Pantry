package org.main.food_pantry.Users;

public class Admin extends User {
    public Admin(int id, String name, String username, String password) {
        super(name, username, password, "Admin");
        this.id = id;
    }

    public void displayMenu() {
        System.out.println("1. Manage Users\n2. View Reports");
    }
}
