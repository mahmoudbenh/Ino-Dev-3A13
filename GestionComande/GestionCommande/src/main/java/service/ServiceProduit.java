/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import model.Panier;
import model.Produit;
import java.sql.*;

import model.ProduitAi;
import utils.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class ServiceProduit{

    private Connection con = DBConnection.getInstance().getCnx();
    String lienImage = null;


    public void insertOne(ProduitAi t) {

        try {
            String req="insert into produitai(nom,prix) values"
                    +"('"+t.getNom()+"','"+t.getPrix()+"')";
            Statement st=con.createStatement();
            st.executeUpdate(req);
            System.out.println("Ajout Produit avec succes");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


    public List<ProduitAi> selectAll() {
        List<ProduitAi> list = new ArrayList<>();

        try {
            String requete = "SELECT * FROM produitai";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new ProduitAi(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }


    public void updateOne(String nom, double prix) {
        try {
            String req="update produitai set  prix= ?  where nom=? ";
            PreparedStatement ps=con.prepareStatement(req);

            ps.setDouble(1, prix);
            ps.setString(2, nom);
            ps.executeUpdate();
            System.out.println("Modification produit avec succes");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void suprimer(ProduitAi t) {
        //if (exist(getById(id))) {
        try {
            Statement ste = con.createStatement();
            String requetedelete = "delete from produitai where id=" + t.getId();
            ste.execute(requetedelete);
            System.out.println("Produit supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        /*} else {
            System.out.println("Produit n'existe pas !");
        }*/
    }
    public void somme(){
    }


    public ArrayList<ProduitAi> RechercheNom(String nom) {

        ArrayList<ProduitAi> produits = new ArrayList<>();
        String requete = "select * from produitai where nom ='" + nom + "'";
        try {
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                produits.add(new ProduitAi(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors d'extraction des données \n" + ex.getMessage());
        }
        return produits;
    }
    /*
        public ArrayList<Produit> triNom() {
            ArrayList<Produit> produits = new ArrayList<>();
            String requete = "select * from produit ORDER BY nom DESC";
            try {
                PreparedStatement pst = con.prepareStatement(requete);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {

                    produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return produits;
        }

        public ArrayList<Produit> triQuqntite() {
            ArrayList<Produit> produits = new ArrayList<>();
            String requete = "select * from produit ORDER BY quantite DESC";
            try {
                PreparedStatement pst = con.prepareStatement(requete);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {

                    produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return produits;
        }

        public ArrayList<Produit> triPrix() {
            ArrayList<Produit> produits = new ArrayList<>();
            String requete = "select * from produit ORDER BY prix DESC";
            try {
                PreparedStatement pst = con.prepareStatement(requete);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {

                    produits.add(new Produit(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return produits;
        }

        public int nombre() {

            int i = 0;
            String requete = "SELECT COUNT(*) as nbr FROM produit";

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(requete);

                while (rs.next()) {

                    i = rs.getInt("nbr");

                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            return i;
        }
         */
    public ProduitAi getById(int id) {
        try {
            String query = "select * from produitai where id=" + id;
            PreparedStatement pst;

            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ProduitAi a = new ProduitAi(rs.getInt(1), rs.getString(2), rs.getDouble(3));
                return a;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


    public Boolean exist(ProduitAi t) {
        return getById(t.getId()) != null;
    }

    public ProduitAi getByNom(String nom) {
        try {
            String query = "select * from produitai where nom='" + nom + "'";
            PreparedStatement pst;

            pst = con.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ProduitAi a = new ProduitAi(rs.getInt(1), rs.getString(2),  rs.getDouble(3));
                return a;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public void modifier1(int id, String nom, String categorie, double prix, String imgproduit) {
        try {
            String requete = "UPDATE produitai SET nom=?, prix=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(requete);

            // Paramètres pour la requête préparée
            pst.setString(1, nom);
            pst.setDouble(2, prix);
            pst.setInt(3, id);

            pst.executeUpdate();
            System.out.println("Produit modifiée !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Boolean produitExist(String nom) {

        if(RechercheNom(nom).isEmpty())
            return false;
        else
            return true;
    }

    public void modifParNom(ProduitAi t) {

        try {
            String requete = "UPDATE produitai SET prix=" + t.getPrix() + " WHERE nom='" + t.getNom() + "'";
            PreparedStatement pst = con.prepareStatement(requete);
            pst.executeUpdate();
            System.out.println("Produit modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<String> GetNameProduct() {
        List<String> list = new ArrayList<>();

        try {
            String requete = "SELECT nom FROM produitai";
            PreparedStatement pst = con.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    public void addtocart(ProduitAi p) throws SQLException{
        String req="insert into panier(nom_produit,prix_produit) values"
                +"('"+p.getNom()+"','"+p.getPrix()+"')";
        Statement st=con.createStatement();
        st.executeUpdate(req);
        System.out.println("Ajout Produit dans panier avec succes");
    }
}