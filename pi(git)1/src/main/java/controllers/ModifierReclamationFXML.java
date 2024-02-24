package controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import models.reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceReclamation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierReclamationFXML implements Initializable {
    @FXML
    private DatePicker date_reclamationm;

    @FXML
    private TextField descriptionm;

    @FXML
    private TextField id_clientm;
    @FXML
    private ComboBox<String> type_reclamationm;
    @FXML
    private TextField idreclamationm;
    @FXML
    private ComboBox<String> statm2;

    @FXML
    private TextField titrem2;
    private final ServiceReclamation sr=new ServiceReclamation();
    private reclamation reclamationToModify;
    @FXML
    void modifierdy(ActionEvent event){
        try{

            int idreclamationm2=Integer.parseInt(idreclamationm.getText());
            int id_clientm2=Integer.parseInt(id_clientm.getText());

            sr.updateOne(new reclamation(idreclamationm2,id_clientm2,titrem2.getText(),date_reclamationm.getValue(),type_reclamationm.getValue(),statm2.getValue(),descriptionm.getText()));

            showSuccessNotification("Modif réussie");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error executing SQL query", e);
        }

    }
    public void setReclamationToModify(reclamation rec)
    {
        this.reclamationToModify = rec;
        // Ensure that the recommendation is not null before initializing the fields
        if(rec!= null)
        {
            idreclamationm.setText(String.valueOf(rec.getId_reclamation()));
            id_clientm.setText(String.valueOf(rec.getId_client()));
            titrem2.setText(String.valueOf(rec.getTitre_reclamation()));
            date_reclamationm.setValue(LocalDate.parse(String.valueOf(rec.getDate_reclamation())));
            type_reclamationm.setValue(String.valueOf(rec.getType_reclamation()));
            statm2.setValue(String.valueOf(rec.getStatut_reclamation()));
            descriptionm.setText(String.valueOf(rec.getDescription()));
        }
    }
    void showSuccessNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    void showErrorNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void annuler(ActionEvent event) {
        idreclamationm.setText(null);
        id_clientm.setText(null);
        titrem2.setText(null);
        date_reclamationm.setValue(null);
        type_reclamationm.setValue(null);
        statm2.setValue(null);
        descriptionm.setText(null);

    }
    @FXML
    void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loaderb = new FXMLLoader(getClass().getResource("/AfficherReclamationFXML.fxml"));
            Parent root = loaderb.load();

            // Créer une nouvelle scène
            Scene sceneb = new Scene(root);

            // Obtenir la référence à la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(sceneb);

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement du FXML
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        //  ObservableList<String>
        type_reclamationm.setItems(FXCollections.observableArrayList("Service","Livraison","Produit"));
        statm2.setItems(FXCollections.observableArrayList("Non traitée","En cours de traitement","traitee"));
    }
}
