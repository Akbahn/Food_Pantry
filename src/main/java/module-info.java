module org.main.food_pantry {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.main.food_pantry to javafx.fxml;
    exports org.main.food_pantry;
    exports org.main.food_pantry.Controllers;
    opens org.main.food_pantry.Controllers to javafx.fxml;
    exports org.main.food_pantry.Users;
    opens org.main.food_pantry.Users to javafx.fxml;
    exports org.main.food_pantry.Items;
    opens org.main.food_pantry.Items to javafx.fxml;
}