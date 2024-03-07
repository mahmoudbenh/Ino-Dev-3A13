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
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }
    @FXML
    void AjouterParticipant(ActionEvent event) {
        try {
            int userId = Integer.parseInt(fx_UserID.getText());
            int eventId = Integer.parseInt(fx_IDevent.getText());
            String nom = fx_Nom.getText();
            String email = Fx_email.getText();
            int tel = Integer.parseInt(fx_tel.getText());


            if (fx_UserID.getText().isEmpty() || fx_IDevent.getText().isEmpty() || fx_Nom.getText().isEmpty() || Fx_email.getText().isEmpty() || fx_tel.getText().isEmpty()) {
                throw new IllegalArgumentException("Tous les champs sont requis");
            }
            if (fx_UserID.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez saisir l'ID du participant");}
            else if (!isNumeric(fx_UserID.getText())) {
                throw new IllegalArgumentException("UserID doit être numérique");
            }
            if (fx_IDevent.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez saisir l'ID de l'evenement");
            } else if  (!isNumeric(fx_IDevent.getText())) {
                throw new IllegalArgumentException("ID de l'événement doit être numérique");
            }

            if (fx_tel.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez saisir le numéro de téléphone du participant");
            } else if (!fx_tel.getText().matches("\\d{8}")) {
                throw new IllegalArgumentException("Le numéro de téléphone doit être composé uniquement de 8 chiffres");
            }

            Participant nouveauParticipant = new Participant(userId, eventId, nom, email, tel);
            ServiceParticipant serviceParticipant = new ServiceParticipant();
            serviceParticipant.insertOne(nouveauParticipant);
            // Envoi de l'e-mail au participant ajouté avec succès
         /*   GMailer mailer = new GMailer();
            mailer.sendNewsletterMail(email, "Nom de l'événement");*/

            // Confirmation message
            showAlert(Alert.AlertType.INFORMATION, "Confirmation", null, "Participant ajouté avec succès !");
            // Confirmation message
           /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Participant  ajouté avec succès !");
            alert.showAndWait();*/

            fx_UserID.clear();
            fx_IDevent.clear();
            fx_Nom.clear();
            Fx_email.clear();
            fx_tel.clear();

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            showErrorAlert(e.getMessage());
        } catch (SQLException e) {
            // Handle SQL exception
            showErrorAlert("Erreur lors de l'ajout du participant. Veuillez réessayer plus tard.");
        }

          /*  Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        }*/




      /*  } catch (IllegalArgumentException e) {
            // Handle validation errors
            showErrorAlert(e.getMessage());
        } catch (SQLException e) {
            // Handle SQL exception
            showErrorAlert("Erreur lors de l'ajout du participant. Veuillez réessayer plus tard.");
        }*/
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
       /* try {
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
        }*/
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherParticipant.fxml"));
            fx_retour.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


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

    }

}
