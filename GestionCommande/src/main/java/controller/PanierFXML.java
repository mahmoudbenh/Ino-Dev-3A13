package controller;
import javafx.scene.control.*;
import model.Panier;
import service.ServiceComande;
import service.ServicePanier;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import java.sql.Connection;



public class PanierFXML implements Initializable {

    @FXML
    private ListView<Panier> ListPanier;
    private final ObservableList<Panier> listP = FXCollections.observableArrayList();
    ServicePanier sp = new ServicePanier();
    @FXML
    private Button back;
    @FXML
    private Label total;
    @FXML
    private Button vider;
    @FXML
    private Button pdfP;
    private Connection con = DBConnection.getInstance().getCnx();

    private double totalPanier = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remplirListPanier();
        ListPanier.setItems(listP);
        totalPanier = listP.stream().mapToDouble(Panier::getPrix_produit).sum();
        total.setText("Total: " + totalPanier);
    }



    private void remplirListPanier() {
        listP.addAll(sp.afficher());
        ListPanier.setCellFactory((ListView<Panier> lv) -> new Cell());
    }
    @FXML
    void commander(ActionEvent event) {
        try {
            double prixTotal = totalPanier;

            ServiceComande serviceComande = new ServiceComande();
            serviceComande.ajouterCommande(prixTotal);

            afficherMessage("Commande effectuée", "La commande a été ajoutée avec succès.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            afficherMessage("Erreur", "Erreur lors de la commande : " + e.getMessage(), Alert.AlertType.ERROR);
        }
        URL fxmlUrl = getClass().getResource("/AfficherCommUserFXML.fxml");

        if (fxmlUrl != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ListPanier.getScene().setRoot(root);
        } else {
            System.err.println("FXML file not found.");
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
    private void retour(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource()==back){
            stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/ProduitFXML.fxml"));
            root = FXMLLoader.load(getClass().getResource("/ProduitFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitFXML lc = loader.getController();
        }
    }

    @FXML
    private void supptable(ActionEvent event) {
        sp.truncate();
        JOptionPane.showMessageDialog(null, "Panier vide!");
        try {
            Stage stageclose=(Stage) ((Node)event.getSource()).getScene().getWindow();

            stageclose.close();
            Parent root=FXMLLoader.load(getClass().getResource("/ProduitFXML.fxml"));
            Stage stage =new Stage();

            Scene scene = new Scene(root);


            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @FXML
    private void imprimer(ActionEvent event) throws IOException, Exception {
        /*try {
            Doc doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\ASUS\\Documents\\Panier.pdf"));
            doc.open();

            Image img = Image.getInstance("C:\\Users\\ASUS\\Documents\\Zoom\\GestionProduitFinalOLD\\src\\IMG\\IMG_9493.jpg");
            img.scaleAbsoluteHeight(91);
            img.scaleAbsoluteWidth(120);
            img.setAlignment(Image.ALIGN_RIGHT);
            doc.open();
            doc.add(img);

            doc.add(new Paragraph(" "));
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.UNDERLINE, BaseColor.BLACK);
            Paragraph p = new Paragraph("Panier ", font);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));

            PdfPTable tabpdf = new PdfPTable(2);
            tabpdf.setWidthPercentage(100);

            PdfPCell cell;
            cell = new PdfPCell(new Phrase("Produit"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);


            cell = new PdfPCell(new Phrase("Prix"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);

            String requete = "SELECT `nom_produit`, `prix_produit` FROM `panier`";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cell = new PdfPCell(new Phrase(rs.getString(1)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(rs.getDouble(2))));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);
            }
            cell = new PdfPCell(new Phrase("Somme"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.WHITE);
            tabpdf.addCell(cell);
            String req = "SELECT SUM(prix_produit) FROM panier";
            PreparedStatement pt = con.prepareStatement(req);
            ResultSet rss = pt.executeQuery();
            while (rss.next()) {
                cell = new PdfPCell(new Phrase(String.valueOf(rss.getDouble(1))));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.WHITE);
                tabpdf.addCell(cell);

            }


            doc.add(tabpdf);

            JOptionPane.showMessageDialog(null, "Success !!");


            doc.close();

            Desktop.getDesktop().open(new File("C:\\Users\\ASUS\\Documents\\Panier.pdf"));
        } catch (DocumentException | HeadlessException | IOException e) {
            System.out.println("ERROR PDF");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }*/
    }

    public class Cell extends ListCell<Panier> {

        Button supprimer = new Button("🚮");

        Label prix = new Label("");

        Label nom = new Label("");
        GridPane pane = new GridPane();
        AnchorPane aPane = new AnchorPane();

        public Cell() {

            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 1.5em;");
            GridPane.setConstraints(nom, 1,0,2,3);

            GridPane.setConstraints(prix, 2,0,2,3);

            supprimer.setStyle("-fx-text-fill : black;-fx-background-color : none;");
            GridPane.setConstraints(supprimer,4, 0, 4, 3);


            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true));
            pane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true));
            pane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true));
            pane.setHgap(1);
            pane.setVgap(1);
            pane.getChildren().setAll(nom,prix,supprimer);
            AnchorPane.setTopAnchor(pane, 0d);
            AnchorPane.setLeftAnchor(pane, 0d);
            AnchorPane.setBottomAnchor(pane, 0d);
            AnchorPane.setRightAnchor(pane, 0d);
            aPane.getChildren().add(pane);
        }

        @Override
        protected void updateItem(Panier item, boolean empty) {
            super.updateItem(item, empty);
//            System.out.println(item);
            setGraphic(null);
            setText(null);
            setContentDisplay(ContentDisplay.LEFT);
            if (!empty && item != null) {

                nom.setText(item.getNom_produit());
                prix.setText(String.valueOf(item.getPrix_produit()));
                supprimer.setOnMouseEntered( (event) ->

                        supprimer.setStyle("-fx-text-fill : white; -fx-background-color : #870505;")
                );

                supprimer.setOnMouseExited( (event) ->

                        supprimer.setStyle("-fx-text-fill : black;-fx-background-color : none;")
                );
                supprimer.setOnAction(event -> {

                            Panier f = getListView().getItems().get(getIndex());

                            System.out.print(f);
                            sp.suprimer(f);
                            JOptionPane.showMessageDialog(null, "Produit supprime !");
                            listP.clear();
                            remplirListPanier();
                        }
                );

                setGraphic(aPane);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }
        }
    }
}