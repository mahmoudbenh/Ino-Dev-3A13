package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Livraison;
import services.ServiceLivraison;


public class AddLivraison implements Initializable {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField Adresse_livraison;

        @FXML
        private DatePicker Date_livraison;

        @FXML
        private TextField frais_livraison;

        @FXML
        private TextField id_commande;

        @FXML
        private ComboBox<String> statutL;

        @FXML
        private TextField tfid_liv;

        @FXML
        private TextField tfid_livreur;

        @FXML
        void AfficherLivraison(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/ShowLivraison.fxml"));
                tfid_liv.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        @FXML
        void AjouterLIvraison(ActionEvent event) {
            try
            {  int id_livraison = Integer.parseInt(tfid_liv.getText());
                int idCommande = Integer.parseInt(id_commande.getText());
                int id_livreur = Integer.parseInt(tfid_livreur.getText());
                LocalDate dateLivraison =  Date_livraison.getValue();
                float Fraislivraison = Float.parseFloat(frais_livraison.getText());
                String AdresseLivraison =  Adresse_livraison.getText();
                String statut_livreur = (String) statutL.getValue();


                Livraison Liv = new Livraison(id_livraison,id_livreur,idCommande,dateLivraison,AdresseLivraison,statut_livreur,Fraislivraison);
                ServiceLivraison SL = new ServiceLivraison();
                SL.insertOne(Liv);
            }catch (SQLException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL ERROR");
                alert.show();
            }
            catch (NumberFormatException e)
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NUMBER FORMAT EXCEPTON");
                alert.show();
            }

        }

        @FXML
        void AnuulerLivraison(ActionEvent event) {

        }

        @FXML
        void initialize() {
            assert Adresse_livraison != null : "fx:id=\"Adresse_livraison\" was not injected: check your FXML file 'AddLivraison.fxml'.";
            assert Date_livraison != null : "fx:id=\"Date_livraison\" was not injected: check your FXML file 'AddLivraison.fxml'.";
            assert frais_livraison != null : "fx:id=\"frais_livraison\" was not injected: check your FXML file 'AddLivraison.fxml'.";
            assert id_commande != null : "fx:id=\"id_commande\" was not injected: check your FXML file 'AddLivraison.fxml'.";
            assert statutL != null : "fx:id=\"statutL\" was not injected: check your FXML file 'AddLivraison.fxml'.";
            assert tfid_liv != null : "fx:id=\"tfid_liv\" was not injected: check your FXML file 'AddLivraison.fxml'.";
            assert tfid_livreur != null : "fx:id=\"tfid_livreur\" was not injected: check your FXML file 'AddLivraison.fxml'.";

        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statutL.setItems(FXCollections.observableArrayList("en cours ","expediee","annulation"));
    }

    }


