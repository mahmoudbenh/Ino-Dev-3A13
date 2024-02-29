package models;
import java.time.LocalDate;
public class Event {
    private  int id_event;
    private LocalDate date_event;
    private String name,description,statut,lieu;

    public Event(){}

    public Event(String name,String description, LocalDate date_event,String statut,String lieu) {
        this.name = name;
        this.description = description;
        this.date_event = date_event;
        this.statut = statut;
        this.lieu=lieu;
    }
    public Event(int id_event,String name,String description,LocalDate date_event,String statut,String lieu) {
        this.id_event = id_event;
        this.name = name;
        this.description = description;
        this.date_event = date_event;
        this.statut = statut;
        this.lieu = lieu;
    }

    public int getId_event() {
        return  id_event;
    }
    public LocalDate getDate_event() {
        return date_event;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatut() {
        return statut;
    }

    public String getLieu() {
        return lieu;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public void setDate_event(LocalDate date_event) {
        this.date_event = date_event;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public boolean validate() {
        return validateName() && validateDescription() && validateDate() && validateStatut() && validateLieu();
    }
    private boolean validateName() {
        return name != null && !name.isEmpty();
    }
    private boolean validateDescription() {
        return description != null && !description.isEmpty();
    }
    private boolean validateDate() {
        return date_event != null && date_event.isAfter(LocalDate.now()); // Date should be in the future
    }
    private boolean validateStatut() {
        return statut != null && !statut.isEmpty();
    }
    private boolean validateLieu() {
        return lieu != null && !lieu.isEmpty();
    }


    @Override
    public String toString() {
        return "Event{" +
                "id_event=" + id_event +
                ", date_event=" + date_event +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", statut='" + statut + '\'' +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
