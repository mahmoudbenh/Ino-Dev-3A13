package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Participant;
import services.ServiceParticipant;

public class InfoParticipant {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ID_event;

    @FXML
    private TextField UserID;

    @FXML
    private TextField fx_mail;

    @FXML
    private TextField fx_nom;

    @FXML
    private TextField fx_tel;

    @FXML
    private Button retour;

    @FXML
    void Annuler(ActionEvent event) {
            UserID.setText(null);
            ID_event.setText(null);
            fx_mail.setText(null);
            fx_nom.setText(null);
            fx_tel.setText(null);
            UserID.requestFocus();
        }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+");
    }


    @FXML
    void Participer(ActionEvent event) {
        try {
            int userId = Integer.parseInt(UserID.getText());
            int id_event = Integer.parseInt(ID_event.getText());
            String nom = fx_nom.getText();
            String email = fx_mail.getText();
            int tel = Integer.parseInt(fx_tel.getText());


            if (UserID.getText().isEmpty() ||  fx_nom.getText().isEmpty() || fx_mail.getText().isEmpty() || fx_tel.getText().isEmpty()) {
                throw new IllegalArgumentException("Tous les champs sont requis");
            }
            if (UserID.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez saisir l'ID du participant");}
            else if (!isNumeric(UserID.getText())) {
                throw new IllegalArgumentException("UserID doit être numérique");
            }

            if (fx_tel.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez saisir le numéro de téléphone du participant");
            } else if (!fx_tel.getText().matches("\\d{8}")) {
                throw new IllegalArgumentException("Le numéro de téléphone doit être composé uniquement de 8 chiffres");
            }

            Participant nouveauParticipant = new Participant(userId,id_event, nom, email, tel);

            ServiceParticipant serviceParticipant = new ServiceParticipant();
            serviceParticipant.insertOne(nouveauParticipant);

            showAlert(Alert.AlertType.INFORMATION, "Confirmation", null, "Votre Participation est bien enregistre dans la base de donnees . Merci pour votre confiance  !");
            UserID.clear();
            ID_event.clear();
            fx_nom.clear();
            fx_mail.clear();
            fx_tel.clear();
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            showErrorAlert(e.getMessage());
        } catch (SQLException e) {
            // Handle SQL exception
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
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML

    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DisplayUser.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }}
    @FXML
    void initialize() {
        assert UserID != null : "fx:id=\"UserID\" was not injected: check your FXML file 'InfoParticipant.fxml'.";
        assert fx_mail != null : "fx:id=\"fx_mail\" was not injected: check your FXML file 'InfoParticipant.fxml'.";
        assert fx_nom != null : "fx:id=\"fx_nom\" was not injected: check your FXML file 'InfoParticipant.fxml'.";
        assert fx_tel != null : "fx:id=\"fx_tel\" was not injected: check your FXML file 'InfoParticipant.fxml'.";
        assert retour != null : "fx:id=\"retour\" was not injected: check your FXML file 'InfoParticipant.fxml'.";

    }

}
