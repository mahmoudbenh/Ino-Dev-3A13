package models;

public class Participant {
    private  int id_event;
    private  int UserID;
    private String nom;
    private String email;

    private String eventName;
    private  int tel;


    public Participant(int UserID, int id_event, String nom, String email, int tel) {
        this.UserID = UserID;
        this.id_event = id_event;
        this.nom = nom;
        this.email = email;
        this.tel = tel;
    }


    public Participant() {

    }

    public int getId_event() {
        return id_event;
    }

    public int getUserID() {
        return UserID;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public int getTel() {
        return tel;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id_event=" + id_event +
                ", UserID=" + UserID +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", tel=" + tel +
                '}';
    }
}


