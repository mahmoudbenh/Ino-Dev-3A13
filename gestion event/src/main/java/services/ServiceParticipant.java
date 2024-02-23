package services;

import models.*;
import models.Participant;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceParticipant implements CRUD<Participant> {
    private Connection cnx;
    public ServiceParticipant(){cnx = DBConnection.getInstance().getCnx();}

    public void insertOne(Participant participant) throws SQLException {
        String req = "INSERT INTO Participant (UserID, id_event, nom, email, tel) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setInt(1, participant.getUserID());
        preparedStatement.setInt(2, participant.getId_event());
        preparedStatement.setString(3, participant.getNom());
        preparedStatement.setString(4, participant.getEmail());
        preparedStatement.setInt(5, participant.getTel());

        preparedStatement.executeUpdate();
    }

    public void updateOne(Participant participant) throws SQLException {
        if (ParticipantExists(participant.getUserID())) {
            try {
                String req = "UPDATE Participant SET id_event=?, nom=?, email=?, tel=? WHERE UserID=?";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setInt(1, participant.getId_event());
                pst.setString(2, participant.getNom());
                pst.setString(3, participant.getEmail());
                pst.setInt(4, participant.getTel());
                pst.setInt(5, participant.getUserID());
                pst.executeUpdate();
                System.out.println("Participant modifié avec succès");
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
            }
        } else {
            System.out.println("Le participant avec l'identifiant " + participant.getUserID() + " n'existe pas dans la base de données.");
        }
    }

  /*  public void updateOne(Participant participant) throws SQLException {
        if (ParticipantExists(participant.getUserID())) {
            try {
                String req = "UPDATE Participant SET id_event=?, nom=?, email=?, tel=? WHERE UserID=?";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setInt(1, participant.getId_event());
                pst.setString(2, participant.getNom());
                pst.setString(3, participant.getEmail());
                pst.setInt(4, participant.getTel());
                pst.setInt(5, participant.getUserID());
                pst.executeUpdate();
                System.out.println("Participant modifié avec succès");
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
            }
        } else {
            System.out.println("Le participant avec l'identifiant " + participant.getUserID() + " n'existe pas dans la base de données.");
        }
    }*/


    public void deleteOne(Participant Participant) throws SQLException {
        if (ParticipantExists(Participant.getUserID())) {
            try {
                String requete = "DELETE FROM Participant WHERE UserID=?";
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, Participant.getUserID());
                pst.executeUpdate();
                System.out.println("Participant supprimé avec succès");
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression du participant : " + ex.getMessage());
            }
        } else {
            System.out.println("Le participant  avec l'identifiant " + Participant.getUserID() + " n'existe pas dans la base de données.");
        }
    }

    private boolean ParticipantExists(int UserID) throws SQLException {
        String requete = "SELECT COUNT(*) FROM Participant WHERE UserID=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, UserID);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }

    @Override
    public List<Participant> selectAll() throws SQLException {
        List<Participant> ParticipantList = new ArrayList<>();
        String req = "SELECT * FROM `Participant`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Participant c = new Participant();

            c.setUserID(rs.getInt(1));
            c.setId_event(rs.getInt(2));
            c.setNom(rs.getString(3));
            c.setEmail(rs.getString(4));
            c.setTel(rs.getInt(5));
            ParticipantList.add(c); //
        }
        return ParticipantList;
    }

}
