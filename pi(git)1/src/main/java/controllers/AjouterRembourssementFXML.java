package controllers;


        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.util.ResourceBundle;

        import javafx.fxml.Initializable;
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
        import javafx.stage.Stage;
        import services.ServiceRembourssement;
        import models.rembourssement;

        import static javafx.collections.FXCollections.*;

public class AjouterRembourssementFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;




    @FXML
    private DatePicker date_remboursr;


    @FXML
    private TextField id_produitr;

    @FXML
    private TextField id_reclamr;

    @FXML
    private TextField id_rembcol;

    @FXML
    private ComboBox<String> moder;

    @FXML
    private TextField prix_produitr;

    @FXML
    private ComboBox<String> statr;

    @FXML

    void ajouterrembourssement(ActionEvent event) {
        try
        {
            String statut_rembourssement = statr.getValue();
            String mode_rembourssement = moder.getValue();
            rembourssement r = new rembourssement(Integer.parseInt(id_reclamr.getText()),Integer.parseInt(id_produitr.getText()),Float.parseFloat(prix_produitr.getText()),date_remboursr.getValue(),statut_rembourssement,mode_rembourssement);
            ServiceRembourssement sr = new ServiceRembourssement();
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
    void annulerrembourssement(ActionEvent event) {
        id_reclamr.setText(null);
        id_produitr.setText(null);
        prix_produitr.setText(null);
        date_remboursr.setValue(null);
        statr.setValue(null);
        moder.setValue(null);


    }
    @FXML
    public void changeScreenButtonPushedr(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherRembourssementFXML.fxml"));
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
        assert date_remboursr != null : "fx:id=\"date_remboursr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert id_produitr != null : "fx:id=\"id_produitr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert id_reclamr != null : "fx:id=\"id_reclamr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert id_rembcol != null : "fx:id=\"id_rembcol\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert moder != null : "fx:id=\"moder\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert prix_produitr != null : "fx:id=\"prix_produitr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert statr != null : "fx:id=\"statr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";

    }


    public void initialize(URL url, ResourceBundle resourceBundle){
        //  ObservableList<String>
        statr.setItems(observableArrayList("Non Rembourse", "Rembourse"));
        moder.setItems(observableArrayList("par carte", "cache", "coupon"));

    }
}