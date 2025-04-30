package org.main.food_pantry.Models;

public class Request {
    private String foodName;
    private int quantityRequested;
    private String status;
    private String requestDate;

    public Request(String foodName, int quantityRequested, String status, String requestDate) {
        this.foodName = foodName;
        this.quantityRequested = quantityRequested;
        this.status = status;
        this.requestDate = requestDate;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQuantityRequested() {
        return quantityRequested;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestDate() {
        return requestDate;
    }
}
