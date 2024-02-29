package models;

public class Livreur {
    private int id_livreur;
    private String Nom_livreur;
    private String statut_livreur;
    private int Num_tel_livreur;

    public Livreur (int id_livreur,  String Nom_livreur, String statut_livreur,int Num_tel_livreur) {
        this.id_livreur = id_livreur;
        this.Nom_livreur=Nom_livreur;
        this.statut_livreur= statut_livreur;
        this.Num_tel_livreur = Num_tel_livreur;

    }
    public Livreur (  String Nom_livreur, String statut_livreur,int Num_tel_livreur) {

        this.Nom_livreur=Nom_livreur;
        this.statut_livreur= statut_livreur;
        this.Num_tel_livreur = Num_tel_livreur;

    }
    public Livreur(){};


    @Override
    public String toString() {
        return "Livreur{" +
                "id_livreur=" + id_livreur +
                ", Nom_livreur='" + Nom_livreur + '\'' +
                ", statut_livreur='" + statut_livreur + '\'' +
                ", Num_tel_livreur=" + Num_tel_livreur +
                '}';
    }

    public String getNom_livreur() {
        return Nom_livreur;
    }

    public String getStatut_livreur() {
        return statut_livreur;
    }

    public int getNum_tel_livreur() {
        return Num_tel_livreur;
    }

    public int getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(int id_livreur) {
        this.id_livreur = id_livreur;
    }

    public void setNom_livreur(String Nom_livreur ) {
        this.Nom_livreur = Nom_livreur;
    }

    public void setStatut_livreur(String statut_livreur) {
        this.statut_livreur = statut_livreur;
    }

    public void setNum_tel_livreur(int num_tel_livreur) {
        Num_tel_livreur = num_tel_livreur;
    }


}
