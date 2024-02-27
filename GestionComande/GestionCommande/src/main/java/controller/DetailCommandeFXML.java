package  controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Produit;

public class DetailCommandeFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox box;

    @FXML
    private Label description;

    @FXML
    private ImageView imageproduit;

    @FXML
    private Label nom;
    private final String[] colors={"B9E5FF","BDB2FE","FF5056"};
    public void setDta(Produit produit){
        String imagePath = produit.getImage();
        if (imagePath != null) {
            Image imagep = new Image(getClass().getResourceAsStream(imagePath));
            imageproduit.setImage(imagep);
        } else {
            // Handle the case where the image path is null
            System.out.println("Image Path is null");
        }
        nom.setText(produit.getNom());
        description.setText(produit.getDescription());
        box.setStyle("-fx-background-color: #"+colors[(int)(Math.random()*colors.length)]+";"+
                "-fx-background-radius: 15;"+
                "-fx-effect: dropShadow(three-passbox,rgba(0,0,0,0.1),10,0,0,10)");
    }

    @FXML
    void initialize() {
        assert box != null : "fx:id=\"box\" was not injected: check your FXML file 'DetailCommandeFXML.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'DetailCommandeFXML.fxml'.";
        assert imageproduit != null : "fx:id=\"imageproduit\" was not injected: check your FXML file 'DetailCommandeFXML.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'DetailCommandeFXML.fxml'.";

    }

}
