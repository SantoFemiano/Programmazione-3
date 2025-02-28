package com.example.grafico;

//La classe PaypalController gestisce la logica di una schermata per effettuare un pagamento tramite bonifico bancario.

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

public class PaypalController implements DataInitializable {
    String avviso;
    @FXML
    public Button bottone_indietro;
    @FXML
    public TextField text_field_email;
    @FXML
    public TextField text_field_password;
    @FXML
    public Button bottone_pagamento;
    Utente utente;
    List<Product> listaProdotti;
    List<Product> listaOrdini;
    List<Product> listaCarrello;
    List<Utente> listaUtenti;
    List<Venditore> listaVenditori;
    OrdiniObserver ordiniObserver = new ConcreteOrdiniObserver(null);

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

    private void indietro() {
        try {
            FXMLLoader schermoSceltaPagamento = new FXMLLoader(getClass().getResource("schermata_scelta_di_pagamento.fxml"));
            Parent sceltaPagamento = schermoSceltaPagamento.load();

            SceltaPagamentoController sceltaPagamentoController = schermoSceltaPagamento.getController();
            sceltaPagamentoController.initData(utente); // Se è necessario inizializzare dati sulla schermata principale

            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_indietro.getScene().getWindow();
            Scene scene = new Scene(sceltaPagamento);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void effettua_pagamento() {
         String email = text_field_email.getText();
        String password = text_field_password.getText();


        PagamentoStrategy pagamentoconpaypal = new PagamentoPaypal(email);
        setPagamentoStrategy setPagamentoStrategy = new setPagamentoStrategy(pagamentoconpaypal);
        double prezzoTotale = carrello.calcolaTotaleCarrello(listaCarrello);
        setPagamentoStrategy.processoPagamento(prezzoTotale);







        try {
            FXMLLoader schermoProgresso = new FXMLLoader(getClass().getResource("ProgressBar.fxml"));
            Parent progresso = schermoProgresso.load();

            ProgressBarController progressBarController = schermoProgresso.getController();
            progressBarController.initData(utente); //


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_pagamento.getScene().getWindow();
            Scene scene = new Scene(progresso);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}