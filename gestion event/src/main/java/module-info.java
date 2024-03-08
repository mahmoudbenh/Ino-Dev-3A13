module workshop.JDBC {

    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
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
    requires unirest.java;
    requires okhttp3;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens controllers;
    opens models;
    opens test;
    opens utils;
    opens services;


}