package controllers;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.GMailer;
import services.ServiceUser;

public class RestPassController implements Initializable {
    String emailPW = null;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private TextField cnvPW;

    @FXML
    private TextField code;

    @FXML
    private Button codeConfirmation;

    @FXML
    private Label codeLab;

    @FXML
    private Label cpwLab;

    @FXML
    private TextField email;

    @FXML
    private Label emailLab;

    @FXML
    private TextField nvPw;

    @FXML
    private Label pwLab;

    @FXML
    private Button resetPW;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private BorderPane rootPane2;

    @FXML
    private Button verifCode;

    // Déclaration de la variable sU
    private GMailer sU;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialisation de la variable sU
            sU = new GMailer();
            visibility(true,false,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void visibility(boolean phase1, boolean phase2, boolean phase3) {
        emailLab.setVisible(phase1);
        email.setVisible(phase1);
        codeConfirmation.setVisible(phase1); // fin phase 1
        codeLab.setVisible(phase2);
        code.setVisible(phase2);
        verifCode.setVisible(phase2); // fin phase 2
        pwLab.setVisible(phase3);
        nvPw.setVisible(phase3);
        cpwLab.setVisible(phase3);
        cnvPW.setVisible(phase3);
        resetPW.setVisible(phase3); // fin phase 3
    }

    Random rand = new Random();
    int codeVerification = rand.nextInt((1000) + (9999));
    String codeVerif = String.valueOf(codeVerification);

    private void BackToLogin() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) resetPW.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/login.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    void codeConfirmation(ActionEvent event) throws Exception {
        String mail = email.getText();
        GMailer sU = new GMailer();


        if (email.getText().isEmpty()
                || !email.getText().contains("@")
                || !email.getText().contains(".")
                || email.getText().indexOf("#", 0) >= 0
                || email.getText().indexOf("&", 0) >= 0
                || email.getText().indexOf("(", 0) >= 0
                || email.getText().indexOf("Â§", 0) >= 0
                || email.getText().indexOf("!", 0) >= 0
                || email.getText().indexOf("Ã§", 0) >= 0
                || email.getText().indexOf("Ã ", 0) >= 0
                || email.getText().indexOf("Ã©", 0) >= 0
                || email.getText().indexOf(")", 0) >= 0
                || email.getText().indexOf("{", 0) >= 0
                || email.getText().indexOf("}", 0) >= 0
                || email.getText().indexOf("|", 0) >= 0
                || email.getText().indexOf("$", 0) >= 0
                || email.getText().indexOf("*", 0) >= 0
                || email.getText().indexOf("â‚¬", 0) >= 0
                || email.getText().indexOf("`", 0) >= 0
                || email.getText().indexOf("\'", 0) >= 0
                || email.getText().indexOf("\"", 0) >= 0
                || email.getText().indexOf("%", 0) >= 0
                || email.getText().indexOf("+", 0) >= 0
                || email.getText().indexOf("=", 0) >= 0
                || email.getText().indexOf("/", 0) >= 0
                || email.getText().indexOf("\\", 0) >= 0
                || email.getText().indexOf(":", 0) >= 0
                || email.getText().indexOf(",", 0) >= 0
                || email.getText().indexOf("?", 0) >= 0
                || email.getText().indexOf(";", 0) >= 0
                || email.getText().indexOf("Â°", 0) >= 0
                || email.getText().indexOf("<", 0) >= 0
                || email.getText().indexOf(">", 0) >= 0)
        {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setContentText("Vous devez saisir un mail valid");
            alert.showAndWait();
            return;
        }

        if ( !sU.verifierEmailBd(mail)) {
            sU.alert_Box("Verifiez votre adresse", "Veuillez saisir une adresse mail valide");
        } else {
            emailPW = mail;
            sU.sendMail(mail, "Mise a jour du mot de passe", "voici votre code de " + codeVerification + "  ");
            sU.information_Box("Succés", "verifiez votre boite mail");
            visibility(false,true,false);
        }
    }

    @FXML
    void resetPW(ActionEvent event) throws Exception {
        String h = Hash();
        String password = nvPw.getText();
        String cPassword = cnvPW.getText();

        if (!password.equals(cPassword)) {
            sU.alert_Box("Verifiez votre mot de passe", "Veillez verifier votre mot de passe ");
        } else {
            sU.modifierPassword(emailPW, (h+ password));
            sU.information_Box("mot de passe", "mot de passe changé avec succes");

            // Charger le fichier login.fxml dans un nouveau Stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            ((Stage) rootPane.getScene().getWindow()).close();
        }
    }



    @FXML
    void verifCode(ActionEvent event) throws Exception {
        GMailer sU = new GMailer();
        String codeV = code.getText();
        if (!codeV.equals(this.codeVerif)) {
            sU.alert_Box("Verifier ", "Veillez verifier le code ");
        } else {
            sU.information_Box("Code verifie", "Vous pouvez  changer votre mot de passe maintenant");
            visibility(false,false,true);
        }
    }

    public String Hash() throws Exception {

        String mdp_user = "";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdp_user.getBytes());

        byte byteData[] = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }




    @FXML
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert cnvPW != null : "fx:id=\"cnvPW\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert code != null : "fx:id=\"code\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert codeConfirmation != null : "fx:id=\"codeConfirmation\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert codeLab != null : "fx:id=\"codeLab\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert cpwLab != null : "fx:id=\"cpwLab\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert emailLab != null : "fx:id=\"emailLab\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert nvPw != null : "fx:id=\"nvPw\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert pwLab != null : "fx:id=\"pwLab\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert resetPW != null : "fx:id=\"resetPW\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'RestPass.fxml'.";
        assert verifCode != null : "fx:id=\"verifCode\" was not injected: check your FXML file 'RestPass.fxml'.";

    }

}
