package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class FXMain extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterCommandeFXML.fxml"));
        Parent parent= loader.load();
        Scene scene= new Scene(parent);
        stage.setTitle("Ajouter une Commande");
        stage.setScene(scene);
        stage.show();*/
        /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/AjouterPanierFXML.fxml"));
        Parent parent= loader.load();
        Scene scene= new Scene(parent);
        stage.setTitle("panier");
        stage.setScene(scene);
        stage.show();*/
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/ProduitFXML.fxml"));
        Parent parent= loader.load();
        Scene scene= new Scene(parent);
        stage.setTitle("Commande");
        stage.setScene(scene);
        stage.show();
        /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/Afficher.fxml"));
        Parent parent= loader.load();
        Scene scene= new Scene(parent);
        stage.setTitle("Ajouter une Commande");
        stage.setScene(scene);
        stage.show();*/
    }
}
