package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.reclamation;
import services.ServiceReclamation;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.MalformedURLException;
import javafx.geometry.Pos;

public class affichageUserreclamationFXML  {

    @FXML
    private VBox reclamationsContainer;


    public void initialize() {
        try {
            // Fetch reclamations from the database
            ServiceReclamation serviceReclamation = new ServiceReclamation();
            List<reclamation> reclamations = serviceReclamation.selectAll();

            // Display reclamations as cards
            displayReclamations(reclamations);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'initialisation : " + e.getMessage());
        }
    }

    private void displayReclamations(List<reclamation> reclamations) {
        if (reclamations != null) {
            int cardsPerRow = 3;
            int rowCount = (int) Math.ceil((double) reclamations.size() / cardsPerRow);

            for (int i = 0; i < rowCount; i++) {
                HBox row = new HBox();
                row.setSpacing(10); // Espacement entre les cartes

                for (int j = 0; j < cardsPerRow && (i + j) < reclamations.size(); j++) {
                    reclamation currentReclamation = reclamations.get(i + j);
                    VBox card = createReclamationCard(currentReclamation);
                    row.getChildren().add(card);
                }

                reclamationsContainer.getChildren().add(row);
            }
        }
    }

    private VBox createReclamationCard(reclamation reclamation) {
        try {
            // Create a VBox for the card
            VBox card = new VBox();
            card.setStyle("-fx-border-color: #3498db; -fx-border-width: 2px; -fx-padding: 10px; -fx-background-color: #ecf0f1");
            // Ajouter HBox pour aligner verticalement l'image et l'étiquette
            HBox imageBox = new HBox();
            imageBox.setAlignment(Pos.CENTER); // Centrez les éléments verticalement
            imageBox.setSpacing(10); // Espacement entre l'image et l'étiquette
            // Add ImageView for the image
            ImageView imageView = new ImageView();
            File imageFile = new File("target/assets/unnamed-removebg-preview.png");
            String imageUrl = imageFile.toURI().toURL().toExternalForm();
            imageView.setImage(new Image(imageUrl));
            imageView.setFitWidth(100); // Ajustez la largeur de l'image selon vos besoins
            imageView.setPreserveRatio(true);
            // Ajouter l'étiquette de l'image en haut (centré horizontalement)
            Label imageLabel = new Label("");
            imageLabel.setStyle("-fx-alignment: CENTER;"); // Centrer horizontalement

            // Ajouter l'ImageView et l'étiquette de l'image à la hBox
            imageBox.getChildren().addAll(imageView, imageLabel);
            // Ajouter l'HBox avec l'image et l'étiquette en haut de la carte
            card.getChildren().add(imageBox);
            // Add labels for reclamation information
            Label titleLabel = new Label(reclamation.getTitre_reclamation());
            titleLabel.setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;"); // Centrer horizontalement
            // Ajouter le titre à la VBox
            card.getChildren().add(titleLabel);
            Label dateLabel = new Label("Date: " + reclamation.getDate_reclamation());
            Label heureLabel = new Label("Heure: " + reclamation.getHeure());
            Label typeLabel = new Label("Type: " + reclamation.getType_reclamation());
            Label statutLabel = new Label("Statut: " + reclamation.getStatut_reclamation());
            Label descriptionLabel = new Label("Description: " + reclamation.getDescription());

            // Customize labels as needed
          // titleLabel.setStyle("-fx-font-weight: bold");
            dateLabel.setStyle("-fx-font-style: italic; -fx-font-family: 'Arial';");

            // Add labels to the card
            card.getChildren().addAll(dateLabel, heureLabel, typeLabel, statutLabel, descriptionLabel);

            return card;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la création de la carte : " + e.getMessage());
            return null;
        }
    }}

