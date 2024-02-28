package service;

import model.Comande;
import utils.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Random;


public class ServiceComande implements CRUD<Comande> {
    private Connection cnx;
    public ServiceComande(){cnx = DBConnection.getInstance().getCnx();}

    public void insertOne(Comande commande) throws SQLException {
        /*String req="INSERT INTO `commande`(`id_commande`, `id_client`, `date_commande`, `disponibilite`, `stock_dispo`, `statut_commande`) VALUES" +
                " ("+commande.getId_commande()+","+commande.getId_client()+",'"+commande.getDate_commande()+"',"+commande.getDisponibilite()+
                ","+commande.getStock_dispo()+",'"+commande.getStatut_commande()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);*/
        /*String req = "INSERT INTO `comande`(`id_commande`,`id_lc`, `date_commande`,`statut_commande`,`prix_total`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, commande.getId_commande());
            preparedStatement.setInt(2, commande.getId_lc());
            preparedStatement.setString(3, commande.getDate_commande());
            preparedStatement.setString(4, commande.getStatut_commande());
            preparedStatement.setDouble(5, commande.getPrix_total());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
        String req = "INSERT INTO `comande`(`id_lc`, `date_commande`,`statut_commande`,`prix_total`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            // preparedStatement.setInt(1, commande.getId_commande()); // No need for this line
            preparedStatement.setInt(1, commande.getId_lc());
            preparedStatement.setString(2, commande.getDate_commande());
            preparedStatement.setString(3, commande.getStatut_commande());
            preparedStatement.setDouble(4, commande.getPrix_total());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }

    @Override
    public void updateOne(Comande comande) throws SQLException {
        try {
            String requete = "UPDATE comande SET id_commande=?, id_lc=? ,date_commande=? ,statut_commande=? ,prix_total=? WHERE id_commande=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            //LocalDate localDate = commande.getDate_commande();
            //java.sql.Date sqlDate = (localDate != null) ? java.sql.Date.valueOf(localDate) : null;

            pst.setInt(1, comande.getId_commande());
            pst.setInt(2, comande.getId_lc());
            pst.setString(3, comande.getDate_commande());
            pst.setString(4, comande.getStatut_commande());
            pst.setDouble(5, comande.getPrix_total());
            pst.setInt(6, comande.getId_commande()); // Set the WHERE condition value

            pst.executeUpdate();
            System.out.println("Commande modifiée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteOne(Comande comande) throws SQLException {
        try{
            String req="DELETE FROM `comande` WHERE id_commande=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, comande.getId_commande());
            pst.executeUpdate();
            System.out.println("commande supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Comande> selectAll() throws SQLException {
        List<Comande> commandeList=new ArrayList<>();
        String req="SELECT * FROM `comande`";
        Statement st = cnx.createStatement();
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            Comande c=new Comande();
            c.setId_commande(rs.getInt(1));
            c.setId_lc(rs.getInt(2));
            c.setDate_commande(rs.getString(3));
            c.setStatut_commande(rs.getString(4));
            c.setPrix_total(rs.getDouble(5));
            commandeList.add(c);
            System.out.println("done");
        }
        return commandeList;
    }
        /*List<Commande> commandeList = new ArrayList<>();
        String req = "SELECT * FROM `commande`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Commande c = new Commande();
            c.setId_commande(rs.getInt(1));
            c.setId_client(rs.getInt(2));
            //c.setDate_commande(rs.getDate(3).toLocalDate());
            c.setDate_commande(rs.getString(3));
            c.setDisponibilite(rs.getInt(4));
            c.setStock_dispo(rs.getInt(5));
            c.setStatut_commande(rs.getString(6));

            // Add the Commande object to the list
            commandeList.add(c);
        }

        return commandeList;
    }*/
        public Comande getDerniereCommande() {
            Connection con = DBConnection.getInstance().getCnx();
            String selectQuery = "SELECT * FROM comande ORDER BY id_commande DESC LIMIT 1";

            try (PreparedStatement pst = con.prepareStatement(selectQuery);
                 ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    int id_commande = rs.getInt("id_commande");
                    int id_lc = rs.getInt("id_lc");
                    String date_commande = rs.getString("date_commande");
                    String statut_commande = rs.getString("statut_commande");
                    double prix_total = rs.getDouble("prix_total");

                    return new Comande(id_commande, id_lc, date_commande, statut_commande, prix_total);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null; // Retourner null si aucune commande trouvée
        }
    public void ajouterCommande(double prixTotal) {
        try {
            Connection con = DBConnection.getInstance().getCnx();
            String insertQuery = "INSERT INTO comande (id_lc, date_commande, statut_commande, prix_total) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            Random random = new Random();
            int randomNumber = random.nextInt(90) + 10;
            pst.setInt(1,randomNumber);
            pst.setString(2, LocalDate.now().toString());  // Date actuelle
            pst.setString(3, "en cours de traitement");  // Statut
            pst.setDouble(4, prixTotal);  // Prix total du panier
            pst.executeUpdate();

            // Récupérez l'ID auto-incrémenté de la commande
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int idCommande = rs.getInt(1);
                System.out.println("Nouvelle commande ajoutée avec l'ID : " + idCommande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
