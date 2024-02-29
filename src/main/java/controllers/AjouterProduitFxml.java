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
            String categorie = (String) tfCategProd.getValue();
            Produit p = new Produit(Integer.parseInt(tfIdProd.getText()),tfNomProd.getText(), tfDesProd.getText(), categorie , Float.parseFloat(tfPrixProd.getText()));
            ServiceProduit sp = new ServiceProduit();
            sp.insertOne_prod(p);
            tableProduit.getItems().add(p);
            tableProduit.refresh();
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
        if (event.getClickCount() > 0) {

            Produit produitSelectionne = tableProduit.getSelectionModel().getSelectedItem();

            if (produitSelectionne != null) {
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
            ServiceProduit sp = new ServiceProduit();
            List<Produit> produits = sp.selectAll_prod();
            ObservableList<Produit> produitsObservable = FXCollections.observableArrayList(produits);
            tableProduit.setItems(produitsObservable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        try {
            Produit produitAModifier = tableProduit.getSelectionModel().getSelectedItem();

            if (produitAModifier != null) {
                produitAModifier.setId_produit(Integer.parseInt(tfIdProd.getText()));
                produitAModifier.setNom_produit(tfNomProd.getText());
                produitAModifier.setDescription(tfDesProd.getText());
                produitAModifier.setCategorie(tfCategProd.getValue());
                produitAModifier.setPrix_produit(Float.parseFloat(tfPrixProd.getText()));

                ServiceProduit se = new ServiceProduit();
                se.updateOne_prod(produitAModifier);

                tableProduit.refresh();

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
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setHeaderText("Êtes-vous sûr de vouloir supprimer ce produit ?");
            confirmation.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ServiceProduit service = new ServiceProduit();
                    service.deleteOne_prod(produitASupprimer);
                    tableProduit.getItems().remove(produitASupprimer);
                    afficherSucces("Suppression réussie", "Le produit a été supprimé avec succès !");
                } catch (SQLException e) {
                    afficherErreur("Erreur de suppression", "Une erreur s'est produite lors de la suppression du produit.");
                }
            }
        } else {
            afficherErreur("Aucun produit sélectionné", "Veuillez sélectionner un produit à supprimer dans le TableView.");
        }
    }

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
        tfCategProd.setItems(list);

        afNom.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
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