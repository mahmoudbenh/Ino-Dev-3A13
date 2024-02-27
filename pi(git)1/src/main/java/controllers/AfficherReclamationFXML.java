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
import models.reclamation;
import services.ServiceReclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class AfficherReclamationFXML implements Initializable {

    public Button button_charger;
    public Button modif;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> date_reclamationColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> id_clientColumn;

    @FXML
    private TableColumn<?, ?> id_reclamationColumn;

    @FXML
    private TableView<reclamation> table_reclamation;
    @FXML
    private TableColumn<reclamation, String> edit;
    @FXML
    private TableColumn<?, ?> type_reclamationColumn;
    @FXML
    private TableColumn<?, ?> titre_reclamationColumn;
    @FXML
    private TableColumn<?, ?> statut_reclamationcolumn;
    @FXML
    private TableColumn<?, ?> heure_reclamationColumn;

    private ServiceReclamation r;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            afficher();
            addEditButtonToTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    ///////
    private void addEditButtonToTable() {
        Callback<TableColumn<reclamation, String>, TableCell<reclamation, String>> cellFactory = (
                TableColumn<reclamation, String> param) -> {
            final TableCell<reclamation, String> cell = new TableCell<reclamation, String>() {
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
                    reclamation selectedReclamation = getTableView().getItems().get(getIndex());
                    modifierrec(event, selectedReclamation);
                }
                private void onDeleteButtonClicked(ActionEvent event) {
                    reclamation selectedReclamation = getTableView().getItems().get(getIndex());
                    deleteReclamation(selectedReclamation);
                }

            };
            return cell;
        };

        edit.setCellFactory(cellFactory);
    }
    private void deleteReclamation(reclamation reclamation) {
this.r = new ServiceReclamation();
    reclamation selectedReclamation = table_reclamation.getSelectionModel().getSelectedItem();
    if (selectedReclamation != null) {

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cette reclamation ?");
        confirmationAlert.setContentText("Cette action ne peut pas être annulée.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try {
                // Assuming s.supprimer() also updates the data model
                r.deleteOne(selectedReclamation);

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
}
    /////
    @FXML
    void afficher() throws SQLException {

        // Appeler la méthode de service pour récupérer la liste des produits
        ServiceReclamation srr = new ServiceReclamation();
        ArrayList<reclamation> reclamations = null;

            reclamations = (ArrayList<reclamation>) srr.selectAll();


        // Créer une liste observable de produits
        ObservableList<reclamation> reclamationsObservable = FXCollections.observableArrayList(reclamations);

        // Assigner la liste observable à la TableView
        table_reclamation.setItems(reclamationsObservable);

        id_reclamationColumn.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        id_clientColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        titre_reclamationColumn.setCellValueFactory(new PropertyValueFactory<>("titre_reclamation"));
        date_reclamationColumn.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        heure_reclamationColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        type_reclamationColumn.setCellValueFactory(new PropertyValueFactory<>("type_reclamation"));
        statut_reclamationcolumn.setCellValueFactory(new PropertyValueFactory<>("statut_reclamation"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    }
    public void affichage(ActionEvent event) throws SQLException {
        afficher();
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loaderb = new FXMLLoader(getClass().getResource("/AjouterReclamationFXML.fxml"));
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

    ////////////////////////
   /* @FXML
    void modifierrec(ActionEvent event) {
        reclamation selectedReclamation = table_reclamation.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                // Charger le fichier FXML de la nouvelle scène de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamationFXML.fxml"));
                Parent root = loader.load();

                // Accédez au contrôleur de la scène de modification
                ModifierReclamationFXML modifyController = loader.getController();

                // Passez la recette sélectionnée au contrôleur de la scène de modification
                modifyController.setReclamationToModify(selectedReclamation);

                // Créer une nouvelle fenêtre pour la scène de modification
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modifier reclamation");
                modifyStage.initModality(Modality.WINDOW_MODAL);
                modifyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                modifyStage.setScene(new Scene(root));

                // Affichez la nouvelle fenêtre
                modifyStage.show();

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement du FXML
            }
        }
    }*/
    ///////////////////
    @FXML
    void modifierrec(ActionEvent event, reclamation selectedReclamation) {
        if (selectedReclamation != null) {
            try {
                // Charger le fichier FXML de la nouvelle scène de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamationFXML.fxml"));
                Parent root = loader.load();

                // Accédez au contrôleur de la scène de modification
                ModifierReclamationFXML modifyController = loader.getController();

                // Passez la reclamation sélectionnée au contrôleur de la scène de modification
                modifyController.setReclamationToModify(selectedReclamation);

                // Créer une nouvelle fenêtre pour la scène de modification
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modifier reclamation");
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
//////////////////////////////

    ////////////////////////////////
@FXML
void supprimer(ActionEvent event) {
    this.r = new ServiceReclamation();
    reclamation selectedReclamation = table_reclamation.getSelectionModel().getSelectedItem();
    if (selectedReclamation != null) {

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cette reclamation ?");
        confirmationAlert.setContentText("Cette action ne peut pas être annulée.");

        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try {
                // Assuming s.supprimer() also updates the data model
                r.deleteOne(selectedReclamation);

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
}
    void showErrorNotification(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Appliquer le style CSS pour rendre l'alerte rouge
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("error-alert");

        alert.showAndWait();
    }
    ////////////////////////
    @FXML
    void initialize() {
        assert titre_reclamationColumn != null : "fx:id=\"titre_reclamationColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert statut_reclamationcolumn != null : "fx:id=\"statut_reclamationcolumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert date_reclamationColumn != null : "fx:id=\"date_reclamationColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert descriptionColumn != null : "fx:id=\"descriptionColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert id_clientColumn != null : "fx:id=\"id_clientColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert id_reclamationColumn != null : "fx:id=\"id_reclamationColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert table_reclamation != null : "fx:id=\"table_reclamation\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert type_reclamationColumn != null : "fx:id=\"type_reclamationColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert edit != null : "fx:id=\"edit\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
        assert heure_reclamationColumn != null : "fx:id=\"heure_reclamationColumn\" was not injected: check your FXML file 'AfficherReclamationFXML.fxml'.";
    }
    }
