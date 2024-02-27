package service;

import model.Comande;
import model.LigneCommande;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;



public class ServiceLigne implements CRUD<LigneCommande> {
    private Connection cnx;
    public ServiceLigne(){cnx = DBConnection.getInstance().getCnx();}
    public void insertOne(LigneCommande ligne) throws SQLException {

        String req = "INSERT INTO `ligne`(`id_lc`, `id_produit`,`id_commande`, `qte`,`prix_produit`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, ligne.getId_lc());
            preparedStatement.setInt(2, ligne.getId_produit());
            preparedStatement.setInt(3, ligne.getId_commande());
            preparedStatement.setInt(4, ligne.getQte());
            preparedStatement.setDouble(5, ligne.getPrix_produit());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void updateOne(LigneCommande ligne) throws SQLException {
        try {
            String requete = "UPDATE ligne SET id_lc=? ,id_produit=? ,id_commande=? ,qte=? ,prix_produit=? WHERE id_lc=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, ligne.getId_lc());
            pst.setInt(2, ligne.getId_produit());
            pst.setInt(3, ligne.getId_commande());
            pst.setInt(4, ligne.getQte());
            pst.setDouble(5, ligne.getPrix_produit());
            pst.setInt(6, ligne.getId_lc()); // Set the WHERE condition value

            pst.executeUpdate();
            System.out.println("Ligne modifiée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteOne(LigneCommande ligne) throws SQLException {
        try{
            String req="DELETE FROM `ligne` WHERE id_lc=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, ligne.getId_lc());
            pst.executeUpdate();
            System.out.println("commande supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<LigneCommande> selectAll() throws SQLException {
        List<LigneCommande> ligneCommandeList=new ArrayList<>();
        String req="SELECT * FROM `comande`";
        Statement st = cnx.createStatement();
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            LigneCommande lc=new LigneCommande();
            lc.setId_lc(rs.getInt(1));
            lc.setId_produit(rs.getInt(2));
            lc.setId_commande(rs.getInt(3));
            lc.setQte(rs.getInt(4));
            lc.setPrix_produit(rs.getDouble(5));
            ligneCommandeList.add(lc);
            System.out.println("done");
        }
        return ligneCommandeList;
    }

}
