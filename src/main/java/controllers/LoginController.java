

package controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import services.ServiceUser;
import utils.DBConnection;

import javax.imageio.IIOParam;


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
    private PasswordField enterPasswordField;

    @FXML
    private Button loginButton;

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


    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        String email = EmailTextField.getText();
        String password = enterPasswordField.getText();

        // Check if the user is an admin
        if (email.equals("ahmadou.ndiayewalymalick@esprit.tn") && password.equals("ahmadou123")) {
            // Show admin welcome message
            showSuccessMessage("Bienvenue Admin");

            // Redirect to the admin interface
            redirectToAdminInterface();
        } else {
            // Authenticate regular user
            validateLogin();
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

    /*public void validateLogin() {
        String email = EmailTextField.getText();
        String password = enterPasswordField.getText();

        // Check if either email or password is empty
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both email and password");
            return;
        }

        // Perform database query only if both fields are not empty
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getCnx();

        String verifyLogin = "SELECT * FROM user WHERE email = '" + email + "' AND mdp = '" + password + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            // After successful login, retrieve user data and pass it to the initializeUser method
            if (queryResult.next()) {
                // Retrieve user information from the query result
                int userID = queryResult.getInt("userID");
                String nom = queryResult.getString("nom");
                String prenom = queryResult.getString("prenom");
                String mdp = queryResult.getString("mdp");
                String userEmail = queryResult.getString("email");
                // Assuming you have a method to retrieve the user's role
                Role userRole = getUserRole(userID); // i Implement this method accordingly

                // Create a User object with the retrieved information
                User currentUser = new User(userID, nom, prenom, mdp, userEmail, userRole);

                // Open the user interface with the logged-in user information
                redirectToUserInterface(currentUser);

                // Initialize the current user in the FrontController
                 FrontController frontController = loader.getController();
                frontController.initializeUser(currentUser);
            } else {
                loginMessageLabel.setText("Invalid login. Please try again");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void validateLogin() {
        String email = EmailTextField.getText();
        String password = enterPasswordField.getText();

        // Check if either email or password is empty
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both email and password");
            return;
        }

        // Perform database query only if both fields are not empty
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getCnx();

        String verifyLogin = "SELECT * FROM user WHERE email = '" + email + "' AND mdp = '" + password + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

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

        } catch (Exception e) {
            e.printStackTrace();
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
    void initialize() {
        assert EmailTextField != null : "fx:id=\"EmailTextField\" was not injected: check your FXML file 'login.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'login.fxml'.";
        assert enterPasswordField != null : "fx:id=\"enterPasswordField\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'.";

    }

}
