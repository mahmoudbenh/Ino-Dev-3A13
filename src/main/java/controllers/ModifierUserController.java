package controllers;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import models.Role;

//import javax.swing.text.html.ImageView;

public class ModifierUserController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button modifier_btn;

    @FXML
    private TextField Nom;
    @FXML
    private TextField Prenom;
    @FXML
    private TextField Email;
    @FXML
    private TextField Mdp;
    @FXML
    private TextField Role;
    @FXML
    private TextField idUser;
    @FXML
    private ImageView UserImageView;
    @FXML
    private AnchorPane rootPane;

    @FXML
    void ModifierU(ActionEvent event) {
        int id_u = Integer.parseInt(idUser.getText());
        ServiceUser sp = new ServiceUser();

        // Validate user input and update user data accordingly
        User modifiedUser = new User(id_u, Nom.getText(), Prenom.getText(), Email.getText(), Mdp.getText());

        // Call the modified modifier method in ServiceUser to update the user in the database
        boolean success = sp.modifier(modifiedUser);

        if (success) {
            // If the modification was successful, close the stage
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        } else {
            // If the modification failed, display an error message
            System.err.println("Failed to modify user.");
        }
    }



    @FXML
    void initialize() {
        assert UserImageView != null : "fx:id=\"UserImageView\" was not injected: check your FXML file 'ModifierUser.fxml'.";
        assert modifier_btn != null : "fx:id=\"modifier_btn\" was not injected: check your FXML file 'ModifierUser.fxml'.";
    }

    public void setTextFields(User R){
        idUser.setText(String.valueOf(R.getUserID()));
        Nom.setText(R.getNom());
        Prenom.setText(R.getPrenom());
        Email.setText(R.getEmail());
        Mdp.setText(R.getMdp());
        //Role.setText(Role.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File UserFile = new File("src/main/Images/416384399_3683592111886021_5329792893211008472_n.png");
        Image UserImage = new Image(UserFile.toURI().toString());
        UserImageView.setImage(UserImage);
    }
}
