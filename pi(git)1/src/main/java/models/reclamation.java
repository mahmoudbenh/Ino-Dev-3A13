package models;
import java.time.LocalDate;
import java.time.LocalTime;

public class reclamation {
    private int id_reclamation;
    private int UserID;
    private String titre_reclamation;
    private LocalDate date_reclamation;
    private LocalTime heure;
    private String type_reclamation;
    private String statut_reclamation;
    private String description;

    public reclamation(String titre_reclamation, LocalDate date_reclamation,LocalTime heure, String type_reclamation,String statut_reclamation, String description)
    {
       // this.id_client = id_client;
        this.titre_reclamation = titre_reclamation;
        this.date_reclamation = date_reclamation;
        this.heure = heure;
        this.type_reclamation = type_reclamation;
        this.statut_reclamation = statut_reclamation;
        this.description = description;

    }
    public reclamation(){}
    public reclamation(int id_reclamation,int UserID,String titre_reclamation,LocalDate date_reclamation,LocalTime heure,String type_reclamation,String statut_reclamation,String description){
        this.id_reclamation = id_reclamation;
        this.UserID = UserID;
        this.titre_reclamation = titre_reclamation;
        this.date_reclamation = date_reclamation;
        this.heure=heure;
        this.type_reclamation = type_reclamation;
        this.statut_reclamation = statut_reclamation;
        this.description = description;

    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public int getUserID() {
        return UserID;
    }

    public String getTitre_reclamation() {
        return titre_reclamation;
    }

    public LocalDate getDate_reclamation() {
        return date_reclamation;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public String getType_reclamation() {
        return type_reclamation;
    }

    public String getStatut_reclamation() {
        return statut_reclamation;
    }

    public String getDescription() {
        return description;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public void setTitre_reclamation(String titre_reclamation) {
        this.titre_reclamation = titre_reclamation;

    }

    public void setDate_reclamation(LocalDate date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public void setType_reclamation(String type_reclamation) {
        this.type_reclamation = type_reclamation;
    }

    public void setStatut_reclamation(String statut_reclamation) {
        this.statut_reclamation = statut_reclamation;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString(){
        return "reclamation{" +
                "id_reclamation" + id_reclamation +
                ",UserID" +UserID +'\'' +
                ",Titre_reclamation" +titre_reclamation +'\'' +
                ",date_reclamation" +date_reclamation +'\'' +
                ",heure" +heure +'\'' +
                ",type_reclamation" +type_reclamation +'\'' +
                ",statut_reclamation" +statut_reclamation +'\'' +
                ",description" + description +
                '}';




    }
}
