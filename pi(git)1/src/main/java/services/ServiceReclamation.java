package services;
import models.reclamation;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
public class ServiceReclamation implements CRUD<reclamation> {
    private Connection cnx;

    public ServiceReclamation() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void insertOne(reclamation reclamation) throws SQLException {
        //String req = "INSERT INTO `reclamation`(`id_client`, `date_reclamation`, `type_reclamation`, `description`) VALUES " + "(" + reclamation.getId_client() +",'"reclamation.getDate_reclamation() +"','" reclamation.getType_reclamation() + "','"reclamation.getDescription() + '")";
        String req = "INSERT INTO `reclamation`(`id_client`, `titre_reclamation`, `date_reclamation`, `type_reclamation`, `statut_reclamation`, `description`) VALUES (" +
                reclamation.getId_client() + ",'" +reclamation.getTitre_reclamation() + "' ,'" + reclamation.getDate_reclamation() + "', '" +
                reclamation.getType_reclamation() + "', '" + reclamation.getStatut_reclamation() +"' ,'" + reclamation.getDescription() + "')";


        Statement st = cnx.createStatement();
        st.executeUpdate(req);

    }

    public void updateOne(reclamation reclamation) throws SQLException{
       /* String req="UPDATE `reclamation` SET "+
                "`id_reclamation`="+reclamation.getId_reclamation()+",`id_client`="+reclamation.getId_client()+",`date_reclamation`='"+reclamation.getDate_reclamation()+"',`type_reclamation`='"+reclamation.getType_reclamation()+"',`description`='"+reclamation.getDescription()+"' where 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);*/
        try {
            String req = "UPDATE reclamation SET id_client=?,titre_reclamation=?, date_reclamation=?, type_reclamation=?,`statut_reclamation`=?, description=? WHERE id_reclamation=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            LocalDate localDate = reclamation.getDate_reclamation();
            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
            pst.setInt(1, reclamation.getId_client());
            pst.setString(2, reclamation.getTitre_reclamation());
            pst.setDate(3, sqlDate);
            pst.setString(4, reclamation.getType_reclamation());
            pst.setString(5, reclamation.getStatut_reclamation());
            pst.setString(6, reclamation.getDescription());

            pst.setInt(7, reclamation.getId_reclamation());

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
            r.setId_client(rs.getInt("id_client"));
            r.setTitre_reclamation(rs.getString("titre_reclamation"));
            r.setDate_reclamation(rs.getDate("date_reclamation").toLocalDate());
            r.setType_reclamation(rs.getString("type_reclamation"));
            r.setStatut_reclamation(rs.getString("statut_reclamation"));
            r.setDescription(rs.getString("description"));
            reclamationList.add(r);
        }
        return reclamationList;
    }
}
