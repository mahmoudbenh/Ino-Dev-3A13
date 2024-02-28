package models;

public class Images {
    private int id_image,id_produit;
    private String url;

    public Images() {};

    public Images(int id_image, int id_produit, String url) {
        this.id_image = id_image;
        this.id_produit = id_produit;
        this.url = url;
    }

    public Images(int id_produit, String url) {
        this.id_produit = id_produit;
        this.url = url;
    }

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id_image=" + id_image +
                ", id_produit=" + id_produit +
                ", url='" + url + '\'' +
                '}';
    }
}
