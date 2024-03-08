package controllers;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ServiceRembourssement;
import models.rembourssement;
import controllers.AfficherReclamationFXML;

import javax.swing.text.Document;

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
    private int idReclamation;
    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }
    @FXML

    void ajouterrembourssement(int idReclamation) {
        try
        {
            String statut_rembourssement = statr.getValue();
            String mode_rembourssement = moder.getValue();
            rembourssement r = new rembourssement(Float.parseFloat(prix_produitr.getText()),date_remboursr.getValue(),heure_remboursr.getValue(),statut_rembourssement,mode_rembourssement);
            r.setId_reclamation(idReclamation);
            System.out.println(r);
            ServiceRembourssement sr = new ServiceRembourssement();
            sr.insertOne(r);
            showSuccessNotification("rembourssement en cours de traitement");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
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
    @FXML
    void handleajouterrembourssement(ActionEvent event) {
        // Obtenez l'ID de la réclamation depuis votre application
        //int idReclamation = /* Obtenez l'ID de la réclamation depuis votre application */;
        System.out.println("ID de la réclamation: " + idReclamation);

        // Appelez la méthode ajouterrembourssement avec l'ID de la réclamation
        ajouterrembourssement(idReclamation);
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
   /*@FXML
    public void changeScreenButtonPushedr(ActionEvent event)  throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherRembourssementFXML.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);

        window.show();
    }*/

    @FXML
    void afficher() throws SQLException {

        // Appeler la méthode de service pour récupérer la liste des produits
        ServiceRembourssement srb = new ServiceRembourssement();
        ArrayList<rembourssement> rembourssements = null;

        rembourssements = (ArrayList<rembourssement>) srb.selectAll1();


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

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
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
    ///////////////
    public void changeScreenButtonPushedr(ActionEvent event) throws SQLException {
        afficher();
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
    void retour2(ActionEvent event) {
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
    //////////////
/*
    @FXML
    private void pdfreport(ActionEvent event) throws DocumentException, FileNotFoundException {
        new rembourssement();
        Document pdfReport = new rembourssement();
        PdfWriter.getInstance(pdfReport, new FileOutputStream("rembourssement.pdf"));
        pdfReport.open();
        pdfReport.add(new Paragraph("rembourssement"));
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);

        PdfPTable my_report_table = new PdfPTable(8);
        PdfPCell tableCellColumn = new PdfPCell(new Phrase("id_rembourssement"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("id_reclamation"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("email"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("prix"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("date_rembourssement"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("heure"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("statut_rembourssement"));
        my_report_table.addCell(tableCellColumn);
        tableCellColumn = new PdfPCell(new Phrase("mode_paiement"));
        my_report_table.addCell(tableCellColumn);

        double h = 0.0;
        this.table_rembourssement.getItems().forEach((e) -> {
            PdfPCell tableCell = new PdfPCell(new Phrase(e.getId_rembourssement()));
            my_report_table.addCell(tableCell);
            String idrec = "" + e.getId_reclamation();
            tableCell = new PdfPCell(new Phrase(idc));
            my_report_table.addCell(tableCell);
            tableCell = new PdfPCell(new Phrase(e.getEmail()));
            my_report_table.addCell(tableCell);
            String idl = "" + e.getLivraisonId();
            tableCell = new PdfPCell(new Phrase(idl));
            my_report_table.addCell(tableCell);
            tableCell = new PdfPCell(new Phrase(e.getNom()));
            my_report_table.addCell(tableCell);
            String numT = "" + e.getNumTelephone();
            tableCell = new PdfPCell(new Phrase(numT));
            my_report_table.addCell(tableCell);
            tableCell = new PdfPCell(new Phrase(e.getPrenom()));
            my_report_table.addCell(tableCell);
            String Tot = "" + e.getTotalCost();
            tableCell = new PdfPCell(new Phrase(Tot));
            my_report_table.addCell(tableCell);
        });
        pdfReport.add(my_report_table);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.add(Chunk.NEWLINE);
        pdfReport.close();
        Alert alertReservation = new Alert(Alert.AlertType.INFORMATION);
        alertReservation.setTitle("Extraction en PDF");
        alertReservation.setHeaderText((String)null);
        alertReservation.setContentText("PDF report has been created.\nYou'll find the file under: C:\\Users\\HAYKEL\\Desktop\\PIDEVPDF");
        alertReservation.showAndWait();
    }
*/
    /////////////
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
        try {

            afficher();
            addEditButtonToTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

/////////////
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
