package controllers;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AfficherImageFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView ftImg_Ps;

    @FXML
    private Button apres;

    @FXML
    private Button avant;

    @FXML
    private Label descPs;

    @FXML
    private Label prxiPs;

    @FXML
    private Label nomPs;

    @FXML
    private Label catgPs;

    private int productId;
    private List<String> imageUrls = new ArrayList<>();
    private int currentIndex = 0;

    public void setProductId(int productId) {
        this.productId = productId;
        try {
            retrieveProductImages(productId);
            displayProductInformation(productId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void next(ActionEvent event) {
        if (!imageUrls.isEmpty() && currentIndex < imageUrls.size() - 1) {
            currentIndex++;
            displayCurrentImage();
        }
    }

    @FXML
    void previous(ActionEvent event) {
        if (!imageUrls.isEmpty() && currentIndex > 0) {
            currentIndex--;
            displayCurrentImage();
        }
    }

    private void displayCurrentImage() {
        String imageUrl = imageUrls.get(currentIndex);
        Image image = new Image(imageUrl);
        ftImg_Ps.setImage(image);
    }

    public void retrieveProductImages(int productId) throws SQLException {
        String sql = "SELECT url FROM image WHERE id_produit = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                imageUrls.clear();

                while (resultSet.next()) {
                    String imageUrl = resultSet.getString("url");
                    imageUrls.add(imageUrl);
                }
                if (!imageUrls.isEmpty()) {
                    displayCurrentImage();
                }
            }

        }
    }

    @FXML
    void initialize() {
        assert ftImg_Ps != null : "fx:id=\"ftImg_Ps\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";
        assert apres != null : "fx:id=\"apres\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";
        assert avant != null : "fx:id=\"avant\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";
        assert descPs != null : "fx:id=\"descPs\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";
        assert prxiPs != null : "fx:id=\"prxiPs\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";
        assert nomPs != null : "fx:id=\"nomPs\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";
        assert catgPs != null : "fx:id=\"catgPs\" was not injected: check your FXML file 'AfficherImageFXML.fxml'.";

        displayProductInformation(productId);
    }

    public void displayProductInformation(int productId) {
        this.productId = productId; // Définir produitId ici également
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT nom_produit, description, categorie, prix_produit FROM produit WHERE id_produit = ?");
            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nomProduit = resultSet.getString("nom_produit");
                String description = resultSet.getString("description");
                String categorie = resultSet.getString("categorie");
                double prixProduit = resultSet.getDouble("prix_produit");

                nomPs.setText(nomProduit);
                descPs.setText(description);
                catgPs.setText(categorie);
                prxiPs.setText(String.valueOf(prixProduit));
            }

            resultSet.close();
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
