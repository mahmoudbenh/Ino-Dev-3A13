package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import models.Event;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.CloudinaryService;
import services.ServiceEvent;
import java.util.regex.Pattern;

public class AjouterEvent implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSettings1;

    @FXML
    private Button btnSettings11;

    @FXML
    private Button btnSettings111;

    @FXML
    private Button btn_importer;

    @FXML
    private DatePicker fx_date;

    @FXML
    private TextField fx_description;

    @FXML
    private TextField fx_lieu;

    @FXML
    private ComboBox<String> fx_statut;

    @FXML
    private TextField fxname;

    @FXML
    private Button idbtncertif;

    @FXML
    private Button idbtnform;

    @FXML
    private ImageView imageevenement;

    @FXML
    private TextField tfimage;
    private String xamppFolderPath;

    @FXML
    private boolean isNumeric(String str) {
        return Pattern.compile("\\d+").matcher(str).matches();
    }
    @FXML
    void AjouterEvent(ActionEvent event) {
        try {
            // Validate input fields
            if (fxname.getText().isEmpty() || fx_description.getText().isEmpty() || fx_date.getValue() == null
                    || fx_statut.getValue() == null || fx_lieu.getText().isEmpty() || tfimage.getText().isEmpty()) {
                // Display an error message if any of the fields are empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.show();
                return; // Exit the method if validation fails
            }

         //   LocalDate localDate = fx_date.getValue();
           // java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
           // localDate = sqlDate.toLocalDate(); // Update the existing localDate variable

// Get the image URL from the tfimage TextField
            String imageUrl = tfimage.getText();

           // Event nouvelEvent = new Event(0, fxname, fx_description, fx_date, fx_statut, fx_lieu);

          /*  // Create a new Event object
            Event nouvelEvent = new Event(0,
                    fxname.getText(),
                    fx_description.getText(),
                   // sqlDate,
                    fx_statut.getValue().toString(), // Assuming the ComboBox returns a String
                    fx_lieu.getText(),
                    imageUrl
            );*/
            Event nouvelEvent = new Event(
                    0,
                    fxname.getText(),
                    fx_description.getText(),
                    fx_date.getValue(), // Assuming fx_date returns a LocalDate
                    fx_statut.getValue().toString(), // Assuming the ComboBox returns a String
                    fx_lieu.getText(),
                    imageUrl
            );


            ServiceEvent serviceEvent = new ServiceEvent();

            // Insert the event
            serviceEvent.insertOne(nouvelEvent);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setContentText("L'événement a été ajouté avec succès.");
            successAlert.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   @FXML
    void Annuler(ActionEvent event) {
        // Nettoyage des champs
        fxname.setText(null);
        fx_description.setText(null);
        fx_date.setValue(null);
        fx_statut.setValue(null);
        fx_lieu.setText(null);
        // Remise du focus sur le premier champ de texte (fxid_event)
        fxname.requestFocus();
    }

   /* @FXML
    void Exit(ActionEvent event) {
        Platform.exit();
    }*/
   @FXML
   void Exit(ActionEvent event) {
       // Get the reference to the button's stage
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       // Close the stage
       stage.close();
   }


    @FXML
    void importerImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a banner file !");
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            // Get the absolute path of the selected image file
            String imageUrl = file.getAbsolutePath();

            try {
                // Upload the image to Cloudinary API
                String cloudinaryUrl = CloudinaryService.uploadImage(imageUrl);

                // Set the image URL to the text field
                tfimage.setText(cloudinaryUrl);

                // Load the image
                Image image = new Image(cloudinaryUrl);

                // Set the image to the ImageView
                imageevenement.setImage(image);

            } catch (Exception ex) {
                System.out.println("Could not upload the image to Cloudinary");
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected");
        }
    }


    @FXML
    void retour(ActionEvent event) {

        try {
            URL fxmlUrl = getClass().getResource("/WelcomeAdmin.fxml");

            if (fxmlUrl != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                Parent root = fxmlLoader.load();
                fxname.getScene().setRoot(root);
            } else {
                System.err.println("FXML file not found.");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fx_statut.setItems(FXCollections.observableArrayList("Done ","Pending","Not done"));
    }

    @FXML
    void consulterListeEvenements(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeEvent.fxml"));
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Create the stage
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}








