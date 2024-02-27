package controllers;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Livraison;
import services.ServiceLivraison;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowLivraison {

    @FXML
    private FlowPane LivContainer;

    private final ServiceLivraison serviceLivraison = new ServiceLivraison();

    @FXML
    void initialize() throws SQLException {
        List<Livraison> livraisons = serviceLivraison.selectAll();
        displayLiv(livraisons);
    }


    private void displayLiv(List<Livraison> livraisons) {
        for (Livraison livraison : livraisons) {
            VBox card = createLivraisonCard(livraison);
            LivContainer.getChildren().add(card);
        }
    }

    private VBox createLivraisonCard(Livraison livraison) {
        VBox card = new VBox();
        card.getStyleClass().add("Livraison_card");
        Label idLiv = new Label("idLiv: " + livraison.getId_livraison());
        Label idCommande = new Label("Id_commande: " + livraison.getId_commande());
        Label idLivreur = new Label("Id_livreur: " + livraison.getId_livreur());
        Label statutLiv = new Label("Statut_livraison: " + livraison.getStatus_livraison());
        Label adresseLiv = new Label("Adresse_livraison: " + livraison.getAdresse_livraison());
        Label fraisLiv = new Label("Frais_livraison: " + livraison.getFrais_livraison());

        card.getChildren().addAll(idLiv, idCommande, idLivreur, statutLiv, adresseLiv, fraisLiv);
        card.setOnMouseClicked(event -> openLivraisonDetailsPage(livraison));
        card.setCursor(Cursor.HAND);
        return card;
    }

    private void openLivraisonDetailsPage(Livraison livraison) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AfficherRestoDetails.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Livraison Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnAjouter(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ajoutreservation.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) LivContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Ajouter reservation");
        stage.show();
    }

    @FXML
    void OnRefresh(ActionEvent event) {
        try {
            ServiceLivraison serviceLivraison = new ServiceLivraison(); // Create an instance of ServiceLivraison
            List<Livraison> livraisons = serviceLivraison.selectAll(); // Call selectAll() on the instance
            LivContainer.getChildren().clear();
            displayLiv(livraisons);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

}
