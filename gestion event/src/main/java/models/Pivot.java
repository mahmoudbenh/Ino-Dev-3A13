package models;
import javafx.beans.property.SimpleIntegerProperty;

public class Pivot {
    private final SimpleIntegerProperty idUE;
    private final SimpleIntegerProperty userId;
    private final SimpleIntegerProperty eventId;

    public Pivot(int idUE, int userId, int eventId) {
        this.idUE = new SimpleIntegerProperty(idUE);
        this.userId = new SimpleIntegerProperty(userId);
        this.eventId = new SimpleIntegerProperty(eventId);
    }

    public int getIdUE() {
        return idUE.get();
    }

    public SimpleIntegerProperty idUEProperty() {
        return idUE;
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public int getEventId() {
        return eventId.get();
    }

    public SimpleIntegerProperty eventIdProperty() {
        return eventId;
    }
}
