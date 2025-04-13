package org.main.food_pantry.Items;

import java.time.LocalDate;

public class Food {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private LocalDate expiration_date;
    private String description;

    public Food(int id, String name, String category, int quantity, LocalDate expirationDate, String description){
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.expiration_date = expirationDate;
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
