package models;

// Card.java
public class Card {
    private String name;
    private String ImageC;
    private String des;

    public Card(String name, String imageC, String des) {
        this.name = name;
        this.ImageC = imageC;
        this.des = des;
    }
    public Card(){}

    public String getName() {
        return name;
    }

    public String getImageC() {
        return ImageC;
    }

    public String getDes() {
        return des;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageC(String imageSrc) {
        ImageC = imageSrc;
    }

    public void setDes(String des) {
        this.des = des;
    }


}

