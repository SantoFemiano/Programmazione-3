package com.example.grafico;
//La classe ProdottoModificaController gestisce la logica di una schermata che permette la visualizzazione del form per inserire i nuovi dati del prodottto (per l'utente venditore).


import it.santosalvatore.ConcreteProductFactory;
import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProdottoModificaController implements DataInitializableSelectedProduct {
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
    @FXML
    public Label imagevecchia;
    @FXML
    public Label colorevecchio;
    @FXML
    public Label quantitavecchia;
    @FXML
    public Label prezzovecchio;
    @FXML
    public Label nomevecchio;
    Product product;
    GestoreDatabase database = new GestoreDatabase();

    public void initialize() {
        bottone_aggiungiProdotto.setOnAction(event -> aggiungiprodotto());
        bottone_modificaProdotto.setOnAction(event -> modificaprodotto());
        bottone_indietro.setOnAction(event -> indietro());
        bottone_eliminaProdotto.setOnAction(event -> eliminaProdotto());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());

        file_locale.setOnAction(event -> usaFileLocale());
        aggiungi.setOnAction(event -> aggiungiIlProdotto());


    }

    public void initData(Product product) {
        this.product = product;
        imagevecchia.setText(product.getImage());
        nomevecchio.setText(product.getName());
        prezzovecchio.setText(Double.toString(product.getPrice()));
        quantitavecchia.setText(Integer.toString(product.getAmount()));
        colorevecchio.setText(product.getColor());
        imagevecchia.setMaxWidth(100);
        nomevecchio.setMaxWidth(100);
        prezzovecchio.setMaxWidth(100);
        quantitavecchia.setMaxWidth(100);
        colorevecchio.setMaxWidth(100);

    }

    private void aggiungiIlProdotto() {
        String nome = this.nome.getText();
        double prezzo = Double.parseDouble(this.prezzo.getText());
        int quantita = Integer.parseInt(this.quantita.getText());
        String colore = this.colore.getText();
        String immagine = this.immagine.getText();
        Product prodottoDaAggiungere = new ConcreteProductFactory().createProduct(0, nome, prezzo, quantita, colore, immagine);


        try {
            database.removeOneProductDB(product);
            database.setOneProdottoIDDB(prodottoDaAggiungere, product.getId_Product());
            Alert inseritoNellaListaProdotti = new Alert(Alert.AlertType.WARNING);
            inseritoNellaListaProdotti.setTitle("Avviso");
            inseritoNellaListaProdotti.setHeaderText("Il prodotto è stato inserito nella lista prodotti");
            inseritoNellaListaProdotti.setContentText("Dettagli del prodotto aggiunto:\n\n Nome:" + prodottoDaAggiungere.getName() + "\n Prezzo" + prodottoDaAggiungere.getPrice() + "\n Quantità" + prodottoDaAggiungere.getAmount() + "\n Colore" + prodottoDaAggiungere.getColor() + "Dettagli del prodotto modificato:\n\n Nome:" + product.getName() + "\n Prezzo" + product.getPrice() + "\n Quantità" + product.getAmount() + "\n Colore" + product.getColor());
            inseritoNellaListaProdotti.showAndWait();


            FXMLLoader schermoAggiungi = new FXMLLoader(getClass().getResource("schermata_visualizza_listaProdotti_Venditore.fxml"));
            Parent aggiungi = schermoAggiungi.load();
            // Se è necessario inizializzare dati sulla schermata principale

            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_aggiungiProdotto.getScene().getWindow();
            Scene scene = new Scene(aggiungi);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
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
            FXMLLoader schermoProdotti = new FXMLLoader(getClass().getResource("schermata_visualizza_listaProdotti_Venditore.fxml"));
            Parent prodotti = schermoProdotti.load();


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_visualizzaListaProdotti.getScene().getWindow();
            Scene scene = new Scene(prodotti);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminaProdotto() {
        try {
            FXMLLoader schermoElimina = new FXMLLoader(getClass().getResource("schermata_visualizza_Elimina_Prodotto.fxml"));
            Parent elimina = schermoElimina.load();


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_eliminaProdotto.getScene().getWindow();
            Scene scene = new Scene(elimina);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void indietro() {
        try {
            FXMLLoader schermoElimina = new FXMLLoader(getClass().getResource("schermata_visualizza_listaModificaProdotto.fxml"));
            Parent elimina = schermoElimina.load();


            // Ottenere lo Stage attuale e settare la nuova scena
            Stage stage = (Stage) bottone_indietro.getScene().getWindow();
            Scene scene = new Scene(elimina);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modificaprodotto() {
    }

    private void aggiungiprodotto() {
    }
}
