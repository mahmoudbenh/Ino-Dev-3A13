package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import models.Images;
import models.Produit;
import services.ServiceImage;
import services.ServiceProduit;
import utils.DBConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterImageProdFxml {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView AdminImageView;

    @FXML
    private Button btRetour;

    @FXML
    private ComboBox<String> ftId_prod;

    @FXML
    private ImageView ftImg;

    @FXML
    private Button ajouterImg;

    @FXML
    private Button btImg;

    @FXML
    private TextField ftUrl;

    @FXML
    private Button modifierImg;

    @FXML
    private Button supprimerImage;

    @FXML
    private GridPane ImageGrid;

    @FXML
    private AnchorPane ImageForm;

    @FXML
    private ScrollPane ftImageScrollPane;

    @FXML
    void ajouterImage(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            int idProduit = Integer.parseInt(ftId_prod.getValue());
            String sql = "INSERT INTO image (id_produit, url) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idProduit);
                preparedStatement.setString(2, ftUrl.getText());
                preparedStatement.executeUpdate();
                System.out.println("Image ajoutée avec succès.");
            } catch (SQLException e) {
                System.out.println("Une erreur s'est produite lors de l'ajout de l'image : " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    @FXML
    void selectioner(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String imageUrl = selectedFile.toURI().toString();
            ftUrl.setText(imageUrl);
            Image image = new Image(imageUrl);
            ftImg.setImage(image);
        }
    }

    @FXML
    void modifierImage(ActionEvent event) {
        String imageUrl = ftUrl.getText(); // Récupérer l'URL de l'image que vous voulez modifier
        int newIdProd = Integer.parseInt(ftId_prod.getValue());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            // Sélectionner l'ID de l'image à partir de l'URL spécifiée
            String selectSql = "SELECT id_image FROM image WHERE url = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, imageUrl);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int idImage = resultSet.getInt("id_image");
                        // Mettre à jour l'image avec le nouvel ID produit
                        String updateSql = "UPDATE image SET id_produit = ? WHERE id_image = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setInt(1, newIdProd);
                            updateStatement.setInt(2, idImage);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("L'image a été modifiée avec succès !");
                            } else {
                                System.out.println("Aucune image correspondant à cet URL n'a été trouvée.");
                            }
                        }
                    } else {
                        System.out.println("Aucune image correspondant à cet URL n'a été trouvée.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la modification de l'image : " + e.getMessage());
        }
    }





    @FXML
    void supprimerImage(ActionEvent event) {
        String imageUrl = ftUrl.getText(); // Récupérer l'URL de l'image que vous voulez supprimer
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String sql = "SELECT id_image FROM image WHERE url = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, imageUrl);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int idImage = resultSet.getInt("id_image");
                        // Maintenant que vous avez l'ID de l'image, vous pouvez exécuter la suppression
                        String deleteSql = "DELETE FROM image WHERE id_image = ?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                            deleteStatement.setInt(1, idImage);
                            deleteStatement.executeUpdate();
                            System.out.println("L'image a été supprimée avec succès !");
                        }
                    } else {
                        System.out.println("Aucune image correspondant à cette URL n'a été trouvée.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Une erreur s'est produite lors de la suppression de l'image : " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la connexion à la base de données : " + e.getMessage());
        }
    }




    // Méthode pour ajouter une image à la grille
    private void ajouterImage(String url, int column, int row) {
        ImageView imageView = new ImageView(new Image(url));
        ImageGrid.add(imageView, column, row);
    }

    // Liste des URLs des images
    private List<String> urls = new ArrayList<>();

    // Méthode pour ajouter une URL d'image à la liste
    public void addImageUrl(String url) {
        urls.add(url);

        System.out.println("URLs d'images : " + urls);

    }

    private AjouterImageFxml ajouterImageController;

    // Méthode pour charger et afficher les images
    private void loadAndDisplayImages() {
        int column = 0;
        int row = 0;
        for (String url : urls) {
            try {
                ImageView imageView = new ImageView(new Image(url));
                System.out.println("URL de l'image : " + url);
                ImageGrid.add(imageView, column, row);
                column++;
                // Réinitialisez la colonne et incrémentez la ligne après chaque 3 images pour créer une grille
                if (column == 1) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                // Gérer les exceptions liées au chargement des images
                System.err.println("Erreur lors du chargement de l'image depuis l'URL : " + url);
                e.printStackTrace();
            }
        }
    }

    private void afficherImagesProduits(ServiceProduit serviceProduit, GridPane gridPane) {
        try {
            // Récupérer tous les produits avec leurs images en utilisant l'instance de ServiceProduit
            List<Produit> produits = serviceProduit.selectAll_prod_2();

            int column = 0;
            int row = 0;
            // Parcourir tous les produits
            for (Produit produit : produits) {
                List<String> urlsImages = produit.getUrlsImages();
                for (String url : urlsImages) {
                    try {
                        // Charger et afficher l'image dans l'interface utilisateur
                        Image img = new Image(url);
                        ImageView imageView = new ImageView(img);

                        // Ajouter l'image au GridPane
                        gridPane.add(imageView, column, row);

                        // Ajouter l'URL à la liste d'URLs
                        addImageUrl(url);

                        // Incrémenter les valeurs de colonne et de ligne
                        column++;
                        // Réinitialiser la colonne et incrémenter la ligne si nécessaire
                        if (column == 1) {
                            column = 0;
                            row++;
                        }
                    } catch (Exception e) {
                        // Gérer les exceptions liées au chargement des images
                        System.err.println("Erreur lors du chargement de l'image depuis l'URL : " + url);
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherImageFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }

    @FXML
    void initialize() {
        try {
            // Utilisation de DBConnection pour obtenir la connexion
            Connection connection = DBConnection.getInstance().getCnx();
            String query = "SELECT id_produit FROM produit";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            ObservableList<String> list_id = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String id = resultSet.getString("id_produit");
                list_id.add(id);
            }
            ftId_prod.setItems(list_id);

            // Fermeture des ressources de la première connexion
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de connexion à la base de données");
            alert.setContentText("Impossible de se connecter à la base de données ou d'exécuter la requête SQL.");
            alert.showAndWait();
        }

        assert ftId_prod != null : "fx:id=\"ftId_prod\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert ftImg != null : "fx:id=\"ftImg\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert ajouterImg != null : "fx:id=\"ajouterImg\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert btImg != null : "fx:id=\"btImg\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
       assert ftUrl != null : "fx:id=\"ftUrl\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert modifierImg != null : "fx:id=\"modifierImg\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert supprimerImage != null : "fx:id=\"supprimerImage\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert ImageForm != null : "fx:id=\"ImageForm\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert ftImageScrollPane != null : "fx:id=\"ftImageScrollPane\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert ImageGrid != null : "fx:id=\"ImageGrid\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert AdminImageView != null : "fx:id=\"AdminImageView\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";
        assert btRetour != null : "fx:id=\"btRetour\" was not injected: check your FXML file 'AjouterImageProdFXML.fxml'.";

        File brandingFile = new File("src/main/img/Back.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        AdminImageView.setImage(brandingImage);

        try {
            Connection connection = DBConnection.getInstance().getCnx();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT url FROM image");

            List<String> imageUrls = new ArrayList<>();
            while (resultSet.next()) {
                String url = resultSet.getString("url");
                imageUrls.add(url);

                try {
                    Image image = new Image(url);
                    // Affichez un message pour indiquer que l'image a été chargée avec succès
                    System.out.println("Image chargée avec succès depuis l'URL : " + url);
                } catch (Exception e) {
                    // Affichez les détails de l'exception s'il y en a
                    System.err.println("Erreur lors du chargement de l'image depuis l'URL : " + url);
                    e.printStackTrace();
                }
            }

            int column = 0;
            int row = 0;
            for (String url : imageUrls) {
                Image image = new Image(url);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150); // ajustez la largeur selon vos besoins
                imageView.setPreserveRatio(true);
                ImageGrid.add(imageView, column, row);

                // Ajouter un gestionnaire d'événements à chaque ImageView dans ImageGrid
                imageView.setOnMouseClicked(event -> {
                    // Extraire l'URL de l'image à partir de l'ImageView cliquée
                    String imageUrl = imageView.getImage().getUrl();
                    // Mettre à jour les champs de texte avec les données de l'image sélectionnée
                    ftUrl.setText(imageUrl);

                    try {
                        // Recherche de l'id_produit associé à l'image en fonction de l'URL
                        Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
                        PreparedStatement statement2 = cnx.prepareStatement("SELECT id_image , id_produit FROM image WHERE url = ?");

                        //String query = "SELECT id_image , id_produit FROM image WHERE url = ?";
                        //PreparedStatement statement2 = connection.prepareStatement(query);
                        statement2.setString(1, imageUrl);
                        ResultSet resultSet2 = statement2.executeQuery();

                        // Si un résultat est trouvé, récupérez l'id_produit associé
                        if (resultSet2.next()) {
                            int id_prod = resultSet2.getInt("id_produit");
                            ftId_prod.setValue(String.valueOf(id_prod));
                            int id_img = resultSet2.getInt("id_image");
                            //ftId_Img.setText(String.valueOf(id_img));
                            // Vous pouvez également utiliser id_prod comme vous le souhaitez ici
                        } else {
                            System.out.println("Aucun id_produit trouvé pour l'image avec l'URL : " + imageUrl);
                        }

                        // Fermer les ressources
                        //resultSet2.close();
                        //statement2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    // Extraire les coordonnées de l'image
    /*int id_img = ImageGrid.getColumnIndex(imageView);
    int id_prod = ImageGrid.getRowIndex(imageView);


    // Afficher les coordonnées dans les champs ftId_prod et ftId_Img
    ftId_Img.setText(String.valueOf(id_img));
    ftId_prod.setValue(String.valueOf(id_prod));*/
                    // Vous pouvez également extraire et afficher l'ID de l'image si nécessaire
                    // Par exemple, si l'URL suit un format spécifique où l'ID est inclus

                    // Afficher l'image sélectionnée dans ftImg
                    ftImg.setImage(imageView.getImage());
                });


                // Gestion de la grille
                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Pour que les images occupent tout l'espace disponible
       /* for (int i = 0; i < 3; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            ImageGrid.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < 10; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            ImageGrid.getRowConstraints().add(rowConstraints);
        }*/
    }

}