package com.example.grafico;
//La classe gestisce la logica di una schermata che visualizza una barra di avanzamento (ProgressBar) durante il processo di pagamento.
import it.santosalvatore.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class ProgressBarController implements DataInitializable {
    String avviso;

    Utente utente;
    @FXML
    private ProgressBar progressBar;
    private List<Product> listaProdotti;
    private List<Utente> listaUtenti;
    private List<Product> listaOrdini;
    private List<Product> listaCarrello;
    private List<Venditore> listaVenditori;
    private OrdiniObserver ordiniObserver;
    private GestoreDatabase database = new GestoreDatabase();
    private GestoreCarrello carrello;

    public void initialize() {
        // Metodo chiamato quando il controller viene inizializzato
        simulateProgress();

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

    private void simulateProgress() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (double progress = 0; progress <= 1.0; progress += 0.01) {
                    updateProgress(progress, 1.0);
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(event -> {
            for (Venditore venditore : this.listaVenditori) {
                for (Product product : this.listaCarrello) {

                    avviso = ordiniObserver.updateOrdini(venditore, product.getName() + " Quantita " + product.getAmount());
                    System.out.println(avviso);

                }

                Alert pagamentoEffettuato = new Alert(Alert.AlertType.WARNING);
                pagamentoEffettuato.setTitle("Avviso");
                pagamentoEffettuato.setHeaderText("Il pagamento è stato effettuato");
                pagamentoEffettuato.setContentText(avviso);
                pagamentoEffettuato.showAndWait();

                database.setAllOrdiniDB(utente, listaCarrello);

            }

            try {
                FXMLLoader schermoPrincipaleLoader = new FXMLLoader(getClass().getResource("Schermata_principale_cliente.fxml"));
                Parent principale = schermoPrincipaleLoader.load();

                ClienteController clienteController = schermoPrincipaleLoader.getController();
                clienteController.initData(utente);

                Stage stage = (Stage) progressBar.getScene().getWindow();
                Scene scene = new Scene(principale);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread thread = new Thread(task);
        thread.start();
    }
}

