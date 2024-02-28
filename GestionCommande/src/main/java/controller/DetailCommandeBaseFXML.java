package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Comande;
import service.ServiceComande;

public class DetailCommandeBaseFXML extends Node {

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
    private Comande commandeToDelete;
    private ServiceComande sc = new ServiceComande();

    public void setData(Comande comande){
        this.commande = commande;
        idcommande.setText(String.valueOf(comande.getId_commande()));
        datecommande.setText(comande.getDate_commande());
        statutcommande.setText(comande.getStatut_commande());
        prixT.setText(String.valueOf(comande.getPrix_total()));
        this.commandeToDelete = comande;
    }
    @FXML
    void Modifier(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommandeFXML.fxml"));
            Parent root = loader.load();
            ModifierCommandeFXML modifierArticleController = loader.getController();
            modifierArticleController.setCommande(commande);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }
    }
    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void Supprimer(ActionEvent event) throws SQLException {
        if (commandeToDelete != null) {
            // Perform deletion logic (replace this with your actual deletion code)
            System.out.println("Deleting Comande with ID: " + commandeToDelete.getId_commande());

            // Close the current stage
            Stage stage = (Stage) idcommande.getScene().getWindow();
            sc.deleteOne(commandeToDelete);
        } else {
            // Show a warning if no Comande is set for deletion
            afficherMessage("Aucune sélection", "Veuillez sélectionner une commande à supprimer.", Alert.AlertType.WARNING);
        }
    }
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert datecommande != null : "fx:id=\"datecommande\" was not injected: check your FXML file 'DetailCommandeBaseFXML.fxml'.";
        assert idcommande != null : "fx:id=\"idcommande\" was not injected: check your FXML file 'DetailCommandeBaseFXML.fxml'.";
        assert prixT != null : "fx:id=\"prixT\" was not injected: check your FXML file 'DetailCommandeBaseFXML.fxml'.";
        assert statutcommande != null : "fx:id=\"statutcommande\" was not injected: check your FXML file 'DetailCommandeBaseFXML.fxml'.";

    }

}
