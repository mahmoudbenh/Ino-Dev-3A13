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
            int eventsPerRow = 4; // Maximum number of events per row
            int count = 0; // Counter for events in the current row
            HBox currentRow = new HBox(); // Initialize a new row
            currentRow.setSpacing(10); // Set spacing between event boxes

            for (Event event : events) {
                // Create a new VBox for each event
                VBox eventBox = new VBox();

                // Apply styles to the event box
                eventBox.setStyle("-fx-padding: 20px; -fx-border-radius: 10px; -fx-background-color: #f7f8fa; -fx-border-color: #5dade2; ");
                String imagePath = "file:///C:/Users/Linda/OneDrive/Desktop/3A/projet%20PI%20DEV/gestion%20event/src/main/resources/assets/event.png";
                // Create an ImageView for the image (assuming imagePath is the path to the image)
                ImageView imageView = new ImageView(new Image(imagePath));
                imageView.setFitWidth(100); // Set the width of the image
                imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image

                // Create a VBox to hold the text information
                VBox textInfoBox = new VBox();
                textInfoBox.setSpacing(5); // Adjust spacing between labels

                // Add text information for the event
                Label nameLabel = new Label("Name: " + event.getName());
                Label descriptionLabel = new Label("Description: " + event.getDescription());
                Label dateLabel = new Label("Date: " + event.getDate_event());
                Label statusLabel = new Label("Status: " + event.getStatut());
                Label lieuLabel = new Label("Location: " + event.getLieu());

                // Add text labels to the text information VBox
                textInfoBox.getChildren().addAll(nameLabel, descriptionLabel, dateLabel, statusLabel, lieuLabel);

                // Apply CSS styles to text labels
                nameLabel.getStyleClass().add("event-label");
                descriptionLabel.getStyleClass().add("event-label");
                dateLabel.getStyleClass().add("event-label");
                statusLabel.getStyleClass().add("event-label");
                lieuLabel.getStyleClass().add("event-label");

                // Add the image and text information to the event box
                eventBox.getChildren().addAll(imageView, textInfoBox);

                // Add the event VBox to the current row
                currentRow.getChildren().add(eventBox);
                count++;

                // Check if the maximum number of events per row is reached
                if (count == eventsPerRow) {
                    // Add the current row to the eventContainer
                    eventContainer.getChildren().add(currentRow);

                    // Reset count and create a new row
                    count = 0;
                    currentRow = new HBox();
                    currentRow.setSpacing(10); // Set spacing between event boxes
                }
            }

            // Check if there are remaining events in the current row
            if (count > 0) {
                // Add the current row to the eventContainer
                eventContainer.getChildren().add(currentRow);
            }
        }
    }





}

