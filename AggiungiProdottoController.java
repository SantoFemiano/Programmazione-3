package com.example.grafico;

/*La classe rappresenta un controller per la vista che consente agli utenti di aggiungere nuovi prodotti al sistema
*Fornisce un pulsante per scegliere un'immagine dal file system locale utilizzando un FileChooser.
*Gestisce gli eventi dei pulsanti nella vista, come il click sul pulsante "Aggiungi Prodotto", "Modifica Prodotto", "Elimina Prodotto", "Visualizza Lista Prodotti" e "Indietro".
*Consente agli utenti di inserire i dettagli del nuovo prodotto attraverso campi di testo per il nome, il prezzo, la quantità, il colore e l'immagine.
*Quando un utente aggiunge un nuovo prodotto, i dettagli inseriti vengono utilizzati per creare un nuovo oggetto Product utilizzando una ConcreteProductFactory.
* Utilizza un oggetto GestoreDatabase per inserire il nuovo prodotto nel database e mostra un messaggio di avviso all'utente in caso di successo o errore.
* Fornisce funzionalità per cambiare la schermata della finestra principale dell'applicazione, ad esempio per visualizzare la lista dei prodotti, tornare indietro al menu principale o modificare un prodotto esistente. */

import it.santosalvatore.ConcreteProductFactory;
import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AggiungiProdottoController {

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
    public TextField nome;
    @FXML
    public TextField prezzo;
    @FXML
    public TextField quantita;
    @FXML
    public TextField colore;
    @FXML
    public TextField immagine;
    @FXML
    public Button file_locale;
    @FXML
    public Button aggiungi;
    GestoreDatabase database = new GestoreDatabase();

    public void initialize() {
        bottone_aggiungiProdotto.setOnAction(event -> aggiungiprodotto());
        bottone_modificaProdotto.setOnAction(event -> modificaprodotto());
        bottone_indietro.setOnAction(event -> indietro());
        bottone_eliminaProdotto.setOnAction(event -> eliminaProdotto());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());

        file_locale.setOnAction(event -> usaFileLocale());
        aggiungi.setOnAction(event -> aggiungiIlProdotto());

// Rendi per tutti i sistemi "file:///"
        //Stampa allert in caso di errore e di successo
        //controlla se boolean riuscito riesce poi nell'alert clicchi no
        // se annulla

    }

    private void aggiungiIlProdotto() {
        String nome = this.nome.getText();
        double prezzo = Double.parseDouble(this.prezzo.getText());
        int quantita = Integer.parseInt(this.quantita.getText());
        String colore = this.colore.getText();
        String immagine = this.immagine.getText();
        Product prodottoDaAggiungere = new ConcreteProductFactory().createProduct(0, nome, prezzo, quantita, colore, immagine);

        if (database.setOneProdottoAutoIDDB(prodottoDaAggiungere)) {

            try {
                Alert inseritoNellaListaProdotti = new Alert(Alert.AlertType.WARNING);
                inseritoNellaListaProdotti.setTitle("Avviso");
                inseritoNellaListaProdotti.setHeaderText("Il prodotto è stato inserito nella lista prodotti");
                inseritoNellaListaProdotti.setContentText("Dettagli dell'acquisto:\n\n Nome:" + prodottoDaAggiungere.getName() + "\n Prezzo" + prodottoDaAggiungere.getPrice() + "\n Quantità" + prodottoDaAggiungere.getAmount() + "\n Colore" + prodottoDaAggiungere.getColor());
                inseritoNellaListaProdotti.showAndWait();


                cambioSchermata("schermata_visualizza_aggiungiProdotto.fxml", bottone_aggiungiProdotto);
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        } else {
            Alert erroreInserimento = new Alert(Alert.AlertType.WARNING);
            erroreInserimento.setTitle("Avviso");
            erroreInserimento.setHeaderText("L'inserimento non è andato a buon fine");
            erroreInserimento.setContentText("Correggi i dati inseriti");
            erroreInserimento.showAndWait();
        }

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


    private void usaFileLocale() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Scegli un file");

        // Imposta i filtri per i tipi di file che vuoi consentire
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Ottieni la finestra attuale per visualizzare il FileChooser
        Stage stage = (Stage) immagine.getScene().getWindow();

        // Mostra il dialogo di selezione del file
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Ottieni il percorso assoluto del file selezionato
            Path path = Paths.get(selectedFile.toURI());
            String pathString = path.toAbsolutePath().toString();

            // Imposta il percorso del file selezionato nella TextField
            immagine.setText("file:///" + pathString);
        }
    }

    private void visualizzaListaProdotti() {
        try {
            cambioSchermata("schermata_visualizza_listaProdotti_Venditore.fxml", bottone_visualizzaListaProdotti);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminaProdotto() {
        try {
            cambioSchermata("schermata_visualizza_Elimina_Prodotto.fxml", bottone_eliminaProdotto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void indietro() {
        try {


            cambioSchermataconTitolo("schermata_venditore.fxml", bottone_indietro, "Menu Venditore");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modificaprodotto() {
        try {
            cambioSchermata("schermata_visualizza_listaModificaProdotto.fxml", bottone_modificaProdotto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aggiungiprodotto() {
    }
}
