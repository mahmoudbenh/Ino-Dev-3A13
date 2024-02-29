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


    @FXML
    private AnchorPane prod;


    @FXML
    void ajouterPanier(ActionEvent event) {
        System.out.println("ajout au panier");
    }

    private Produit produit;

    public void initializeData(Produit produit) {
        this.produit = produit;
        nomP.setText(produit.getNom_produit());
        prixPrd.setText(String.valueOf(produit.getPrix_produit()));
        descP.setText(produit.getDescription());
        String imageUrl = retrieveImageUrlFromDatabase(produit.getId_produit());
        if (imageUrl != null) {
            Image img = new Image(imageUrl);
            ftImgProds.setImage(img);
        }
    }

    private String retrieveImageUrlFromDatabase(int productId) {
        String imageUrl = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT url FROM image WHERE id_produit = ?");
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                imageUrl = resultSet.getString("url");
            }
            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherImageFXML.fxml"));
                Parent imageViewParent = loader.load();
                Stage parentStage = (Stage) ftImgProds.getScene().getWindow();
                Scene newScene = new Scene(imageViewParent);
                parentStage.setScene(newScene);

                parentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
