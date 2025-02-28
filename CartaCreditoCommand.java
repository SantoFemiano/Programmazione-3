package com.example.grafico;
//La classe CartaCreditoCommand rappresenta una classe concreta che implemnta l'interfaccia Command e effettua l'override del metodo implementato execute()

import it.santosalvatore.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class CartaCreditoCommand implements Command {
    private final FXMLLoader loader;
    private final Utente utente;
    private final Button sourceButton;

    public CartaCreditoCommand(FXMLLoader loader, Utente utente, Button sourceButton) {
        this.loader = loader;
        this.utente = utente;
        this.sourceButton = sourceButton;
    }

    @Override
    public void execute() {
        try {
            Parent cartacredito = loader.load();
            CartaDiCreditoController cartaDiCreditoController = loader.getController();
            cartaDiCreditoController.initData(utente);

            Stage stage = (Stage) sourceButton.getScene().getWindow();
            Scene scene = new Scene(cartacredito);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
