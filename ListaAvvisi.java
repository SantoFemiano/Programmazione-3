package com.example.grafico;

//Questa classe ListaAvvisi gestisce la visualizzazione degli avvisi all'utente in base a diverse situazioni.

import it.santosalvatore.Product;

public class ListaAvvisi {
    private static final String HEADER_CARRELLO = "Il prodotto è stato inserito nel carrello";
    private static final String HEADER_ACQUISTO = "Il prodotto è stato acquistato";
    private static final String TEXT_CARRELLO = "Dettagli del prodotto";
    private static final String TEXT_ACQUISTO = "Dettagli dell'acquisto";
    private static final String HEADER_QUANTITA_MAGGIORE = "La quantità è maggiore delle scorte";
    private static final String TEXT_QUANTITA_MAGGIORE = "Diminuisci la quantità";
    private static final String HEADER_CARRELLO_VUOTO = "Il carrello è vuoto";
    private static final String TEXT_CARRELLO_VUOTO = "Non ci sono prodotti nel carrello. Aggiungi dei prodotti prima di confermare l'acquisto.";
    Avvisi avvisi = new Avvisi();

    public void avvisoCarrelloSuccesso(Product selectedProduct, int quantita) {
        avvisi.AvvisoConSelectedProduct(selectedProduct, quantita, HEADER_CARRELLO, TEXT_CARRELLO);
    }

    public void avvisoAcquistoSuccesso(Product selectProduct, int quantita) {
        avvisi.AvvisoConSelectedProduct(selectProduct, quantita, HEADER_ACQUISTO, TEXT_ACQUISTO);
    }

    public void quantitaMaggiore() {
        avvisi.AvvisoSenzaSelectedProduct(HEADER_QUANTITA_MAGGIORE, TEXT_QUANTITA_MAGGIORE);
    }

    public void carrelloVuoto() {
        avvisi.AvvisoSenzaSelectedProduct(HEADER_CARRELLO_VUOTO, TEXT_CARRELLO_VUOTO);
    }
}
