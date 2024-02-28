package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Comande;
import service.ServiceComande;

public class DetailCommandeBaseUserFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label datecommande;

    @FXML
    private Label idcommande;

    @FXML
    private Label prixT;

    @FXML
    private Label statutcommande;
    private Comande commande;
    private ServiceComande sc = new ServiceComande();

    public void setData(Comande comande){
        this.commande = commande;
        idcommande.setText(String.valueOf(comande.getId_commande()));
        datecommande.setText(comande.getDate_commande());
        statutcommande.setText(comande.getStatut_commande());
        prixT.setText(String.valueOf(comande.getPrix_total()));
    }
    @FXML
    void initialize() {
        assert datecommande != null : "fx:id=\"datecommande\" was not injected: check your FXML file 'DetailCommandeBaseUserFXML.fxml'.";
        assert idcommande != null : "fx:id=\"idcommande\" was not injected: check your FXML file 'DetailCommandeBaseUserFXML.fxml'.";
        assert prixT != null : "fx:id=\"prixT\" was not injected: check your FXML file 'DetailCommandeBaseUserFXML.fxml'.";
        assert statutcommande != null : "fx:id=\"statutcommande\" was not injected: check your FXML file 'DetailCommandeBaseUserFXML.fxml'.";

    }

}
