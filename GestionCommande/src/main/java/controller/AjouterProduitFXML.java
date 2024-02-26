package controller;
import model.ProduitAi;
import service.ServiceProduit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class AjouterProduitFXML implements Initializable {

    @FXML
    private TextField nomP;
    @FXML
    private TextField prixP;
    @FXML
    private Button addP;
    @FXML
    private Button annule;


    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    @FXML
    private void addition(ActionEvent event) {

        if(Controledesaisie()){
            ServiceProduit sp = new ServiceProduit();
            String s0=nomP.getText();
            String s1=prixP.getText();
            double si0=Double.parseDouble(s1);

            sp.insertOne(new ProduitAi(s0 ,si0));

            JOptionPane.showMessageDialog(null, "Produit ajoutée !");
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
    }
    public boolean Controledesaisie() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

        nomP.setStyle(styledefault);
        prixP.setStyle(styledefault);


        if (nomP.getText().equals("")) {
            nomP.setStyle(style);
            verif = 1;
        }
        if (prixP.getText().equals("")) {
            prixP.setStyle(style);
            verif = 1;
        }

        if (verif == 0) {
            return true;
        }

        JOptionPane.showMessageDialog(null, "Remplir tous les champs!");
        return false;
    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource()==annule){
            stage = (Stage) annule.getScene().getWindow();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/ProduitFXML.fxml"));
            root = FXMLLoader.load(getClass().getResource("/ProduitFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitFXML lc = loader.getController();
        }
    }




}