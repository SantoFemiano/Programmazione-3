package com.example.grafico;
//La classe OrdiniController gestisce la logica di una schermata che permette la visualizzazione della lista dei Prodotti disponibili per l'utente venditore.


import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProdottiVenditoreController {
    @FXML
    public Button bottone_aggiungiProdotto;
    @FXML
    public Button bottone_modificaProdotto;
    @FXML
    public Button bottone_eliminaProdotto;
    @FXML
    public Button bottone_visualizzaListaProdotti;
    @FXML
    public Button bottone_indietro;
    @FXML
    public ListView Listaview_listaProdotti;
    List<Product> listaProdotti;
    GestoreDatabase database = new GestoreDatabase();

    public void initialize() {


        bottone_aggiungiProdotto.setOnAction(event -> aggiungiprodotto());
        bottone_modificaProdotto.setOnAction(event -> modificaprodotto());
        bottone_indietro.setOnAction(event -> indietro());
        bottone_eliminaProdotto.setOnAction(event -> eliminaProdotto());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());


        if (listaProdotti != null) {
            listaProdotti.clear();
        }
        listaProdotti = database.getAllProductsDB();

        ObservableList<Product> observableListProdotti = FXCollections.observableArrayList();
        Listaview_listaProdotti.setCellFactory(param -> new OrderListCell());

        observableListProdotti.addAll(listaProdotti);
        Listaview_listaProdotti.setItems(observableListProdotti);
    }


    private void eliminaProdotto() {
        try {
            FXMLLoader schermoElimina = new FXMLLoader(getClass().getResource("schermata_visualizza_Elimina_Prodotto.fxml"));
            Parent elimina = schermoElimina.load();


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_eliminaProdotto.getScene().getWindow();
            Scene scene = new Scene(elimina);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void visualizzaListaProdotti() {
    }

    private void indietro() {
        try {


            FXMLLoader schermoVenditore = new FXMLLoader(getClass().getResource("schermata_venditore.fxml"));
            Parent schermoVend = schermoVenditore.load();


            // Creazione di una nuova scena
            Scene VenditoreLogin = new Scene(schermoVend);

            // Ottieni lo Stage attuale
            Stage stage = (Stage) bottone_indietro.getScene().getWindow();

            // Imposta la nuova scena nello Stage
            stage.setTitle("Menu Venditore");
            stage.setScene(VenditoreLogin);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modificaprodotto() {
        try {
            FXMLLoader schermoElimina = new FXMLLoader(getClass().getResource("schermata_visualizza_listaModificaProdotto.fxml"));
            Parent elimina = schermoElimina.load();


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_modificaProdotto.getScene().getWindow();
            Scene scene = new Scene(elimina);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aggiungiprodotto() {
        {
            try {
                FXMLLoader schermoProdotti = new FXMLLoader(getClass().getResource("schermata_visualizza_aggiungiProdotto.fxml"));
                Parent prodotti = schermoProdotti.load();

                // Ottenere lo Stage attuale e settare la nuova scena
                Stage stage = (Stage) bottone_aggiungiProdotto.getScene().getWindow();
                Scene scene = new Scene(prodotti);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
