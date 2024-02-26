package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Event;
import services.ServiceEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.util.List;

public class AfficherUser {


    @FXML
    private FlowPane eventContainer;

    public void initialize() throws SQLException {
        // Create an instance of ServiceEvent
        ServiceEvent serviceEvent = new ServiceEvent();

        // Fetch events from the database
        List<Event> events = serviceEvent.selectAll();

        // Populate the container with event boxes
        createEventBoxes(events);
        eventContainer.setPadding(new Insets(10)); // Set padding between rows

    }

    private void createEventBoxes(List<Event> events) {
        if (events != null) {
            HBox currentRow = new HBox(10); // Initialize and set spacing in one line

            for (Event event : events) {
                VBox eventBox = new VBox();
                eventBox.getStyleClass().add("event-box");

                String imagePath = "file:///path/to/your/image.png"; // Make sure this path is correct
                ImageView imageView = new ImageView(new Image(imagePath));
                imageView.getStyleClass().add("event-image");

                VBox textInfoBox = new VBox();
                textInfoBox.getStyleClass().add("event-info-box");

                Label nameLabel = new Label("Name: " + event.getName());
                Label descriptionLabel = new Label("Description: " + event.getDescription());
                // ... Add other labels
                nameLabel.getStyleClass().add("event-label");
                descriptionLabel.getStyleClass().add("event-label");
                // ... Add classes to other labels

                textInfoBox.getChildren().addAll(nameLabel, descriptionLabel /*, other labels*/);
                eventBox.getChildren().addAll(imageView, textInfoBox);
                currentRow.getChildren().add(eventBox);

                // Check and handle row logic as before
            }

            // Check and handle remaining events as before
        }
    }





}

