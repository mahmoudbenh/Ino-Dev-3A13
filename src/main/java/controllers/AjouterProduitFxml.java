package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import models.Produit;
import services.ServiceProduit;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javax.swing.text.TableView;

public class AjouterProduitFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView AdminImageView;

    @FXML
    private TextField tfNomProd;

    @FXML
    private TextField tfIdProd;

    @FXML
    private TextArea tfDesProd;

    @FXML
    private ComboBox<String> tfCategProd;

    @FXML
    private TextField tfPrixProd;

    @FXML
    private TableView<Produit> tableProduit;

    @FXML
    private TableColumn<Produit, String> afNom;

    @FXML
    private TableColumn<Produit, String> afDesc;

    @FXML
    private TableColumn<Produit, String> afCatg;

    @FXML
    private TableColumn<Produit, String> afPrix;


    @FXML
    private Button ftAjtImg;

    @FXML
    void ajouterProduit(ActionEvent event) {
        try {
            String categorie = (String) tfCategProd.getValue(); // Récupérer la valeur sélectionnée dans la ComboBox
            Produit p = new Produit(Integer.parseInt(tfIdProd.getText()),tfNomProd.getText(), tfDesProd.getText(), categorie , Float.parseFloat(tfPrixProd.getText()));
            ServiceProduit sp = new ServiceProduit();
            sp.insertOne_prod(p);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }

    @FXML
    void annulerProduit(ActionEvent event) {
        tfIdProd.setText(null);
        tfNomProd.setText(null);
        tfDesProd.setText(null);
        tfCategProd.setValue(null);
        tfPrixProd.setText(null);
    }

    @FXML
    void afficherProduit(MouseEvent event) {
        // Vérifie si un clic de souris a été effectué
        if (event.getClickCount() > 0) {
            // Récupérer la ligne sélectionnée dans le TableView
            Produit produitSelectionne = tableProduit.getSelectionModel().getSelectedItem();

            // Vérifier si un événement a été sélectionné
            if (produitSelectionne != null) {
                // Remplir les champs du formulaire avec les informations de l'événement sélectionné
                tfIdProd.setText(String.valueOf(produitSelectionne.getId_produit()));
                tfNomProd.setText(produitSelectionne.getNom_produit());
                tfDesProd.setText(produitSelectionne.getDescription());
                tfCategProd.setValue(produitSelectionne.getCategorie());
                tfPrixProd.setText(String.valueOf(produitSelectionne.getPrix_produit()));
            }
        }


    }

    @FXML
    void afficher() {
        tableProduit.setOnMouseClicked(this::afficherProduit);
        try {
            // Appeler la méthode de service pour récupérer la liste des produits
            ServiceProduit sp = new ServiceProduit();
            List<Produit> produits = sp.selectAll_prod();

            // Créer une liste observable de produits
            ObservableList<Produit> produitsObservable = FXCollections.observableArrayList(produits);

            // Assigner la liste observable à la TableView
            tableProduit.setItems(produitsObservable);
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
        }
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        try {
            Produit produitAModifier = tableProduit.getSelectionModel().getSelectedItem();

            if (produitAModifier != null) {
                // Mettre à jour les données de l'événement dans la base de données
                produitAModifier.setId_produit(Integer.parseInt(tfIdProd.getText()));
                produitAModifier.setNom_produit(tfNomProd.getText());
                produitAModifier.setDescription(tfDesProd.getText());
                produitAModifier.setCategorie(tfCategProd.getValue());
                produitAModifier.setPrix_produit(Float.parseFloat(tfPrixProd.getText()));

                // Mettre à jour l'événement dans la base de données
                ServiceProduit se = new ServiceProduit();
                se.updateOne_prod(produitAModifier);

                // Mettre à jour l'affichage dans le TableView
                tableProduit.refresh();

                // Afficher un message de réussite
                afficherSucces("Modification réussie", "Le produit a été modifié avec succès !");
            } else {
                afficherErreur("Aucun produit sélectionné", "Veuillez sélectionner un produit à modifier dans le TableView.");
            }
        } catch (SQLException e) {
            afficherErreur("Erreur de modification", "Une erreur s'est produite lors de la modification du produit.");
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de saisie", "Vous avez une erreur dans la saisie de vos données !");
        }
    }

    @FXML
    void supprimerProduit(ActionEvent event) {
        Produit produitASupprimer = tableProduit.getSelectionModel().getSelectedItem();

        if (produitASupprimer != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setHeaderText("Êtes-vous sûr de vouloir supprimer ce produit ?");
            confirmation.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = confirmation.showAndWait();

            // Vérifier si l'utilisateur a cliqué sur le bouton OK
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Supprimer le produit de la base de données
                    ServiceProduit service = new ServiceProduit();
                    service.deleteOne_prod(produitASupprimer);

                    // Supprimer le produit de la TableView
                    tableProduit.getItems().remove(produitASupprimer);

                    // Afficher un message de réussite
                    afficherSucces("Suppression réussie", "Le produit a été supprimé avec succès !");
                } catch (SQLException e) {
                    afficherErreur("Erreur de suppression", "Une erreur s'est produite lors de la suppression du produit.");
                }
            }
        } else {
            afficherErreur("Aucun produit sélectionné", "Veuillez sélectionner un produit à supprimer dans le TableView.");
        }
    }

        /*
        // Récupérer l'index de la ligne sélectionnée dans la TableView
    int selectedIndex = tableProduit.getSelectionModel().getSelectedIndex();

    // Vérifier si une ligne est effectivement sélectionnée
    if (selectedIndex >= 0) {
        // Récupérer le produit sélectionné
        Produit selectedProduit = tableProduit.getItems().get(selectedIndex);

        // Récupérer les nouvelles valeurs des champs de texte
        String newNomProd = tfNomProd.getText();
        String newDesProd = tfDesProd.getText();
        String newCategProd = tfCategProd.getValue();
        float newPrixProd = Float.parseFloat(tfPrixProd.getText());

        try {
            // Mettre à jour le produit sélectionné avec les nouvelles valeurs
            selectedProduit.setNomProd(newNomProd);
            selectedProduit.setDescription(newDesProd);
            selectedProduit.setCategorie(newCategProd);
            selectedProduit.setPrixProd(newPrixProd);

            // Appeler le service pour mettre à jour le produit dans la base de données
            ServiceProduit sp = new ServiceProduit();
            sp.updateOne_prod(selectedProduit);

            // Afficher une confirmation à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mise à jour du produit");
            alert.setHeaderText(null);
            alert.setContentText("Le produit a été mis à jour avec succès!");
            alert.showAndWait();

            // Rafraîchir la TableView pour refléter les changements
            afficherProduit(event);
        } catch (SQLException e) {
            // Gérer les erreurs de mise à jour
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la mise à jour du produit.");
            alert.showAndWait();
        }
    } else {
        // Aucune ligne sélectionnée, afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune sélection");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un produit à mettre à jour.");
        alert.showAndWait();
    }
         */


    @FXML
    void select(ActionEvent event) {

        String s = tfCategProd.getSelectionModel().getSelectedItem().toString();

    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherSucces(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);

        // Supprime le bouton de fermeture de l'alerte
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.OK);

        alert.showAndWait();
    }

    @FXML
    void AjoutImg(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AjouterImageProdFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }

    @FXML
    void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList("Materiel","Logiciel");
        tfCategProd.setItems(list); // Appliquer la liste observable à votre ComboBox

        // Associer chaque colonne à la propriété correspondante de l'objet Produit
        afNom.setCellValueFactory(new PropertyValueFactory<>("nom_produit")); // Assurez-vous que "nom" correspond à la propriété dans la classe Produit
        afDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        afCatg.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        afPrix.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));


        assert tfNomProd != null : "fx:id=\"tfNomProd\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert tfDesProd != null : "fx:id=\"tfDesProd\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert tfPrixProd != null : "fx:id=\"tfPrixProd\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert tfCategProd != null : "fx:id=\"tfCategProd\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert tableProduit != null : "fx:id=\"tableProduit\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert afNom != null : "fx:id=\"afNom\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert afDesc != null : "fx:id=\"afDesc\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert afCatg != null : "fx:id=\"afCatg\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert afPrix != null : "fx:id=\"afPrix\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert tfIdProd != null : "fx:id=\"tfIdProd\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert ftAjtImg != null : "fx:id=\"ftAjtImg\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";
        assert AdminImageView != null : "fx:id=\"AdminImageView\" was not injected: check your FXML file 'AjouterProduitFXML.fxml'.";

        File brandingFile = new File("src/main/img/Back.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        AdminImageView.setImage(brandingImage);


        afficher();

    }
}