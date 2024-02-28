package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Participant;
import services.ServiceParticipant;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;



public class AjouterParticipant {

    @FXML
    private TextField Fx_email;

    @FXML
    private TextField fx_IDevent;

    @FXML
    private TextField fx_Nom;

    @FXML
    private TextField fx_UserID;

    @FXML
    private TextField fx_tel;

    @FXML
    private Button fx_retour;
    private Button retourButton;

    @FXML
    void AjouterParticipant(ActionEvent event) {
        try {
            int userId = Integer.parseInt(fx_UserID.getText());
            int eventId = Integer.parseInt(fx_IDevent.getText());
            String nom = fx_Nom.getText();
            String email = Fx_email.getText();
            int tel = Integer.parseInt(fx_tel.getText());

            // Validate input fields if necessary

            Participant nouveauParticipant = new Participant(userId, eventId, nom, email, tel);

            ServiceParticipant serviceParticipant = new ServiceParticipant();
            serviceParticipant.insertOne(nouveauParticipant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Participant ajouté avec succès !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            showErrorAlert("Veuillez saisir des valeurs numériques valides pour l'ID utilisateur, l'ID de l'événement et le téléphone.");
        } catch (IllegalArgumentException e) {
            showErrorAlert(e.getMessage());
        } catch (SQLException e) {
            showErrorAlert("Erreur lors de l'ajout du participant. Veuillez réessayer plus tard.");
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void Annuler(ActionEvent event) {
        // Nettoyage des champs
        fx_UserID.setText(null); // Champ correspondant à UserID
        fx_IDevent.setText(null); // Champ correspondant à id_event
        fx_Nom.setText(null); // Pour le champ nom
        Fx_email.setText(null); // Pour le champ email
        fx_tel.setText(null); // Pour le champ téléphone
        // Remise du focus sur le premier champ de texte (fxname)
        fx_UserID.requestFocus();
    }

    @FXML
    void AfficherParticipant(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/AfficherParticipant.fxml");

            if (fxmlUrl != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                Parent root = fxmlLoader.load();
                fx_UserID.getScene().setRoot(root);
            } else {
                System.err.println("FXML file not found.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


  /*  @FXML
    void retour(ActionEvent event) {
        try {
            // Load the AjouterEvent view from FXML
            URL fxmlUrl = getClass().getResource("/WelcomeAdmin.fxml");

            if (fxmlUrl != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                Parent root = fxmlLoader.load();

                // Get the stage from the current scene and set the root to AjouterEvent view
                Stage stage = (Stage) retourButton.getScene().getWindow();
                stage.getScene().setRoot(root);
            } else {
                System.err.println("FXML file not found.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }*/
  @FXML

  void retour(ActionEvent event) {
      try {
        Parent root = FXMLLoader.load(getClass().getResource("/WelcomeAdmin.fxml"));
        fx_retour.getScene().setRoot(root);
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }}




    @FXML
    void initialize() {
        assert Fx_email != null : "fx:id=\"fx_email\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert fx_IDevent != null : "fx:id=\"fx_IDevent\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert fx_Nom != null : "fx:id=\"fx_Nom\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert fx_UserID != null : "fx:id=\"fx_UserID\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
        assert fx_tel != null : "fx:id=\"fx_tel\" was not injected: check your FXML file 'AjouterParticipant.fxml'.";
    }

}
