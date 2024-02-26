package controllers;


import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Participant;
import services.ServiceParticipant;

import java.sql.SQLException;
import java.util.List;

public class AfficherParticipant {

    @FXML
    private FlowPane ParticipantContainer;

    public void initialize() throws SQLException {
        // Create an instance of ServiceParticipant
        ServiceParticipant serviceParticipant = new ServiceParticipant();

        // Fetch Participants from the database
        List<Participant> Participants = serviceParticipant.selectAll();

        // Populate the container with Participant boxes
        createParticipantBoxes(Participants);
    }

    private void createParticipantBoxes(List<Participant> Participants) {
        if (Participants != null) {
            int ParticipantsPerRow = 5;// Maximum number of Participants per row
            int count = 0; // Counter for Participants in the current row
            HBox currentRow = new HBox(); // Initialize a new row
            currentRow.setSpacing(10); // Set spacing between Participant boxes

            for (Participant Participant : Participants) {
                // Create a new VBox for each Participant
                VBox ParticipantBox = new VBox();
                ParticipantBox.setSpacing(10);

                // Set a unique ID for the Participant box using the Participant's ID
                ParticipantBox.setId("Participant-box-" + Participant.getUserID());

                // Apply styles to the Participant box
                ParticipantBox.setStyle("-fx-padding: 20px; -fx-border-radius: 10px; -fx-background-color: #f7f8fa; -fx-border-color: #5dade2; ");

                // Create a VBox to hold the text information
                VBox textInfoBox = new VBox();
                textInfoBox.setSpacing(5); // Adjust spacing between labels

                // Add text information for the Participant
                Label idULabel = new Label("User ID: " + Participant.getUserID()); // Display ID
                Label idELabele = new Label("ID evenement: " + Participant.getId_event());
                Label nomLabel = new Label("Nom du participant: " + Participant.getNom());
                Label emailLabel = new Label("Email du participant: " + Participant.getEmail());
                Label telLabel = new Label("Tel du participant: " + Participant.getTel());

                // Add text labels to the text information VBox
                textInfoBox.getChildren().addAll(idULabel, idELabele,  nomLabel,  emailLabel, telLabel);

                // Apply CSS styles to text labels
                idULabel.getStyleClass().add("Participant-label");
                idELabele.getStyleClass().add("Participant-label");
                nomLabel.getStyleClass().add("Participant-label");
                emailLabel.getStyleClass().add("Participant-label");
                telLabel.getStyleClass().add("Participant-label");

                // Create buttons for the Participant
               /* Button updateButton = new Button("Update");
                updateButton.setOnAction(e -> {
                    openUpdateWindow(Participant, ParticipantContainer); // Pass the ParticipantContainer
                });*/
                // Create buttons for the Participant
                Button updateButton = new Button("Update");
                updateButton.getStyleClass().add("button-style"); // Add style class to update button
                updateButton.setOnAction(e -> {
                    openUpdateWindow(Participant, ParticipantContainer); // Pass the ParticipantContainer
                });

                Button postponeButton = new Button("Postpone");
                postponeButton.getStyleClass().add("button-style"); // Add style class to postpone button
                postponeButton.setOnAction(e -> {
                    try {
                        // Get the Participant ID
                        int ParticipantId = Participant.getId_Participant();

                        // Update status to 'not done'
                        ServiceParticipant.updateStatusToNotDone(ParticipantId);
                    } catch (SQLException ex) {
                        System.out.println("Error updating status: " + ex.getMessage());
                    }
                });


               /* Button postponeButton = new Button("Postpone");
                postponeButton.setOnAction(e -> {
                    try {
                        // Get the Participant ID
                        int ParticipantId = Participant.getId_Participant();

                        // Update status to 'not done'
                        ServiceParticipant.updateStatusToNotDone(ParticipantId);
                    } catch (SQLException ex) {
                        System.out.println("Error updating status: " + ex.getMessage());
                    }
                });*/

                // Create an HBox to hold the buttons
                HBox buttonBox = new HBox(updateButton, postponeButton);
                buttonBox.setSpacing(10);

                // Add text information VBox and button HBox to the Participant VBox
                ParticipantBox.getChildren().addAll(textInfoBox, buttonBox);

                // Add the Participant VBox to the current row
                currentRow.getChildren().add(ParticipantBox);
                count++;

                // Check if the maximum number of Participants per row is reached
                if (count == ParticipantsPerRow) {
                    // Add the current row to the ParticipantContainer
                    ParticipantContainer.getChildren().add(currentRow);

                    // Reset count and create a new row
                    count = 0;
                    currentRow = new HBox();
                    currentRow.setSpacing(10); // Set spacing between Participant boxes
                }
            }

            // Check if there are remaining Participants in the current row
            if (count > 0) {
                // Add the current row to the ParticipantContainer
                ParticipantContainer.getChildren().add(currentRow);
            }
        }
    }


    public void openUpdateWindow(Participant Participant, FlowPane ParticipantContainer) {
        Stage updateStage = new Stage();
        updateStage.setTitle("Update Participant");

        VBox updateLayout = new VBox();
        updateLayout.setSpacing(10);
        updateLayout.setPadding(new Insets(10));

        TextField nameField = new TextField(Participant.getName());
        TextField descriptionField = new TextField(Participant.getDescription());
        DatePicker dateField = new DatePicker(Participant.getDate_Participant());
        TextField statusField = new TextField(Participant.getStatut());
        TextField lieuField = new TextField(Participant.getLieu());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            Participant.setName(nameField.getText());
            Participant.setDescription(descriptionField.getText());
            Participant.setDate_Participant(dateField.getValue());
            Participant.setStatut(statusField.getText());
            Participant.setLieu(lieuField.getText());

            try {
                ServiceParticipant.updateParticipant(Participant);
                updateStage.close();
                ParticipantContainer.getChildren().clear(); // Clear the container
                initialize(); // Re-populate the container
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle exception...
            }
        });

        updateLayout.getChildren().addAll(nameField, descriptionField, dateField, statusField, lieuField, updateButton);

        Scene updateScene = new Scene(updateLayout, 600,300);
        updateStage.setScene(updateScene);
        updateStage.show();
    }
}


