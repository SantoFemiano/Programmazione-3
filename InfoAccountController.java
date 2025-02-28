package com.example.grafico;

//La classe InfoAccountController gestisce la logica di una schermata che permette la visualizzazione dei dati di un utente.

import it.santosalvatore.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class InfoAccountController implements DataInitializable {
    CambioSchermata cambio = new CambioSchermata();

    AzioniCliente Azioni = new AzioniCliente();

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
    private TextArea Testo_Informazioni;

    private Utente utente;


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
        Testo_Informazioni.setText("username:" + utente.getUsername() + "\n password:" + utente.getPassword() + "\n Indirizzo:" + utente.getIndirizzo() + "\n Email:" + utente.getEmail() + "\n Numero Telefono:" + utente.getNumeroTel());
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