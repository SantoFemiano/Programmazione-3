package com.example.grafico;

//La classe OrdiniController gestisce la logica di una schermata che permette la visualizzazione della lista dei Prodotti disponibili per l'utente.


import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import it.santosalvatore.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

public class ProdottiController implements DataInitializable {

    AzioniCliente Azioni = new AzioniCliente();
    GestoreDatabase database = new GestoreDatabase();
    @FXML
    private Button bottone_aggiungialCarrello;
    @FXML
    private Button bottone_visualizzaCarrello;
    @FXML
    private Button bottone_visualizzaOrdini;
    @FXML
    private Button bottone_visualizzaInfoAccount;
    @FXML
    private Button bottone_visualizzaListaProdotti;
    @FXML
    private Button bottone_indietro;
    @FXML
    private ListView<Product> Listaview_listaProdotti;
    private Utente utente;
    private List<Product> listaProdotti;

    public void initialize() {
        bottone_aggiungialCarrello.setOnAction(event -> aggiungialCarrello());
        bottone_visualizzaCarrello.setOnAction(event -> visualizzaCarrello());
        bottone_visualizzaOrdini.setOnAction(event -> visualizzaOrdini());
        bottone_visualizzaInfoAccount.setOnAction(event -> visualizzaInfoAccount());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());
        bottone_indietro.setOnAction(event -> indietro());

    }

    public void initData(Utente utente) {
        this.utente = utente;
        if (listaProdotti != null) {
            listaProdotti.clear();
        }
        listaProdotti = database.getAllProductsDB();

        ObservableList<Product> observableListProdotti = FXCollections.observableArrayList();
        Listaview_listaProdotti.setCellFactory(param -> new OrderListCell());

        observableListProdotti.addAll(listaProdotti);
        Listaview_listaProdotti.setItems(observableListProdotti);
    }

    private void aggiungialCarrello() {
        Azioni.aggiungialCarrello(bottone_aggiungialCarrello, utente);
    }

    private void visualizzaCarrello() {
        Azioni.visualizzaCarrello(bottone_visualizzaCarrello, utente);
    }

    private void visualizzaOrdini() {
        Azioni.visualizzaOrdini(bottone_visualizzaOrdini, utente);
    }

    private void visualizzaInfoAccount() {
        Azioni.visualizzaInfoAccount(bottone_visualizzaInfoAccount, utente);
    }

    private void visualizzaListaProdotti() {
        Azioni.visualizzaListaProdotti(bottone_visualizzaListaProdotti, utente);
    }

    private void indietro() {
        Azioni.indietro(bottone_indietro, utente);
    }
}
