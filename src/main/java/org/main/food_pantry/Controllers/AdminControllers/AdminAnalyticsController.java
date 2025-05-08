package org.main.food_pantry.Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.main.food_pantry.Databases.FoodDAO;
import org.main.food_pantry.Databases.RequestDAO;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdminAnalyticsController {

    @FXML
    private BarChart<String, Number> foodChart;

    @FXML
    private BarChart<String, Number> userChart;

    @FXML
    public void initialize() {
        loadPopularFoods();
        loadTopRequesters();
    }

    private void loadPopularFoods() {
        Map<String, Integer> foodData = RequestDAO.getMostRequestedFoods();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Food Requests");

        for (Map.Entry<String, Integer> entry : foodData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        foodChart.getData().clear();
        foodChart.getData().add(series);
    }

    private void loadTopRequesters() {
        Map<String, Integer> requesterData = RequestDAO.getTopRequestingStudents();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Students");

        for (Map.Entry<String, Integer> entry : requesterData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        userChart.getData().clear();
        userChart.getData().add(series);
    }
}
