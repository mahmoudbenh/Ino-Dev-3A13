/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ProduitAi;
import service.ServiceProduit;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ModifierProduitFXML implements Initializable {

    private TextField Idproduit;
    @FXML
    private TextField prixproduit;
    @FXML
    private Button modif;
    @FXML
    private Button annul;
    @FXML
    private ComboBox<String> nomP;

    private final Connection con = DBConnection.getInstance().getCnx();
    ObservableList<String> options = FXCollections.observableArrayList();
    ServiceProduit sp = new ServiceProduit();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        remplirListProduits();
        nomP.setItems(options);
    }

    private void remplirListProduits() {

        try {
            String requete = "Select nom from produitai";
            ResultSet rs;
            PreparedStatement pst = con.prepareStatement(requete);
            rs = pst.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("nom"));
            }
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void Modifier(ActionEvent event) {

        ServiceProduit sf = new ServiceProduit();
        String s0=nomP.getValue();
//        int p = sp.getByNom(nomP.getSelectionModel().getSelectedItem()).getId();
        String s1=prixproduit.getText();
        double sd1= Double.parseDouble(s1);
        sf.updateOne(s0,sd1);
        JOptionPane.showMessageDialog(null, "Produit modifiier !");

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
//    public boolean Controledesaisie() {
//        int verif = 0;
//        String style = " -fx-border-color: red;";
//
//        String styledefault = "-fx-border-color: green;";
//
//        Idproduit.setStyle(styledefault);
//        prixproduit.setStyle(styledefault);
//        
//
//        if (Idproduit.getText().equals("")) {
//            Idproduit.setStyle(style);
//            verif = 1;
//        }
//        if (prixproduit.getText().equals("")) {
//            prixproduit.setStyle(style);
//            verif = 1;
//        }
//        
//        if (verif == 0) {
//            return true;
//        }
//        
//        JOptionPane.showMessageDialog(null, "Remplir tous les champs!");
//        return false;
//    }

    @FXML
    private void Annuler(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        if(event.getSource()==annul){
            stage = (Stage) annul.getScene().getWindow();
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/ProduitFXML.fxml"));
            root = FXMLLoader.load(getClass().getResource("/ProduitFXML.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ProduitFXML lc = loader.getController();
        }
    }

}