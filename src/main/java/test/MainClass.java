package test;

import models.User;
import services.ServiceUser;
import utils.DBConnection;

import java.sql.SQLException;
import java.time.LocalDate;

import static models.Role.ADMIN;
import static models.Role.CLIENT;

public class MainClass {

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();
        LocalDate date = LocalDate.of(2024,2,1);

        User p = new User("Ahmadou","Ndiaye","dfsdfsd","ahmadou.ndiayewalymalick@esprit.tn",CLIENT);
        User p1 = new User("Ndiaye","Malick","dfdsfs","ahmadou.ndiayewalymalick@esprit.tn",ADMIN);
        User p2 = new User("eya","abid","sdfsfsdf","eya.abid@esprit.tn",CLIENT);

        ServiceUser sp = new ServiceUser();

        //sp.insertOne1(p2);
        System.out.println(sp.selectAll());

        //modify
        p2.setUserID(38);
        p2.setMdp("Ndiaye12");

        sp.modifier(p2);

        // Supprimer le produit avec l'ID 1
        //User UsertoDelete = new User();
        //UsertoDelete.setUserID(18);
        //sp.supprimer(UsertoDelete);
        System.out.println("user supprimé avec succès.");

    }
}
