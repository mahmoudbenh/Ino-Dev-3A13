package com.gmail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import models.rembourssement;
import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.mail.Service;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.google.api.services.gmail.GmailScopes.*;
import static javax.mail.Message.RecipientType.TO;

public class GMailer {
private static final String TEST_Email = "rostomatri60@gmail.com";
private final Gmail service;

public  GMailer() throws Exception{
    //send email to yourself
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
            .setApplicationName("Test Mailer")
            .build();
}

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws  IOException {
        // Load client secrets.
               GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jsonFactory, new InputStreamReader(GMailer.class.getResourceAsStream("/client_secret_183263072035-2aigp0um6bia494vg0v2d5r04eoh2e0g.apps.googleusercontent.com.json")));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
               httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }



public void sendRembourssementMail(List<rembourssement> rembourssements) throws Exception {
    for (rembourssement rembourssement : rembourssements) {
        String subject;
        String messageTemplate;

        if ("Rembourse".equals(rembourssement.getStatut_rembourssement())) {

            subject = "Remboursement effectué";
            messageTemplate = "Bonjour, votre rembourssement a été effectué avec succès.";
        } else {
            subject = "Échec du remboursement";
            messageTemplate = "Bonjour, votre reclamation a été ignoré";
        }
        sendMail(rembourssement.getEmail(), subject, messageTemplate);
    }
}
    public void sendMail(String recipient, String subject, String messageTemplate) throws Exception {
        // Vérifier si l'adresse e-mail est valide
        if (recipient == null || recipient.trim().isEmpty()) {
            System.err.println("Adresse e-mail invalide : " + recipient);
            return; // Arrêter le traitement si l'adresse e-mail est invalide
        }

        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        // Ajouter le destinataire seulement si l'adresse e-mail est valide
        email.addRecipient(TO, new InternetAddress(recipient));

        email.setFrom(new InternetAddress(TEST_Email));
        email.setSubject(subject);
        email.setText(messageTemplate);

        // Encode and wrap the MIME message into a Gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            // Create send message
            msg = service.users().messages().send("me", msg).execute();
            System.out.println("Message id: " + msg.getId());
            System.out.println(msg.toPrettyString());

        } catch (GoogleJsonResponseException e) {

            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Récupérez la liste des remboursements depuis la base de données
        List<rembourssement> rembourssements = fetchRemboursementsFromDatabase();

        // Utilisez la liste des remboursements pour envoyer des emails personnalisés
        new GMailer().sendRembourssementMail(rembourssements);
    }
    private static List<rembourssement> fetchRemboursementsFromDatabase() {
        List<rembourssement> rembourssements = new ArrayList<>();

        // Utilisez try-with-resources pour assurer une fermeture propre de la connexion
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reclamation", "root", "")) {
            String sql = "SELECT email, statut_rembourssement FROM rembourssement";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String statutRemboursementStr = resultSet.getString("statut_rembourssement");


                    rembourssement rembourssement = new rembourssement();
                    rembourssement.setEmail(email);
                    rembourssement.setStatut_rembourssement(rembourssement.getStatut_rembourssement());

                    rembourssements.add(rembourssement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions d'accès à la base de données de manière appropriée
        }

        return rembourssements;
    }

}







