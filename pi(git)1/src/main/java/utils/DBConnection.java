package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String  URL = "jdbc:mysql://localhost:3306/reclamation";
    private static final String  USER = "root";
    private static final String  PASSWORD = "";

    //second step creer une instance static de meme type que la classe
    private static DBConnection instance;
    private Connection cnx;
    //first step rendre le const priv
    private DBConnection() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connected to database !");
        } catch (SQLException e) {
            System.err.println("Error :" + e.getMessage());
        }}
    //third step : creeer une methode static pour recuperer linstance
    public static DBConnection getInstance(){
        if (instance == null) instance = new DBConnection();
        return instance;
    }
    public Connection getCnx(){
        return cnx;
    }
}


