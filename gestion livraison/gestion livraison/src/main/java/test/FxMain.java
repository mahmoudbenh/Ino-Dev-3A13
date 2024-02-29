package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FxMain extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLivreurFXML.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddLivraison.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Ajouter");
        stage.setScene(scene);
        stage.show();


    }
}
