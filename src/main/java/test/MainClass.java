package test;

import models.Images;
import models.Produit;
import services.ServiceImage;
import services.ServiceProduit;
import utils.DBConnection;

public class MainClass {
    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        Produit p = new Produit(8,"ddddd", "zzzz","Materiel", 58.2F);

        ServiceProduit sp = new ServiceProduit();

        /*try {
            sp.insertOne_prod(p);
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
        //System.out.println(sp.selectAll());
        //modifier
        p.setId_prod(2);
        p.setNom_prod("lldld");
        try {
            sp.updateOne_prod(p);
        } catch (SQLException ex) {
            System.err.println("Erreur:" + ex.getMessage());
        }

        try {
            // Supprimer le produit avec l'ID 1
           Produit produitToDelete = new Produit();
            produitToDelete.setId_prod(8);
            sp.deleteOne_prod(produitToDelete);
            System.out.println("produit supprimé avec succès.");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression du produit : " + ex.getMessage());
        }

        try {
            // Sélectionner tous les produits
            List<Produit> produits = sp.selectAll_prod();
            System.out.println("Liste de tous les produits :");
            for (Produit pp : produits) {
                System.out.println(pp);
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }*/

        Images i = new Images(4,8, "1EE");

        ServiceImage si = new ServiceImage();

        /*try {
            si.insertOne_img(i);
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }*/
        //System.out.println(sp.selectAll());
        //modifier
       /* i.setId_image(1);
        i.setUrl("ffdd");
        try {
            si.updateOne_img(i);
        } catch (SQLException ex) {
            System.err.println("Erreur:" + ex.getMessage());
        }*/

        /*try {
            // Supprimer le produit avec l'ID 1
            Image imageToDelete = new Image();
            imageToDelete.setId_image(4);
            si.deleteOne_img(imageToDelete);
            System.out.println("image supprimé avec succès.");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression d'image : " + ex.getMessage());
        }*/

        /*try {
            // Sélectionner tous les produits
            List<Image> images = si.selectAll_img();
            System.out.println("Liste de tous les produits :");
            for (Image ii : images) {
                System.out.println(ii);
            }
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }*/
    }
}