package models;

import java.util.List;

public class Produit {
    private int id_produit;
    private String nom_produit, description, categorie;
    private float prix_produit;
    private List<String> urlsImages;

    // Les autres méthodes et constructeurs restent inchangés

    public List<String> getUrlsImages() {
        return urlsImages;
    }

    public void setUrlsImages(List<String> urlsImages) {
        this.urlsImages = urlsImages;
    }

    public Produit() {}

    public Produit(String nom_produit, String description, String categorie, float prix_produit) {
        this.nom_produit = nom_produit;
        this.description = description;
        this.categorie = categorie;
        this.prix_produit = prix_produit;
    }

    public Produit(int id_produit, String nom_produit, String description, String categorie, float prix_produit) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.description = description;
        this.categorie = categorie;
        this.prix_produit = prix_produit;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_prod=" + id_produit +
                ", nom_prod='" + nom_produit + '\'' +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", prix_prod=" + prix_produit +
                '}';
    }
}
