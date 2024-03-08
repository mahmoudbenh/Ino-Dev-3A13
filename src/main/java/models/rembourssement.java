package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class rembourssement {
    private int id_rembourssement;
    private int id_reclamation;
    private LocalTime heure;
    private float prix;
    private LocalDate date_rembourssement;
    private String statut_rembourssement;
    private String mode_paiement;
    private String email;
    public rembourssement(float prix,LocalDate date_rembourssement,LocalTime heure,String statut_rembourssement,String mode_paiement)
    {

        this.prix=prix;
        this.date_rembourssement=date_rembourssement;
        this.heure = heure;
        this.statut_rembourssement=statut_rembourssement;
        this.mode_paiement=mode_paiement;
    }
    public rembourssement(){}
    public rembourssement(int id_rembourssement,int id_reclamation,String email,float prix,LocalDate date_rembourssement,LocalTime heure,String statut_rembourssement,String mode_paiement)
    {
        this.email=email;
        this.id_rembourssement=id_rembourssement;
        this.id_reclamation=id_reclamation;
        this.heure=heure;
        this.prix=prix;
        this.date_rembourssement=date_rembourssement;
        this.statut_rembourssement=statut_rembourssement;
        this.mode_paiement=mode_paiement;
    }


    public String getEmail() {
        return email;
    }

    public int getId_rembourssement() {
        return id_rembourssement;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }


    public LocalTime getHeure() {
        return heure;
    }

    public float getPrix() {
        return prix;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDate_rembourssement(LocalDate date_rembourssement) {
        this.date_rembourssement = date_rembourssement;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
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

                ",prix" +prix +'\'' +
                ",date_rembourssement" +date_rembourssement +'\'' +
                ",heure" +heure +'\'' +
                ",statut_rembourssement" + statut_rembourssement +'\'' +
                ",mode_paiement" + mode_paiement +

                '}';




    }
}

