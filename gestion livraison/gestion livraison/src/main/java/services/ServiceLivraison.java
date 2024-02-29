package services;
import models.Livraison;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ServiceLivraison implements CRUD<Livraison> {
    private Connection cnx;
    public ServiceLivraison(){cnx = DBConnection.getInstance().getCnx();}
    public void insertOne(Livraison livraison) throws SQLException {
        String req="INSERT INTO `livraison`(`id_commande`, `id_livreur`, `Date_livraison`, `Adresse_livraison`, `status_livraison`, `frais_livraison`) VALUES" +
                " ("+livraison.getId_commande()+","+livraison.getId_livreur()+",'"+livraison.getDate_livraison()+"','"+livraison.getAdresse_livraison()+
                "','"+livraison.getStatus_livraison()+"',"+livraison.getFrais_livraison()+")";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void updateOne(Livraison livraison) throws SQLException {
        String req="UPDATE `livraison` SET " +
                "`id_livraison`="+livraison.getId_livraison()+",`id_commande`="+livraison.getId_commande()+",`id_livreur`="+livraison.getId_livreur()+",`Date_livraison`='"+livraison.getDate_livraison()+"',`Adresse_livraison`="+livraison.getAdresse_livraison()+
                ",`status_livraison`="+livraison.getStatus_livraison()+",`frais_livraison`='"+livraison.getFrais_livraison()+"' WHERE 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void deleteOne(Livraison livraison) throws SQLException {
        String req="DELETE FROM `livraison` WHERE 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public List<Livraison> selectAll() throws SQLException {
        List<Livraison> livraisonList = new ArrayList<>();
        String req = "SELECT * FROM `livraison`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Livraison l = new Livraison();
            l.setId_livraison(rs.getInt(1));
            l.setId_commande(rs.getInt(2));
            l.setId_livreur(rs.getInt(3));
            l.setDate_livraison(String.valueOf(rs.getDate(4).toLocalDate()));
            l.setAdresse_livraison(rs.getString(5));
            l.setStatus_livraison(rs.getInt(6));
            l.setFrais_livraison(String.valueOf(rs.getFloat(7)));
            livraisonList.add(l); // Add the Livraison object to the list
        }
        // Close ResultSet and Statement to release resources
        rs.close();
        st.close();
        return livraisonList;
    }



}
