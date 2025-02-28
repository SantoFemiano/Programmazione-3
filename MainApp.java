package com.example.grafico;

import it.santosalvatore.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        Main.main(new String[]{});
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//avvio della primaryStage (prima schermata)

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Schermata_avvio.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("E-commerce App");
            primaryStage.getIcons().add(new Image("file:///C:/Users/salva/OneDrive/Desktop/Progetto/untitled/grafico/src/main/resources/com/example/grafico/logo_parthenope_avvio.png"));
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);


            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
