package com.example.grafico;
//La classe Bonificocontroller gestisce la logica di una schermata per effettuare un pagamento tramite bonifico bancario.

import it.santosalvatore.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BonificoController implements DataInitializable {

    @FXML
    public Button bottone_pagamento;
    @FXML
    public TextField text_field_iban;
    @FXML
    public TextField text_field_cognome;
    @FXML
    public TextField text_field_nome;
    @FXML
    public Button bottone_indietro;
    List<Venditore> listaVenditori;
    OrdiniObserver ordiniObserver = new ConcreteOrdiniObserver(null);
    Utente utente;
    List<Product> listaProdotti;
    List<Product> listaOrdini;
    List<Product> listaCarrello;
    List<Utente> listaUtenti;
    GestoreDatabase database = new GestoreDatabase();
    GestoreCarrello carrello;

    public void initialize() {
        bottone_pagamento.setOnAction(event -> effettua_pagamento());
        bottone_indietro.setOnAction(event -> indietro());
        listaVenditori = database.getAllVenditoriDBreturn();
        for (Venditore venditore : listaVenditori) {
            ordiniObserver = new ConcreteOrdiniObserver(venditore);
        }
    }

    public void initData(Utente utente) {
        this.utente = utente;

        if (listaProdotti != null) {
            listaProdotti.clear();
            listaUtenti.clear();
            listaOrdini.clear();
            listaCarrello.clear();
        }

        listaProdotti = database.getAllProductsDB();
        listaUtenti = database.getAllUtentiDB();
        listaOrdini = database.getAllOrdiniDB(utente);
        listaCarrello = database.getAllCarrelloDB(utente);
        this.carrello = new GestoreCarrello(listaCarrello, listaProdotti, listaOrdini, utente, listaUtenti);


    }

    private void cambioSchermataConUtente(String nome, Button bottone) throws IOException {
        FXMLLoader schermoGenerico = new FXMLLoader(getClass().getResource(nome));
        Parent progresso = schermoGenerico.load();

        DataInitializable controller = schermoGenerico.getController();
        controller.initData(utente); //


        // Ottenere lo Stage attuale e settare la nuova scena
        Stage stage = (Stage) bottone.getScene().getWindow();
        Scene scene = new Scene(progresso);
        stage.setScene(scene);
        stage.show();
    }


    private void cambioSchermataconTitolo(String nome, Button bottone, String title) throws IOException {
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

    private void indietro() {
        try {
            cambioSchermataconTitolo("schermata_scelta_di_pagamento.fxml", bottone_indietro, "Scelta menu pagamento");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void effettua_pagamento() {
        String nome = text_field_nome.getText();
        String cognome = text_field_cognome.getText();
        String numeroIban = text_field_iban.getText();


        PagamentoStrategy pagamentoconbonifico = new PagamentoBonificoBancario(numeroIban);
        setPagamentoStrategy setPagamentoStrategy = new setPagamentoStrategy(pagamentoconbonifico);
        double prezzoTotale = carrello.calcolaTotaleCarrello(listaCarrello);
        setPagamentoStrategy.processoPagamento(prezzoTotale);

        for (Venditore venditore : listaVenditori) {
            for (Product product : listaCarrello) {
                ordiniObserver.updateOrdini(venditore, product.getName() + " Quantita " + product.getAmount());
            }
        }

        database.setAllOrdiniDB(utente, listaCarrello);


        try {
            cambioSchermataConUtente("ProgressBar.fxml", bottone_pagamento);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


