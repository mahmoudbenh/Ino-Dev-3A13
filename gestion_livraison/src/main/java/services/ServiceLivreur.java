package services;
import models.Livreur;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ServiceLivreur implements CRUD<Livreur>  {



   // public class ServiceLivreur implements CRUD<Livreur> {

        private Connection cnx;

        public ServiceLivreur() {
            cnx = DBConnection.getInstance().getCnx();
        }

        @Override
        public void insertOne(Livreur livreur) throws SQLException {
            String req = "INSERT INTO livreur(Nom_livreur, statut_livreur, Num_tel_livreur) VALUES (?, ?, ?)";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setString(1, livreur.getNom_livreur());
                st.setString(2, livreur.getStatut_livreur());
                st.setInt(3, livreur.getNum_tel_livreur());
                st.executeUpdate();
            }
        }

        @Override
        public void updateOne(Livreur livreur) throws SQLException {
            String req = "UPDATE livreur SET Nom_livreur=?, statut_livreur=?, Num_tel_livreur=? WHERE id_livreur=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setString(1, livreur.getNom_livreur());
                st.setString(2, livreur.getStatut_livreur());
                st.setInt(3, livreur.getNum_tel_livreur());
                st.setInt(4, livreur.getId_livreur());
                st.executeUpdate();
            }
        }

        @Override
        public void deleteOne(Livreur livreur) throws SQLException {
            String req = "DELETE FROM livreur WHERE id_livreur=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, livreur.getId_livreur());
                st.executeUpdate();
            }
        }

        @Override
        public List<Livreur> selectAll() throws SQLException {
            List<Livreur> livreurList = new ArrayList<>();
            String req = "SELECT * FROM livreur";
            try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
                while (rs.next()) {
                    Livreur liv = new Livreur();
                    liv.setId_livreur(rs.getInt("id_livreur"));
                    liv.setNom_livreur(rs.getString("Nom_livreur"));
                    liv.setStatut_livreur(rs.getString("statut_livreur"));
                    liv.setNum_tel_livreur(rs.getInt("Num_tel_livreur"));
                    livreurList.add(liv);
                }
            }
            return livreurList;
        }
    }



