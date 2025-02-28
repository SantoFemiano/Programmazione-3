package com.example.grafico;
//La classe gestisce la logica di una schermata che visualizza le varie modalità di pagamento  durante il processo di pagamento.


import it.santosalvatore.ConcreteUtenteFactory;
import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import it.santosalvatore.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.util.List;

public class SceltaPagamentoController implements DataInitializable {
    @FXML
    public Button bottone_indietro;
    @FXML
    public Button bottone_carta_credito;
    @FXML
    public Button bottone_bonifico;
    @FXML
    public Button bottone_paypal;
    GestoreDatabase database = new GestoreDatabase();
    List<Product> listaProdotti;
    List<Utente> listaUtenti;
    List<Product> listaCarrello;
    List<Product> listaOrdini;
    CartaCreditoCommand cartaCreditoCommand;
    PaypalCommand paypalCommand;
    BonificoCommand bonificoCommand;
    IndietroCommand indietroCommand;
    private Utente utente = new ConcreteUtenteFactory().createUtente("test uente", "", "", "", "");

    public void initialize() {
        bottone_indietro.setOnAction(event -> indietro());
        bottone_carta_credito.setOnAction(event -> selezione_carta_credito());
        bottone_paypal.setOnAction(event -> selezione_paypal());
        bottone_bonifico.setOnAction(event -> selezione_bonifico());


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

    }

    private void indietro() {
        indietroCommand = new IndietroCommand(new FXMLLoader(getClass().getResource("Schermata_principale_cliente.fxml")), utente, bottone_indietro);
        indietroCommand.execute();
    }

    private void selezione_carta_credito() {
        cartaCreditoCommand = new CartaCreditoCommand(new FXMLLoader(getClass().getResource("schermata_carta_di_credito.fxml")), utente, bottone_carta_credito);
        cartaCreditoCommand.execute();
    }

    private void selezione_paypal() {

        paypalCommand = new PaypalCommand(new FXMLLoader(getClass().getResource("Paypal.fxml")), utente, bottone_paypal);
        paypalCommand.execute();
    }

    private void selezione_bonifico() {
        bonificoCommand = new BonificoCommand(new FXMLLoader(getClass().getResource("Bonifico.fxml")), utente, bottone_bonifico);
        bonificoCommand.execute();

    }
}
