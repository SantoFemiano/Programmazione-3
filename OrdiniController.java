package com.example.grafico;

//La classe OrdiniController gestisce la logica di una schermata che permette la visualizzazione della lista degli ordini di un singolo utente.


import it.santosalvatore.ConcreteUtenteFactory;
import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import it.santosalvatore.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrdiniController implements DataInitializable {

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
    private ListView<Product> Listaview_MieiOrdini = new ListView<>();
    @FXML
    private Button bottone_indietro;
    private Utente utente = new ConcreteUtenteFactory().createUtente("test utente", "", "", "", "");
    private List<Product> listaOrdini = new ArrayList<>();
    private List<Utente> listaUtenti = new ArrayList<>();
    private List<Product> listaCarrello = new ArrayList<>();
    private List<Product> listaProdotti = new ArrayList<>();

    public void initData(Utente utente) {
        this.utente = utente;
        if (listaOrdini != null) {
            listaUtenti.clear();
            listaCarrello.clear();
            listaProdotti.clear();
            listaOrdini.clear();
        }
        listaUtenti = database.getAllUtentiDB();
        listaCarrello = database.getAllCarrelloDB(utente);
        listaProdotti = database.getAllProductsDB();
        listaOrdini = database.getAllOrdiniDB(utente);
        if (listaOrdini.isEmpty()) {
            System.out.println("lista vuota");
        }
        for (Product product1 : listaOrdini) {
            System.out.println(product1.getName());
        }
        ObservableList<Product> observableListOrdini = FXCollections.observableArrayList();
        Listaview_MieiOrdini.setCellFactory(param -> new OrderListCell());

        observableListOrdini.addAll(listaOrdini);
        Listaview_MieiOrdini.setItems(observableListOrdini);

    }

    public void initialize() {
        bottone_aggiungialCarrello.setOnAction(event -> aggiungialCarrello());
        bottone_visualizzaCarrello.setOnAction(event -> visualizzaCarrello());
        bottone_visualizzaOrdini.setOnAction(event -> visualizzaOrdini());
        bottone_visualizzaInfoAccount.setOnAction(event -> visualizzaInfoAccount());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());
        bottone_indietro.setOnAction(event -> indietro());
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
