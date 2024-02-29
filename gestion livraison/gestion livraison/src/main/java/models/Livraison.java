package models;

import java.time.LocalDate;


public class Livraison {
    private int id_livraison;
    private int id_commande;
    private int id_livreur;
    private LocalDate Date_livraison;
    private String Adresse_livraison;
    private String status_livraison;
    private float frais_livraison;

    public  Livraison(int id_livraison, int id_livreur,int id_commande , LocalDate Date_livraison, String Adresse_livraison, String status_livraison, float frais_livraison) {
        this.id_livraison = id_livraison;
        this.id_commande = id_commande;
        this.id_livreur = id_livreur;
        this.Date_livraison=Date_livraison;
        this.Adresse_livraison= Adresse_livraison;
        this.status_livraison = status_livraison;
        this.frais_livraison = frais_livraison;
    }
    public  Livraison( int id_commande ,int id_livreur, LocalDate Date_livraison, String Adresse_livraison, String status_livraison, float frais_livraison) {

        this.id_commande = id_commande;
        this.id_livreur = id_livreur;
        this.Date_livraison=Date_livraison;
        this.Adresse_livraison= Adresse_livraison;
        this.status_livraison = status_livraison;
        this.frais_livraison = frais_livraison;
    }
    public Livraison(){};

    @Override
    public String toString() {
        return "Livraison{" +
                "id_livraison=" + id_livraison +
                ", id_commande=" + id_commande +
                ", id_livreur=" + id_livreur +
                ", Date_livraison=" + Date_livraison +
                ", Adresse_livraison='" + Adresse_livraison + '\'' +
                ", status_livraison='" + status_livraison + '\'' +
                ", frais_livraison=" + frais_livraison +
                '}';
    }

    public int getId_livraison() {return id_livraison;}
    public void setId_livraison(int id_livraison) {
        this.id_livraison = id_livraison;
    }
    public int getId_commande() {
        return id_commande;
    }
    public void setId_commande(int id_command) {
        id_commande = id_commande;
    }
    public LocalDate getDate_livraison() {
        return Date_livraison;
    }
    public void setDate_livraison(String Date_livraison) {Date_livraison = Date_livraison;}
    public String getAdresse_livraison() {
        return Adresse_livraison;
    }
    public void setAdresse_livraison(String Adresse_livraison) {
        Adresse_livraison = Adresse_livraison;
    }
    public String getStatus_livraison() {
        return status_livraison;
    }
    public void setStatus_livraison(int status_livraison) {
        status_livraison = status_livraison;
    }
    public Float getFrais_livraison() {
        return frais_livraison;
    }
    public void setFrais_livraison(String frais_livraison) {
        frais_livraison = frais_livraison;
    }
    public int getId_livreur() {return id_livreur;}
    public void setId_livreur(int id_livreur) {this.id_livreur = id_livreur;}
}
