package services;

import models.Event;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class ServiceEvent implements CRUD<Event> {
    private Connection cnx;
    public ServiceEvent(){cnx = DBConnection.getInstance().getCnx();}

    public void insertOne(Event event) throws SQLException {
        String req = "INSERT INTO `event` (`name`, `description`, `date_event`, `statut`, `lieu`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setDate(3, java.sql.Date.valueOf(event.getDate_event()));
        preparedStatement.setString(4, event.getStatut());
        preparedStatement.setString(5, event.getLieu());

        preparedStatement.executeUpdate();
    }

    public void updateOne(Event event) throws SQLException{
        if (eventExists(event.getId_event())) {
            try {
                String req = "UPDATE event SET id_event=?, name=?,description=?, date_event=?, statut=?, lieu=? WHERE id_event=?";
                PreparedStatement pst = cnx.prepareStatement(req);
                LocalDate localDate = event.getDate_event();
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                pst.setInt(1, event.getId_event());
                pst.setString(2, event.getName());
                pst.setString(3, event.getDescription());
                pst.setDate(4, sqlDate);
                pst.setString(5, event.getStatut());
                pst.setString(6, event.getLieu());
                pst.setInt(7, event.getId_event());
                pst.executeUpdate();
                System.out.println("event modifiée avec succés");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("L'événement avec l'identifiant " + event.getId_event() + " n'existe pas dans la base de données.");
        }
    }
    public void deleteOne(Event event) throws SQLException {
        // Vérifier si l'événement existe avant de le supprimer
        if (eventExists(event.getId_event())) {
            try {
                String requete = "DELETE FROM event WHERE id_event=?";
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, event.getId_event());
                pst.executeUpdate();
                System.out.println("Événement supprimé avec succès");
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de l'événement : " + ex.getMessage());
            }
        } else {
            System.out.println("L'événement avec l'identifiant " + event.getId_event() + " n'existe pas dans la base de données.");
        }
    }

    // Méthode pour vérifier si l'événement existe
    private boolean eventExists(int eventId) throws SQLException {
        String requete = "SELECT COUNT(*) FROM event WHERE id_event=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, eventId);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }


  /* @Override
    public List<Event> selectAll() throws SQLException {
        List<Event> EventList=new ArrayList<>();
        String req="SELECT * FROM `event`";
        Statement st = cnx.createStatement();
       LocalDate date2 = LocalDate.of(2022, 2, 7);
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            Event c=new Event();
            c.setId_event(rs.getInt(1));
            c.setName(rs.getString(2));
            c.setDescription(rs.getString(3));
            c.setDate_event(rs.getDate(4).toLocalDate());
            c.setStatut(rs.getString(5));
            c.setLieu(rs.getString(6));
            System.out.println("done");
        }
        return EventList;
    }*/
  @Override
  public List<Event> selectAll() throws SQLException {
      List<Event> EventList = new ArrayList<>();
      String req = "SELECT * FROM `event`";
      Statement st = cnx.createStatement();
      LocalDate date2 = LocalDate.of(2022, 2, 7);
      ResultSet rs = st.executeQuery(req);
      while (rs.next()) {
          Event c = new Event();
          c.setId_event(rs.getInt(1));
          c.setName(rs.getString(2));
          c.setDescription(rs.getString(3));
          c.setDate_event(rs.getDate(4).toLocalDate());
          c.setStatut(rs.getString(5));
          c.setLieu(rs.getString(6));
          EventList.add(c); // Ajout de l'événement à la liste
      }
      return EventList;
  }



}
