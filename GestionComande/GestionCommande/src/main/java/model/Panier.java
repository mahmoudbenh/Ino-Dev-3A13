/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ASUS
 */
public class Panier {
    private String nom_produit;
    private double prix_produit;

    public Panier() {
    }

    public Panier(String nom_produit, double prix_produit) {
        this.nom_produit = nom_produit;
        this.prix_produit = prix_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public double getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(double prix_produit) {
        this.prix_produit = prix_produit;
    }

    @Override
    public String toString() {
        return "Panier{" + "nom_produit=" + nom_produit + ", prix_produit=" + prix_produit + '}';
    }

}