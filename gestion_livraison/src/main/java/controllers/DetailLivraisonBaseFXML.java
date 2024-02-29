package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Livraison;
import services.ServiceLivraison;

import java.io.IOException;
import java.sql.SQLException;

public class DetailLivraisonBaseFXML {




        @FXML
        private Label AdressLiv;

        @FXML
        private Label DateLiv;

        @FXML
        private HBox HboxLiv;

        @FXML
        private Label StatutLiv;

        @FXML
        private Label frais;

        @FXML
        private Label idC;

        @FXML
        private Label idLiv;

        @FXML
        private Label idLivrai;
    private Livraison Livrai;
    private Livraison LivToDelete;
    private ServiceLivraison sl = new ServiceLivraison();

        @FXML
        void Modifier(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLivraison.fxml"));
                Parent root = loader.load();
                ModifierLivraison modifierLivraisonController = loader.getController();
                modifierLivraisonController.setLivraison(Livrai); // Utilise Livrai au lieu de liv

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
            }

        }

        @FXML
        void Supprimer(ActionEvent event) {
            try {
                if (LivToDelete != null) {
                    // Perform deletion logic
                    sl.deleteOne(LivToDelete);

                    // Clear the LivToDelete reference to indicate that no delivery is selected
                    LivToDelete = null;

                    // Refresh the displayed list of deliveries (assuming you have a method to do this)
                    // For example, if you have a method to reload the deliveries, you can call it here
                    // refreshDeliveryList();

                    // Show a message to indicate successful deletion
                    afficherMessage("Suppression réussie", "La livraison a été supprimée avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    // Show a warning if no Livraison is set for deletion
                    afficherMessage("Aucune sélection", "Veuillez sélectionner une livraison à supprimer.", Alert.AlertType.WARNING);
                }
            } catch (SQLException e) {
                // Handle SQL exceptions
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la livraison : " + e.getMessage());
            }
            refreshDeliveryList();
        }
    private void refreshDeliveryList() {

        idLivrai.setText("");
        idC.setText("");
        idLiv.setText("");
        AdressLiv.setText("");
        DateLiv.setText("");
        StatutLiv.setText("");
        frais.setText("");
        HboxLiv.getChildren().clear();


    }
    public void setData(Livraison Livrai){
        this.Livrai = Livrai;
        idLivrai.setText(String.valueOf(Livrai.getId_livraison()));
        idC.setText(String.valueOf(Livrai.getId_commande()));
        idLiv.setText(String.valueOf(Livrai.getId_livreur()));
        AdressLiv.setText(Livrai.getAdresse_livraison());
        DateLiv.setText(String.valueOf(Livrai.getDate_livraison()));
        StatutLiv.setText(Livrai.getStatus_livraison());
        frais.setText(String.valueOf(Livrai.getFrais_livraison()));

        this.LivToDelete = Livrai;
    }
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    @FXML
    void initialize() {
        assert AdressLiv != null : "fx:id=\"AdressLiv\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert DateLiv != null : "fx:id=\"DateLiv\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert HboxLiv != null : "fx:id=\"HboxLiv\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert StatutLiv != null : "fx:id=\"StatutLiv\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert frais != null : "fx:id=\"frais\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert idC != null : "fx:id=\"idC\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert idLiv != null : "fx:id=\"idLiv\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";
        assert idLivrai != null : "fx:id=\"idLivrai\" was not injected: check your FXML file 'DetailLivraisonBaseFXML.fxml'.";

    }
}
