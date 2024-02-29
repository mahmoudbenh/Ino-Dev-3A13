package controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;

public class FrontController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button acceuilbtn;

    /*@FXML
    private TextField mail;*/

    @FXML
    private TextField nom;

    @FXML
    private AnchorPane rootPane2;

    @FXML
    private TextField prenom;

    @FXML
    private TextField mdp;

    @FXML
    private Button qrcode;

    @FXML
    private Button upd;

    private User currentUser; // Not static anymore

    // Method to set the current user
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Method to get the current user
    public User getCurrentUser() {
        return currentUser;
    }



    // Method to initialize the user information in the UI components
    public void initializeUser(User user) {
        if (user != null) {
            nom.setText(user.getNom()); // Set the first name
            prenom.setText(user.getPrenom()); // Set the last name
            mdp.setText(user.getMdp()); // Set the email
            // i can set other fields as needed
        } else {
            // Handle the case where the user is null
            // For example, clear the text fields
            nom.clear();
            prenom.clear();
            mdp.clear();
        }
    }




    @FXML
    void ActionAcceuilbtn(ActionEvent event) throws IOException {
        // Load the login.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        BorderPane loginPane = loader.load();

        // Get the root pane of the current scene
        BorderPane rootPane = (BorderPane) rootPane2.getScene().getRoot();

        // Replace the center content of the root pane with the loginPane
        rootPane.setCenter(loginPane);
    }

    @FXML
    void upd(ActionEvent event) {
        if (currentUser != null) {
            // Add a debug statement to check currentUser
            System.out.println("Current User: " + currentUser.toString());

            // Retrieve the modified values from the text fields
            String newNom = nom.getText();
            String newPrenom = prenom.getText();
            String newMdp = mdp.getText();

            // Update the user object with the new values
            currentUser.setNom(newNom);
            currentUser.setPrenom(newPrenom);
            currentUser.setMdp(newMdp);

            // Call the modifier method in ServiceUser to update the user in the database
            ServiceUser serviceUser = new ServiceUser();
            boolean success = serviceUser.modifier(currentUser);

            // Display debug messages to troubleshoot
            if (success) {
                System.out.println("User information updated successfully in the database.");
                showAlert(Alert.AlertType.INFORMATION, "Success", "User information updated successfully.");
            } else {
                System.err.println("Failed to update user information in the database.");
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user information.");
            }
        } else {
            System.err.println("No user information available to update.");
            showAlert(Alert.AlertType.ERROR, "Error", "No user information available.");
        }
    }


    @FXML
    void qrAction(ActionEvent event) {

    }



    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void initialize() {

        assert acceuilbtn != null : "fx:id=\"acceuilbtn\" was not injected: check your FXML file 'Front.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'Front.fxml'.";
        assert prenom != null : "fx:id=\"prenom\" was not injected: check your FXML file 'Front.fxml'.";
        assert qrcode != null : "fx:id=\"qrcode\" was not injected: check your FXML file 'Front.fxml'.";
        assert upd != null : "fx:id=\"upd\" was not injected: check your FXML file 'Front.fxml'.";

        // Retrieve the current user using the static method
        User currentUser = User.getCurrent_User();

        // Call initializeUser method to set the current user information
        initializeUser(currentUser);

    }

}
