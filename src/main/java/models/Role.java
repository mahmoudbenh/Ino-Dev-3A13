package models;

import javafx.scene.control.cell.PropertyValueFactory;

public enum Role {
    ADMIN,
    CLIENT,
    DEFAULT; // Add a default role in case the string value doesn't match any enum value

    public static Role fromString(String roleString) {
        switch (roleString.toLowerCase()) {
            case "admin":
                return ADMIN;
            case "client":
                return CLIENT;
            default:
                // Handle unknown role or return a default role
                return DEFAULT;
        }
    }


}
