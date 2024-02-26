package controller;
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
import model.Comande;
import service.ServiceComande;

public class AjouterComandeFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> LStatutC2;

    @FXML
    private DatePicker tdDate;

    @FXML
    private TextField tfIdCommande;

    @FXML
    private TextField tfIdLc;

    @FXML
    private TextField tfPrixT;

    @FXML
    void AfficherCommande(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/AfficherCommandeFXML.fxml");

            if (fxmlUrl != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                Parent root = fxmlLoader.load();
                tfIdCommande.getScene().setRoot(root);
            } else {
                System.err.println("FXML file not found.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void AjouterCommande(ActionEvent event) {
        try
        {
            //int idCommande = Integer.parseInt(tfIdCommande.getText());
            int idCommande = Integer.parseInt(tfIdCommande.getText());
            int idLc = Integer.parseInt(tfIdLc.getText());
            LocalDate dateCommande = tdDate.getValue();
            String dateString = dateCommande.toString();
            String statut = LStatutC2.getValue();
            //LocalDate dateCommande = tdDate.getValue();
            double prix = Integer.parseInt(tfPrixT.getText());
            Comande c = new Comande(idCommande,idLc,dateString,statut,prix);
            ServiceComande sc = new ServiceComande();
            sc.insertOne(c);
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
        assert LStatutC2 != null : "fx:id=\"LStatutC2\" was not injected: check your FXML file 'AjouterComandeFXML.fxml'.";
        assert tdDate != null : "fx:id=\"tdDate\" was not injected: check your FXML file 'AjouterComandeFXML.fxml'.";
        assert tfIdCommande != null : "fx:id=\"tfIdCommande\" was not injected: check your FXML file 'AjouterComandeFXML.fxml'.";
        assert tfIdLc != null : "fx:id=\"tfIdLc\" was not injected: check your FXML file 'AjouterComandeFXML.fxml'.";
        assert tfPrixT != null : "fx:id=\"tfPrixT\" was not injected: check your FXML file 'AjouterComandeFXML.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LStatutC2.setItems(FXCollections.observableArrayList("en cours de traitement","expediee","annulation"));

    }
}
