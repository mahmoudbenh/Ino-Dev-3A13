package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Event;
import models.Participant;
import services.Mail;
import services.QRCodeService;
import services.ServiceEvent;
import services.ServiceParticipant;
import javafx.scene.image.Image;

public class CardDynamic extends Node {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label date;
    @FXML
    private Button fx_participer;

    @FXML
    private Label description;

    @FXML
    private Label id_event;

    @FXML
    private Label lieu;

    @FXML
    private Label nom_event;

    @FXML
    private Label statut;
    private Event event;
    private Event eventToParticipate;

    private ServiceEvent se = new ServiceEvent();
     @FXML
    private ImageView imageview;
private final  ServiceParticipant serviceParticipant=new ServiceParticipant();

     private int  getLoggedInUser () {return 20;}



    @FXML
    public void setData(Event event){
        this.id_event = id_event;
        id_event.setText(String.valueOf(event.getId_event()));
        nom_event.setText(event.getName());
        description.setText(event.getDescription());
        date.setText(String.valueOf(event.getDate_event()));
        statut.setText(event.getStatut());
        lieu.setText(event.getLieu());

        try {
            // Load the image from the API using the image URL
            Image image = new Image(event.getImage_event());
            imageview.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }

        this.eventToParticipate = event;
    }


    @FXML
    void Participer(ActionEvent event) {
           /* try {
                Parent root = FXMLLoader.load(getClass().getResource("/InfoParticipant.fxml"));
                fx_participer.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }*/
       /* try {
            // Retrieve the user ID of the currently logged-in user (replace with actual user ID retrieval logic)
            int userID = getLoggedInUser(); // Example user ID

            // Retrieve the event ID associated with this card
            int eventID = eventToParticipate.getId_event();

            // Insert a new record into the pivot table
             se.insertParticipant(userID, eventID);

            // Show success message
            afficherMessage("Succès", "Vous vous êtes inscrit avec succès à l'événement.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'inscription à l'événement.");
        }*/
        try {
            // Retrieve the user ID of the currently logged-in user (replace with actual user ID retrieval logic)
            int userID = getLoggedInUser(); // Example user ID

            // Retrieve the event ID associated with this card
            int eventID = eventToParticipate.getId_event();

            // Check if the user is already registered for the event
            boolean isRegistered = se.isParticipantRegistered(userID, eventID);

            if (isRegistered) {
                // Show message indicating the user is already registered
                afficherMessage("Info", "Vous êtes déjà inscrit à cet événement.", Alert.AlertType.INFORMATION);
            } else {
                // Insert a new record into the pivot table
                se.insertParticipant(userID, eventID);
                afficherMessage("Success", "Vous vous êtes inscrit avec succès à l'événement.", Alert.AlertType.INFORMATION);
                Participant p=serviceParticipant. getParticipantByUserId( getLoggedInUser ());
                System.out.println(p);        Mail.sendEmail(p.getEmail(),p);
            }

            // Generate QR code data
            String qrData = generateQRCodeData(eventToParticipate, userID);

            // Generate QR code image
            String qrImagePath = QRCodeService.generateQRCode(qrData);


            // Open a new window to display the QR code
            displayQRCode(qrImagePath);

        } catch (Exception e) {
            e.printStackTrace();
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'inscription à l'événement.");
        }
    }

    private void displayQRCode(String qrImagePath) {
        // Create a new stage
        Stage stage = new Stage();

        // Create an ImageView to display the QR code image
        ImageView imageView = new ImageView(new Image("file:" + qrImagePath));

        // Create a VBox to hold the ImageView
        VBox vbox = new VBox(imageView);
        vbox.setAlignment(Pos.CENTER);

        // Create a scene and set it on the stage
        Scene scene = new Scene(vbox);
        stage.setScene(scene);

        // Set the title of the stage
        stage.setTitle("QR Code");

        // Show the stage
        stage.show();
    }

    private String generateQRCodeData(Event event, int userID) {
         Participant participant= new Participant();
        try {
             participant = serviceParticipant.getParticipantByUserId(userID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String qrData = "Nom de l'évènement: " + event.getName() + "\n";
        qrData += "Localisation: " + event.getLieu() + "\n";
        qrData += "Nom: " + participant.getNom() + "\n"; // Include user's name
        // Add any other information as needed
        return qrData;
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }




    @FXML
    void initialize() {

    }

}
