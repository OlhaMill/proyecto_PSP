package org.om2324.heroesmarvel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MarvelApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MarvelApplication.class.getResource("prueba-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            stage.setTitle("Héroes Marvel");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();  // O manejar de otra manera más adecuada
        }
    }

    public static void main(String[] args) {
        launch();
    }
}