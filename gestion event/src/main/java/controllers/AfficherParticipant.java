package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Participant;
import services.ServiceParticipant;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class AfficherParticipant {

    @FXML
    private FlowPane participantContainer;


    public void initialize() throws SQLException {
        // Create an instance of ServiceParticipant
        ServiceParticipant serviceParticipant = new ServiceParticipant();

        // Fetch Participants from the database
        List<Participant> participants = serviceParticipant.selectAll();

        // Populate the container with Participant boxes
        createParticipantBoxes(participants);
    }

    private void createParticipantBoxes(List<Participant> participants) {
        if (participants != null) {
            int participantsPerRow = 5; // Maximum number of Participants per row
            int count = 0; // Counter for Participants in the current row
            HBox currentRow = new HBox(); // Initialize a new row
            currentRow.setSpacing(10); // Set spacing between Participant boxes

            for (Participant participant : participants) {
                // Create a new VBox for each Participant
                VBox participantBox = new VBox();
                participantBox.setSpacing(10);

                // Set a unique ID for the Participant box using the Participant's ID
                participantBox.setId("Participant-box-" + participant.getUserID());

                // Apply styles to the Participant box
                participantBox.setStyle("-fx-padding: 20px; -fx-border-radius: 10px; -fx-background-color: #f7f8fa; -fx-border-color: #5dade2; ");

                // Create a VBox to hold the text information
                VBox textInfoBox = new VBox();
                textInfoBox.setSpacing(5); // Adjust spacing between labels

                // Add text information for the Participant
                Label idULabel = new Label("User ID: " + participant.getUserID()); // Display ID
                Label idELabele = new Label("ID evenement: " + participant.getId_event());
                Label nomLabel = new Label("Nom du participant: " + participant.getNom());
                Label emailLabel = new Label("Email du participant: " + participant.getEmail());
                Label telLabel = new Label("Tel du participant: " + participant.getTel());

                // Add text labels to the text information VBox
                textInfoBox.getChildren().addAll(idULabel, idELabele, nomLabel, emailLabel, telLabel);

                // Apply CSS styles to text labels
                idULabel.getStyleClass().add("Participant-label");
                idELabele.getStyleClass().add("Participant-label");
                nomLabel.getStyleClass().add("Participant-label");
                emailLabel.getStyleClass().add("Participant-label");
                telLabel.getStyleClass().add("Participant-label");

                Button updateButton = new Button("Update");
                updateButton.getStyleClass().add("button-style"); // Add style class to update button
                updateButton.setOnAction(e -> {
                    openUpdateWindow(participant); // Pass the participant
                });

                // Create an HBox to hold the button
                HBox buttonBox = new HBox(updateButton);
                buttonBox.setSpacing(10);

                // Add text information VBox and button HBox to the Participant VBox
                participantBox.getChildren().addAll(textInfoBox, buttonBox);

                // Add the Participant VBox to the current row
                currentRow.getChildren().add(participantBox);
                count++;

                // Check if the maximum number of Participants per row is reached
                if (count == participantsPerRow) {
                    // Add the current row to the ParticipantContainer
                    participantContainer.getChildren().add(currentRow);

                    // Reset count and create a new row
                    count = 0;
                    currentRow = new HBox();
                    currentRow.setSpacing(10); // Set spacing between Participant boxes
                }
            }

            // Check if there are remaining Participants in the current row
            if (count > 0) {
                // Add the current row to the ParticipantContainer
                participantContainer.getChildren().add(currentRow);
            }
        }
    }

    public void openUpdateWindow(Participant participant) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Participant");

        VBox updateLayout = new VBox();
        updateLayout.setSpacing(10);
        updateLayout.setPadding(new Insets(10));

        TextField userIdField = new TextField(String.valueOf(participant.getUserID()));
        TextField eventIdField = new TextField(String.valueOf(participant.getId_event()));
        TextField nomField = new TextField(participant.getNom());
        TextField emailField = new TextField(participant.getEmail());
        TextField telField = new TextField(String.valueOf(participant.getTel()));

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                ServiceParticipant serviceParticipant = new ServiceParticipant();
                participant.setUserID(Integer.parseInt(userIdField.getText()));
                participant.setId_event(Integer.parseInt(eventIdField.getText()));
                participant.setNom(nomField.getText());
                participant.setEmail(emailField.getText());
                participant.setTel(Integer.parseInt(telField.getText()));

                serviceParticipant.updateOne(participant);
                updateStage.close();
                participantContainer.getChildren().clear(); // Clear the container
                initialize(); // Re-populate the container
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle exception...
            }
        });

        updateLayout.getChildren().addAll(userIdField, eventIdField, nomField, emailField, telField, updateButton);

        Scene updateScene = new Scene(updateLayout, 600, 300);
        updateStage.setScene(updateScene);
        updateStage.show();
    }
}

