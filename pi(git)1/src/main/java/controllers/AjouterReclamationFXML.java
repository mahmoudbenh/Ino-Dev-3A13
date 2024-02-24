
package controllers;

        import java.awt.*;
        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.util.ResourceBundle;
        import javafx.collections.ObservableList;
        import javafx.fxml.Initializable;
        import javafx.collections.FXCollections;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;
        import services.ServiceReclamation;
        import models.reclamation;
public class AjouterReclamationFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date_reclamation1;

    @FXML
    private TextField description1;

    @FXML
    private TextField id_client1;


    /*@FXML
    private TextField type_reclamation1;*/
    @FXML
    private ComboBox<String> type;
   /* @FXML
    private TextField idreclamation1;*/

    @FXML
    private ComboBox<String> stat;

    @FXML
    private TextField titre1;


    // private reclamation reclamationtomodify;
    @FXML
    void ajouterreclamation(ActionEvent event) {
        try
        {
            String type_reclamation = type.getValue();
            String statut_reclamation = stat.getValue();
            reclamation r = new reclamation(Integer.parseInt(id_client1.getText()), titre1.getText(), date_reclamation1.getValue(),type_reclamation,statut_reclamation,description1.getText());
            ServiceReclamation sr = new ServiceReclamation();
            sr.insertOne(r);
        }
        catch (NumberFormatException e)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void annuler(ActionEvent event) {
        id_client1.setText(null);
        titre1.setText(null);
        description1.setText(null);
        type.setValue(null);
        stat.setValue(null);
        date_reclamation1.setValue(null);


    }
    @FXML
    public void changeScreenButtonPushed(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherReclamationFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }


    @FXML
    public void reclamation(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AjouterReclamationFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }
    @FXML
    public void rembourssement(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AjouterRembourssementFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }


    @FXML
    void initialize() {
        assert date_reclamation1 != null : "fx:id=\"date_reclamation1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";

        assert description1 != null : "fx:id=\"description1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";

        assert id_client1 != null : "fx:id=\"id_client1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";

       // assert idreclamation1 != null : "fx:id=\"idreclamation1\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";

        assert type != null : "fx:id=\"type\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";

        assert stat != null : "fx:id=\"stat\" was not injected: check your FXML file 'AjouterReclamationFXML.fxml'.";
        assert titre1 != null : "fx:id=\"titre1\" was not injected: check your FXML file 'AjouterReclamationFXML.fxml'.";
    }


    public void initialize(URL url, ResourceBundle resourceBundle){
        //  ObservableList<String>
        type.setItems(FXCollections.observableArrayList("Service","Livraison","Produit"));
        stat.setItems(FXCollections.observableArrayList("Non traitée","En cours de traitement","traitee"));
    }
}