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
            try
            {
                int id_livreur = Integer.parseInt(tfIdLivreur.getText());
                int Num_tel_livreur = Integer.parseInt(tftelL.getText());
                String Nom_livreur = tfNomLivreur.getText();
                String statut_livreur = (String) statutL.getValue();


                Livreur L = new Livreur(id_livreur,Nom_livreur,statut_livreur,Num_tel_livreur);
                ServiceLivreur sl = new ServiceLivreur();
                sl.insertOne(L);
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
