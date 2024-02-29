package services;

//import com.google.protobuf.DescriptorMessageInfoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Role;
import models.User;
import utils.DBConnection;

import java.sql.*;

public class ServiceUser implements CRUD<User> {

    private Connection cnx;

    public ServiceUser() {
        cnx = DBConnection.getInstance().getCnx();
        if (cnx != null) {
            System.out.println("Database connection established successfully");
        } else {
            System.err.println("Failed to establish database connection");
        }
    }

    public User selectById(int id) {
        User user = null;
        String req = "SELECT * FROM user WHERE UserID = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMdp(rs.getString("mdp"));
                // Assuming the role is stored as a string in the database
                String roleString = rs.getString("role");
                Role role = Role.valueOf(roleString); // Convert string to Role enum
                user.setRole(role);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return user;
    }

    public void insertOne1(User user) {

        System.out.println("Inserting user: " + user);
        String req = "INSERT INTO user (nom, prenom, mdp, email, role) VALUES (?, ?, ?, ?, ?)";
        try {
            //String hashedPassword = Hash(user.getMdp()); // Assuming Hash() hashes the password

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getMdp()); // Set the hashed password
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole().toString());
            ps.executeUpdate();
            System.out.println("User added!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


    /*@Override
    public boolean modifier(User u) {
        String req = "update user set nom = ? , prenom = ?  where UserID = ? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setInt(3, u.getUserID());
            ps.executeUpdate();
            System.out.println("user Modified !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }*/

    @Override
    public boolean modifier(User u) {
        String req = "UPDATE user SET nom = ?, prenom = ?, mdp = ?, email = ? WHERE UserID = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getMdp());
            ps.setString(4, u.getEmail());
            ps.setInt(5, u.getUserID());

            // Add debug statements to print SQL query and parameters
            System.out.println("Executing SQL Query: " + req);
            System.out.println("Parameters: " + u.getNom() + ", " + u.getPrenom() + ", " + u.getUserID());

            ps.executeUpdate();
            System.out.println("User Modified !");
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }


    @Override
    public boolean supprimer(User u) {
        String req = "DELETE FROM user WHERE UserID = ? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, u.getUserID());
            ps.executeUpdate();
            System.out.println("User Deleted");
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public ObservableList<User> getUser() {
        ObservableList<User> list = FXCollections.observableArrayList();
        try {

            String req = "SELECT * FROM user ";
            PreparedStatement st = cnx.prepareStatement(req);

            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setMdp(rs.getString("mdp"));
                u.setEmail(rs.getString("email"));
                //Utilisateur
                String roleS=rs.getString("role");
                Role role = Role.valueOf(roleS);
                u.setRole(role);
                list.add(u);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;

    }

    public ObservableList<User> selectAll() {
        ObservableList<User> userList = FXCollections.observableArrayList(); // Initialize as ObservableList

        try {
            String req = "SELECT * FROM `user`";
            Statement st = cnx.createStatement();

            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                User p = new User();

                p.setUserID(rs.getInt(("UserID")));
                p.setNom(rs.getString(("nom")));
                p.setPrenom(rs.getString(("prenom")));
                p.setEmail(rs.getString(("email")));
                p.setMdp(rs.getString(("mdp")));

                // Get role string from database
                String roleS = rs.getString("role");

                // Convert role string to Role enum using fromString method
                Role role = Role.fromString(roleS);

                // Set the role for the user
                p.setRole(role);

                userList.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return userList;
    }


}
