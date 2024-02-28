package services;

import models.Images;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceImage {
    private Connection cnx ;

    public ServiceImage() {
        cnx = DBConnection.getInstance().getCnx();
    }

    /*public void insertOne_img(Image image) throws SQLException {
        String req = "INSERT INTO `image`(`id_produit`, `url`) VALUES " +
                "(?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        //ps.setInt(1, image.getId_image());
        ps.setInt(1, image.getId_produit());
        ps.setString(2, image.getUrl());

        ps.executeUpdate();
        System.out.println("image Added !");
    }*/

    public int getImageIdByUrl(String imageUrl) throws SQLException {
        String query = "SELECT id_image FROM image WHERE url = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, imageUrl);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_image");
                }
            }
        }
        // Si l'image n'est pas trouvée, vous pouvez renvoyer une valeur par défaut ou lever une exception selon votre besoin.
        return -1; // Par exemple, retourne -1 si l'image n'est pas trouvée
    }
    public void insertOne_img(Images image) throws SQLException {
        try {
            // Vérifier si l'id_produit existe dans la table produit
            String checkProduitQuery = "SELECT COUNT(*) FROM produit WHERE id_produit = ?";
            PreparedStatement checkProduitStatement = cnx.prepareStatement(checkProduitQuery);
            checkProduitStatement.setInt(1, image.getId_produit());
            ResultSet produitResult = checkProduitStatement.executeQuery();
            produitResult.next(); // Déplacer le curseur vers la première ligne
            int produitCount = produitResult.getInt(1);
            if (produitCount == 0) {
                // L'id_produit n'existe pas dans la table produit
                System.out.println("L'id_produit spécifié n'existe pas dans la table produit.");
                return;
            }

            // Si l'id_produit existe dans la table produit, insérer l'image
            String insertQuery = "INSERT INTO `image`(`id_produit`, `url`) VALUES " +
                    "(?,?)";
            PreparedStatement ps = cnx.prepareStatement(insertQuery);

            //ps.setInt(1, image.getId_image());
            ps.setInt(1, image.getId_produit());
            ps.setString(2, image.getUrl());

            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("Image ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /*public void updateOne_img(Image image) throws SQLException  {
        try {
            String req = "UPDATE image SET id_produit=?, url=? WHERE id_image=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            // Définition des valeurs des paramètres de substitution
            ps.setInt(1, image.getId_produit());
            ps.setString(2, image.getUrl());
            ps.setInt(3, image.getId_image()); // Filtrer par identifiant

            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("Image modifié avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/

    public void updateOne_img(Images image) throws SQLException {
        try {
            String req = "UPDATE image SET id_produit=?, url=? WHERE id_image=?";
            PreparedStatement ps = cnx.prepareStatement(req);

            // Définition des valeurs des paramètres de substitution
            ps.setInt(1, image.getId_produit());
            ps.setString(2, image.getUrl());
            ps.setInt(3, image.getId_image());

            // Exécution de la requête préparée
            ps.executeUpdate();
            System.out.println("Image modifié avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*public void deleteOne_img(Image image) throws SQLException {
        try
        {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM image WHERE id_image = "+image.getId_image()+"";
            st.executeUpdate(req);
            System.out.println("image  supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/

    public void deleteOne_img(int idImage) {
        String sql = "DELETE FROM image WHERE id_image = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idImage);
            preparedStatement.executeUpdate();
            System.out.println("Image supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la suppression de l'image : " + e.getMessage());
        }
    }


    public List<Images> selectAll_img() throws SQLException {
        List<Images> imageList = new ArrayList<>();

        String req = "SELECT * FROM `image`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Images p = new Images();

            p.setId_image(rs.getInt("id_image"));
            p.setId_produit(rs.getInt("id_produit"));
            p.setUrl(rs.getString("url"));

            imageList.add(p);
        }

        return imageList;
    }

    public List<Images> getImagesByProductId(int productId) throws SQLException {

        List<Images> imagesList = new ArrayList<>();
        String query = "SELECT * FROM image WHERE id_produit = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idImage = resultSet.getInt("id_image");
                    String url = resultSet.getString("url");
                    System.out.println(url);
                    int idProduit = resultSet.getInt("id_produit");
                    Images image = new Images(idImage, idProduit, url);
                    imagesList.add(image);
                }
            }
        }
        return imagesList;
    }
}
