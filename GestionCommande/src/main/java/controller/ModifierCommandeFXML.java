package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import model.Comande;
import service.ServiceComande;

public class ModifierCommandeFXML implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> LStatutC2;

    @FXML
    private Label idModif1;

    @FXML
    private DatePicker tdDate;

    @FXML
    private TextField tfIdCommande;

    @FXML
    private TextField tfIdLc;

    @FXML
    private TextField tfPrix;
    private Comande commande;
    public void setCommande(Comande commande) {
        this.commande = commande;
        afficherDetailsCommande() ;
    }
    private  void  afficherDetailsCommande(){
        try {
            // Appeler la méthode de service pour récupérer la liste des produits
            ServiceComande sc = new ServiceComande();
            List<Comande> commandes = sc.selectAll();

            // Créer une liste observable de produits
            ObservableList<Comande> commandeObservableList = FXCollections.observableArrayList(commandes);

            // Créer le ListView
            ListView<Comande> listView = new ListView<>(commandeObservableList);

            // Configurer la cellule de rendu pour afficher les détails de la commande
            listView.setCellFactory(param -> new ListCell<Comande>() {
                @Override
                protected void updateItem(Comande commande, boolean empty) {
                    super.updateItem(commande, empty);

                    if (empty || commande == null) {
                        setText(null);
                    } else {
                        setText("Id_Commande: "+ commande.getId_commande() + "| " +
                                "Id_LigneC: "+ commande.getId_lc()+ "| " +
                                "Date: "+ commande.getDate_commande() + "| " +
                                "Statut: "+ commande.getStatut_commande() + "| " +
                                "Prix: "+commande.getPrix_total());
                    }
                }
            });

        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
        }

    }
    public void setId(int id) {

        int id_commande = id;
        System.out.println("the id " + id_commande);
    }
    @FXML
    public void Affichage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommandeFXML.fxml"));
            Parent newPageRoot = loader.load();
            AfficherCommandeFXML afficherCommandeController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) tfIdCommande.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setCommandeToModify(Comande c)
    {
        this.commande = c;
        // Ensure that the recommendation is not null before initializing the fields
        if(c!= null)
        {
            tfIdCommande.setText(String.valueOf(c.getId_commande()));
            tfIdLc.setText(String.valueOf(c.getId_lc()));
            DatePicker datePicker = new DatePicker();
            String dateStringFromDatabase = c.getDate_commande();
            LocalDate date = LocalDate.parse(dateStringFromDatabase);
            datePicker.setValue(date);
            //B1.setText(String.valueOf(c.getDisponibilite()));
            LStatutC2.setValue(String.valueOf(c.getStatut_commande()));
            tfPrix.setText(String.valueOf(c.getPrix_total()));
        }
    }
    @FXML
    void Ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterComandeFXML.fxml"));
            tfIdCommande.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void modifierCommande(ActionEvent event) {
        try {
            int idCommande = Integer.parseInt(tfIdCommande.getText());
            int idLc = Integer.parseInt(tfIdLc.getText());
            LocalDate dateCommande = tdDate.getValue();
            String dateString = dateCommande.toString();
            String statut = LStatutC2.getValue();
            //LocalDate dateCommande = tdDate.getValue();
            //double prix = Integer.parseInt(tfPrix.getText());
            double prix = Double.parseDouble(tfPrix.getText());

            Comande c = new Comande(idCommande,idLc,dateString,statut,prix);
            ServiceComande sc = new ServiceComande();
            sc.updateOne(c);
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();
        }
    }

    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommandeFXML.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert LStatutC2 != null : "fx:id=\"LStatutC2\" was not injected: check your FXML file 'ModifierCommandeFXML.fxml'.";
        assert idModif1 != null : "fx:id=\"idModif1\" was not injected: check your FXML file 'ModifierCommandeFXML.fxml'.";
        assert tdDate != null : "fx:id=\"tdDate\" was not injected: check your FXML file 'ModifierCommandeFXML.fxml'.";
        assert tfIdCommande != null : "fx:id=\"tfIdCommande\" was not injected: check your FXML file 'ModifierCommandeFXML.fxml'.";
        assert tfIdLc != null : "fx:id=\"tfIdLc\" was not injected: check your FXML file 'ModifierCommandeFXML.fxml'.";
        assert tfPrix != null : "fx:id=\"tfPrix\" was not injected: check your FXML file 'ModifierCommandeFXML.fxml'.";

    }
   /* public void setCommandeToModify(Comande commande)
    {
        this.commande = commande;
        // Ensure that the recommendation is not null before initializing the fields
        if(commande!= null)
        {
            tfIdCommande.setText(String.valueOf(commande.getId_commande()));
            tfIdLc.setText(String.valueOf(commande.getId_lc()));
            tdDate.setValue(LocalDate.parse(String.valueOf(commande.getDate_commande())));
            LStatutC2.setText(commande.getStatut_commande());
            tfIdLc.setText(Double.valueOf(commande.getPrix_total()));
        }
    }*/
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LStatutC2.setItems(FXCollections.observableArrayList("en cours de traitement","expediee","annulation"));

    }
}
