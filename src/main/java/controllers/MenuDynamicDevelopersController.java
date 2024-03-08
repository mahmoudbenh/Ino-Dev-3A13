package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;

public class MenuDynamicDevelopersController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Fermer;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button Gestion_User;

    @FXML
    private ImageView UserImageView;

    @FXML
    private ImageView UserImageView1;

    private User currentUser;



    @FXML
    void Gestion_User(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/Front.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

<<<<<<< HEAD
    /*@FXML
    public void exit(){
        System.exit(0);
    }*/

    @FXML
    void exit(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
=======
    @FXML
    public void exit(){
        System.exit(0);
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
    }

    public void initializeUser(User user) {
        // Initialize the current user
        this.currentUser = user;

        // You can perform any initialization tasks with the user data here
        // For example, display user information in the UI
    }


    @FXML
    void initialize() {
        assert Fermer != null : "fx:id=\"Fermer\" was not injected: check your FXML file 'MenuDynamicDevelopers.fxml'.";
        assert Gestion_User != null : "fx:id=\"Gestion_User\" was not injected: check your FXML file 'MenuDynamicDevelopers.fxml'.";
        assert UserImageView != null : "fx:id=\"UserImageView\" was not injected: check your FXML file 'MenuDynamicDevelopers.fxml'.";
        assert UserImageView1 != null : "fx:id=\"UserImageView1\" was not injected: check your FXML file 'MenuDynamicDevelopers.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load Admin image
            File UserFile = new File("src/main/Images/Back.jpg");
            Image UserImage = new Image(UserFile.toURI().toString());
            UserImageView.setImage(UserImage);

            // Load User image
            File UserFile1 = new File("src/main/Images/User.png");
            Image UserImage1 = new Image(UserFile1.toURI().toString());
            UserImageView1.setImage(UserImage1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
