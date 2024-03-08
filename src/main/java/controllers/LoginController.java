

package controllers;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import models.Role;
import models.User;
//import javax.mail.Message;
//import javax.mail.Session;
import utils.DBConnection;

//import static org.bouncycastle.cms.RecipientId.password;


public class LoginController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField EmailTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button sendPaswword_btn;

    @FXML
    private PasswordField enterPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button mdp_oub;

    @FXML
    private Button registreButton;

    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;

    @FXML
    private Label loginMessageLabel;

    private Connection cnx;

    private FXMLLoader loader;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load branding image
            File brandingFile = new File("src/main/Images/416384399_3683592111886021_5329792893211008472_n.png");
            Image brandingImage = new Image(brandingFile.toURI().toString());
            brandingImageView.setImage(brandingImage);

            // Load lock image
            File lockFile = new File("src/main/Images/pngtree-vector-lock-icon-png-image_316863.jpg");
            Image lockImage = new Image(lockFile.toURI().toString());
            lockImageView.setImage(lockImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Hash(String password) throws Exception {

        String mdp_user = "";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(mdp_user.getBytes());

        byte byteData[] = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

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
    @FXML
    public void loginButtonOnAction(ActionEvent event) throws NoSuchAlgorithmException {
        String email = EmailTextField.getText();
        String password = enterPasswordField.getText();

        // Hash the entered password before comparison
        String hashedPassword = hashPassword(password);

        // Check if the user is an admin
        if (email.equals("ahmadou.ndiayewalymalick@esprit.tn") && hashedPassword.equals("8b03a11588f36a24ec410250af4f9a6289748fb36c0429a290f2177629300c8d")) {
            // Show admin welcome message
            showSuccessMessage("Bienvenue Admin");

            // Redirect to the admin interface
            redirectToAdminInterface();
        } else {
            // Authenticate regular user
            validateLogin(email, hashedPassword);
        }
    }


    private void redirectToUserInterface(User currentUser) {
        try {
            // Load the user interface FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuDynamicDevelopers.fxml"));
            Parent root = loader.load();

            // Pass the current user to the controller of the user interface
            MenuDynamicDevelopersController controller = loader.getController();
            controller.initializeUser(currentUser); // Initialize the user in MenuDynamicDevelopersController

            // Show the user interface
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            // Close the login window
            cancelButtonOnAction(null); // Assuming this method closes the login window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void redirectToAdminInterface() {
        try {
            // Load the admin interface FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/Back.fxml"));
            Scene scene = new Scene(root);

            // Create a new stage for the admin interface
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

            // Close the login window
            cancelButtonOnAction(null); // Assuming this method closes the login window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(" Success Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(String email, String hashedPassword) {
        // Check if either email or password is empty
        if (email.isEmpty() || hashedPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both email and password");
            return;
        }

        // Perform database query only if both fields are not empty
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getCnx();

        String verifyLogin = "SELECT * FROM user WHERE email = ? AND mdp = ?";
        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
            statement.setString(1, email);
            statement.setString(2, hashedPassword);

            ResultSet queryResult = statement.executeQuery();

            // After successful login, retrieve user data and pass it to the initializeUser method
            if (queryResult.next()) {
                // Retrieve user information from the query result
                int userID = queryResult.getInt("userID");
                String nom = queryResult.getString("nom");
                String prenom = queryResult.getString("prenom");
                String mdp = queryResult.getString("mdp");
                String userEmail = queryResult.getString("email");
                // Assuming you have a method to retrieve the user's role
                Role userRole = getUserRole(userID); // Implement this method accordingly

                // Create a User object with the retrieved information
                User currentUser = new User(userID, nom, prenom, mdp, userEmail, userRole);

                // Set the CurrentUser static field in the User class
                User.setCurrent_User(currentUser);

                // Open the user interface with the logged-in user information
                redirectToUserInterface(currentUser);

                // Assuming loader is correctly set up elsewhere
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front.fxml"));
                Parent root = loader.load();
                FrontController frontController = loader.getController();

                // Set the currentUser in FrontController
                frontController.setCurrentUser(currentUser);

                // Initialize user information in FrontController
                frontController.initializeUser(currentUser);

                // Show the user interface
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                loginMessageLabel.setText("Invalid login. Please try again");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while logging in.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the user interface.");
        }
    }




    public Role getUserRole(int userID) {
        // Define the SQL query to retrieve the user's role based on the user ID
        String query = "SELECT role FROM user WHERE userID = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/pi_project", "username", "password");
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the user ID parameter in the prepared statement
            statement.setInt(1, userID);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the query returned a result
            if (resultSet.next()) {
                // Retrieve the role from the result set
                String roleName = resultSet.getString("role");

                // Map the role name to a Role enum value (assuming you have a Role enum)
                return Role.valueOf(roleName); // Assuming Role is an enum with values like ADMIN, USER, etc.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if the user's role is not found or an error occurs
    }



    @FXML
    private void openSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registre.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void action_forgot_pass(ActionEvent event) {
        Stage Stage1 ;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/RestPass.fxml"));
            Stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
            root.setOnMousePressed(pressEvent -> {
                root.setOnMouseDragged(dragEvent -> {
                    Stage1.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    Stage1.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            Scene  scene = new Scene(root);
            Stage1.setScene(scene);
            Stage1.show();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @FXML
    void initialize() {
        assert EmailTextField != null : "fx:id=\"EmailTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert brandingImageView != null : "fx:id=\"brandingImageView\" was not injected: check your FXML file 'login.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'login.fxml'.";
        assert enterPasswordField != null : "fx:id=\"enterPasswordField\" was not injected: check your FXML file 'login.fxml'.";
        assert lockImageView != null : "fx:id=\"lockImageView\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";
        assert loginMessageLabel != null : "fx:id=\"loginMessageLabel\" was not injected: check your FXML file 'login.fxml'.";
        assert mdp_oub != null : "fx:id=\"mdp_oub\" was not injected: check your FXML file 'login.fxml'.";
        assert registreButton != null : "fx:id=\"registreButton\" was not injected: check your FXML file 'login.fxml'.";
    }


}
