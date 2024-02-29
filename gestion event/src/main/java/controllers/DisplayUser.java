package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import models.Card;
import models.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;;
import services.ServiceEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayUser implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private HBox dynamicbox;

    @FXML
    private HBox cardLayout;
    private List<Card> topEvents;
    private List<Event> listEvents;
    private ServiceEvent serviceEvent = new ServiceEvent();




    private List<Card> topEvents(){
        List<Card> ls =new ArrayList<>();
        Card card =new Card();
        card.setName("TechXcelerate");
        card.setImageC("/assets/event1.jpeg");
        card.setDes("TechXcelerate: Where AI innovation\n meets industry leaders, driving \n forward the future of technology.\n");
        ls.add(card);

        card  = new Card();
        card.setName("TechSphere \n Expo");
        card.setImageC("/assets/event2.jpeg");
        card.setDes("Step into the TechSphere Expo \n and immerse yourself in a world of \n cutting-edge technology.\n From robotics to virtual reality, experience the future today.");
        ls.add(card);

        card = new Card();
        card.setName("Digital Frontier\n Conference");
        card.setImageC("/assets/event3.jpg");
        card.setDes(" Embark on a journey to the Digital Frontier Conference,\n where the digital landscape meets endless possibilities.\n Discover the latest trends and innovations shaping our digital future.");
        ls.add(card);
        return ls;
    }

    @FXML
    private void displayEvent(List<Event> events) {
        for (Event  event : events) {
            HBox card = createEventCard(event);
            openCardDetailsPage(event);

        }
    }





    private HBox createEventCard(Event event) {
        HBox card = new HBox();
        card.setStyle("-fx-border-color: black; -fx-padding: 10;");
        Label nomlabel = new Label("Nom : " + event.getName());
        Label ideventlabel = new Label("ID: " + event.getId_event());
        Label desclabel = new Label("Description: " + event.getDescription());
        Label dateLabel = new Label("Date: " + event.getDescription());
        Label statutLabel = new Label("Statut: " + event.getStatut());
        Label lieulabel = new Label("Lieu: " + event.getLieu());

        card.getChildren().addAll(nomlabel,ideventlabel,desclabel, dateLabel, statutLabel,lieulabel);

        // Add any other UI elements as needed
        return card;
    }
    private void openCardDetailsPage(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CardDynamic.fxml"));
            Parent root = fxmlLoader.load();

            CardDynamic controller = fxmlLoader.getController();
            controller.setData(event);

            // Instead of creating a new stage and scene, add the root to the existing container
            dynamicbox.getChildren().add(root);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier FXML", e);
        }
    }



    @FXML
    void initialize() {
        assert cardLayout != null : "fx:id=\"cardLayout\" was not injected: check your FXML file 'DisplayUser.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topEvents = new ArrayList<>(topEvents());
        try {
            for (int i = 0; i < topEvents.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Card.fxml"));
                HBox cardBox = fxmlLoader.load();

                CardFXML card  = fxmlLoader.getController();
                card.initializeCard(topEvents.get(i));

                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Event> events;
        try {
            events = serviceEvent.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayEvent(events);

    }
}
