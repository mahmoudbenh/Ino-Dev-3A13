package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import models.Livreur;
import services.ServiceLivreur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class AjouterLivreurFXML implements Initializable {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private ComboBox<String> statutL;

        @FXML
        private TextField tfIdLivreur;

       @FXML
       private TextField tfNomLivreur;

        @FXML
        private TextField tftelL;

        @FXML
        void AfficherLivreur(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherLivreurFXML.fxml"));
                tfIdLivreur.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }
    @FXML
    void AjouterLivreur(ActionEvent event) {
        // Vérifiez si les champs ne sont pas vides
        if (tfIdLivreur.getText().isEmpty() || tfNomLivreur.getText().isEmpty() || statutL.getValue().isEmpty() || tftelL.getText().isEmpty() ) {
            // Affichez une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            // Contrôle de saisie pour le numéro de téléphone
            String telLivreur = tftelL.getText();
            if (!telLivreur.matches("\\d{8}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le numéro de téléphone doit être un entier de 8 chiffres.");
                alert.showAndWait();
                return; // Sortir de la méthode si le numéro de téléphone est invalide
            }

            // Contrôle de saisie pour le nom du livreur
            String nomLivreur = tfNomLivreur.getText();
            if (!nomLivreur.matches("[a-zA-Z]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le nom du livreur ne doit contenir que des lettres.");
                alert.showAndWait();
                return; // Sortir de la méthode si le nom du livreur est invalide
            }

            // Tous les contrôles de saisie sont valides, procéder à l'ajout du livreur
            try {
                int id_livreur = Integer.parseInt(tfIdLivreur.getText());
                int Num_tel_livreur = Integer.parseInt(telLivreur); // Numéro de téléphone validé précédemment
                String Nom_livreur = nomLivreur; // Nom de livreur validé précédemment
                String statut_livreur = (String) statutL.getValue();

                Livreur L = new Livreur(id_livreur, Nom_livreur, statut_livreur, Num_tel_livreur);
                ServiceLivreur sl = new ServiceLivreur();
                sl.insertOne(L);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL ERROR");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NUMBER FORMAT EXCEPTION");
                alert.show();
            }
        }
    }




    @FXML
        void initialize() {
            assert statutL != null : "fx:id=\"statutL\" was not injected: check your FXML file 'AjouterLivreurFXML.fxml'.";
            assert tfIdLivreur != null : "fx:id=\"tfIdLivreur\" was not injected: check your FXML file 'AjouterLivreurFXML.fxml'.";
            assert tfNomLivreur != null : "fx:id=\"tfNomLivreur\" was not injected: check your FXML file 'AjouterLivreurFXML.fxml'.";
          //  assert tfNomLivreur != null : "fx:id=\"tfNomLivreur\" was not injected: check your FXML file 'AjouterLivreurFXML.fxml'.";
            assert tftelL != null : "fx:id=\"tftelL\" was not injected: check your FXML file 'AjouterLivreurFXML.fxml'.";
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statutL.setItems(FXCollections.observableArrayList("congée","travaille"));

    }
}
