
package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxMain extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Card.fxml")); // Ensure this is the correct path
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomeAdmin.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/test.css").toExternalForm());
       // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
       // scene.getStylesheets().add(getClass().getResource("/DisplayUser.css").toExternalForm());
        stage.setTitle("Gestion Event");
        stage.setScene(scene);
        stage.show();
    }

}
