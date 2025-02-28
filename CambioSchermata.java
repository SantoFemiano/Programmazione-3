package com.example.grafico;

/*La classe CambioSchermata fornisce metodi per cambiare la schermata in un'applicazione JavaFX.
* Il metodo cambioschermataconController(String nome, Button bottone, String title, Utente utente) permette di cambiare la schermata attuale con una nuova schermata, passando i dati dell'utente corrente al controller della nuova schermata.
*Il metodo cambiaSchermataConUtente(String fxmlPath, String title, Utente utente, ActionEvent actionEvent) permette di cambiare la schermata attuale con una nuova schermata, passando i dati dell'utente corrente al controller della nuova schermata.
* Questi metodi prendono in input il percorso del file FXML della nuova schermata*/

import it.santosalvatore.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CambioSchermata {

    public void cambioschermataconController(String nome, Button bottone, String title, Utente utente) throws IOException {
        FXMLLoader schermoGenerico = new FXMLLoader(getClass().getResource(nome));
        Parent schermoVend = schermoGenerico.load();

        DataInitializable controller = schermoGenerico.getController();
        controller.initData(utente);


        // Creazione di una nuova scena
        Scene Generico = new Scene(schermoVend);

        // Ottieni lo Stage attuale
        Stage stage = (Stage) bottone.getScene().getWindow();

        // Imposta la nuova scena nello Stage
        stage.setTitle(title);
        stage.setScene(Generico);
    }


    public void cambiaSchermataConUtente(String fxmlPath, String title, Utente utente, ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent Padre = loader.load();
        DataInitializable controller = loader.getController(); // Ottieni il controller
        controller.initData(utente);

        // Creazione di una nuova scena
        Scene NuovaScena = new Scene(Padre);

        // Ottieni lo Stage attuale
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Imposta la nuova scena nello Stage
        stage.setTitle(title);
        stage.setScene(NuovaScena);


    }


}
