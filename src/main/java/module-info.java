module org.main.food_pantry {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.main.food_pantry to javafx.fxml;
    exports org.main.food_pantry;
}