package services;

import models.Participant;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class Mail {




    public static void sendEmail(String recipientEmail, Participant p) {
        // Sender's email ID needs to be mentioned
        String from = "ttelacontact@gmail.com";
        // Assuming you are sending email from Gmail
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");



        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ttelacontact@gmail.com", "umemwindhcrlkjvi");
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            // Set Subject: header field
            message.setSubject("NewsLetter");
            // Now set the actual message
            message.setText("Cher "+ p.getNom() +","  +"\n" + "Merci pour votre participation." +"\n" + "Pour garantir une entrée fluide le jour de l'événement, n'oubliez pas de prendre une photo du code QR correspondant à cet événement. Ce code QR servira de billet numérique, permettant un enregistrement rapide et efficace à votre arrivée. Votre coopération à cet égard nous aidera grandement à faciliter une expérience sans accroc pour tous les participants. " +"\n" +"Cordialement INODEV." );
          //  message.setText("Merci pour votre participation." +"\n");
            //message.setText("Pour garantir une entrée fluide le jour de l'événement, n'oubliez pas de prendre une photo du code QR correspondant à cet événement. Ce code QR servira de billet numérique, permettant un enregistrement rapide et efficace à votre arrivée. Votre coopération à cet égard nous aidera grandement à faciliter une expérience sans accroc pour tous les participants. " +"\n");

           // message.setText("Cordialement INODEV.");


            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }



}
