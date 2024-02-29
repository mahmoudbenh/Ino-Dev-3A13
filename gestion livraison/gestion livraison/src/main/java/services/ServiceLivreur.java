package services;
import models.Livreur;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceLivreur implements CRUD<Livreur>  {

    private Connection cnx;
    public ServiceLivreur(){cnx = DBConnection.getInstance().getCnx();}
    public void insertOne(Livreur livreur) throws SQLException {
        String req="INSERT INTO `livreur`(`Nom_livreur`, `statut_livreur`, `Num_tel_livreur`) VALUES" +
                " ('"+livreur.getNom_livreur()+ "','"+livreur.getStatut_livreur()+"','"+livreur.getNum_tel_livreur()+"')";
                 Statement st = cnx.createStatement();
                    st.executeUpdate(req);

    }

    @Override
    public void updateOne(Livreur livreur) throws SQLException {
        String req="UPDATE `livreur` SET " + "`id_livreur`="+livreur.getId_livreur()+",`Nom_livreur`='"+livreur.getNom_livreur()+"',`statut_livreur`='"+livreur.getStatut_livreur()+ "',`Num_tel_livreur`="+livreur.getNum_tel_livreur()+" WHERE 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void deleteOne(Livreur livreur) throws SQLException {
        String req="DELETE FROM `livreur` WHERE 1";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public List<Livreur> selectAll() throws SQLException {
        List<Livreur> livreurList=new ArrayList<>();
        String req="SELECT * FROM `livreur`";
        Statement st = cnx.createStatement();
        ResultSet rs= st.executeQuery(req);
        while (rs.next()){
            Livreur liv=new Livreur();
            liv.setId_livreur(rs.getInt(1));
            liv.setNom_livreur(rs.getString(2));
            liv.setStatut_livreur(rs.getString(3));
            liv.setNum_tel_livreur(rs.getInt(4));
            livreurList.add(liv);
            System.out.println("done");
        }
        return livreurList;
    }


}
