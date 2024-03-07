package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/event1";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    //Second Step: creer const
    private static DBConnection instance;

    private Connection cnx;
//first step : rendre le constructeur privé

    private DBConnection()
    {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Database");
        }catch (SQLException e)
        {
            System.err.println("Error: "+e.getMessage());
        }

    }
    //third step: creer une methode statique pour récuperer l'instance
    public static DBConnection getInstance(){
        if (instance== null)
            instance= new DBConnection();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
