package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Comande;
import model.Produit;
import service.ServiceComande;

public class AfficherCommUserFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox CardLayout;

    @FXML
    private VBox LastCommande;
    private List<Produit> recentlyAdded;

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
    @FXML
    void initialize() {
        try {
            assert CardLayout != null : "fx:id=\"CardLayout\" was not injected: check your FXML file 'AfficherCommUserFXML.fxml'.";
            assert LastCommande != null : "fx:id=\"LastCommande\" was not injected: check your FXML file 'AfficherCommUserFXML.fxml'.";
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
            ServiceComande serviceComande = new ServiceComande();

            // Call the method to get the last command
            Comande lastCommand = serviceComande.getDerniereCommande();

            if (lastCommand != null) {
                // Assuming you have a method to create a UI element for a Comande
                openProductDetailsPage(lastCommand);
            } else {
                // Handle the case where no command is found
                System.out.println("No command found.");
            }

            System.out.println("Initialize method completed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'initialisation : " + e.getMessage());

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DetailCommandeBaseUserFXML.fxml"));
            Parent root = fxmlLoader.load();

            DetailCommandeBaseUserFXML controller = fxmlLoader.getController();
            controller.setData(commande);

            // Instead of creating a new stage and scene, add the root to the existing container
            LastCommande.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier FXML", e);
        }
    }
}
