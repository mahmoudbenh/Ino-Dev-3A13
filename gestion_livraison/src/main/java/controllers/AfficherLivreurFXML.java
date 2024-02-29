package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Livreur;
import services.ServiceLivreur;

public class AfficherLivreurFXML {

    private final ServiceLivreur sl = new ServiceLivreur();
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Label SelectedHotel;

        @FXML
        private ListView<Livreur> listeView;

        @FXML
        private Text myText;

        @FXML
        void Ajouter(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AjouterLivreurFXML.fxml"));
                myText.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }

        @FXML
        void boutonSupprimer(ActionEvent event) {
            Livreur livreurSupprimer = listeView.getSelectionModel().getSelectedItem();

            if (livreurSupprimer != null) {
                // Supprimer livreur de la base de données et de la liste affichée
                try {
                    sl.deleteOne(livreurSupprimer);
                    listeView.getItems().remove(livreurSupprimer);

                    // Afficher un message de confirmation
                    afficherMessage("Suppression réussie", "  suppression avec succès.", Alert.AlertType.INFORMATION);
                } catch (SQLException e) {
                    // Afficher un message d'erreur en cas d'échec de la suppression
                    afficherMessage("Erreur", "Erreur lors de la suppression  : " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                // Si aucun hôtel n'est sélectionné, afficher un message d'avertissement
                afficherMessage("Aucune sélection", "Veuillez sélectionner un livreur à supprimer.", Alert.AlertType.WARNING);
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
            Livreur selectedLivreur = listeView.getSelectionModel().getSelectedItem();
            if (selectedLivreur != null) {

                switchToUpdatePage(selectedLivreur);
            } else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un livreur à modifier.");
                alert.showAndWait();
                ToModifierListeLivreur(); ;
            }

        }

    private void ToModifierListeLivreur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLiv.fxml"));
            Parent root = loader.load();
            ModifierLivcontroller ModifierController = loader.getController();
            Scene pageScene = new Scene(root);

            Stage stage = (Stage) myText.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void switchToUpdatePage(Livreur livreur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLiv.fxml"));
            Parent root = loader.load();

            ModifierLivcontroller modifierArticleController = loader.getController();
            modifierArticleController.setLivreur(livreur);

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
        void initialize() {
            assert SelectedHotel != null : "fx:id=\"SelectedHotel\" was not injected: check your FXML file 'AfficherLivreurFXML.fxml'.";
            assert listeView != null : "fx:id=\"listeView\" was not injected: check your FXML file 'AfficherLivreurFXML.fxml'.";
            assert myText != null : "fx:id=\"myText\" was not injected: check your FXML file 'AfficherLivreurFXML.fxml'.";
        try {
            // Récupère la liste des hôtels depuis le service
            List<Livreur> livreurs = sl.selectAll();

            // Crée une cellule personnalisée pour la ListView
            listeView.setCellFactory(param -> new ListCell<Livreur>() {
                @Override
               protected void updateItem(Livreur livreur, boolean empty) {
                    super.updateItem(livreur, empty);

                    if (empty || livreur == null) {
                        // Si la cellule est vide ou l'objet est null, ne rien afficher
                        setText(null);
                    } else {
                      /*  // Affiche les valeurs des attributs
                        setText(livreur.getId_livreur() + " - " +
                               ""+ livreur.getNom_livreur() + " - " +
                                "" +livreur.getStatut_livreur() + " - " +
                                ""+livreur.getNum_tel_livreur());*/
                        setText(livreur.getId_livreur() + " - " +
                                livreur.getNom_livreur() + " - " +
                                livreur.getStatut_livreur() + " - " +
                                livreur.getNum_tel_livreur()) ;


                    }
                }
            });



            listeView.getItems().addAll(livreurs);


            // Ajoute un gestionnaire d'événements pour gérer les clics sur les éléments de la liste
            listeView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Livreur selectedItem = listeView.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        switchToUpdatePage(selectedItem);
                    }
                }
            });
        } catch (SQLException e) {
            // Affiche une alerte en cas d'erreur
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la récupération des livreurs: " + e.getMessage());
        }
        }

    }



