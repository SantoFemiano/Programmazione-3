package com.example.grafico;
//La classe CartaDiCreditoController gestisce la logica di una schermata per effettuare un pagamento tramite bonifico bancario.

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

public class CartaDiCreditoController implements DataInitializable {


    @FXML
    public TextField Nome;
    @FXML
    public TextField Cognome;
    @FXML
    public TextField Numero_Carta;
    @FXML
    public TextField Scadenza;
    @FXML
    public Button bottone_effettua_pagamento;
    @FXML
    public Button bottone_indietro;
    Utente utente;
    List<Product> listaProdotti;
    List<Product> listaOrdini;
    List<Product> listaCarrello;
    List<Utente> listaUtenti;
    List<Venditore> listaVenditori;
    GestoreDatabase database = new GestoreDatabase();
    GestoreCarrello carrello;
    OrdiniObserver ordiniObserver = new ConcreteOrdiniObserver(null);

    public void initialize() {
        bottone_effettua_pagamento.setOnAction(event -> effettua_pagamento());
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
            cambioSchermataConUtente("schermata_scelta_di_pagamento.fxml", bottone_indietro);
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private void effettua_pagamento() {
        String nome = this.Nome.getText();
        String cognome = this.Cognome.getText();
        String numeroCarta = this.Numero_Carta.getText();
        String scadenza = this.Scadenza.getText();

        PagamentoStrategy pagamentoconcarta = new PagamentoCartaCredito(numeroCarta, nome, scadenza);
        setPagamentoStrategy setPagamentoStrategy = new setPagamentoStrategy(pagamentoconcarta);
        double prezzoTotale = carrello.calcolaTotaleCarrello(listaCarrello);
        setPagamentoStrategy.processoPagamento(prezzoTotale);


        for (Venditore venditore : listaVenditori) {
            for (Product product : listaCarrello) {
                ordiniObserver.updateOrdini(venditore, product.getName() + " Quantita " + product.getAmount());
            }
        }


        database.setAllOrdiniDB(utente, listaCarrello);


        try {
            cambioSchermataConUtente("ProgressBar.fxml", bottone_effettua_pagamento);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
