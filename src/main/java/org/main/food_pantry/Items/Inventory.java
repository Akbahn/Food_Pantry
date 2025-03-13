package org.main.food_pantry.Items;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Food> items = new ArrayList<>();

    public void addFood(Food food) {
        items.add(food);
    }

    public void displayInventory() {
        for (Food item : items) {
            System.out.println(item.getName() + " - " + item.getQuantity());
        }
    }
}
