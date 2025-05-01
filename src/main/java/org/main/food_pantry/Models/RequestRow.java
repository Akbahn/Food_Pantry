package org.main.food_pantry.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class RequestRow {
    private final IntegerProperty requestId;
    private final StringProperty studentName;
    private final StringProperty foodName;
    private final IntegerProperty quantity;
    private final ObjectProperty<LocalDate> requestDate;
    private final StringProperty status;

    public RequestRow(int requestId, String studentName, String foodName, int quantity, LocalDate requestDate, String status) {
        this.requestId = new SimpleIntegerProperty(requestId);
        this.studentName = new SimpleStringProperty(studentName);
        this.foodName = new SimpleStringProperty(foodName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.requestDate = new SimpleObjectProperty<>(requestDate);
        this.status = new SimpleStringProperty(status);
    }

    public int getRequestId() { return requestId.get(); }
    public String getStudentName() { return studentName.get(); }
    public String getFoodName() { return foodName.get(); }
    public int getQuantity() { return quantity.get(); }
    public LocalDate getRequestDate() { return requestDate.get(); }
    public String getStatus() { return status.get(); }

    public IntegerProperty requestIdProperty() { return requestId; }
    public StringProperty studentNameProperty() { return studentName; }
    public StringProperty foodNameProperty() { return foodName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public ObjectProperty<LocalDate> requestDateProperty() { return requestDate; }
    public StringProperty statusProperty() { return status; }
}
