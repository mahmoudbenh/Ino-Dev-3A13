package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Comande;
import model.Produit;
import service.ServiceComande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherCommandeFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox CardLayout;
    @FXML
    private VBox CommandeContainer;

    private List<Produit> recentlyAdded;
    private List<Comande> listcommande;
    private ServiceComande serviceCommande = new ServiceComande();



    private List<Produit> recentlyAdded(){
        List<Produit> lp= new ArrayList<>();
        Produit produit= new Produit();
        produit.setNom("LOGICIEL IA APPLE");
        produit.setDescription("LOGICIEL IA APPLE");
        produit.setImage("/img/p2.jpg");
        lp.add(produit);
        produit= new Produit();
        produit.setNom("ROBOT IA");
        produit.setDescription("ROBOT IA");
        produit.setImage("/img/p1.jpg");
        lp.add(produit);
        produit= new Produit();
        produit.setNom("CAMERA");
        produit.setDescription("CAMERA");
        produit.setImage("/img/p3.jpg");
        lp.add(produit);
        return lp;
    }
    private void displayCommandes(List<Comande> commandes) {
        for (Comande commande : commandes) {
            VBox card = createCommandeCard(commande);
            openProductDetailsPage(commande);

        }
    }
    private VBox createCommandeCard(Comande commande) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: black; -fx-padding: 10;");

        Label idLabel = new Label("ID: " + commande.getId_commande());
        Label dateLabel = new Label("Date: " + commande.getDate_commande());
        Label statutLabel = new Label("Statut: " + commande.getStatut_commande());
        Label prixLabel = new Label("Prix Total: " + commande.getPrix_total());

        card.getChildren().addAll(idLabel, dateLabel, statutLabel, prixLabel);

        // Add any other UI elements as needed


        return card;
    }
    private void openProductDetailsPage(Comande commande) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DetailCommandeBaseFXML.fxml"));
            Parent root = fxmlLoader.load();

            DetailCommandeBaseFXML controller = fxmlLoader.getController();
            controller.setData(commande);

            // Instead of creating a new stage and scene, add the root to the existing container
            CommandeContainer.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier FXML", e);
        }
    }

    @FXML
    void initialize(){
        assert CardLayout != null : "fx:id=\"CardLayout\" was not injected: check your FXML file 'AfficherCommandeFXML.fxml'.";
        assert CommandeContainer != null : "fx:id=\"CommandeContainer\" was not injected: check your FXML file 'AfficherCommandeFXML.fxml'.";

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/DetailCommandeFXML.fxml"));
                HBox cardBox = fxmlLoader.load();

                DetailCommandeFXML detailCommandeFXML = fxmlLoader.getController();
                detailCommandeFXML.setDta(recentlyAdded.get(i));

                CardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Comande> commandes;
        try {
            commandes = serviceCommande.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayCommandes(commandes);

    }

}
