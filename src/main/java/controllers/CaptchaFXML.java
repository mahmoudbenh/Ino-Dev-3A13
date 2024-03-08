package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaptchaFXML implements Initializable {
    @FXML
    private Label captchaLabel;

    @FXML
    private TextField captchaCode;

    @FXML
    private Button refreshCaptcha;

    @FXML
    private Button submit;

    @FXML
    private Label msg;

    private String captcha;

    @FXML
    void onRefreshCaptcha(ActionEvent e) {
        captcha = generateCaptcha();
        captchaLabel.setText(captcha);
        captchaCode.setText("");
    }

    @FXML
    void onSubmit(ActionEvent event) {
        // Vérifier si le captcha est correct
        if (isCaptchaCorrect()) {
            try {
                // Charger la nouvelle fenêtre d'ajout de réclamation avec un message de réussite
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamationFXML.fxml"));
                Parent root = loader.load();
                AjouterReclamationFXML controller = loader.getController();
                controller.showSuccessMessage("Captcha correct. Vous pouvez ajouter une réclamation.");

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Fermer la fenêtre actuelle
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Afficher un message d'erreur indiquant que le captcha est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Captcha Incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Le captcha que vous avez saisi est incorrect. Veuillez réessayer.");
            alert.showAndWait();
        }
    }


    private String generateCaptcha() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    public boolean isCaptchaCorrect() {
        String userCaptcha = captchaCode.getText(); // Récupérer le texte saisi par l'utilisateur dans le champ du captcha
        return userCaptcha.equals(captcha); // Comparer le captcha saisi par l'utilisateur avec le captcha généré
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        captcha = generateCaptcha();
        captchaLabel.setText(captcha);
    }
}