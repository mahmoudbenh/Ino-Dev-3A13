
package controllers;
        import javafx.collections.FXCollections;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.Stage;

        import javafx.fxml.FXML;
        import models.rembourssement;

        import services.ServiceRembourssement;

        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.time.LocalDate;
        import java.util.ResourceBundle;

public class ModifierRembourssementFXML implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date_p;

    @FXML
    private TextField id_pp;

    @FXML
    private TextField id_recp;

    @FXML
    private TextField id_rembp;

    @FXML
    private ComboBox<String> modep;

    @FXML
    private TextField prix_pp;



    @FXML
    private ComboBox<String> statp;

    private final ServiceRembourssement sr=new ServiceRembourssement();
    private rembourssement rembourssementToModify;
    @FXML
    void modifierdy(ActionEvent event){
        try{

            int id_rembp2=Integer.parseInt(id_rembp.getText());
            int id_recp2=Integer.parseInt(id_recp.getText());
            int id_pp2=Integer.parseInt(id_pp.getText());
            float prix_pp2=Float.parseFloat(prix_pp.getText());

            sr.updateOne(new rembourssement(id_rembp2,id_recp2,id_pp2,prix_pp2,date_p.getValue(),statp.getValue(),modep.getValue()));

            showSuccessNotification("Modif réussie");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error executing SQL query", e);
        }

    }
    public void setRembourssementToModify(rembourssement rem)
    {
        this.rembourssementToModify = rem;
        // Ensure that the recommendation is not null before initializing the fields
        if(rem!= null)
        {
            id_rembp.setText(String.valueOf(rem.getId_rembourssement()));
            id_recp.setText(String.valueOf(rem.getId_reclamation()));
            id_pp.setText(String.valueOf(rem.getId_produit()));
            prix_pp.setText(String.valueOf(rem.getPrix_produit()));
            date_p.setValue(LocalDate.parse(String.valueOf(rem.getDate_rembourssement())));
            statp.setValue(String.valueOf(rem.getStatut_rembourssement()));
            modep.setValue(String.valueOf(rem.getMode_paiement()));
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
        id_rembp.setText(null);
        id_recp.setText(null);
        id_pp.setText(null);
        prix_pp.setText(null);
        date_p.setValue(null);
        statp.setValue(null);
        modep.setValue(null);
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loaderb = new FXMLLoader(getClass().getResource("/AfficherRembourssementFXML.fxml"));
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
        statp.setItems(FXCollections.observableArrayList("Non Rembourse", "Rembourse"));
        modep.setItems(FXCollections.observableArrayList("par carte", "cache", "coupon"));
    }
}
