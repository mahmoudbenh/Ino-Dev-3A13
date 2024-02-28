package controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Card;
import javafx.scene.paint.Color;



public class CardFXML  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox box;

    @FXML
    private Label des;

    @FXML
    private Label name;

    @FXML
    private ImageView imageC;
  private final String[] colors={"B9E5FF","BDB2FE","FF5056"};




    public void initializeCard(Card card) {
        String imagePath = card.getImageC();
        if (imagePath != null) {
            Image image1 = new Image(getClass().getResourceAsStream(imagePath));
            imageC.setImage(image1);
        } else {
            System.out.println("Image Path is null");
        }
        name.setText(card.getName());
        des.setText(card.getDes());
 box.setStyle("-fx-background-color: #"+colors[(int)(Math.random()*colors.length)]+";"+
            "-fx-background-radius: 15;"+
            "-fx-effect: dropShadow(three-passbox,rgba(0,0,0,0.1),10,0,0,10)");
}





    @FXML
    void initialize() {
        assert box != null : "fx:id=\"box\" was not injected: check your FXML file 'Card.fxml'.";
        assert des != null : "fx:id=\"des\" was not injected: check your FXML file 'Card.fxml'.";
        assert name != null : "fx:id=\"eventname\" was not injected: check your FXML file 'Card.fxml'.";
        assert imageC != null : "fx:id=\"image\" was not injected: check your FXML file 'Card.fxml'.";

    }

}

