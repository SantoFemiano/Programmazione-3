package com.example.grafico;

/*La classe è responsabile per l'esecuzione di azioni specifiche dell'utente cliente all'interno dell'interfaccia grafica
* Il metodo visualizzaCarrello gestisce l'azione di visualizzazione del carrello dell'utente.
* Il metodo visualizzaOrdini gestisce l'azione di visualizzazione degli ordini dell'utente.
* Il metodo visualizzaInfoAccount gestisce l'azione di visualizzazione delle informazioni dell'account dell'utente.
* Il metodo visualizzaListaProdotti gestisce l'azione di visualizzazione della lista dei prodotti disponibili.
* Il metodo indietro gestisce l'azione di tornare alla schermata principale dell'utente cliente.
* Il metodo aggiungialCarrello gestisce l'azione di aggiungere un prodotto al carrello.
* Il metodo vaiSchermataPrincipaleCliente gestisce l'azione di navigazione alla schermata principale dell'utente cliente.
* Il metodo vaiSceltaPagamento gestisce l'azione di navigazione alla schermata di scelta del pagamento.
 */

import it.santosalvatore.Utente;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class AzioniCliente {
    CambioSchermata cambio = new CambioSchermata();

    public void visualizzaCarrello(Button bottone_visualizzaCarrello, Utente utente) {
        // Codice per tornare alla schermata principale usando il WelcomeController
        try {
            cambio.cambioschermataconController("schermata_visualizzazione_carrello.fxml", bottone_visualizzaCarrello, "Visualizza Carrello", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void visualizzaOrdini(Button bottone_visualizzaOrdini, Utente utente) {
        // Codice per tornare alla schermata principale usando il WelcomeController
        try {
            cambio.cambioschermataconController("schermata_visualizza_iMieiOrdini.fxml", bottone_visualizzaOrdini, "Visualizza Ordini", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void visualizzaInfoAccount(Button bottone_visualizzaInfoAccount, Utente utente) {
        // Codice per tornare alla schermata principale usando il WelcomeController
        try {
            cambio.cambioschermataconController("schermata_visualizza_infoAccount.fxml", bottone_visualizzaInfoAccount, "Visualizza Info Account", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void visualizzaListaProdotti(Button bottone_visualizzaListaProdotti, Utente utente) {
        try {
            cambio.cambioschermataconController("schermata_visualizza_listaProdotti.fxml", bottone_visualizzaListaProdotti, "Visualizza lista Prodotti", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indietro(Button bottone_indietro, Utente utente) {
        // Codice per tornare alla schermata principale usando il WelcomeController
        try {
            cambio.cambioschermataconController("Schermata_principale_cliente.fxml", bottone_indietro, "Menu Venditore", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aggiungialCarrello(Button bottone_aggiungialCarrello, Utente utente) {
        try {
            cambio.cambioschermataconController("schermata_visualizza_aggiungiAlCarrello.fxml", bottone_aggiungialCarrello, "Aggiungi al Carrello", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void vaiSchermataPrincipaleCliente(Utente utente, ActionEvent actionEvent) {
        try {
            cambio.cambiaSchermataConUtente("Schermata_principale_cliente.fxml", "Menu Cliente", utente, actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void vaiSceltaPagamento(Button bottone_visualizzaCarrello, Utente utente) {
        try {
            cambio.cambioschermataconController("schermata_scelta_di_pagamento.fxml", bottone_visualizzaCarrello, "Scelta Pagamento", utente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
