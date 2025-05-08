package org.main.food_pantry.Controllers.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InfoBoardEditorController {

    @FXML
    private TextArea announcementArea;

    @FXML
    private TextArea hoursArea;

    @FXML
    private Label saveStatusLabel;

    private final String FILE_PATH = "info-board.txt";

    @FXML
    public void initialize() {
        loadInfo();
    }

    @FXML
    private void handleSave() {
        String announcements = announcementArea.getText().trim();
        String hours = hoursArea.getText().trim();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("[ANNOUNCEMENT]\n");
            writer.write(announcements + "\n");
            writer.write("[HOURS]\n");
            writer.write(hours + "\n");
            saveStatusLabel.setText("✅ Changes saved!");
        } catch (IOException e) {
            saveStatusLabel.setText("❌ Failed to save.");
            e.printStackTrace();
        }
    }

    private void loadInfo() {
        if (!Files.exists(Paths.get(FILE_PATH))) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder announcements = new StringBuilder();
            StringBuilder hours = new StringBuilder();
            String line;
            boolean isHours = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("[ANNOUNCEMENT]")) {
                    isHours = false;
                } else if (line.equals("[HOURS]")) {
                    isHours = true;
                } else {
                    if (isHours) {
                        hours.append(line).append("\n");
                    } else {
                        announcements.append(line).append("\n");
                    }
                }
            }

            announcementArea.setText(announcements.toString().trim());
            hoursArea.setText(hours.toString().trim());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
