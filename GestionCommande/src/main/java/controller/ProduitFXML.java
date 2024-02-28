/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ProduitAi;
import service.ServiceProduit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ProduitFXML implements Initializable {

    private TableView<ProduitAi> tableP;
    private TableColumn<ProduitAi, Integer> idProduit;
    private TableColumn<ProduitAi, String> NomProduit;
    private TableColumn<ProduitAi, Double> prixproduit;
    @FXML
    private Button add;
    @FXML
    private Button modf;

    @FXML
    private ListView<ProduitAi> ListProduit;
    private final ObservableList<ProduitAi> listf = FXCollections.observableArrayList();
    ServiceProduit sf = new ServiceProduit();
    @FXML
    private Button panier;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        remplirListProduit();
        ListProduit.setItems(listf);
    }

    private void remplirListProduit() {
        listf.addAll(sf.selectAll());
        ListProduit.setCellFactory((ListView<ProduitAi> lv) -> new Cell());
        //ListProduit.setCellFactory((ListView<Produit> lv) -> new Cell());
    }

    @FXML
    private void gotopanier(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == panier) {
            stage = (Stage) panier.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierFXML.fxml"));
            root = FXMLLoader.load(getClass().getResource("/PanierFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitFXML lc = loader.getController();
        }
    }


    public class Cell extends ListCell<ProduitAi> {

        Button check = new Button("📌");
        Button supprimer = new Button("🚮");
        Label Idp = new Label("");

        Label prix = new Label("");

        Label nom = new Label("");
        GridPane pane = new GridPane();
        AnchorPane aPane = new AnchorPane();

        public Cell() {

            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em;");
            GridPane.setConstraints(nom, 1, 0, 2, 3);

//            prix.setStyle("-fx-text-align: center;");
            GridPane.setConstraints(prix, 2, 0, 2, 3);

            supprimer.setStyle("-fx-text-fill : black;-fx-background-color : none;");
            GridPane.setConstraints(supprimer, 4, 0, 4, 3);
            check.setStyle("-fx-background-color:  #616161; -fx-background-radius:  90; -fx-text-fill: white; -fx-font-weight: bold;");
            GridPane.setConstraints(check, 3, 0, 1, 4);
//            dislike.setStyle("-fx-background-color:  #616161; -fx-background-radius:  90; -fx-text-fill: white; -fx-font-weight: bold;");
//            GridPane.setConstraints(dislike, 5, 0, 1, 3);

//            date.setStyle("-fx-font-size: 0.9em; -fx-font-style: italic; -fx-opacity: 0.5;");
//            GridPane.setConstraints(date, 1, 1);
//            GridPane.setConstraints(nom,  1, 3);
//            GridPane.setValignment(nom, VPos.CENTER);
//            GridPane.setConstraints(check, 3, 1);
//            GridPane.setValignment(check, VPos.CENTER);
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));
            pane.setHgap(1);
            pane.setVgap(1);
            pane.getChildren().setAll(nom, prix, check, supprimer);
            AnchorPane.setTopAnchor(pane, 0d);
            AnchorPane.setLeftAnchor(pane, 0d);
            AnchorPane.setBottomAnchor(pane, 0d);
            AnchorPane.setRightAnchor(pane, 0d);
            aPane.getChildren().add(pane);
        }

        @Override
        protected void updateItem(ProduitAi item, boolean empty) {
            super.updateItem(item, empty);
//        System.out.println(item);
            setGraphic(null);
            setText(null);
            setContentDisplay(ContentDisplay.LEFT);
            if (!empty && item != null) {
//                like.setText("Like("+item.getLikes()+")");
//                dislike.setText("Dislike("+item.getReport()+")");
////                File f = new File(item.getImage().replaceAll("file:/", ""));
////                BufferedImage bufferedImage = null;
////                try {
////                    bufferedImage = ImageIO.read(f);
////                } catch (IOException ex) {
//////                    Logger.getLogger(ListeProduitsController.class.getName()).log(Level.SEVERE, null, ex);
////                }
////                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
//
////                img.setImage(image);
                Idp.setText(String.valueOf(item.getId()));
                //date.setText(String.valueOf(item.getDate_frm()));
                nom.setText(item.getNom());
                prix.setText(String.valueOf(item.getPrix()));
                supprimer.setOnMouseEntered((event)
                        -> supprimer.setStyle("-fx-text-fill : white; -fx-background-color : #870505;")
                );

                supprimer.setOnMouseExited((event)
                        -> supprimer.setStyle("-fx-text-fill : black;-fx-background-color : none;")
                );
                supprimer.setOnAction(event -> {

                            ProduitAi f = getListView().getItems().get(getIndex());

                            System.out.print(f);
                            sf.suprimer(f);
                            JOptionPane.showMessageDialog(null, "Produit supprime !");
                            listf.clear();
                            remplirListProduit();
                        }
                );
                //Panier ici
                check.setOnMouseEntered((event)
                        -> check.setStyle("-fx-text-fill : white; -fx-background-color : #157d1c; -fx-background-radius:  90;")
                );

                check.setOnMouseExited((event)
                        -> check.setStyle("-fx-background-color:  #616161; -fx-background-radius:  90; -fx-text-fill: white; -fx-font-weight: bold;")
                );
                check.setOnAction(event -> {

                            ProduitAi f = getListView().getItems().get(getIndex());

                            System.out.print(f);
                            try {
                                sf.addtocart(f);
                            } catch (SQLException ex) {
                                Logger.getLogger(ProduitFXML.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            JOptionPane.showMessageDialog(null, "Produit ajoute dans le panier!");
                        }
                );

//                like.setOnAction(event -> {
//                           
//                            Forum f = getListView().getItems().get(getIndex());
//
//                            f.setLikes(f.getLikes()+1);
//                            sf.modifier(f, f.getId_frm());
//                            JOptionPane.showMessageDialog(null, "vous avez aimer cette poste!");
//                            listf.clear();
//                            remplirListForum();
//                        }
//                        );
//                dislike.setOnAction(event -> {
//                           
//                            
//                            Forum f = getListView().getItems().get(getIndex());
//                            f.setReport(f.getReport()+1);
//                            sf.modifier(f, f.getId_frm());
//                            JOptionPane.showMessageDialog(null, "vous n'avez pas aimer cette poste !");
//                            listf.clear();
//                            remplirListForum();
//                        }
//                        );
//                setText(null);
                setGraphic(aPane);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }

    //    public void view(){
//        try {
//
//            List Produit = sf.afficher();
//            ObservableList list = FXCollections.observableArrayList(Produit);
//            idProduit.setCellValueFactory(new PropertyValueFactory<>("id"));
//            NomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
//            prixproduit.setCellValueFactory(new PropertyValueFactory<>("prix"));
//            
//            tableP.setItems(list);
//            
//          
//        }catch (Exception ex) {
//             Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//   }
    @FXML
    private void Add(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if (event.getSource() == add) {
            stage = (Stage) add.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduitFXML.fxml"));
            root = FXMLLoader.load(getClass().getResource("/AjouterProduitFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitFXML lc = loader.getController();
        }
    }

    @FXML
    private void modifier(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource()==modf){
            stage = (Stage) modf.getScene().getWindow();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/ModifierProduitFXML.fxml"));
            root = FXMLLoader.load(getClass().getResource("/ModifierProduitFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitFXML lc = loader.getController();
        }
    }
}