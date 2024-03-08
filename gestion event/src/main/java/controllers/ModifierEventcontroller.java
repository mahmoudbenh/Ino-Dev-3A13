package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.ServiceEvent;
import models.Event;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierEventcontroller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker Mdate;

    @FXML
    private TextField Mdescription;

    @FXML
    private TextField Mlieu;

    @FXML
    private TextField Mname;

    @FXML
    private ComboBox<String> Mstatut;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSettings1;

    @FXML
    private Button btnSettings11;

    @FXML
    private Button btnSettings111;

    @FXML
    private Button idbtncertif;

    @FXML
    private Button idbtnform;

    @FXML
    private ImageView imageevenement;

    // It's good practice to prefix member variables with something to denote they are member variables.
    // However, the Java community typically uses 'this.' to access member variables, rather than prefixing them with 'M'.
    private Event event; // Make sure this is the correct Event you want to use (your model)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Mstatut.setItems(FXCollections.observableArrayList("Done", "Not Done", "Pending"));
        // Consider calling here any initial setup method, like loading event details if needed.
    }
    @FXML
    void Annuler(ActionEvent event) {

    }

    @FXML
    void Exit(ActionEvent event) {
        // Get the reference to the button's stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
    @FXML
    void retour(ActionEvent event) {

    }

    public void setEvent(Event event) {
        this.event = event;
        // Assuming you want to display the details of 'event' in your form fields, you should do it here.
        // This method effectively acts as part of the "initialization" for this controller when an event is being modified.
        Mname.setText(event.getName());
        Mdescription.setText(event.getDescription());
        Mdate.setValue(event.getDate_event()); // Assuming getDateEvent() returns a LocalDate
        Mstatut.setValue(event.getStatut());
        // Mlieu.setText(event.getLieu()); // Uncomment and modify according to your Event model
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        try {
            String nom = Mname.getText();
            String description = Mdescription.getText();
            LocalDate date = Mdate.getValue();
            String statut = Mstatut.getValue(); // Correctly assuming this is a String
            String lieu = Mlieu.getText();
            // Assuming you have a way to get the current Event's id_event. Placeholder here:
            int currentId = this.event.getId_event(); // Ensure 'this.event' is correctly initialized

            Event e = new Event(currentId, nom, description, date, statut, lieu,this.event.getImage_event()); // Correctly matches an existing constructor
            ServiceEvent serviceEvent = new ServiceEvent();
            serviceEvent.updateOne(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void initialize() {
        assert Mdate != null : "fx:id=\"Mdate\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert Mdescription != null : "fx:id=\"Mdescription\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert Mlieu != null : "fx:id=\"Mlieu\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert Mname != null : "fx:id=\"Mname\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert Mstatut != null : "fx:id=\"Mstatut\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert btnCustomers != null : "fx:id=\"btnCustomers\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert btnOrders != null : "fx:id=\"btnOrders\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert btnSettings != null : "fx:id=\"btnSettings\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert btnSettings1 != null : "fx:id=\"btnSettings1\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert btnSettings11 != null : "fx:id=\"btnSettings11\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert btnSettings111 != null : "fx:id=\"btnSettings111\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert idbtncertif != null : "fx:id=\"idbtncertif\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert idbtnform != null : "fx:id=\"idbtnform\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";
        assert imageevenement != null : "fx:id=\"imageevenement\" was not injected: check your FXML file 'ModifierEventcontroller.fxml'.";

    }

}
