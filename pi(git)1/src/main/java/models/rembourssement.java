package models;

import java.time.LocalDate;

public class rembourssement {
    private int id_rembourssement;
    private int id_reclamation;
    private int id_produit;
    private float prix_produit;
    private LocalDate date_rembourssement;
    private String statut_rembourssement;
    private String mode_paiement;
    public rembourssement(int id_reclamation,int id_produit,float prix_produit,LocalDate date_rembourssement,String statut_rembourssement,String mode_paiement)
    {
      this.id_reclamation=id_reclamation;
      this.id_produit=id_produit;
      this.prix_produit=prix_produit;
      this.date_rembourssement=date_rembourssement;
      this.statut_rembourssement=statut_rembourssement;
      this.mode_paiement=mode_paiement;
    }
    public rembourssement(){}
    public rembourssement(int id_rembourssement,int id_reclamation,int id_produit,float prix_produit,LocalDate date_rembourssement,String statut_rembourssement,String mode_paiement)
    {
        this.id_rembourssement=id_rembourssement;
        this.id_reclamation=id_reclamation;
        this.id_produit=id_produit;
        this.prix_produit=prix_produit;
        this.date_rembourssement=date_rembourssement;
        this.statut_rembourssement=statut_rembourssement;
        this.mode_paiement=mode_paiement;
    }



    public int getId_rembourssement() {
        return id_rembourssement;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public int getId_produit() {
        return id_produit;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public String getStatut_rembourssement() {
        return statut_rembourssement;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public LocalDate getDate_rembourssement() {
        return date_rembourssement;
    }

    public void setId_rembourssement(int id_rembourssement) {
        this.id_rembourssement = id_rembourssement;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    public void setDate_rembourssement(LocalDate date_rembourssement) {
        this.date_rembourssement = date_rembourssement;
    }

    public void setStatut_rembourssement(String statut_rembourssement) {
        this.statut_rembourssement = statut_rembourssement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }
    @Override
    public String toString(){
        return "rembourssement{" +
                "id_rembourssement" + id_rembourssement +
                "id_reclamation" + id_reclamation +'\''+
                ",id_produit" +id_produit +'\'' +
                ",prix_produit" +prix_produit +'\'' +
                ",date_rembourssement" +date_rembourssement +'\'' +
                ",statut_rembourssement" + statut_rembourssement +'\'' +
                ",mode_paiement" + mode_paiement +

                '}';




    }
}
