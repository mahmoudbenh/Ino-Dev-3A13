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
            String imageUrl = ftUrl.getText();
            String sql = "INSERT INTO image (id_produit, url) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idProduit);
                preparedStatement.setString(2, ftUrl.getText());
                preparedStatement.executeUpdate();
                System.out.println("Image ajoutée avec succès.");
                Image image = new Image(imageUrl);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                ImageGrid.add(imageView, 0, ImageGrid.getRowCount());
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
        String imageUrl = ftUrl.getText();
        int newIdProd = Integer.parseInt(ftId_prod.getValue());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String selectSql = "SELECT id_image FROM image WHERE url = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setString(1, imageUrl);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int idImage = resultSet.getInt("id_image");
                        String updateSql = "UPDATE image SET id_produit = ? WHERE id_image = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setInt(1, newIdProd);
                            updateStatement.setInt(2, idImage);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("L'image a été modifiée avec succès !");
                                for (Node node : ImageGrid.getChildren()) {
                                    if (node instanceof ImageView) {
                                        ImageView imageView = (ImageView) node;
                                        if (imageView.getImage().getUrl().equals(imageUrl)) {
                                            imageView.setImage(new Image(new File(imageUrl).toURI().toString()));
                                            break;
                                        }
                                    }
                                }
                            } else {
                                System.out.println("La mise à jour de l'image a échoué.");
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
        String imageUrl = ftUrl.getText();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "")) {
            String sql = "SELECT id_image FROM image WHERE url = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, imageUrl);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int idImage = resultSet.getInt("id_image");

                        String deleteSql = "DELETE FROM image WHERE id_image = ?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                            deleteStatement.setInt(1, idImage);
                            deleteStatement.executeUpdate();
                            System.out.println("L'image a été supprimée avec succès !");

                            for (Node node : ImageGrid.getChildren()) {
                                if (node instanceof ImageView) {
                                    ImageView imageView = (ImageView) node;
                                    if (imageView.getImage().getUrl().equals(imageUrl)) {
                                        int rowIndex = GridPane.getRowIndex(node);
                                        ImageGrid.getChildren().remove(node);
                                        ImageGrid.getRowConstraints().remove(rowIndex);
                                        break;
                                    }
                                }
                            }
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





    private void ajouterImage(String url, int column, int row) {
        ImageView imageView = new ImageView(new Image(url));
        ImageGrid.add(imageView, column, row);
    }

    private List<String> urls = new ArrayList<>();

    public void addImageUrl(String url) {
        urls.add(url);

        System.out.println("URLs d'images : " + urls);

    }

    private AjouterImageFxml ajouterImageController;

    private void loadAndDisplayImages() {
        int column = 0;
        int row = 0;
        for (String url : urls) {
            try {
                ImageView imageView = new ImageView(new Image(url));
                System.out.println("URL de l'image : " + url);
                ImageGrid.add(imageView, column, row);
                column++;
                if (column == 1) {
                    column = 0;
                    row++;
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image depuis l'URL : " + url);
                e.printStackTrace();
            }
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

                    System.out.println("Image chargée avec succès depuis l'URL : " + url);
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement de l'image depuis l'URL : " + url);
                    e.printStackTrace();
                }
            }

            int column = 0;
            int row = 0;
            for (String url : imageUrls) {
                Image image = new Image(url);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setPreserveRatio(true);
                ImageGrid.add(imageView, column, row);


                imageView.setOnMouseClicked(event -> {
                    String imageUrl = imageView.getImage().getUrl();
                    ftUrl.setText(imageUrl);

                    try {
                        Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
                        PreparedStatement statement2 = cnx.prepareStatement("SELECT id_image , id_produit FROM image WHERE url = ?");

                        statement2.setString(1, imageUrl);
                        ResultSet resultSet2 = statement2.executeQuery();

                        if (resultSet2.next()) {
                            int id_prod = resultSet2.getInt("id_produit");
                            ftId_prod.setValue(String.valueOf(id_prod));
                            int id_img = resultSet2.getInt("id_image");
                        } else {
                            System.out.println("Aucun id_produit trouvé pour l'image avec l'URL : " + imageUrl);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    ftImg.setImage(imageView.getImage());
                });

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
    }

}