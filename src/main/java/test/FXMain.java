package test;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

<<<<<<< HEAD
/*public class FXMain extends Application {
=======
public class FXMain extends Application {
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));

<<<<<<< HEAD
            primaryStage.initStyle(StageStyle.UNDECORATED);
=======
            //primaryStage.initStyle(StageStyle.UNDECORATED);
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Erreur de chargement", "Impossible de charger l'interface utilisateur.");
        }
<<<<<<< HEAD

=======
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

<<<<<<< HEAD
}*/



public class FXMain extends Application{
    public static void main(String[] args){
        launch();
    }
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CaptchaFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}



=======
}
>>>>>>> 6ebb2b8f6a53c0ad29802743fb1ecbb3f8bfc214
