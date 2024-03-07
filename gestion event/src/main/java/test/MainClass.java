package test;

import models.Event;
import models.Participant;
import services.ServiceEvent;
import services.ServiceParticipant;
import utils.DBConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class MainClass {
    public static void main(String[] args) {
        /*DBConnection cn1 = DBConnection.getInstance();
        DBConnection cn2 = DBConnection.getInstance();
        DBConnection cn3 = DBConnection.getInstance();
        DBConnection cn4 = DBConnection.getInstance();

        System.out.println(cn1.hashCode());
        System.out.println(cn2.hashCode());
        System.out.println(cn3.hashCode());
        System.out.println(cn4.hashCode());*/
        DBConnection cn1 = DBConnection.getInstance();
        LocalDate date1 = LocalDate.of(2024, 2, 1);
        Event c1= new Event("job fair ","ffhrthffhfjjf",date1,"en cours","nasser2","0");
        LocalDate date2 = LocalDate.of(2022, 2, 7);
         Event c2= new Event(5555,"job fair ","ffhrthffhfjjf",date2,"en cours","nasser2","555");
        ServiceEvent sc= new ServiceEvent();
        //test ajout c1
         /*try{
            sc.insertOne(c1);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }
        //test ajout c2
        try {
            sc.insertOne(c2);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }*/
        //test supp
      /*  int eventIdToTest = 0;
        Event eventToDelete = new Event();
        eventToDelete.setId_event(eventIdToTest);
        ServiceEvent serviceEvent = new ServiceEvent();
        try {
            serviceEvent.deleteOne(eventToDelete);
        } catch (SQLException ex) {
            System.err.println("Erreur : " + ex.getMessage());
        }*/
        //test update
       /* Event eventToUpdate = new Event(0,"linda ","aaaaa",date1,"anuule","new york");
        try {
            sc.updateOne(eventToUpdate);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de l'event: " + ex.getMessage());
        }*/
        //test select
       /* try {
            List<Event> eventList = sc.selectAll();
            if (eventList.isEmpty()) {
                System.out.println("Aucun événement trouvé dans la base de données.");
            } else {
                System.out.println("Liste des événements :");
                for (Event event : eventList) {
                    System.out.println(event);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des événements : " + ex.getMessage());
        }*/

      //test insert participant
       ServiceParticipant serviceParticipant = new ServiceParticipant();
      /*  try {
            // Création d'un participant
            Participant participant1 = new Participant(1, 1001, "John Doe", "john@example.com", 123456789);
            // Insertion du participant
            serviceParticipant.insertOne(participant1);
            System.out.println("Participant ajouté avec succès.");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion du participant : " + ex.getMessage());
        }*/


        //test update participant
      /*  Participant participantToUpdate = new Participant(5551, 9999, "amine", "56555yujjukj", 51556251);
        try {
            serviceParticipant.updateOne(participantToUpdate); // Utiliser la méthode updateOne de ServiceParticipant
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du participant : " + ex.getMessage());
        }*/

        int participantIdToDelete = 5551;
        Participant participantToDelete = new Participant();
        participantToDelete.setUserID(participantIdToDelete);

        try {
            serviceParticipant.deleteOne(participantToDelete);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du participant : " + ex.getMessage());
        }





    }
}



