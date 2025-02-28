package com.example.grafico;
//La classe ClienteController gestisce la schermata principale del cliente .


import it.santosalvatore.ConcreteUtenteFactory;
import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import it.santosalvatore.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public class ClienteController implements DataInitializable {

    AzioniCliente Azioni = new AzioniCliente();
    List<Product> listaProdotti;
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
    private Button bottone_esciaccountutente;
    @FXML
    private GridPane gridPane;
    @FXML
    //private Button bottone_indietro;

    private Utente utente = new ConcreteUtenteFactory().createUtente("test utente", "", "", "", "");
    private List<Utente> listaUtenti;
    private List<Product> listaOrdini;
    private List<Product> listaCarrello;


    public void initData(Utente utente) {
        this.utente = utente;
        System.out.println(utente.getUsername());
        GestoreDatabase database = new GestoreDatabase();

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


        int col = 0;
        int row = 0;
        for (Product product : listaProdotti) {
            if (product.getAmount() > 0) {
                String imagePath = product.getImage();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Imposta la larghezza desiderata
                imageView.setFitHeight(100); // Imposta l'altezza desiderata

                gridPane.add(imageView, col, row); // Aggiungi l'immagine alla posizione specificata

                col++;
                if (col == 5) { // Numero massimo di colonne nel tuo caso
                    col = 0;
                    row++;

                }
            } else {
                String imagePath = product.getImage();
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);

                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setSaturation(-1); // Imposta la saturazione a -1 per ottenere l'effetto in bianco e nero
                imageView.setEffect(colorAdjust);

                imageView.setFitWidth(100); // Imposta la larghezza desiderata
                imageView.setFitHeight(100); // Imposta l'altezza desiderata

                gridPane.add(imageView, col, row); // Aggiungi l'immagine alla posizione specificata

                col++;
                if (col == 5) { // Numero massimo di colonne nel tuo caso
                    col = 0;
                    row++;

                }
            }
        }
    }


    public void initialize() {
        bottone_aggiungialCarrello.setOnAction(event -> aggiungialCarrello());
        bottone_visualizzaCarrello.setOnAction(event -> visualizzaCarrello());
        bottone_visualizzaOrdini.setOnAction(event -> visualizzaOrdini());
        bottone_visualizzaInfoAccount.setOnAction(event -> visualizzaInfoAccount());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());
        bottone_esciaccountutente.setOnAction(this::esciaccountutente);
    }


    private void visualizzaOrdini() {
        Azioni.visualizzaOrdini(bottone_visualizzaOrdini, utente);

    }

    private void esciaccountutente(ActionEvent event) {
        try {
            WelcomeController indietro = new WelcomeController();
            indietro.VaiIndietro(event);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    private void visualizzaListaProdotti() {
        Azioni.visualizzaListaProdotti(bottone_visualizzaListaProdotti, utente);
    }

    private void visualizzaInfoAccount() {
        Azioni.visualizzaInfoAccount(bottone_visualizzaInfoAccount, utente);
    }

    private void visualizzaCarrello() {
        Azioni.visualizzaCarrello(bottone_visualizzaCarrello, utente);

    }

    private void aggiungialCarrello() {
        Azioni.aggiungialCarrello(bottone_aggiungialCarrello, utente);
    }

}
