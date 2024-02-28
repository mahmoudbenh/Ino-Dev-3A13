package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Images;
import models.Produit;
import services.ServiceImage;
import services.ServiceProduit;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProduitFxml {

    @FXML
    private ImageView ftImgProds;

    @FXML
    private Spinner<?> ftQuantiteProds;

    @FXML
    private Button ftAjouterPanier;

    @FXML
    private Label nomP;

    @FXML
    private Label prixPrd;

    @FXML
    private Label descP;


   /* @FXML
    private Button ftOuvrir;*/

    @FXML
    private AnchorPane prod;


    @FXML
    void ajouterPanier(ActionEvent event) {
        System.out.println("ajout au panier");
    }

    private Produit produit;

    // Méthode pour initialiser les données du produit dans l'interface utilisateur
    public void initializeData(Produit produit) {
        this.produit = produit;
        // Affichage du nom du produit dans le champ de texte nomP
        nomP.setText(produit.getNom_produit());
        // Affichage du prix du produit dans le champ de texte prixP
        prixPrd.setText(String.valueOf(produit.getPrix_produit()));
        descP.setText(produit.getDescription());

        // Récupération de l'URL de l'image du produit à partir de la base de données
        String imageUrl = retrieveImageUrlFromDatabase(produit.getId_produit());
        if (imageUrl != null) {
            // Chargement de l'image depuis l'URL et affichage dans ImageView ftImgProds
            Image img = new Image(imageUrl);
            ftImgProds.setImage(img);
        }
    }

    // Méthode pour récupérer l'URL de l'image du produit à partir de la base de données
    private String retrieveImageUrlFromDatabase(int productId) {
        String imageUrl = null;
        try {
            // Établissement de la connexion à la base de données MySQL
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            // Préparation de la requête SQL pour récupérer l'URL de l'image en fonction de l'ID du produit
            PreparedStatement statement = connection.prepareStatement("SELECT url FROM image WHERE id_produit = ?");
            statement.setInt(1, productId);
            // Exécution de la requête SQL et récupération du résultat dans un objet ResultSet
            ResultSet resultSet = statement.executeQuery();
            // Vérification si un résultat est retourné
            if (resultSet.next()) {
                // Récupération de l'URL de l'image à partir du résultat de la requête
                imageUrl = resultSet.getString("url");
            }
            // Fermeture du ResultSet, du statement et de la connexion à la base de données
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            // Gestion des exceptions lors de l'accès à la base de données
            e.printStackTrace();
        }
        // Renvoi de l'URL de l'image récupérée (ou null si aucune image n'a été trouvée)
        return imageUrl;
    }


    public ImageView getFtImgProds() {
        return ftImgProds;
    }

    // Méthode appelée automatiquement lors de l'initialisation du contrôleur FXML
    @FXML
    void initialize() {
        // Vérification que les éléments d'interface utilisateur ont été correctement injectés depuis le fichier FXML
        assert prod != null : "fx:id=\"prod\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";
        assert nomP != null : "fx:id=\"nomP\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";
        assert prixPrd != null : "fx:id=\"prixPrd\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";
        assert ftImgProds != null : "fx:id=\"ftImgProds\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";
        assert descP != null : "fx:id=\"descP\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";
        assert ftQuantiteProds != null : "fx:id=\"ftQuantiteProds\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";
        assert ftAjouterPanier != null : "fx:id=\"ftAjouterPanier\" was not injected: check your FXML file 'AfficherProduitFXML.fxml'.";

        ftImgProds.setOnMouseClicked(event -> {
            try {
                // Charger l'interface AfficherImageFXML.fxml dans un Parent
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherImageFXML.fxml"));
                Parent imageViewParent = loader.load();

                // Obtenir la fenêtre parente de AjouterProduit
                Stage parentStage = (Stage) ftImgProds.getScene().getWindow();

                // Créer une nouvelle scène avec le contenu de l'interface AfficherImageFXML.fxml
                Scene newScene = new Scene(imageViewParent);

                // Remplacer la scène actuelle de la fenêtre parente par la nouvelle scène
                parentStage.setScene(newScene);

                // Afficher la fenêtre parente mise à jour
                parentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
