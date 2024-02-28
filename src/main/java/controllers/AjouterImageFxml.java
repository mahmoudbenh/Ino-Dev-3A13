// AjouterImageFxml.java
package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.Images;
import models.Produit;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class AjouterImageFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button acceuilbtn;

    @FXML
    private AnchorPane ProduitForm;

    @FXML
    private ScrollPane ftProduitScrollPane;

    @FXML
    private GridPane ProduitGrid;

    @FXML
    private TableView<Images> ftProduitTable;

    @FXML
    private TableColumn<Images, String> ftProdCol;

    @FXML
    private TableColumn<Images, String> ftQuantView;

    @FXML
    private TableColumn<Images, String> ftPrixView;
    @FXML
    private TextField ftTotale;

    @FXML
    private Button ftConfButt;

    @FXML
    private Button ftAnnulerBut;

    @FXML
    private Button ftProduit;

    @FXML
    private Button ftReclamation;

    @FXML
    private Button ftPropos;

    @FXML
    private Button ftEvenements;

    @FXML
    private ImageView ftLogo;

    private ServiceProduit serviceProduit = new ServiceProduit();

    @FXML
    void initialize() {

        assert acceuilbtn != null : "fx:id=\"acceuilbtn\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ProduitForm != null : "fx:id=\"ProduitForm\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftProduitScrollPane != null : "fx:id=\"ftProduitScrollPane\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ProduitGrid != null : "fx:id=\"ProduitGrid\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftProduitTable != null : "fx:id=\"ftProduitTable\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftProdCol != null : "fx:id=\"ftProdCol\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftQuantView != null : "fx:id=\"ftQuantView\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftPrixView != null : "fx:id=\"ftPrixView\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftTotale != null : "fx:id=\"ftTotale\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftConfButt != null : "fx:id=\"ftConfButt\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftAnnulerBut != null : "fx:id=\"ftAnnulerBut\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftProduit != null : "fx:id=\"ftProduit\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftReclamation != null : "fx:id=\"ftReclamation\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftPropos != null : "fx:id=\"ftPropos\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftEvenements != null : "fx:id=\"ftEvenements\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";
        assert ftLogo != null : "fx:id=\"ftLogo\" was not injected: check your FXML file 'AjouterImageFXML.fxml'.";


        File brandingFile = new File("src/main/img/logo.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        ftLogo.setImage(brandingImage);

        afficherProduits();
    }

    private void afficherProduits() {
        try {
            List<Produit> produits = serviceProduit.selectAll_prod_2();

            int column = 0;
            int row = 0;

            for (Produit produit : produits) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitFXML.fxml"));
                Region produitView = loader.load();

                AfficherProduitFxml controller = loader.getController();
                controller.initializeData(produit);

                // Ajouter un gestionnaire d'événements pour l'image
                ImageView imageView = controller.getFtImgProds();
                int produitId = produit.getId_produit();
                imageView.setOnMouseClicked(event -> afficherDetailsProduit(produitId));

                ProduitGrid.add(produitView, column, row);

                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Méthode pour afficher les détails du produit dans l'interface AfficherImageFxml
    // Méthode pour afficher les détails du produit dans l'interface AfficherImageFxml
    private void afficherDetailsProduit(int productId) {
        // Charger l'interface AfficherImageFxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherImageFXML.fxml"));
        Parent root;
        try {
            root = loader.load();
            AfficherImageFxml controller = loader.getController();
            // Appeler la méthode pour afficher les détails du produit
            try {
                controller.retrieveProductImages(productId);
                controller.displayProductInformation(productId);
            } catch (SQLException e) {
                e.printStackTrace(); // Gérer l'exception de manière appropriée
            }
            // Afficher la nouvelle interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}