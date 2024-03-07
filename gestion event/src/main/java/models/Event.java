package models;

import java.time.LocalDate;
import java.util.List;


public class Event {
    private int id_event;
    private String name;
    private String description;
    private LocalDate date_event;
    private String statut;
    private String lieu;
    private String image_event;
    private List<Participant> participants;


    public Event() {}

  /*  public Event(String name, String description, LocalDate date_event, String statut, String lieu, int nbr_participant, String image_event) {
        this.name = name;
        this.description = description;
        this.date_event = date_event;
        this.statut = statut;
        this.lieu = lieu;
        this.image_event = image_event;
    }*/
  public Event(String name, String description, LocalDate date_event, String statut, String lieu, String image_event) {
      this.name = name;
      this.description = description;
      this.date_event = date_event;
      this.statut = statut;
      this.lieu = lieu;
      this.image_event = image_event;
  }


    public Event(int id_event, String name, String description, LocalDate date_event, String statut, String lieu, String image_event) {
        this.id_event = id_event;
        this.name = name;
        this.description = description;
        this.date_event = date_event;
        this.statut = statut;
        this.lieu = lieu;
        this.image_event = image_event;
    }


    public List<Participant> getParticipants() {return participants;}
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }



    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate_event() {
        return date_event;
    }

    public void setDate_event(LocalDate date_event) {
        this.date_event = date_event;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImage_event() {
        return image_event;
    }

    public void setImage_event(String image_event) {
        this.image_event = image_event;
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
                ", image_event='" + image_event + '\'' +
                '}';
    }
}
