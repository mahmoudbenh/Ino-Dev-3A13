
package controllers;

        import java.awt.*;
        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.time.LocalTime;
        import java.util.Arrays;
        import java.util.Random;
        import java.util.ResourceBundle;
        import javafx.collections.ObservableList;
        import javafx.fxml.Initializable;
        import javafx.collections.FXCollections;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;
        import services.ServiceReclamation;
        import models.reclamation;
        import java.util.List;
        import java.util.Arrays;
        import java.util.Random;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import static javafx.collections.FXCollections.*;
public class AjouterReclamationFXML implements Initializable {
    @FXML
    private ImageView imageview1;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date_reclamation1;

    @FXML
    private TextField description1;

    @FXML
    private Spinner<LocalTime> heure1;


    /*@FXML
    private TextField type_reclamation1;*/
    @FXML
    private ComboBox<String> type;
   /* @FXML
    private TextField idreclamation1;*/

    @FXML
    private ComboBox<String> stat;

    @FXML
    private TextField titre1;

/*
    public void start(Stage primaryStage) {
        // Créez votre AnchorPane et votre Scene
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 800, 600);

        // Ajoutez le code pour construire votre interface utilisateur ici...

        // Configurez la scène pour qu'elle occupe tout l'écran
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);

        // Définissez le titre de la fenêtre
        primaryStage.setTitle("Votre Application");

        // Affichez la fenêtre principale
        primaryStage.show();
    }*/
    // private reclamation reclamationtomodify;

    private boolean isPhoneNumberInDescription(String description) {
        // Utilisez une expression régulière pour rechercher un numéro de téléphone tunisien à 8 chiffres
        String phoneNumberRegex = "\\b[2-9]\\d{7}\\b";

        // Vérifiez si la description contient un numéro de téléphone
        return description.matches(phoneNumberRegex);
    }

    @FXML
    void ajouterreclamation(ActionEvent event) {
        try
        {
            String type_reclamation = type.getValue();
            String statut_reclamation = stat.getValue();
            String description = description1.getText();
            String titre = titre1.getText();

            // Vérifier si le titre contient plus de trois mots
            if (countWords(titre) > 3) {
                throw new TitreException("Le titre ne doit pas contenir plus de trois mots !");
            }
            if (isPhoneNumberInDescription(description)) {
                throw new PhoneNumberException("La description ne doit pas contenir de numéro de téléphone !");
            }
            reclamation r = new reclamation(titre, date_reclamation1.getValue(),heure1.getValue(),type_reclamation,statut_reclamation,description);
            ServiceReclamation sr = new ServiceReclamation();
            sr.insertOne(r);
            showSuccessNotification("reclamation envoyée");
        } catch (TitreException e) {
            System.out.println("Erreur : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Le titre ne doit pas contenir plus de trois mots !");
            alert.show();
        } catch (PhoneNumberException e) {
            // Gérez l'exception comme vous le souhaitez (affichez un message d'erreur, logguez, etc.)
            System.out.println("Erreur : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous n'avez pas le droit de partager vos numeros de telephones!");
            alert.show();
        }
        catch (SQLException  e)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("vous devez entrer tout les informations necessaires!");
            alert.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }
    private int countWords(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        String[] words = s.split("\\s+");
        return words.length;
    }

    private static class TitreException extends Exception {
        public TitreException(String message) {
            super(message);
        }
    }
    private static class PhoneNumberException extends Exception {
        public PhoneNumberException(String message) {
            super(message);
        }
    }
    void showSuccessNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void choisirPhoto(ActionEvent event) {
        // Liste de noms de fichiers d'images préconfigurées
        List<String> images = Arrays.asList("target/assets/fache.png","target/assets/fache2.png");

        // Choisissez une image aléatoire
        Random random = new Random();
        String randomImage = images.get(random.nextInt(images.size()));
// Chargez l'image et définissez-la sur l'ImageView
        Image image = new Image("file:" + randomImage);
        imageview1.setImage(image);
        // Affichez le chemin de l'image choisie (vous pouvez l'utiliser pour afficher l'image dans votre interface utilisateur)
        System.out.println("Chemin de l'image choisie : " + randomImage);
    }

    @FXML
    void annuler(ActionEvent event) {

        titre1.setText(null);
        description1.setText(null);
        type.setValue(null);
        stat.setValue(null);
        date_reclamation1.setValue(null);
        heure1.getValueFactory().setValue(null);
       // heure1.setValue(null);


    }
    @FXML
    void raffraichir(ActionEvent event) {

        date_reclamation1.setValue(LocalDate.now());
        // Mettre à jour l'heure avec l'heure actuelle
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>() {
            {
                setValue(LocalTime.now());
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };

        heure1.setValueFactory(timeValueFactory);
    }
    @FXML
    public void changeScreenButtonPushed(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherReclamationFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }


    @FXML
    public void reclamation(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AjouterReclamationFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }
    @FXML
    public void rembourssement(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AjouterRembourssementFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }


    @FXML
    void initialize() {
        assert date_reclamation1 != null : "fx:id=\"date_reclamation1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert heure1 != null : "fx:id=\"heure1\" was not injected: check your FXML file 'AjouterReclamationFXML.fxml'.";
        assert description1 != null : "fx:id=\"description1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        // assert idreclamation1 != null : "fx:id=\"idreclamation1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert stat != null : "fx:id=\"stat\" was not injected: check your FXML file 'AjouterReclamationFXML.fxml'.";
        assert titre1 != null : "fx:id=\"titre1\" was not injected: check your FXML file 'AjouterReclamationFXML.fxml'.";
    }


    public void initialize(URL url, ResourceBundle resourceBundle){
        //  ObservableList<String>

        int maxLength = 100;
        description1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                description1.setText(oldValue);
            }
        });
        type.setItems(FXCollections.observableArrayList("Service","Livraison","Produit"));
        stat.setItems(FXCollections.observableArrayList("Non traitée","En cours de traitement","traitee"));

        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>() {
            {
                setConverter(new javafx.util.StringConverter<>() {
                    @Override
                    public String toString(LocalTime object) {
                        return object != null ? object.toString() : "";
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        return string.isEmpty() ? null : LocalTime.parse(string);
                    }
                });
                setValue(LocalTime.now());
                date_reclamation1.setValue(LocalDate.now());
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };

        heure1.setValueFactory(timeValueFactory);
    }
}
