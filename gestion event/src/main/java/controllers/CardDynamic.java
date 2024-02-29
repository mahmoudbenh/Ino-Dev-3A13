package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Event;
import services.ServiceEvent;
import services.ServiceParticipant;

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
    public void setData(Event event){
        this.id_event = id_event;
        id_event.setText(String.valueOf(event.getId_event()));
        nom_event.setText(event.getName());
        description.setText(event.getDescription());
        date.setText(String.valueOf(event.getDate_event()));
        statut.setText(event.getStatut());
        lieu.setText(event.getLieu());
        this.eventToParticipate = event;
    }


    @FXML
    void Participer(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/InfoParticipant.fxml"));
                fx_participer.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }


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
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'CardDynamic.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'CardDynamic.fxml'.";
        assert fx_participer != null : "fx:id=\"fx_participer\" was not injected: check your FXML file 'CardDynamic.fxml'.";
        assert id_event != null : "fx:id=\"id_event\" was not injected: check your FXML file 'CardDynamic.fxml'.";
        assert lieu != null : "fx:id=\"lieu\" was not injected: check your FXML file 'CardDynamic.fxml'.";
        assert nom_event != null : "fx:id=\"nom_event\" was not injected: check your FXML file 'CardDynamic.fxml'.";
        assert statut != null : "fx:id=\"statut\" was not injected: check your FXML file 'CardDynamic.fxml'.";

    }

}
