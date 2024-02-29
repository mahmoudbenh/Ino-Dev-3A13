module JavaPidev {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens controllers;
    opens models;
    opens test;
    opens utils;
    opens services;


}