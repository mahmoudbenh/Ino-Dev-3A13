package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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

public class DetailLivraisonBase {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label DateLiv;

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
    @FXML
    private Label AdressLiv;

    @FXML
    private HBox HboxLiv;
    private Livraison Livrai;
    private Livraison LivToDelete;
    private ServiceLivraison sl = new ServiceLivraison();


   /* @FXML
    void Modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLivraison.fxml"));
            Parent root = loader.load();
            ModifierLivraison modifierArticleController = loader.getController();
            modifierArticleController.setLivraison(liv);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }

    }*/
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

   /* @FXML
    void Supprimer(ActionEvent event) throws SQLException {
      /*  try {
            if (LivToDelete != null) {
                // Perform deletion logic (replace this with your actual deletion code)
                System.out.println("Suppression de la livraison avec l'ID: " + LivToDelete.getId_livraison());

                // Call the deleteOne method of the serviceLivraison to delete the selected delivery
                sl.deleteOne(LivToDelete);

                // Close the current stage
                Stage stage = (Stage) idLivrai.getScene().getWindow();
                stage.close();
            } else {
                // Show a warning if no Livraison is set for deletion
                afficherMessage("Aucune sélection", "Veuillez sélectionner une livraison à supprimer.", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la livraison : " + e.getMessage());
        }*///@FXML
       // void Supprimer(ActionEvent event) throws SQLException {
          /*  if (LivToDelete != null) {
                // Perform deletion logic (replace this with your actual deletion code)
                System.out.println("Deleting livraison with ID: " + LivToDelete.getId_livraison());

                // Close the current stage
                Stage stage = (Stage) idLivrai.getScene().getWindow();
                sl.deleteOne(LivToDelete);
            } else {
                // Show a warning if no Comande is set for deletion
                afficherMessage("Aucune sélection", "Veuillez sélectionner une commande à supprimer.", Alert.AlertType.WARNING);
            }*/
           /* try {
                if (LivToDelete != null) {
                    // Perform deletion logic
                    sl.deleteOne(LivToDelete);

                    // Close the current stage
                    Stage stage = (Stage) idLivrai.getScene().getWindow();
                    stage.close();

                    // Refresh the displayed list of deliveries (assuming you have a method to do this)
                    // For example, if you have a method to reload the deliveries, you can call it here
                    refreshDeliveryList();
                } else {
                    // Show a warning if no Livraison is set for deletion
                    afficherMessage("Aucune sélection", "Veuillez sélectionner une livraison à supprimer.", Alert.AlertType.WARNING);
                }
            } catch (SQLException e) {
                // Handle SQL exceptions
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la livraison : " + e.getMessage());
            }
        }
*/
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

    //}

    public void setData(Livraison Livrai){
        this.Livrai = Livrai;
        idLivrai.setText(String.valueOf(Livrai.getId_livraison()));
        idC.setText(String.valueOf(Livrai.getId_commande()));
        idLiv.setText(String.valueOf(Livrai.getId_livreur()));
        AdressLiv.setText(Livrai.getAdresse_livraison());
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
        assert DateLiv != null : "fx:id=\"DateLiv\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
        assert StatutLiv != null : "fx:id=\"StatutLiv\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
        assert frais != null : "fx:id=\"frais\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
        assert idC != null : "fx:id=\"idC\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
        assert idLiv != null : "fx:id=\"idLiv\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
        assert idLivrai != null : "fx:id=\"idLivrai\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
        assert AdressLiv != null : "fx:id=\"AdressLiv\" was not injected: check your FXML file 'DetailLivraisonBase.fxml'.";
    }

}
