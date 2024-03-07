package services;
import models.Pivot;
import models.Event;
import utils.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceEvent implements CRUD<Event> {
    private static Connection cnx;

    public ServiceEvent() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public List<Event> selectAll() throws SQLException {
        List<Event> eventList = new ArrayList<>();
        String req = "SELECT * FROM `event`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Event event = new Event();
            event.setId_event(rs.getInt(1));
            System.out.println("Retrieved ID from database: " + event.getId_event()); // Debug message
            event.setName(rs.getString(2));
            event.setDescription(rs.getString(3));
            event.setDate_event(rs.getDate(4).toLocalDate());
            event.setStatut(rs.getString(5));
            event.setLieu(rs.getString(6));
            event.setImage_event(rs.getString(7));
            eventList.add(event);
        }
        return eventList;
    }

/*    public List<Pivot> getPivotEntries(int eventId) throws SQLException {
        List<Pivot> pivotEntries = new ArrayList<>();
        String query = "SELECT * FROM pivot WHERE event_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, eventId);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int idUE = resultSet.getInt("id_u_e");
                    int userId = resultSet.getInt("user_id");
                    int eventId = resultSet.getInt("event_id");

                    Pivot pivotEntry = new Pivot(idUE, userId, eventId);
                    pivotEntries.add(pivotEntry);
                }
            }
        }

        return pivotEntries;
    }*/

    public static void updateStatusToNotDone(int eventId) throws SQLException {
        String req = "UPDATE event SET statut='not done' WHERE id_event=?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, eventId);
        pst.executeUpdate();
        System.out.println("Status updated to 'not done' successfully");
    }

    public Event selectOne(int eventId) throws SQLException {
        Event selectedEvent = null;

        String query = "SELECT * FROM event WHERE id_event = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, eventId);

            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    selectedEvent = new Event();
                    selectedEvent.setId_event(resultSet.getInt(1));
                    selectedEvent.setName(resultSet.getString(2));
                    selectedEvent.setDescription(resultSet.getString(3));
                    selectedEvent.setDate_event(resultSet.getObject(4, LocalDate.class));
                    selectedEvent.setStatut(resultSet.getString(5));
                    selectedEvent.setLieu(resultSet.getString(6));
                    selectedEvent.setImage_event(resultSet.getString(7));
                }
            }
        }

        return selectedEvent;
    }

    public void insertOne(Event event) throws SQLException {
        String req = "INSERT INTO `event` (`name`, `description`, `date_event`, `statut`, `lieu`,`image_event`) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = cnx.prepareStatement(req);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getDescription());
        preparedStatement.setDate(3, java.sql.Date.valueOf(event.getDate_event()));
        preparedStatement.setString(4, event.getStatut());
        preparedStatement.setString(5, event.getLieu());
        preparedStatement.setString(6, event.getImage_event());


        preparedStatement.executeUpdate();
    }

    public void updateOne(Event event) throws SQLException {
        if (eventExists(event.getId_event())) {
            String req = "UPDATE event SET name=?, description=?, date_event=?, statut=?, lieu=? WHERE id_event=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, event.getName());
            pst.setString(2, event.getDescription());
            pst.setDate(3, java.sql.Date.valueOf(event.getDate_event()));
            pst.setString(4, event.getStatut());
            pst.setString(5, event.getLieu());
            pst.setInt(6, event.getId_event());
            pst.executeUpdate();
            System.out.println("Event updated successfully");
        } else {
            System.out.println("The event with ID " + event.getId_event() + " does not exist in the database.");
        }
    }
    public static void updateEvent(Event event) throws SQLException {
        if (eventExists(event.getId_event())) {
            String req = "UPDATE event SET name=?, description=?, date_event=?, statut=?, lieu=? WHERE id_event=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, event.getName());
            pst.setString(2, event.getDescription());
            pst.setDate(3, java.sql.Date.valueOf(event.getDate_event()));
            pst.setString(4, event.getStatut());
            pst.setString(5, event.getLieu());
            pst.setInt(6, event.getId_event());
            pst.executeUpdate();
            System.out.println("Event updated successfully");
        } else {
            System.out.println("The event with ID " + event.getId_event() + " does not exist in the database.");
        }
    }


    public void deleteOne(Event event) throws SQLException {
        if (eventExists(event.getId_event())) {
            String req = "DELETE FROM event WHERE id_event=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, event.getId_event());
            pst.executeUpdate();
            System.out.println("Event deleted successfully");
        } else {
            System.out.println("The event with ID " + event.getId_event() + " does not exist in the database.");
        }
    }

    private static boolean eventExists(int eventId) throws SQLException {
        String req = "SELECT COUNT(*) FROM event WHERE id_event=?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, eventId);
        ResultSet rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;
    }

    public void insertParticipant(int userID, int eventID) throws SQLException {
        String query = "INSERT INTO pivot (user_id, event_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, eventID);
            pstmt.executeUpdate();
        }
    }

    public boolean isParticipantRegistered(int userId, int eventId) throws SQLException {
        String query = "SELECT COUNT(*) FROM pivot WHERE user_id = ? AND event_id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }
}
