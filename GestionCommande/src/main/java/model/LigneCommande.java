package model;

public class LigneCommande {
    private int id_lc;
    private int id_produit;
    private int id_commande;
    private int qte;
    private double prix_produit;

    public LigneCommande(int id_lc, int id_produit, int id_commande, int qte, double prix_produit) {
        this.id_lc = id_lc;
        this.id_produit = id_produit;
        this.id_commande = id_commande;
        this.qte = qte;
        this.prix_produit = prix_produit;
    }

    public LigneCommande(int id_produit, int id_commande, int qte, double prix_produit) {
        this.id_produit = id_produit;
        this.id_commande = id_commande;
        this.qte = qte;
        this.prix_produit = prix_produit;
    }
public LigneCommande(){}
    public int getId_lc() {
        return id_lc;
    }

    public int getId_produit() {
        return id_produit;
    }

    public int getId_commande() {
        return id_commande;
    }

    public int getQte() {
        return qte;
    }

    public double getPrix_produit() {
        return prix_produit;
    }

    public void setId_lc(int id_lc) {
        this.id_lc = id_lc;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public void setPrix_produit(double prix_produit) {
        this.prix_produit = prix_produit;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
                "id_lc=" + id_lc +
                ", id_produit=" + id_produit +
                ", id_commande=" + id_commande +
                ", qte=" + qte +
                ", prix_produit=" + prix_produit +
                '}';
    }
}
