package com.example.grafico;


//La classe ModificaController gestisce la logica di una schermata per effettuare la modifica di un prodotto gia esistente.


import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ModificaController {

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
    public ListView<Product> Listaview_listaProdotti;
    List<Product> listaProdotti;
    GestoreDatabase database = new GestoreDatabase();

    public void initialize() {


        bottone_aggiungiProdotto.setOnAction(event -> aggiungiprodotto());
        bottone_modificaProdotto.setOnAction(event -> modificaprodotto());
        bottone_indietro.setOnAction(event -> indietro());
        bottone_eliminaProdotto.setOnAction(event -> eliminaProdotto());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());
        Listaview_listaProdotti.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {

                Product selectedProduct = Listaview_listaProdotti.getSelectionModel().getSelectedItem();

                Alert modifica = new Alert(Alert.AlertType.CONFIRMATION);
                modifica.setTitle("Modifica Prodotto");
                modifica.setHeaderText("Dettagli del prodotto da modificare:\n\n Nome:" + selectedProduct.getName() + "\n Prezzo" + selectedProduct.getPrice() + "\n Quantità" + selectedProduct.getAmount() + "\n Colore" + selectedProduct.getColor());
                modifica.setContentText("Vuoi modificarlo?");
                modifica.showAndWait().ifPresent(response -> {
                    if (response == javafx.scene.control.ButtonType.OK) {
                        try {
                            cambioSchermataConSelectedProduct("schermata_visualizza_modificaProdotto.fxml", selectedProduct, Listaview_listaProdotti);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });


        if (listaProdotti != null) {
            listaProdotti.clear();
        }
        listaProdotti = database.getAllProductsDB();

        ObservableList<Product> observableListProdotti = FXCollections.observableArrayList();
        Listaview_listaProdotti.setCellFactory(param -> new OrderListCell());

        observableListProdotti.addAll(listaProdotti);
        Listaview_listaProdotti.setItems(observableListProdotti);
    }


    private void cambioSchermataConSelectedProduct(String nome, Product selectedProduct, ListView<Product> Lista) throws IOException {
        FXMLLoader nuovoSchermo = new FXMLLoader(getClass().getResource(nome));
        Parent modificazione = nuovoSchermo.load();

        DataInitializableSelectedProduct controller = nuovoSchermo.getController();
        controller.initData(selectedProduct); // Se è necessario inizializzare dati sulla schermata principale


        // Ottenere lo Stage attuale e settare la nuova scena
        Stage stage = (Stage) Lista.getScene().getWindow();
        Scene scene = new Scene(modificazione);
        stage.setScene(scene);
        stage.show();
    }

    private void cambioSchermata(String nome, Button bottone) throws IOException {
        FXMLLoader schermoGenerico = new FXMLLoader(getClass().getResource(nome));
        Parent padre = schermoGenerico.load();
        // Se è necessario inizializzare dati sulla schermata principale

        // Ottenere lo Stage attuale e settare la nuova scena
        Stage stage = (Stage) bottone.getScene().getWindow();
        Scene scene = new Scene(padre);
        stage.setScene(scene);
        stage.show();
    }

    private void cambioSchermataconTitolo(String nome, Button bottone, String title) throws IOException {
        FXMLLoader schermoGenerico = new FXMLLoader(getClass().getResource(nome));
        Parent schermoVend = schermoGenerico.load();


        // Creazione di una nuova scena
        Scene Generico = new Scene(schermoVend);

        // Ottieni lo Stage attuale
        Stage stage = (Stage) bottone.getScene().getWindow();

        // Imposta la nuova scena nello Stage
        stage.setTitle(title);
        stage.setScene(Generico);
    }

    private void eliminaProdotto() {
        try {
            cambioSchermata("schermata_visualizza_Elimina_Prodotto.fxml", bottone_eliminaProdotto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void visualizzaListaProdotti() {

    }

    private void indietro() {
        try {


            cambioSchermataconTitolo("schermata_venditore.fxml", bottone_indietro, "Menu Venditore");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modificaprodotto() {
    }

    private void aggiungiprodotto() {
        {
            try {
                cambioSchermata("schermata_visualizza_aggiungiProdotto.fxml", bottone_aggiungiProdotto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
