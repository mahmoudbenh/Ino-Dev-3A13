
package controllers;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.layout.HBox;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Alert.AlertType;
        import javafx.scene.control.ButtonType;
        import javafx.util.Callback;
        import javafx.scene.control.TableCell;
        import javafx.scene.control.Button;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.layout.HBox;
        import javafx.util.Callback;
        import models.reclamation;
        import models.rembourssement;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.cell.TextFieldTableCell;
        import javafx.util.Callback;


        import java.io.IOException;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;

        import services.ServiceReclamation;
        import services.ServiceRembourssement;

public class AfficherRembourssementFXML implements Initializable {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_charger;



    @FXML
    private TableColumn<?, ?> dateremColumn;


    @FXML
    private TableColumn<?, ?> heurecol;
    @FXML
    private TableColumn<?, ?> modecol;
    @FXML
    private TableColumn<?, ?> id_recColumn;

    @FXML
    private TableColumn<?, ?> id_rembColumn;

    @FXML
    private TableColumn<?, ?> prix_prodColumn;

    @FXML
    private TableColumn<?, ?> statcol;

    @FXML
    private TableView<rembourssement> table_rembourssement;
    private ServiceRembourssement rb;
    @FXML
    private TableColumn<rembourssement, String> edit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            afficher();
            addEditButtonToTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
////////////
private void addEditButtonToTable() {
    Callback<TableColumn<rembourssement, String>, TableCell<rembourssement, String>> cellFactory = (
            TableColumn<rembourssement, String> param) -> {
        final TableCell<rembourssement, String> cell = new TableCell<rembourssement, String>() {
            final Button editButton = new Button("✎");
            final Button deleteButton = new Button("🗑");


            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {

                    editButton.setOnAction(this::onEditButtonClicked);
                    deleteButton.setOnAction(this::onDeleteButtonClicked);
                    HBox buttonsContainer = new HBox(editButton, deleteButton);
                    setGraphic(buttonsContainer);
                    setText(null);
                }
            }

            private void onEditButtonClicked(ActionEvent event) {
                rembourssement selectedRembourssement = getTableView().getItems().get(getIndex());
                modifierremb(event, selectedRembourssement);
            }

            private void onDeleteButtonClicked(ActionEvent event) {
                rembourssement selectedRembourssement = getTableView().getItems().get(getIndex());
                deleteRembourssement(selectedRembourssement);
            }

        };
        return cell;
    };

    edit.setCellFactory(cellFactory);
}

/////////////
private void deleteRembourssement(rembourssement rembourssement) {

    this.rb = new ServiceRembourssement();
    rembourssement selectedRembourssement = table_rembourssement.getSelectionModel().getSelectedItem();
    if (selectedRembourssement != null) {

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cet rembourssement ?");
        confirmationAlert.setContentText("Cette action ne peut pas être annulée.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try {
                // Assuming s.supprimer() also updates the data model
                rb.deleteOne(selectedRembourssement);

                // Assuming addRecetteListData() correctly updates the TableView
                afficher();

            } catch (SQLException e) {
                e.printStackTrace();
                showErrorNotification("Erreur lors de la suppression du rembourssement");
            }
        }
    } else {
        showErrorNotification("Veuillez sélectionner un rembourssement à supprimer");
    }
}
    ///////////////
    @FXML
    void afficher() throws SQLException {

        // Appeler la méthode de service pour récupérer la liste des produits
        ServiceRembourssement srb = new ServiceRembourssement();
        ArrayList<rembourssement> rembourssements = null;

        rembourssements = (ArrayList<rembourssement>) srb.selectAll();


        // Créer une liste observable de produits
        ObservableList<rembourssement> rembourssementsObservable = FXCollections.observableArrayList(rembourssements);

        // Assigner la liste observable à la TableView
        table_rembourssement.setItems(rembourssementsObservable);
        heurecol.setCellValueFactory(new PropertyValueFactory<>("heure"));
        //id_recColumn.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        //id_rembColumn.setCellValueFactory(new PropertyValueFactory<>("id_rembourssement"));
        prix_prodColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dateremColumn.setCellValueFactory(new PropertyValueFactory<>("date_rembourssement"));
        statcol.setCellValueFactory(new PropertyValueFactory<>("statut_rembourssement"));
        modecol.setCellValueFactory(new PropertyValueFactory<>("mode_paiement"));

    }
    public void affichage(ActionEvent event) throws SQLException {
        afficher();
    }
    @FXML
    void retour2(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loaderb = new FXMLLoader(getClass().getResource("/AjouterRembourssementFXML.fxml"));
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
    ////////////////
    @FXML
    void modifierremb(ActionEvent event, rembourssement selectedRembourssement) {
       // rembourssement selectedRembourssement = table_rembourssement.getSelectionModel().getSelectedItem();
        if (selectedRembourssement != null) {
            try {
                // Charger le fichier FXML de la nouvelle scène de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierRembourssementFXML.fxml"));
                Parent root = loader.load();

                // Accédez au contrôleur de la scène de modification
                ModifierRembourssementFXML modifyController = loader.getController();

                // Passez la recette sélectionnée au contrôleur de la scène de modification
                modifyController.setRembourssementToModify(selectedRembourssement);

                // Créer une nouvelle fenêtre pour la scène de modification
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modifier rembourssement");
                modifyStage.initModality(Modality.WINDOW_MODAL);
                modifyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                modifyStage.setScene(new Scene(root));

                // Affichez la nouvelle fenêtre
                modifyStage.show();

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement du FXML
            }
        }
    }
//////////////////////////
@FXML
void supprimer(ActionEvent event) {
    this.rb = new ServiceRembourssement();
    rembourssement selectedRembourssement = table_rembourssement.getSelectionModel().getSelectedItem();
    if (selectedRembourssement != null) {

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer ce rembourssement ?");
        confirmationAlert.setContentText("Cette action ne peut pas être annulée.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try {
                // Assuming s.supprimer() also updates the data model
                rb.deleteOne(selectedRembourssement);

                // Assuming addRecetteListData() correctly updates the TableView
                afficher();

            } catch (SQLException e) {
                e.printStackTrace();
                showErrorNotification("Erreur lors de la suppression de la reclamation");
            }
        }
    } else {
        showErrorNotification("Veuillez sélectionner une reclamation à supprimer");
    }
}//////////////////////////////

    void showErrorNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Appliquer le style CSS pour rendre l'alerte rouge
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("error-alert");

        alert.showAndWait();
    }
    //////////////////
    @FXML
    void initialize() {
        assert heurecol != null : "fx:id=\"heurecol\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert button_charger != null : "fx:id=\"button_charger\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert dateremColumn != null : "fx:id=\"dateremColumn\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert id_recColumn != null : "fx:id=\"id_recColumn\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert id_rembColumn != null : "fx:id=\"id_rembColumn\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert modecol != null : "fx:id=\"modecol\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert prix_prodColumn != null : "fx:id=\"prix_prodColumn\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert statcol != null : "fx:id=\"statcol\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert edit != null : "fx:id=\"edit\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
        assert table_rembourssement != null : "fx:id=\"table_rembourssement\" was not injected: check your FXML file 'AfficherRembourssementFXML.fxml'.";
    }
    }
