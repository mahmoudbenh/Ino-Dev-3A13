package controllers;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import com.mysql.cj.result.Row;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import models.Event;
import services.ServiceEvent;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.controlsfx.control.Notifications;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherEvent {

    private final ServiceEvent serviceEvent = new ServiceEvent();

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSettings1;

    @FXML
    private Button btnSettings11;

    @FXML
    private Button btnSettings111;

    @FXML
    private ListView<Event> listeView;
    @FXML
    private Label mytext;
    private final ServiceEvent sl = new ServiceEvent();

    @FXML
    private void initialize() {
        try {
            List<Event> events = serviceEvent.selectAll();
            listeView.setItems(FXCollections.observableArrayList(events));
            listeView.setCellFactory(param -> new TextFieldListCell<Event>() {
                @Override
                public void updateItem(Event event, boolean empty) {
                    super.updateItem(event, empty);
                    if (empty || event == null) {
                        setText(null);
                    } else {
                        setText(event.getId_event() + " - " + event.getName() + " - " + event.getDescription() + " - " + event.getLieu());
                    }
                }
            });
            listeView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Event selectedItem = listeView.getSelectionModel().getSelectedItem();
                    if (selectedItem != null) {
                        switchToUpdatePage(selectedItem);
                    }
                }
            });
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error retrieving events: " + e.getMessage());
        }
    }

    private void switchToUpdatePage(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEventcontroller.fxml"));
            Parent root = loader.load();
            ModifierEventcontroller controller = loader.getController();
            controller.setEvent(event);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading modification page: " + e.getMessage());
        }
    }
    @FXML
    void boutonSupprimer(ActionEvent event) {
        Event event1 = listeView.getSelectionModel().getSelectedItem();

        if (event1 != null) {
            // Supprimer livreur de la base de données et de la liste affichée
            try {
                sl.deleteOne(event1);
                listeView.getItems().remove(event1);

                // Afficher un message de confirmation
                afficherMessage("Suppression réussie", "  suppression avec succès.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                // Afficher un message d'erreur en cas d'échec de la suppression
                afficherMessage("Erreur", "Erreur lors de la suppression  : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            // Si aucun hôtel n'est sélectionné, afficher un message d'avertissement
            afficherMessage("Aucune sélection", "Veuillez sélectionner un livreur à supprimer.", Alert.AlertType.WARNING);
        }
    }
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void exportParticipantsToExcel(ActionEvent event)  {
        String excelFilePath =  "C:/Users/Linda/OneDrive/Desktop/3A/gestion event/src/main/resources/Excel.xlsx";


        try (Workbook workbook = new XSSFWorkbook()) {
            // Créer une feuille de calcul
            Sheet sheet = workbook.createSheet("Evenements");

            // Ajouter des en-têtes
            Row headerRow = sheet.createRow(0);
           // headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(0).setCellValue("Nom event");
            headerRow.createCell(1).setCellValue("Description");
            headerRow.createCell(2).setCellValue("Statut ");
            headerRow.createCell(3).setCellValue("Lieu ");

            // Récupérer les données depuis la base de données
            populateData(sheet);

            // Écrire dans le fichier Excel
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }
           /* Notifications.create()
                    .title("Event")
                    .text("Données exportées avec succès vers " + excelFilePath)
                    .showInformation();*/

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bt_modifier(ActionEvent event) {
        Event selectedevent = listeView.getSelectionModel().getSelectedItem();
        if (selectedevent != null) {

            switchToUpdatePage(selectedevent);
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un evenement à modifier.");
            alert.showAndWait();
            ToModifierListeLivreur(); ;
        }

    }

    private void ToModifierListeLivreur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLiv.fxml"));
            Parent root = loader.load();
            ModifierEventcontroller ModifierController = loader.getController();
            Scene pageScene = new Scene(root);

            Stage stage = (Stage) mytext.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private static void populateData(Sheet sheet) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/event1";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT name,description,statut,lieu FROM event";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                int rowNum = 1; // Commencez à partir de la deuxième ligne (après les en-têtes)
                while (resultSet.next()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(resultSet.getString("name"));
                    row.createCell(1).setCellValue(resultSet.getString("description"));
                    row.createCell(2).setCellValue(resultSet.getString("statut"));
                    row.createCell(3).setCellValue(resultSet.getString("lieu"));

                }
            }
        }
    }

    @FXML
    void Exit(ActionEvent event) {
        // Get the reference to the button's stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }

    @FXML
    void retour(ActionEvent event) {
        // Add functionality to navigate back to the previous screen
    }
    @FXML
    void reloadEvents(ActionEvent event) {
        try {
            List<Event> events = serviceEvent.selectAll();
            listeView.setItems(FXCollections.observableArrayList(events));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error retrieving events: " + e.getMessage());
        }
    }

}
