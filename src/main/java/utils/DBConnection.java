package utils;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/pi_projet";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    //Second Step: Creer une instance static de meme type que la classe
    private static DBConnection instance;

    private Connection cnx;

    //First Step: Rendre le constructeur privé
    public DBConnection() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected To DATABASE !");
        } catch (SQLException e) {
            System.err.println("Error: Unable to connect to the database: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for detailed error information
        }
    }


    //Thrid Step: Creer une methode static pour recuperer l'instance

    public static DBConnection getInstance(){
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
