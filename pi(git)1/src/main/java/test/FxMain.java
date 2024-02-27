package test;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class FxMain extends Application{
    public static void main(String[] args){
        launch();
    }
    public void start(Stage stage) throws Exception {

        /*FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/AfficherReclamationFXML.fxml"));
        Parent parent2 = loader2.load();
        Scene scene2 = new Scene(parent2);
        stage.setTitle("afficher une reclamation ");
        stage.setScene(scene2);
        stage.show();*/




        /*FXMLLoader loaderm = new FXMLLoader(getClass().getResource("/ModifierReclamationFXML.fxml"));
        Parent parentm = loaderm.load();
        Scene scenem = new Scene(parentm);
        stage.setTitle("modifier une reclamation ");
        stage.setScene(scenem);
        stage.show();*/


        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/AfficherReclamationFXML.fxml"));
        Parent parent2 = loader2.load();
        Scene scene2 = new Scene(parent2);
        stage.setTitle("afficher une reclamation ");
        stage.setScene(scene2);
        stage.show();

        FXMLLoader loaderm = new FXMLLoader(getClass().getResource("/ModifierReclamationFXML.fxml"));
        Parent parentm = loaderm.load();
        Scene scenem = new Scene(parentm);
        stage.setTitle("modifier une reclamation ");
        stage.setScene(scenem);
        stage.show();




        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamationFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("ajouter une reclamation ");
        stage.setScene(scene);
        stage.show();

        FXMLLoader loader5 = new FXMLLoader(getClass().getResource("/AjouterRembourssementFXML.fxml"));
        Parent parent5 = loader5.load();
        Scene scene5 = new Scene(parent5);
        stage.setTitle("ajouter un remboussement ");
        stage.setScene(scene5);
        stage.show();



       /* FXMLLoader loaderu = new FXMLLoader(getClass().getResource("/affichageUserreclamationFXML.fxml"));
        Parent parentu = loaderu.load();
        Scene sceneu = new Scene(parentu);
        stage.setTitle("afficher les reclamations ");
        stage.setScene(sceneu);
        stage.show();*/
    }
}
