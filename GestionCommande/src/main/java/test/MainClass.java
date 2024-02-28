package test;

import model.Comande;
import model.LigneCommande;
import service.ServiceComande;
import service.ServiceLigne;
import utils.DBConnection;

import java.sql.SQLException;
import java.time.LocalDate;

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

        /*LocalDate date1 = LocalDate.of(2024, 2, 1);
        LocalDate date5 = LocalDate.of(2022, 2, 18);
        Commande c1= new Commande();
        LocalDate date2 = LocalDate.of(2022, 2, 7);*/
        /*Comande c2= new Comande(3,8,"2024-11-22","annulation",45);
        //Commande c3= new Commande(12222312,78787878,date1,1,24,"annulation");
        ServiceComande sc= new ServiceComande();
        try {
            sc.insertOne(c2);
            System.out.println(sc.selectAll());
        }catch (SQLException e){
            System.out.println("Erreue"+e.getMessage());
        }
        try {
            // Supprimer la commande avec l'ID 1
            Comande CommandeDelete = new Comande();
            CommandeDelete.setId_commande(12312345);
            sc.deleteOne(CommandeDelete);
            System.out.println("user supprimé avec succès.");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du user : " + ex.getMessage());
        }
        c2.setId_commande(1);
        c2.setPrix_total(15);
        try {
            sc.updateOne(c2);
        } catch (SQLException exp) {
            System.err.println("Erreur:" + exp.getMessage());
        }*/
        LigneCommande lc = new LigneCommande(16,3,1,20);
        //Commande c3= new Commande(12222312,78787878,date1,1,24,"annulation");
        ServiceLigne sl = new ServiceLigne();
        try {
            sl.insertOne(lc);
            System.out.println(sl.selectAll());
        } catch (SQLException e) {
            System.out.println("Erreue" + e.getMessage());
        }
        try {
            // Supprimer la commande avec l'ID 1
            LigneCommande LigneDelete = new LigneCommande();
            LigneDelete.setId_lc(11);
            sl.deleteOne(LigneDelete);
            System.out.println("user supprimé avec succès.");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du user : " + ex.getMessage());
        }
        lc.setId_lc(3);
        lc.setQte(500);
        try {
            sl.updateOne(lc);
        } catch (SQLException exp) {
            System.err.println("Erreur:" + exp.getMessage());
        }
    }
}
