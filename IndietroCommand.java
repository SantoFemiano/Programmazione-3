package com.example.grafico;

//La classe IndietroCommand rappresenta una classe concreta che implemnta l'interfaccia Command e effettua l'override del metodo implementato execute()
//questo comando gestisce il ritorno alla schermata precedente.


import it.santosalvatore.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class IndietroCommand implements Command {
    private final FXMLLoader loader;
    private final Utente utente;
    private final Button sourceButton;

    public IndietroCommand(FXMLLoader loader, Utente utente, Button sourceButton) {
        this.loader = loader;
        this.utente = utente;
        this.sourceButton = sourceButton;
    }

    @Override
    public void execute() {
        try {
            Parent indietro = loader.load();
            ClienteController clienteController = loader.getController();
            clienteController.initData(utente);

            Stage stage = (Stage) sourceButton.getScene().getWindow();
            Scene scene = new Scene(indietro);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}