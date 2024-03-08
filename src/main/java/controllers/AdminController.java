package controllers;

<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.DatabaseMetaData;
<<<<<<< HEAD
//import com.sun.javafx.tk.quantum.PaintRenderJob;
=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
<<<<<<< HEAD
import javafx.stage.FileChooser;
=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;
import services.ServiceUser;
<<<<<<< HEAD
import test.FXMain;

import javax.swing.*;

import static java.time.zone.ZoneRulesProvider.refresh;
//import static jdk.vm.ci.aarch64.AArch64.sp;
=======

import static java.time.zone.ZoneRulesProvider.refresh;
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214

public class AdminController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
<<<<<<< HEAD
    @FXML
    private Button generatePdf;
=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214

    @FXML
    private TableColumn<?, ?> Email;

    @FXML
    private TableColumn<?, ?> Mdp;

    @FXML
    private TableColumn<?, ?> Nom;

    @FXML
    private TableColumn<?, ?> Prenom;

    @FXML
    private TextField Recherche_User;

    @FXML
    private TableColumn<?, ?> Role;

    @FXML
    private TableColumn<?, ?> idUser;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button SupprimerUser;

<<<<<<< HEAD

    @FXML
    private Button generatePDF;

=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
    @FXML
    private Button ModifierUser;

    @FXML
    private TableView<User> tableviewUser;

    private Connection cnx;

    User user = null ;

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
    }

    @FXML
    void handleMouseAction(MouseEvent event) {

    }

    @FXML
    private void ModifierUser(ActionEvent event) {
        user = tableviewUser.getSelectionModel().getSelectedItem();
        if (user != null) { // Check if the selected user is not null
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ModifierUser.fxml"));
            try {
                loader.load();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            ModifierUserController muc = loader.getController();
            muc.setTextFields(user);
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait(); // Show the modification window and wait for it to be closed

            // After the modification window is closed, refresh the table data
            showRec();
            searchRec();
        } else {
            // Handle the case where no user is selected, e.g., display an error message
            System.out.println("No user selected.");
        }
    }



    @FXML
    private void SupprimerUser(ActionEvent event) {
        ServiceUser u=new ServiceUser();
        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        u.supprimer(user);
        refresh();
        searchRec();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé");
        alert.showAndWait();

    }

    @FXML
    void showRec() {
        ServiceUser sp = new ServiceUser();
        ObservableList<User> list = sp.selectAll();
        idUser.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Mdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));
        tableviewUser.setItems(list);
        refresh();
        searchRec();
    }

    private void searchRec() {
        ServiceUser s = new ServiceUser();
        //String lastName = Recherche_User.getText();
        ObservableList<User> list = s.getUser();

        // Set up the cell value factories
        idUser.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Mdp.setCellValueFactory(new PropertyValueFactory<>("mdp"));
        Role.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Set the items of the tableviewUser with the filtered list
        tableviewUser.setItems(list);

        // Set up filtering
        FilteredList<User> filteredData = new FilteredList<>(list, b -> true);
        Recherche_User.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return user.getNom().toLowerCase().contains(lowerCaseFilter)
                        || user.getPrenom().toLowerCase().contains(lowerCaseFilter)
                        || user.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Bind the filtered list to the tableviewUser
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableviewUser.comparatorProperty());
        tableviewUser.setItems(sortedData);
    }



<<<<<<< HEAD
    /*@FXML
    void initialize() {
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Mdp != null : "fx:id=\"Mdp\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Nom != null : "fx:id=\"Nom\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Prenom != null : "fx:id=\"Prenom\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Recherche_User != null : "fx:id=\"Recherche_User\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Role != null : "fx:id=\"Role\" was not injected: check your FXML file 'Admin.fxml'.";
        assert idUser != null : "fx:id=\"idUser\" was not injected: check your FXML file 'Admin.fxml'.";
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'Admin.fxml'.";
        assert tableviewUser != null : "fx:id=\"tableviewUser\" was not injected: check your FXML file 'Admin.fxml'.";

    }*/

=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
    @FXML
    void initialize() {
        assert Email != null : "fx:id=\"Email\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Mdp != null : "fx:id=\"Mdp\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Nom != null : "fx:id=\"Nom\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Prenom != null : "fx:id=\"Prenom\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Recherche_User != null : "fx:id=\"Recherche_User\" was not injected: check your FXML file 'Admin.fxml'.";
        assert Role != null : "fx:id=\"Role\" was not injected: check your FXML file 'Admin.fxml'.";
<<<<<<< HEAD
        assert generatePdf != null : "fx:id=\"generatePdf\" was not injected: check your FXML file 'Admin.fxml'.";
=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
        assert idUser != null : "fx:id=\"idUser\" was not injected: check your FXML file 'Admin.fxml'.";
        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'Admin.fxml'.";
        assert tableviewUser != null : "fx:id=\"tableviewUser\" was not injected: check your FXML file 'Admin.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
<<<<<<< HEAD
        // Charger le fichier CSS
        //rootPane.getStylesheets().add(getClass().getResource("src/main/Images/design.css").toExternalForm());
        showRec();
        searchRec();
    }

    /*@FXML
    public void generatePDF() {
        ServiceUser sp = new ServiceUser();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Get the primary stage from FXMain
        Stage primaryStage = (Stage) generatePdf.getScene().getWindow(); //

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                sp.generatePDF(file);
                JOptionPane.showMessageDialog(null, "Le PDF a été généré");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la génération du PDF.");
            }
        }
    }*/

    @FXML
    public void generatePDF() {
        ServiceUser sp = new ServiceUser();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (.pdf)", ".pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Get the primary stage from FXMain
        Stage primaryStage = (Stage) generatePdf.getScene().getWindow(); //

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                // Check if a row is selected in your table
                User selectedUser = tableviewUser.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    // Generate PDF for the selected row only
                    sp.generatePDF(file, selectedUser);
                } else {
                    // Generate PDF for all rows
                    //sp.generatePDF(file);
                }
                JOptionPane.showMessageDialog(null, "Le PDF a été généré");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la génération du PDF.");
            }
        }
    }

=======
        showRec();
        searchRec();
    }
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
}
