package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Event;
import services.ServiceEvent;

import java.sql.SQLException;
import java.util.List;

public class AfficherEvent {

    @FXML
    private FlowPane eventContainer;

    public void initialize() throws SQLException {
        ServiceEvent serviceEvent = new ServiceEvent();
        List<Event> events = serviceEvent.selectAll();
        createEventBoxes(events);
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

                // Set a unique ID for the event box using the event's ID
                eventBox.setId("event-box-" + event.getId_event());

                // Apply styles to the event box
                eventBox.setStyle("-fx-padding: 20px; -fx-border-radius: 10px; -fx-background-color: #f7f8fa; -fx-border-color: #5dade2; ");

                // Create a VBox to hold the text information
                VBox textInfoBox = new VBox();
                textInfoBox.setSpacing(5); // Adjust spacing between labels

                // Add text information for the event
                Label idLabel = new Label("ID: " + event.getId_event()); // Display ID
                Label nameLabel = new Label("Name: " + event.getName());
                Label descriptionLabel = new Label("Description: " + event.getDescription());
                Label dateLabel = new Label("Date: " + event.getDate_event());
                Label statusLabel = new Label("Status: " + event.getStatut());
                Label lieuLabel = new Label("Location: " + event.getLieu());

                // Add text labels to the text information VBox
                textInfoBox.getChildren().addAll(idLabel, nameLabel, descriptionLabel, dateLabel, statusLabel, lieuLabel);

                // Apply CSS styles to text labels
                idLabel.getStyleClass().add("event-label");
                nameLabel.getStyleClass().add("event-label");
                descriptionLabel.getStyleClass().add("event-label");
                dateLabel.getStyleClass().add("event-label");
                statusLabel.getStyleClass().add("event-label");
                lieuLabel.getStyleClass().add("event-label");

                // Create buttons for the event
                Button updateButton = new Button("Update");
                updateButton.setOnAction(e -> {
                    openUpdateWindow(event, eventContainer); // Pass the eventContainer
                });

                Button postponeButton = new Button("Postpone");
                postponeButton.setOnAction(e -> {
                    try {
                        // Get the event ID
                        int eventId = event.getId_event();

                        // Update status to 'not done'
                        ServiceEvent.updateStatusToNotDone(eventId);
                    } catch (SQLException ex) {
                        System.out.println("Error updating status: " + ex.getMessage());
                    }
                });

                // Create an HBox to hold the buttons
                HBox buttonBox = new HBox(updateButton, postponeButton);
                buttonBox.setSpacing(10);

                // Add text information VBox and button HBox to the event VBox
                eventBox.getChildren().addAll(textInfoBox, buttonBox);

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


    public void openUpdateWindow(Event event, FlowPane eventContainer) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Event");

        VBox updateLayout = new VBox();
        updateLayout.setSpacing(10);
        updateLayout.setPadding(new Insets(10));

        TextField nameField = new TextField(event.getName());
        TextField descriptionField = new TextField(event.getDescription());
        DatePicker dateField = new DatePicker(event.getDate_event());
        TextField statusField = new TextField(event.getStatut());
        TextField lieuField = new TextField(event.getLieu());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            event.setName(nameField.getText());
            event.setDescription(descriptionField.getText());
            event.setDate_event(dateField.getValue());
            event.setStatut(statusField.getText());
            event.setLieu(lieuField.getText());

            try {
                ServiceEvent.updateEvent(event);
                updateStage.close();
                eventContainer.getChildren().clear(); // Clear the container
                initialize(); // Re-populate the container
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle exception...
            }
        });

        updateLayout.getChildren().addAll(nameField, descriptionField, dateField, statusField, lieuField, updateButton);

        Scene updateScene = new Scene(updateLayout, 300, 200);
        updateStage.setScene(updateScene);
        updateStage.show();
    }
}

