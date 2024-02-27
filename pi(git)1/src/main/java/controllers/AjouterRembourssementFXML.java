package controllers;


        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.time.LocalTime;
        import java.util.ResourceBundle;

        import javafx.fxml.Initializable;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
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
    private Spinner<LocalTime> heure_remboursr;


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
            rembourssement r = new rembourssement(Float.parseFloat(prix_produitr.getText()),date_remboursr.getValue(),heure_remboursr.getValue(),statut_rembourssement,mode_rembourssement);
            ServiceRembourssement sr = new ServiceRembourssement();
            sr.insertOne(r);
            showSuccessNotification("rembourssement en cours de traitement");
        }
        catch (SQLException e)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("vous devez entrer tout les informations necessaires!");
            alert.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }
    void showSuccessNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void annulerrembourssement(ActionEvent event) {

        prix_produitr.setText(null);
        date_remboursr.setValue(null);
        statr.setValue(null);
        moder.setValue(null);
        heure_remboursr.getValueFactory().setValue(null);


    }
    @FXML
    void raffraichir(ActionEvent event) {

        date_remboursr.setValue(LocalDate.now());
        // Mettre à jour l'heure avec l'heure actuelle
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>() {
            {
                setValue(LocalTime.now());
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };

        heure_remboursr.setValueFactory(timeValueFactory);
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
        assert heure_remboursr != null : "fx:id=\"heure_remboursr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert id_rembcol != null : "fx:id=\"id_rembcol\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert moder != null : "fx:id=\"moder\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert prix_produitr != null : "fx:id=\"prix_produitr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";
        assert statr != null : "fx:id=\"statr\" was not injected: check your FXML file 'AjouterRembourssementFXML.fxml'.";

    }


    public void initialize(URL url, ResourceBundle resourceBundle){
        //  ObservableList<String>

        statr.setItems(observableArrayList("Non Rembourse", "Rembourse"));
        moder.setItems(observableArrayList("par carte", "cache", "coupon"));
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>() {
            {
                setConverter(new javafx.util.StringConverter<>() {
                    @Override
                    public String toString(LocalTime object) {
                        return object != null ? object.toString() : "";
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        return string.isEmpty() ? null : LocalTime.parse(string);
                    }
                });
                setValue(LocalTime.now());
                date_remboursr.setValue(LocalDate.now());
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };

        heure_remboursr.setValueFactory(timeValueFactory);
    }


}
