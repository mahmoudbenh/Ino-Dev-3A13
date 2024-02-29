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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Livraison;
import services.ServiceLivraison;

public class ModifierLivraison implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label idModif1;

    @FXML
    private DatePicker tdDate;

    @FXML
    private TextField tfAdressLiv;

    @FXML
    private TextField tfIdC;
    @FXML
    private TextField tfFrais;
    @FXML
    private TextField tfIdLiv;

    @FXML
    private ComboBox<String> tfSL;

    @FXML
    private TextField tfidLi;

    @FXML
    void RrvenirAjouter(ActionEvent event) {

    }
    @FXML
    void modifierLiv(ActionEvent event) {
        try {
            int idLiv = Integer.parseInt(tfIdLiv.getText());
            int idLivreur = Integer.parseInt(tfidLi.getText());
            int idC = Integer.parseInt(tfIdC.getText());
            LocalDate dateLiv = tdDate.getValue();
            String statut = tfSL.getValue();
            String AdressLiv = tfAdressLiv.getText();
            float frais = Float.parseFloat(tfFrais.getText());

            Livraison livraison = new Livraison(idLiv, idLivreur, idC,dateLiv, AdressLiv,statut, frais);

            // Livraison livraison = new Livraison(idLiv, idLivreur,idC,LocalDate dateLiv, statut, frais);
            ServiceLivraison serviceLivraison = new ServiceLivraison();
            serviceLivraison.updateOne(livraison);
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
        }
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowLivraison.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setLivraison(Livraison livraison) {
        // this.livraison = livraison;
        //afficherDetailsLivraison() ;
    }

    @FXML
    void initialize() {
        assert idModif1 != null : "fx:id=\"idModif1\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";
        assert tdDate != null : "fx:id=\"tdDate\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";
        assert tfAdressLiv != null : "fx:id=\"tfAdressLiv\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";
        assert tfIdC != null : "fx:id=\"tfIdC\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";
        assert tfIdLiv != null : "fx:id=\"tfIdLiv\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";
        assert tfSL != null : "fx:id=\"tfSL\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";
        assert tfidLi != null : "fx:id=\"tfidLi\" was not injected: check your FXML file 'ModifierLivraison.fxml'.";

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfSL.setItems(FXCollections.observableArrayList("en cours de traitement","expediee","annulation"));

    }


}
