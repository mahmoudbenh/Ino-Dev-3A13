module JavaPidev {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
<<<<<<< HEAD
    requires kernel;
    requires layout;
    requires io;
    requires java.mail;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires jdk.httpserver;
    //requires mail;
    //requires java.mail;
=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214

    opens controllers;
    opens models;
    opens test;
    opens utils;
    opens services;


}