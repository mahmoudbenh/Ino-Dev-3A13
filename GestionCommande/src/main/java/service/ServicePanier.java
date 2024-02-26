/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Panier;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author ASUS
 */
public class ServicePanier {
    private Connection con = DBConnection.getInstance().getCnx();
    String lienImage = null;


    public void ajouter(Panier t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public List<Panier> afficher() {
        List<Panier> list = new ArrayList<>();

        try {
//            System.out.println("************************Liste des panier************************");
            String requete = "SELECT * FROM panier";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Panier(rs.getString(1), rs.getDouble(2)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }


    public void modifier(String nom, double prix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void modifParNom(Panier t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void suprimer(Panier t) {
        try {
            Statement ste = con.createStatement();
            String requetedelete = "delete from panier where nom_produit='" + t.getNom_produit()+"'";
            ste.execute(requetedelete);
            System.out.println("Produit supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    public void truncate(){
        try {
            Statement ste = con.createStatement();
            String requete = "truncate table panier";
            ste.execute(requete);
            System.out.println("Panier vide !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public Boolean exist(Panier t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Panier getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void afficherSuma(){
        String requete = "SELECT SUM(prix_produit) FROM panier";
//            System.out.println("************************Liste des panier************************");

        PreparedStatement pst;
        try {
            pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}