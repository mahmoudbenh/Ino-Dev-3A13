package test;
import models.Livraison;
import services.ServiceLivraison;
import models.Livreur;
import services.ServiceLivreur;
import utils.DBConnection;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainClass {
    public static void main(String[] args) {

        DBConnection cn1 = DBConnection.getInstance();
        LocalDate date1 = LocalDate.of(2024, 2, 1);
        Livraison l = new Livraison(1,100, date1, "bardo", "hh", 20);
        LocalDate date2 = LocalDate.of(2022, 2, 7);
        Livraison l1 = new Livraison(100,10, date2, "ariana", "en cours", 20);
       // Livreur Liv = new Livreur("Aziz","active",2132552);
        ServiceLivreur Sli= new ServiceLivreur();
        ServiceLivraison SL= new ServiceLivraison();
        try {
            SL.insertOne(l);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }
        try {
            SL.insertOne(l1);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }
       /* try {
            Sli.insertOne(Liv);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }*/
       /* try {
            SL.insertOne(l3);
        }catch (SQLException e){
            System.out.println("Erreur"+e.getMessage());
        }*/
       /* try {
            SL.deleteOne(l);
            System.out.println("Livraison supprimée : " + l);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la livraison : " + e.getMessage());
        }*/
       /* try {
            System.out.println(Sli.selectAll());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }
}
