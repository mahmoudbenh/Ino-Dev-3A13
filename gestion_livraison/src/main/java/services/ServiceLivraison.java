package services;

import models.Livraison;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceLivraison implements CRUD<Livraison> {
    private Connection cnx;

    public ServiceLivraison() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Livraison livraison) throws SQLException {
        String req = "INSERT INTO `livraison`(`id_commande`, `id_livreur`, `Date_livraison`, `Adresse_livraison`, `status_livraison`, `frais_livraison`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, livraison.getId_commande());
        st.setInt(2, livraison.getId_livreur());
        st.setDate(3, Date.valueOf(livraison.getDate_livraison()));
        st.setString(4, livraison.getAdresse_livraison());
        st.setString(5, livraison.getStatus_livraison());
        st.setFloat(6, livraison.getFrais_livraison());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void updateOne(Livraison livraison) throws SQLException {
        String req = "UPDATE `livraison` SET `id_commande`=?, `id_livreur`=?, `Date_livraison`=?, `Adresse_livraison`=?, `status_livraison`=?, `frais_livraison`=? WHERE `id_livraison`=?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, livraison.getId_commande());
        st.setInt(2, livraison.getId_livreur());
        st.setDate(3, Date.valueOf(livraison.getDate_livraison()));
        st.setString(4, livraison.getAdresse_livraison());
        st.setString(5, livraison.getStatus_livraison());
        st.setFloat(6, livraison.getFrais_livraison());
        st.setInt(7, livraison.getId_livraison());
        st.executeUpdate();
        st.close();
    }

    @Override
    public void deleteOne(Livraison livraison) throws SQLException {
        String req = "DELETE FROM `livraison` WHERE `id_livraison`=?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, livraison.getId_livraison());
        st.executeUpdate();
        st.close();
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
            l.setDate_livraison(rs.getDate(4).toLocalDate());
            l.setAdresse_livraison(rs.getString(5));
            l.setStatus_livraison(rs.getString(6));
            l.setFrais_livraison(rs.getFloat(7));
            livraisonList.add(l);
        }
        rs.close();
        st.close();
        return livraisonList;
    }
}
