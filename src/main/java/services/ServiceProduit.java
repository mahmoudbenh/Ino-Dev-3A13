package services;

import models.Produit;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceProduit {
        private Connection cnx ;

        public ServiceProduit() {
            cnx = DBConnection.getInstance().getCnx();
        }

        //@Override
       /* public void insertOne(Produit produit) throws SQLException {
            String req = "INSERT INTO `produit`(`id`, `nom`, `description`, `image`, `prix`) VALUES " +
                    "('"+produit.getNom()+"','"+produit.getDescription()+"','"+produit.getImage()+"',"+produit.getPrix()+")";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit Added !");
        }*/

    public void insertOne_prod(Produit produit) throws SQLException {
        String req = "INSERT INTO `produit`(`id_produit` , `nom_produit`, `description`, `categorie`, `prix_produit`) VALUES " +
                "(?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, produit.getId_produit());
        ps.setString(2, produit.getNom_produit());
        ps.setString(3, produit.getDescription());
        ps.setString(4, produit.getCategorie());
        ps.setFloat(5, produit.getPrix_produit());

        ps.executeUpdate();
        System.out.println("Produit Added !");
    }
    public void updateOne_prod(Produit produit) throws SQLException  {
        try {
            String req = "UPDATE produit SET nom_produit=?, description=?, categorie=?, prix_produit=? WHERE id_produit=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            // Définition des valeurs des paramètres de substitution
            ps.setString(1, produit.getNom_produit());
            ps.setString(2, produit.getDescription());
            ps.setString(3, produit.getCategorie());
            ps.setFloat(4, produit.getPrix_produit());
            ps.setInt(5, produit.getId_produit()); // Filtrer par identifiant

            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("Produit modifié avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteOne_prod(Produit produit) throws SQLException {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM produit WHERE id_produit = "+produit.getId_produit()+"";
            st.executeUpdate(req);
            System.out.println("produit  supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        public List<Produit> selectAll_prod() throws SQLException {
            List<Produit> produitList = new ArrayList<>();

            String req = "SELECT * FROM `produit`";
            Statement st = cnx.createStatement();

            ResultSet rs = st.executeQuery(req);

            while (rs.next()){
                Produit p = new Produit();

                p.setId_produit(rs.getInt("id_produit"));
                p.setNom_produit(rs.getString("nom_produit"));
                p.setDescription(rs.getString("description"));
                p.setCategorie(rs.getString("categorie"));
                p.setPrix_produit(rs.getFloat("prix_produit"));

                produitList.add(p);
            }

            return produitList;
        }

    public List<Produit> selectAll_prod_2() throws SQLException {
        List<Produit> produitList = new ArrayList<>();

        String req = "SELECT * FROM `produit`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Produit p = new Produit();

            p.setId_produit(rs.getInt("id_produit"));
            p.setNom_produit(rs.getString("nom_produit"));
            p.setDescription(rs.getString("description"));
            p.setCategorie(rs.getString("categorie"));
            p.setPrix_produit(rs.getFloat("prix_produit"));

            // Récupérer les URLs des images pour ce produit
            List<String> urlsImages = selectImagesForProduit(p.getId_produit());
            p.setUrlsImages(urlsImages);

            produitList.add(p);
        }

        return produitList;
    }

    private List<String> selectImagesForProduit(int idProduit) throws SQLException {
        List<String> urlsImages = new ArrayList<>();
        String req = "SELECT url FROM `image` WHERE id_produit = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idProduit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            urlsImages.add(rs.getString("url"));
        }
        return urlsImages;
    }

}
