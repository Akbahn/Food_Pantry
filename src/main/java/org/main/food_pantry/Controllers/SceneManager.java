package org.main.food_pantry.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager
{
    public static void switchScene(Stage stage, String fxmlPath)
    {
        try {
            URL resource = SceneManager.class.getResource(fxmlPath);
            if (resource == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



