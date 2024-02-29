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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BackController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView AdminImageView;

    @FXML
    private ImageView AdminImageView1;

    @FXML
    private Button Fermer;

    @FXML
    private Button Gestion_User;

    @FXML
    void Gestion_User(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    void initialize() {
        assert AdminImageView != null : "fx:id=\"AdminImageView\" was not injected: check your FXML file 'Back.fxml'.";
        assert AdminImageView1 != null : "fx:id=\"AdminImageView1\" was not injected: check your FXML file 'Back.fxml'.";
        assert Fermer != null : "fx:id=\"Fermer\" was not injected: check your FXML file 'Back.fxml'.";
        assert Gestion_User != null : "fx:id=\"Gestion_User\" was not injected: check your FXML file 'Back.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            File AdminFile1 = new File("src/main/Images/Admin.png");
            Image AdminImage1 = new Image(AdminFile1.toURI().toString());
            AdminImageView1.setImage(AdminImage1);

            File AdminFile = new File("src/main/Images/Back.jpg");
            Image AdminImage = new Image(AdminFile.toURI().toString());
            AdminImageView.setImage(AdminImage);



        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
