package services;
import models.reclamation;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceReclamation implements CRUD<reclamation> {
    private Connection cnx;

    public ServiceReclamation() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void insertOne(reclamation reclamation) throws SQLException {
        //String req = "INSERT INTO `reclamation`(`id_client`, `date_reclamation`, `type_reclamation`, `description`) VALUES " + "(" + reclamation.getId_client() +",'"reclamation.getDate_reclamation() +"','"reclamation.getHeure()+"','" reclamation.getType_reclamation() + "','"reclamation.getDescription() + '")";
       /* String req = "INSERT INTO `reclamation`(`UserID`, `titre_reclamation`, `date_reclamation`,`heure`, `type_reclamation`, `statut_reclamation`, `description`) VALUES (" +
                reclamation.getUserID() + ",'" +reclamation.getTitre_reclamation() + "' ,'" + reclamation.getDate_reclamation() + "', '" + reclamation.getHeure() + "', '"
                reclamation.getType_reclamation() + "', '" + reclamation.getStatut_reclamation() +"' ,'" + reclamation.getDescription() + "')";*/
        String req ="INSERT INTO `reclamation`(`UserID`, `titre_reclamation`, `date_reclamation`, `heure`, `type_reclamation`, `statut_reclamation`, `description`) VALUES (" +
                reclamation.getUserID() + ", '" + reclamation.getTitre_reclamation() + "', '" + reclamation.getDate_reclamation() + "', '" + reclamation.getHeure() + "', '" +
                reclamation.getType_reclamation() + "', '" + reclamation.getStatut_reclamation() + "', '" + reclamation.getDescription() + "')";



        Statement st = cnx.createStatement();
        st.executeUpdate(req);

    }

    public void updateOne(reclamation reclamation) throws SQLException{
       /* String req="UPDATE `reclamation` SET "+
                "`id_reclamation`="+reclamation.getId_reclamation()+",`id_client`="+reclamation.getId_client()+",`date_reclamation`='"+reclamation.getDate_reclamation()+"',`heure`='"+reclamation.getHeure()+"',`type_reclamation`='"+reclamation.getType_reclamation()+"',`description`='"+reclamation.getDescription()+"' where 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);*/
        try {
            String req = "UPDATE reclamation SET UserID=?,titre_reclamation=?, date_reclamation=?,heure=?, type_reclamation=?,`statut_reclamation`=?, description=? WHERE id_reclamation=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            LocalDate localDate = reclamation.getDate_reclamation();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            LocalTime localTime = reclamation.getHeure();
            java.sql.Time sqlTime = java.sql.Time.valueOf(localTime);
            pst.setInt(1, reclamation.getUserID());
            pst.setString(2, reclamation.getTitre_reclamation());
            pst.setDate(3, sqlDate);
            pst.setTime(4, sqlTime);
            pst.setString(5, reclamation.getType_reclamation());
            pst.setString(6, reclamation.getStatut_reclamation());
            pst.setString(7, reclamation.getDescription());

            pst.setInt(8, reclamation.getId_reclamation());

            pst.executeUpdate();
            System.out.println("reclamation modifiée avec succés");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void deleteOne(reclamation reclamation) throws SQLException{
        // String req="DELETE FROM `reclamation` WHERE `reclamation`.`id_reclamation`='"+id_reclamation+"';";
        /*try { String req="DELETE FROM `reclamation` WHERE `reclamation`.`id_reclamation`='"+ reclamation.getId_client()+"';";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion de l'exception (affichage de la trace)
        }*/
        try {
            String requete = "DELETE FROM reclamation where id_reclamation=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, reclamation.getId_reclamation());
            pst.executeUpdate();
            System.out.println("reclamation supprimer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<reclamation> selectAll() throws SQLException{
        List<reclamation> reclamationList = new ArrayList<>();
        String req = "SELECT * FROM `reclamation`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while(rs.next())

        {

            reclamation r = new reclamation();
            r.setId_reclamation(rs.getInt("id_reclamation"));
             r.setUserID(rs.getInt("UserID"));
            r.setTitre_reclamation(rs.getString("titre_reclamation"));
            r.setDate_reclamation(rs.getDate("date_reclamation").toLocalDate());
            r.setHeure(rs.getTime("heure").toLocalTime());
            r.setType_reclamation(rs.getString("type_reclamation"));
            r.setStatut_reclamation(rs.getString("statut_reclamation"));
            r.setDescription(rs.getString("description"));
            reclamationList.add(r);
        }
        return reclamationList;
    }
}
