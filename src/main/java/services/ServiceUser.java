package services;

//import com.google.protobuf.DescriptorMessageInfoFactory;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import models.User;
import models.Role;
import utils.DBConnection;

//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes());

        // Convert byte array to a string representation of the hash
        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void insertOne1(User user) {
        System.out.println("Inserting user: " + user);
        String req = "INSERT INTO user (nom, prenom, mdp, email, role) VALUES (?, ?, ?, ?, ?)";
        try {
            // Hash the password before storing it in the database
            String hashedPassword = hashPassword(user.getMdp());

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, hashedPassword); // Store hashed password
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole().toString());
            ps.executeUpdate();
            System.out.println("User added!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

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
                String roleS = rs.getString("role");
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

    /*public void generatePDF(File file) throws IOException, java.io.IOException {
        ServiceUser serviceUser = new ServiceUser(); // Instantiate the service class

        // Retrieve the user list from the service
        ObservableList<User> userList = serviceUser.selectAll(); // Adjust this method according to your service class

        // Create a PdfWriter object
        PdfWriter writer = new PdfWriter(file);

        // Create a PdfDocument object
        PdfDocument pdfDocument = new PdfDocument(writer);

        // Create a Document object
        Document document = new Document(pdfDocument);

        // Create a paragraph for the title
        Paragraph title = new Paragraph("Liste des utilisateurs")
                .setFontSize(20)
                .setBold();

        // Add the title to the document
        document.add(title);

        // Add current date to the document
        document.add(new Paragraph("Date d'impression: " + new Date()));

        // Create a table with 5 columns
        Table table = new Table(5);

        // Add table headers
        table.addCell("ID");
        table.addCell("Nom");
        table.addCell("Prenom");
        table.addCell("Email");
        table.addCell("Role");

        // Populate table with user data
        for (User user : userList) {
            table.addCell(String.valueOf(user.getUserID()));
            table.addCell(user.getNom());
            table.addCell(user.getPrenom());
            table.addCell(user.getEmail());
            table.addCell(user.getRole().toString());
        }

        // Add table to the document
        document.add(table);

        // Close the Document
        document.close();

        // Close the PdfDocument
        pdfDocument.close();

        // Close the PdfWriter
        writer.close();

        System.out.println("Users PDF generated successfully.");
    }*/

    public void generatePDF(File file, User user) throws IOException, java.io.IOException {
        // Create a PdfWriter object
        PdfWriter writer = new PdfWriter(file);

        // Create a PdfDocument object
        PdfDocument pdfDocument = new PdfDocument(writer);

        // Create a Document object
        Document document = new Document(pdfDocument);

        // Create a paragraph for the title
        Paragraph title = new Paragraph("Informations utilisateur")
                .setFontSize(20)
                .setBold();

        // Add the title to the document
        document.add(title);

        // Add user information to the document
        document.add(new Paragraph("ID: " + user.getUserID()));
        document.add(new Paragraph("Nom: " + user.getNom()));
        document.add(new Paragraph("Prenom: " + user.getPrenom()));
        document.add(new Paragraph("Email: " + user.getEmail()));
        document.add(new Paragraph("Role: " + user.getRole().toString()));

        // Close the Document
        document.close();

        // Close the PdfDocument
        pdfDocument.close();

        // Close the PdfWriter
        writer.close();

        System.out.println("User PDF generated successfully.");
    }


    /*public void sendMail(String receveursList, String object, String corps) {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        String MonEmail = "samuelsims45@gmail.com";
        String password = "lfaa fdci klzq baxr";

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(MonEmail, password);
            }

        });
        javax.mail.Message message = prepareMessage(session, MonEmail, receveursList, object, corps);

        try {
            javax.mail.Transport.send(message);
        } catch (javax.mail.MessagingException ex) {
            System.out.println();
        }

        System.err.println("Message envoyée avec succés");
    }*/


    /*private static javax.mail.Message prepareMessage(Session session, String email, String receveursList, String object, String corps) {
        javax.mail.Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(email));

            message.setSubject(object);
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(receveursList));
            message.setText(corps);

            return message;
        } catch (javax.mail.MessagingException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }*/

    public boolean verifierEmailBd(String email) { //Controle De Saisie si mail existe
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT * FROM user WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, email);
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public void information_Box(String title, String message) {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setTitle(title);
        dg.setContentText(message);
        dg.show();
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean testEmail(String mail) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        return matcher.find();
    }

    public void alert_Box(String title, String message) {
        Alert dg = new Alert(Alert.AlertType.WARNING);
        dg.setTitle(title);
        dg.setContentText(message);
        dg.show();
    }

    public static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", Pattern.CASE_INSENSITIVE);

    public boolean testPassword(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }


    public void modifierPassword(String mail, String password) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE user SET password=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public User getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllByUser(int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}