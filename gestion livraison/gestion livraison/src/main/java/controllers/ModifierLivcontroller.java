package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import models.Livreur;
import services.ServiceLivreur;


public class ModifierLivcontroller implements Initializable {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private ComboBox<String> LStatutC2;

        @FXML
        private Label idModif1;

        @FXML
        private TextField tfNom_livreur;

        @FXML
        private TextField tfid_livreur;

        @FXML
        private TextField tftelL;
    private Livreur livreur;


    private  void  afficherDetailslivreur(){try {
        // Appeler la méthode de service pour récupérer la liste des livreurs
        ServiceLivreur sl = new ServiceLivreur();
        List<Livreur> livreur = sl.selectAll();

        ObservableList<Livreur> livreurObservableList = FXCollections.observableArrayList(livreur);

        // Créer le ListView
        ListView<Livreur> listView = new ListView<>(livreurObservableList);

        // Configurer la cellule de rendu pour afficher les détails
        listView.setCellFactory(param -> new ListCell<Livreur>() {
            @Override
            protected void updateItem(Livreur livreur, boolean empty) {
                super.updateItem(livreur, empty);

                if (empty || livreur == null) {
                    setText(null);
                } else {
                    setText("id_livreur: " + livreur.getId_livreur() +
                            ", Nom_livreur: " + livreur.getNom_livreur() +
                            ", statut_livreur: " + livreur.getStatut_livreur() +
                            ", Num_tel_livreur " + livreur.getNum_tel_livreur());
                }
            }
        });

    } catch (SQLException e) {
        // Gérer les exceptions SQL
        e.printStackTrace();
    }}
    @FXML
        void Affichage_modif(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivreurFXML.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    public void setId(int id) {

        int id_livreur = id;
        System.out.println("her id " + id_livreur);
    }
    public void someMethod() {

        if (livreur != null) {

        } else {
            System.out.println(" livreur n'est pas initialisé. Assurez-vous d'appeler initData pour l'initialiser.");
        }
    }

        @FXML
        void Ajouter(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/AjouterLivreurFXML.fxml"));
                tfid_livreur.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }

        @FXML
        void modifierLivreur(ActionEvent event) {
            try {
                int id_Livreur = Integer.parseInt(tfid_livreur.getText());
                int Tel_livreur = Integer.parseInt(tftelL.getText());
                String Nom_livreur = tfNom_livreur.toString();
                String statut_livreur = String.valueOf(LStatutC2.getValue());
                Livreur l = new Livreur(id_Livreur,Nom_livreur,statut_livreur, Tel_livreur);
                ServiceLivreur sl = new ServiceLivreur();
                sl.updateOne(l);
            } catch (SQLException e) {
                // Gérer les exceptions SQL
                e.printStackTrace();
            }

        }

        @FXML
        void initialize() {
            assert LStatutC2 != null : "fx:id=\"LStatutC2\" was not injected: check your FXML file 'ModifierLiv.fxml'.";
            assert idModif1 != null : "fx:id=\"idModif1\" was not injected: check your FXML file 'ModifierLiv.fxml'.";
            assert tfNom_livreur != null : "fx:id=\"tfNom_livreur\" was not injected: check your FXML file 'ModifierLiv.fxml'.";
            assert tfid_livreur != null : "fx:id=\"tfid_livreur\" was not injected: check your FXML file 'ModifierLiv.fxml'.";
            assert tftelL != null : "fx:id=\"tftelL\" was not injected: check your FXML file 'ModifierLiv.fxml'.";

        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LStatutC2.setItems(FXCollections.observableArrayList("congée","travaille"));

    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
        afficherDetailslivreur(); ;
    }
}