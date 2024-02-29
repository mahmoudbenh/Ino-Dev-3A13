package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Livraison;
import services.ServiceLivraison;

public class ShowLivraison implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox CardLayout;
    @FXML
    private TextField idrecher;



    @FXML
    private VBox LivraisonContainer;

    private ServiceLivraison ServLiv = new ServiceLivraison();

    private VBox createLivraisonCard(Livraison livr) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: black; -fx-padding: 10;");

        Label idLiv = new Label("id_livraison " + livr.getId_livraison());
        Label idCommande = new Label("id_commande " + livr.getId_commande());
        Label idLivreur = new Label("id_livreur " + livr.getId_livreur());
        Label DateLivr = new Label("Date_livraison " + livr.getDate_livraison());
        Label AdresseLiv = new Label(" Adressse_livraison" + livr.getAdresse_livraison());
        Label statutLiv = new Label("Statut_livraison" + livr.getStatus_livraison());
        Label fraisLiv = new Label(" frais_livraison" + livr.getFrais_livraison());

        card.getChildren().addAll(idLiv, idCommande, idLivreur, DateLivr, AdresseLiv, statutLiv, fraisLiv);

        return card;
    }

    private void displayLivraison(List<Livraison> Livraisons) {
        for (Livraison liv : Livraisons) {
            VBox card = createLivraisonCard(liv);
            //LivraisonContainer.getChildren().add(card);
          //  oenProductDetailsPage(commande);
            openLivraisonDetailsPage(liv);

        }
    }
    private void openLivraisonDetailsPage(Livraison liv) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DetailLivraisonBaseFXML.fxml"));
            Parent root = fxmlLoader.load();

            DetailLivraisonBaseFXML controller = fxmlLoader.getController();
            controller.setData(liv);

            // Instead of creating a new stage and scene, add the root to the existing container
            LivraisonContainer.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier FXML", e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            assert CardLayout != null : "fx:id=\"CardLayout\" was not injected: check your FXML file 'ShowLivraison.fxml'.";
            assert LivraisonContainer != null : "fx:id=\"LivraisonContainer\" was not injected: check your FXML file 'ShowLivraison.fxml'.";
        List<Livraison> livraisons;
        try {
            livraisons = ServLiv.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayLivraison(livraisons);
    }
    @FXML
    void rechercher(ActionEvent event) throws SQLException {
        // Récupérer le mot-clé de recherche saisi par l'utilisateur
        String keyword = idrecher.getText().toLowerCase().trim();

        // Récupérer la liste complète des livraisons depuis le service ServLiv
        List<Livraison> livraisons = ServLiv.selectAll();

        // Créer une liste filtrée à partir de la liste des livraisons
        FilteredList<Livraison> filteredLivraison = new FilteredList<>(FXCollections.observableArrayList(livraisons));

        // Appliquer le filtre en fonction du mot-clé de recherche
        filteredLivraison.setPredicate(livraison -> LivraisonContainsKeyword(livraison, keyword));

        // Afficher les livraisons filtrées
        displayLivraison(filteredLivraison);
    }

    private boolean LivraisonContainsKeyword(Livraison liv, String keyword) {
        // Vérifier si l'ID de la livraison contient le mot-clé
        return String.valueOf(liv.getId_livraison()).contains(keyword);
    }


}
