/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.InputStream;
import java.sql.Blob;

/**
 *
 * @author user
 */
public class ProduitAi {
    private int id;
    private String nom;
    private double prix;


    public ProduitAi(int id, String nom , double prix) {
        this.id = id;
        this.nom = nom;

        this.prix = prix;

    }

    public ProduitAi(String nom,  double prix) {
        this.nom = nom;

        this.prix = prix;

    }
    public ProduitAi(String nom) {
        this.nom = nom;



    }

    public ProduitAi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }



    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", prix=" + prix + '}';
    }


}