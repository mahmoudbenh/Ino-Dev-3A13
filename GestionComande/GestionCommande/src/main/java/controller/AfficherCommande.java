/*package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Comande;
import service.ServiceComande;

public class AfficherCommande {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label SelectedHotel;

    @FXML
    private VBox commandeDetailsVBox;

    @FXML
    private ListView<Comande> listeView;

    @FXML
    private Text myText;
    private final ServiceComande sc = new ServiceComande();

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterComandeFXML.fxml"));
            myText.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void displayCommandes(List<Comande> comandes) {
        for (Comande comande : comandes) {
            VBox card = createCommandeCard(comande);
            commandeDetailsVBox.getChildren().add(card); // Add card to container

            // Add click event handler (optional)
            card.setOnMouseClicked(event -> {
                // Handle user click on card
                detailcommande(comande); // Display details here
            });
        }
    }
    private VBox createCommandeCard(Comande commande) {
        VBox card = new VBox();
        card.getStyleClass().add("commande-card");
        Label id_LigneCommande = new Label("ID Ligne de commande: " + commande.getId_lc());
        Label id_commande = new Label("ID commande: " + commande.getId_commande());
        Label dateCommandeLabel = new Label("Date Commande: " + commande.getDate_commande());
        Label statut_commande = new Label("Date Commande: " + commande.getStatut_commande());
        Label prix_Total = new Label("Date Commande: " + commande.getPrix_total());
        card.getChildren().addAll(id_LigneCommande, id_commande, dateCommandeLabel,statut_commande,prix_Total);
        card.setOnMouseClicked(event -> detailcommande(commande));
        return card;
    }


    private void detailcommande(Comande commande) {
        try {
            // Chargement du DetailCommandeFXML
            FXMLLoader detailCommandeLoader = new FXMLLoader(getClass().getResource("/DetailCommandeFXML.fxml"));
            Parent detailCommandeRoot = detailCommandeLoader.load();

            // Création d'une nouvelle scène et ajout de DetailCommandeRoot
            Stage stage = new Stage();
            stage.setScene(new Scene(detailCommandeRoot));

            // Récupération du contrôleur DetailCommandeFXML
            DetailCommandeFXML detailCommandeController = detailCommandeLoader.getController();

            // Affichage des détails de la commande
            detailCommandeController.displayCommandeDetails(commande);

            // Définition du titre de la fenêtre
            stage.setTitle("Commande Details");

            // Affichage de la fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void boutonSupprimer(ActionEvent event) {
        Comande commandeASupprimer = listeView.getSelectionModel().getSelectedItem();

        if (commandeASupprimer != null) {
            // Supprimer la commande de la base de données et de la liste affichée
            try {
                sc.deleteOne(commandeASupprimer);
                listeView.getItems().remove(commandeASupprimer);

                // Afficher un message de confirmation
                afficherMessage("Suppression réussie", "La commande a été supprimé avec succès.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                // Afficher un message d'erreur en cas d'échec de la suppression
                afficherMessage("Erreur", "Erreur lors de la suppression de la commande : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            // Si aucun hôtel n'est sélectionné, afficher un message d'avertissement
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
    void bt_modifier(ActionEvent event) {
        Comande selectedCommande = listeView.getSelectionModel().getSelectedItem();

        if (selectedCommande != null) {
            // L'objet Commande est sélectionné, vous pouvez maintenant l'utiliser comme vous le souhaitez
            switchToUpdatePage(selectedCommande);
        } else {
            // Aucune Commande sélectionné, afficher un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une commande à modifier.");
            alert.showAndWait();
            ToModifierListeCommande(); ;
        }
    }
    public void ToModifierListeCommande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommmandeFXML.fxml"));
            Parent root = loader.load();
            ModifierCommandeFXML ModifierController = loader.getController();

            Scene pageScene = new Scene(root);

            Stage stage = (Stage) myText.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ServiceComande serviceCommande = new ServiceComande();

    @FXML
    void initialize() throws SQLException {
        assert SelectedHotel != null : "fx:id=\"SelectedHotel\" was not injected: check your FXML file 'AfficherCommandeFXML.fxml'.";
        assert commandeDetailsVBox != null : "fx:id=\"commandeDetailsVBox\" was not injected: check your FXML file 'AfficherCommandeFXML.fxml'.";
        assert listeView != null : "fx:id=\"listeView\" was not injected: check your FXML file 'AfficherCommandeFXML.fxml'.";
        assert myText != null : "fx:id=\"myText\" was not injected: check your FXML file 'AfficherCommandeFXML.fxml'.";
        List<Comande> commandes = serviceCommande.selectAll();
        //displayCommandes(commandes);
        for (Comande commande : commandes) {
            VBox card = createCommandeCard(commande);
            commandeDetailsVBox.getChildren().add(card);
        }
    }
    private void switchToUpdatePage(Comande commande) {


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommandeFXML.fxml"));
            Parent root = loader.load();
            Comande selectedCommande = listeView.getSelectionModel().getSelectedItem();

            ModifierCommandeFXML modifierArticleController = loader.getController();
            modifierArticleController.setCommande(commande);
            modifierArticleController.setCommandeToModify(selectedCommande);

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

}*/
