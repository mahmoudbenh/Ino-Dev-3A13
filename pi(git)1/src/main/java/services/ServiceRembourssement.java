package services;

import models.rembourssement;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
public class ServiceRembourssement implements CRUD<rembourssement> {
    private Connection cnx;

    public ServiceRembourssement() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void insertOne(rembourssement rembourssement) throws SQLException {

        String req = "INSERT INTO `rembourssement`(`id_reclamation`, `prix`, `date_rembourssement`, `heure`, `statut_rembourssement`, `mode_paiement`) VALUES (" +
                rembourssement.getId_reclamation() + ", " +
                rembourssement.getPrix() + ", '" + rembourssement.getDate_rembourssement() + "','" +rembourssement.getHeure() + "', '"+ rembourssement.getStatut_rembourssement() + "','" + rembourssement.getMode_paiement() + "')";


        Statement st = cnx.createStatement();
        st.executeUpdate(req);

    }

    public void updateOne(rembourssement rembourssement) throws SQLException{
       /* String req="UPDATE `reclamation` SET "+
                "`id_reclamation`="+reclamation.getId_reclamation()+",`id_client`="+reclamation.getId_client()+",`date_reclamation`='"+reclamation.getDate_reclamation()+"',`type_reclamation`='"+reclamation.getType_reclamation()+"',`description`='"+reclamation.getDescription()+"' where 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);*/
        try {
            String req = "UPDATE rembourssement SET id_reclamation=?,  prix=?, date_rembourssement=?,heure=?, statut_rembourssement=?, mode_paiement=?  WHERE id_rembourssement=?";

            PreparedStatement pst = cnx.prepareStatement(req);
            LocalDate localDate = rembourssement.getDate_rembourssement();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            LocalTime localTime = rembourssement.getHeure();
            java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);
            pst.setInt(1,rembourssement.getId_reclamation());
            pst.setFloat(2, rembourssement.getPrix());
            pst.setDate(3, sqlDate);
            pst.setTime(4, sqlTime);
            pst.setString(5, rembourssement.getStatut_rembourssement());
            pst.setString(6, rembourssement.getMode_paiement());
            pst.setInt(7, rembourssement.getId_rembourssement());
            pst.executeUpdate();
            System.out.println("remboussement modifiée avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void deleteOne(rembourssement rembourssement) throws SQLException{
        // String req="DELETE FROM `reclamation` WHERE `reclamation`.`id_reclamation`='"+id_reclamation+"';";
        /*try { String req="DELETE FROM `reclamation` WHERE `reclamation`.`id_reclamation`='"+ reclamation.getId_client()+"';";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion de l'exception (affichage de la trace)
        }*/
        try {
            String requete = "DELETE FROM rembourssement where id_rembourssement=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, rembourssement.getId_rembourssement());
            pst.executeUpdate();
            System.out.println("rembourssement supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<rembourssement> selectAll() throws SQLException{
        List<rembourssement> rembourssementList = new ArrayList<>();
        String req = "SELECT * FROM `rembourssement`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next())

        {

            rembourssement rb = new rembourssement();
            rb.setId_rembourssement(rs.getInt("id_rembourssement"));
            rb.setId_reclamation(rs.getInt("id_reclamation"));
            rb.setHeure(rs.getTime("heure").toLocalTime());
            rb.setPrix(rs.getFloat("prix"));
            rb.setDate_rembourssement(rs.getDate("date_rembourssement").toLocalDate());
            rb.setStatut_rembourssement(rs.getString("statut_rembourssement"));
            rb.setMode_paiement(rs.getString("mode_paiement"));
            rembourssementList.add(rb);
        }
        return rembourssementList;
    }
}
