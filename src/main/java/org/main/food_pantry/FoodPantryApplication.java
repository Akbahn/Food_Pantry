package org.main.food_pantry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.main.food_pantry.Databases.Database;

public class FoodPantryApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        //test
        // Initialize the database first
        Database.initializeDatabase();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main/food_pantry/splash-page.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Food Pantry Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
