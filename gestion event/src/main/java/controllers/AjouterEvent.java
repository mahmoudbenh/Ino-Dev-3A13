package controllers;

        import javafx.event.ActionEvent;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.control.*;
        import services.ServiceEvent;
        import models.Event;
        import java.net.URL;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;




public class AjouterEvent {


    @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private DatePicker fx_date;

        @FXML
        private TextField fx_description;

        @FXML
        private TextField fx_lieu;

        @FXML
        private ComboBox<String> fx_statut;



        @FXML
        private TextField fxname;




    @FXML
    void AjouterEvent(ActionEvent event) {
        try {
            // Check if statut is selected
            String statut = fx_statut.getValue();
            if (statut == null || statut.isEmpty()) {
                throw new IllegalArgumentException("Statut is not selected");
            }

            // No need to parse event ID as it's auto-incremented
            String eventName = fxname.getText();
            String eventDescription = fx_description.getText();
            LocalDate eventDate = fx_date.getValue();
            String eventLieu = fx_lieu.getText();

            // Validate other fields if necessary (e.g., not empty)

            // Create new Event object
            Event nouvelEvent = new Event(0, eventName, eventDescription, eventDate, statut, eventLieu);

            // Insert event into the database
            ServiceEvent serviceEvent = new ServiceEvent();
            serviceEvent.insertOne(nouvelEvent);

            // Confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Événement ajouté avec succès !");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Handle null statut or any other validation errors
            showErrorAlert(e.getMessage());
        } catch (SQLException e) {
            // Handle SQL exception
            showErrorAlert("Erreur lors de l'ajout de l'événement. Veuillez réessayer plus tard.");
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
    void Afficher(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/AfficherEvent.fxml");

            if (fxmlUrl != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                Parent root = fxmlLoader.load();
                fx_description.getScene().setRoot(root);
            } else {
                System.err.println("FXML file not found.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }





    @FXML
    void Annuler(ActionEvent event) {
        // Nettoyage des champs
        fxname.setText(null);
        fx_description.setText(null);
        fx_date.setValue(null);
        fx_statut.setValue(null);
        fx_lieu.setText(null);
        // Remise du focus sur le premier champ de texte (fxid_event)
        fxname.requestFocus();
    }



    @FXML
    void initialize() {
        assert fx_date != null : "fx:id=\"fx_date\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert fx_description != null : "fx:id=\"fx_description\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert fx_lieu != null : "fx:id=\"fx_lieu\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert fx_statut != null : "fx:id=\"fx_statut\" was not injected: check your FXML file 'AjouterEvent.fxml'.";
        assert fxname != null : "fx:id=\"fxname\" was not injected: check your FXML file 'AjouterEvent.fxml'.";

    }



    }







