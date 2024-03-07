/*package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
//import javafx.event.Event;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Event;
import models.Participant;
import services.ServiceEvent;

public class ListeEvent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Participant,String> colEventName;

    @FXML
    private TableColumn<Participant,Integer> colUserId;

    @FXML
    private TableColumn<Participant,String> colUserName;

    @FXML
    private TableView<Participant> tbParticipants;
    @FXML
    private TextField tfSearch;

    private ServiceEvent  serviceEvent;

    private int eventId; // Set the event ID when navigating to this interface

    private List<Participant> allParticipants; // Store all participants for filtering
    private Event selectedEvent;


    public void initialize() {


        colUserId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserID()).asObject());
        colUserName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        //colEventName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // Load and display participants
        loadParticipants();
        setupButtonColumns();
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
        // Fetch the selected event based on the eventId
        try {
            ServiceEvent se = new ServiceEvent();
            this.selectedEvent = se.selectOne(eventId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        loadParticipants();
    }

    private void loadParticipants() {
        try {
            ServiceEvent se=new ServiceEvent();
            allParticipants = se.getParticipants(eventId);
            ObservableList<Participant> participantList = FXCollections.observableArrayList(allParticipants);
            tbParticipants.setItems(participantList);

            // Update participant count label
          //  lblParticipantCount.setText("Participant Count: " + allParticipants.size());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    @FXML
    private void searchParticipants() {
        String searchText = tfSearch.getText().toLowerCase().trim();

        List<Participant> filteredParticipants;

        if (searchText.isEmpty()) {
            // If search text is empty, display all participants
            if (selectedEvent == null) {
                // Fetch all participants
                try {
                    ServiceEvent se = new ServiceEvent();
                   // allParticipants = se.getParticipants(0); // Pass 0 to indicate all participants
                    allParticipants = se.getParticipants(selectedEvent.getId_event());
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle exception
                }
            }
            filteredParticipants = allParticipants;
        } else {
            // Filter participants based on the search text
            filteredParticipants = allParticipants.stream()
                    .filter(participant -> String.valueOf(participant.getUserID()).contains(searchText) ||
                            participant.getNom().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        }
        System.out.println("rechercher...");
        updateTableView(filteredParticipants);
    }

    private void updateTableView(List<Participant> participants) {
        ObservableList<Participant> participantList = FXCollections.observableArrayList(participants);
        tbParticipants.setItems(participantList);

        // Update participant count label
       // lblParticipantCount.setText("Participant Count: " + participants.size());
    }

    private void setupButtonColumns() {
        setupSupprimerButtonColumn();
    }
    private void setupSupprimerButtonColumn() {
        TableColumn<Participant, Void> supprimerColumn = new TableColumn<>("Supprimer");
        supprimerColumn.setCellFactory(col -> new TableCell<Participant, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Participant up = getTableView().getItems().get(getIndex());
                    // Action to perform when the "Supprimer" button is clicked
                    ServiceEvent se=new ServiceEvent();
                    System.out.println("Delete participant: " + up.getNom());
                    try {
                        se.deletetwo(up);
                        loadParticipants();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
        tbParticipants.getColumns().add(supprimerColumn);
    }*//*
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.ServiceEvent;
import models.Pivot;

import java.sql.SQLException;
import java.util.List;

public class ListeEvent {

    @FXML
    private TableColumn<Pivot, Integer> colIdUE;

    @FXML
    private TableColumn<Pivot, Integer> colUserId;

    @FXML
    private TableColumn<Pivot, Integer> colEventId;

    @FXML
    private TableView<Pivot> tbPivot;

    private int eventId; // Set the event ID when navigating to this interface

    public void initialize() {
        colIdUE.setCellValueFactory(cellData -> cellData.getValue().idUEProperty().asObject());
        colUserId.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        colEventId.setCellValueFactory(cellData -> cellData.getValue().eventIdProperty().asObject());

        // Load and display pivot entries
        loadPivotEntries();
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
        loadPivotEntries();
    }

    private void loadPivotEntries() {
        try {
            ServiceEvent se = new ServiceEvent();
            List<Pivot> pivotEntries = se.getPivotEntries(eventId);
            tbPivot.getItems().setAll(pivotEntries);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}*/

package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Event;
import models.Participant;
import services.ServiceEvent;
import services.ServiceParticipant;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListeEvent {

    @FXML
    private TableView<Participant> tbParticipants;

    @FXML
    private TableColumn<Participant, Integer> colUserId;

    @FXML
    private TableColumn<Participant, String> colUserName;

    @FXML
    private TableColumn<Participant, String> colEventName;

    private ServiceEvent serviceEvent = new ServiceEvent();
    private ServiceParticipant serviceParticipant = new ServiceParticipant();

    @FXML
    void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        try {
            // Retrieve all events
            List<Event> events = serviceEvent.selectAll();

            // Create an observable list to store all participants
            ObservableList<Participant> allParticipants = FXCollections.observableArrayList();

            // Iterate through each event
            for (Event event : events) {
                // Retrieve participants for the current event
                List<Participant> participants = serviceParticipant.getParticipantsByEventId(event.getId_event());

                // Set the event name for each participant
                serviceParticipant.setEventNameForParticipants(participants, event.getName());

                // Add participants to the observable list
                allParticipants.addAll(participants);
            }

            // Set the observable list of participants to the TableView
            tbParticipants.setItems(allParticipants);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void consulterListeEvenements(ActionEvent event) {
        // Handle button action if needed
    }

    // Implement event handling methods for buttons if needed
}







