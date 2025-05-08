package org.main.food_pantry.Models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class RequestRow {

    private final IntegerProperty requestId = new SimpleIntegerProperty();
    private final IntegerProperty studentId = new SimpleIntegerProperty();
    private final StringProperty studentName = new SimpleStringProperty();
    private final StringProperty foodName = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDate> requestDate = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();
    private final BooleanProperty fulfilled = new SimpleBooleanProperty(false);


    // Constructor
    public RequestRow(int requestId, int studentId, String studentName, String foodName, int quantity, LocalDate requestDate, String status) {
        this.requestId.set(requestId);
        this.studentId.set(studentId);
        this.studentName.set(studentName);
        this.foodName.set(foodName);
        this.quantity.set(quantity);
        this.requestDate.set(requestDate);
        this.status.set(status);
    }

    // Getters for TableView binding
    public int getRequestId() {
        return requestId.get();
    }

    public IntegerProperty requestIdProperty() {
        return requestId;
    }

    public int getStudentId() {
        return studentId.get();
    }

    public IntegerProperty studentIdProperty() {
        return studentId;
    }

    public String getStudent() {
        return studentName.get();
    }

    public StringProperty studentProperty() {
        return studentName;
    }

    public String getFoodName() {
        return foodName.get();
    }

    public StringProperty foodNameProperty() {
        return foodName;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public LocalDate getRequestDate() {
        return requestDate.get();
    }

    public ObjectProperty<LocalDate> requestDateProperty() {
        return requestDate;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public boolean isFulfilled() {
        return fulfilled.get();
    }

    public BooleanProperty fulfilledProperty() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled.set(fulfilled);
    }
}
