package controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Role;
import models.User;
import services.ServiceUser;

public class RegistreController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closeButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private Button registreButton;

    @FXML
    private PasswordField setPasswordField;

    @FXML
    private ImageView shieldImageView;

    @FXML
    private ImageView i1View;
    @FXML
    private ImageView i2View;
    @FXML
    private ImageView i3View;
    @FXML
    private ImageView i4View;
    @FXML
    private ImageView i5View;
    @FXML
    private ImageView i6View;


    @FXML
    private Image shieldImage;

    @FXML
    private TextField usernameTextField;

    @FXML
    private  TextField emailTextField;

    @FXML
    void initialize() {
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'registre.fxml'.";
        assert confirmPasswordField != null : "fx:id=\"confirmPasswordField\" was not injected: check your FXML file 'registre.fxml'.";
        assert firstnameTextField != null : "fx:id=\"firstnameTextField\" was not injected: check your FXML file 'registre.fxml'.";
        assert lastnameTextField != null : "fx:id=\"lastnameTextField\" was not injected: check your FXML file 'registre.fxml'.";
        assert registreButton != null : "fx:id=\"registreButton\" was not injected: check your FXML file 'registre.fxml'.";
        assert setPasswordField != null : "fx:id=\"setPasswordField\" was not injected: check your FXML file 'registre.fxml'.";
        assert shieldImageView != null : "fx:id=\"shieldImageView\" was not injected: check your FXML file 'registre.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'registre.fxml'.";

    }


    public void registreButtonOnAction() {
        String nom = firstnameTextField.getText();
        String prenom = lastnameTextField.getText();
        String email = emailTextField.getText();
        String mdp = setPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        Role role = Role.CLIENT; // Rôle par défaut

        // Vérification des champs obligatoires
        if (nom.isEmpty() || prenom.isEmpty() || mdp.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs obligatoires", "please grab all fields.");
            return;
        }

        // Vérification de la similitude entre le mot de passe et la confirmation
        if (!mdp.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Vérification du format de l'email
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Format d'email invalide", "Veuillez saisir une adresse email valide.");
            return;
        }

        // Si toutes les validations sont réussies, ajoutez l'utilisateur à la base de données
        User utilisateur = new User(nom, prenom, mdp, email, Role.CLIENT);

        // Créer une instance du service utilisateur
        ServiceUser serviceUser = new ServiceUser();

        // Utiliser le service utilisateur pour ajouter l'utilisateur à la base de données
        serviceUser.insertOne1(utilisateur);

        // Afficher un message de succès
        showAlert(Alert.AlertType.INFORMATION, "Succès", "L'utilisateur a été ajouté avec succès !");

        // Clear input fields after successful registration
        firstnameTextField.setText("");
        lastnameTextField.setText("");
        usernameTextField.setText("");
        emailTextField.setText("");
        setPasswordField.setText("");
        confirmPasswordField.setText("");
    }


    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(emailTextField.getText());

        if(match.find() && match.group().equals(emailTextField.getText()))
        {
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();

            return false;
        }
    }


        private void showAlert(Alert.AlertType type, String title, String message) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load branding image
        File shieldFile = new File("src/main/Images/pngtree-vector-lock-icon-png-image_316863.jpg");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);

        File f1 = new File("src/main/Images/id-card.png");
        Image i1 = new Image(f1.toURI().toString());
        i1View.setImage(i1);

        File f2 = new File("src/main/Images/id-card.png");
        Image i2 = new Image(f2.toURI().toString());
        i2View.setImage(i2);

        File f3 = new File("src/main/Images/id-card.png");
        Image i3 = new Image(f3.toURI().toString());
        i3View.setImage(i3);

        File f4 = new File("src/main/Images/email.png");
        Image i4 = new Image(f4.toURI().toString());
        i4View.setImage(i4);

        File f5 = new File("src/main/Images/key.png");
        Image i5 = new Image(f5.toURI().toString());
        i5View.setImage(i5);

        File f6 = new File("src/main/Images/key.png");
        Image i6 = new Image(f6.toURI().toString());
        i6View.setImage(i6);

    }
}
