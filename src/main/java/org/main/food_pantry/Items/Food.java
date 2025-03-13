package org.main.food_pantry.Items;

public class Food {
    private String name;
    private int quantity;

    public Food(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
}
