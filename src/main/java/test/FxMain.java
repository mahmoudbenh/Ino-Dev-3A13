package test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxMain extends Application {
    public static void main(String[] args)
    {
        launch();
    }

    public void start(Stage stage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduitFXML.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterImageProdFXML.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitFXML.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterImageFXML.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        stage.setTitle("Ajouter un produit");
        stage.setScene(scene);
        stage.show();

    }
}
