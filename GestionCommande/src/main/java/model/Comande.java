package model;

public class Comande {
    private int id_commande;
    private int id_lc;
    private String date_commande;
    private String statut_commande;
    private double prix_total;

    public Comande(int id_commande, int id_lc, String date_commande, String statut_commande, double prix_total) {
        this.id_commande = id_commande;
        this.id_lc = id_lc;
        this.date_commande = date_commande;
        this.statut_commande = statut_commande;
        this.prix_total = prix_total;
    }

    public Comande(int id_lc, String date_commande, String statut_commande, double prix_total) {
        this.id_lc = id_lc;
        this.date_commande = date_commande;
        this.statut_commande = statut_commande;
        this.prix_total = prix_total;
    }

    public Comande(){}

    public int getId_commande() {
        return id_commande;
    }

    public int getId_lc() {
        return id_lc;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public String getStatut_commande() {
        return statut_commande;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public void setId_lc(int id_lc) {
        this.id_lc = id_lc;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public void setStatut_commande(String statut_commande) {
        this.statut_commande = statut_commande;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    @Override
    public String toString() {
        return "Comande{" +
                "id_commande=" + id_commande +
                ", id_lc=" + id_lc +
                ", date_commande='" + date_commande + '\'' +
                ", statut_commande='" + statut_commande + '\'' +
                '}';
    }
}
