package com.example.grafico;

//La classe VenditoreController gestisce la schermata principale del venditore .


import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class VenditoreController {

    GestoreDatabase database = new GestoreDatabase();
    @FXML
    private Button bottone_aggiungiprodotto;
    @FXML
    private Button bottone_modificaprodotto;
    @FXML
    private Button bottone_eliminaprodotto;
    @FXML
    private Button bottone_visualizzalistaprodotti;
    @FXML
    private Button bottone_escidaccountVenditore;
    @FXML
    private GridPane gridPane;
    private List<Product> listaProdotti = null;

    public void initialize() {
        bottone_aggiungiprodotto.setOnAction(event -> aggiungiProdotto());
        bottone_modificaprodotto.setOnAction(event -> modificaProdotto());
        bottone_eliminaprodotto.setOnAction(event -> eliminaProdotto());
        bottone_visualizzalistaprodotti.setOnAction(event -> visualizzaListaProdotti());
        bottone_escidaccountVenditore.setOnAction(this::escidaccountVenditore);

        listaProdotti = database.getAllProductsDB();


        int col = 0;
        int row = 0;
        for (Product product : listaProdotti) {
            if (product.getAmount() > 0) {
                String imagePath = product.getImage();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Imposta la larghezza desiderata
                imageView.setFitHeight(100); // Imposta l'altezza desiderata

                gridPane.add(imageView, col, row); // Aggiungi l'immagine alla posizione specificata

                col++;
                if (col == 5) { // Numero massimo di colonne nel tuo caso
                    col = 0;
                    row++;

                }
            } else {
                String imagePath = product.getImage();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);

                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setSaturation(-1); // Imposta la saturazione a -1 per ottenere l'effetto in bianco e nero
                imageView.setEffect(colorAdjust);

                imageView.setFitWidth(100); // Imposta la larghezza desiderata
                imageView.setFitHeight(100); // Imposta l'altezza desiderata

                gridPane.add(imageView, col, row); // Aggiungi l'immagine alla posizione specificata

                col++;
                if (col == 5) { // Numero massimo di colonne nel tuo caso
                    col = 0;
                    row++;

                }
            }


        }
    }

    private void aggiungiProdotto() {

        try {
            FXMLLoader schermoProdotti = new FXMLLoader(getClass().getResource("schermata_visualizza_aggiungiProdotto.fxml"));
            Parent prodotti = schermoProdotti.load();


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_aggiungiprodotto.getScene().getWindow();
            Scene scene = new Scene(prodotti);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void modificaProdotto() {
        try {
            FXMLLoader schermoElimina = new FXMLLoader(getClass().getResource("schermata_visualizza_listaModificaProdotto.fxml"));
            Parent elimina = schermoElimina.load();


            Stage stage = (Stage) bottone_modificaprodotto.getScene().getWindow();
            Scene scene = new Scene(elimina);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminaProdotto() {
        try {
            FXMLLoader schermoElimina = new FXMLLoader(getClass().getResource("schermata_visualizza_Elimina_Prodotto.fxml"));
            Parent elimina = schermoElimina.load();


            Stage stage = (Stage) bottone_eliminaprodotto.getScene().getWindow();
            Scene scene = new Scene(elimina);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void visualizzaListaProdotti() {
        try {
            FXMLLoader schermoProdotti = new FXMLLoader(getClass().getResource("schermata_visualizza_listaProdotti_Venditore.fxml"));
            Parent prodotti = schermoProdotti.load();


            Stage stage = (Stage) bottone_visualizzalistaprodotti.getScene().getWindow();
            Scene scene = new Scene(prodotti);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escidaccountVenditore(ActionEvent event) {
        try {
            WelcomeController indietro = new WelcomeController();
            indietro.VaiIndietro(event);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
