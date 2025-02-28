package com.example.grafico;
//La classe CarrelloController gestisce la logica di una schermata per gestire il carrello.


import it.santosalvatore.GestoreDatabase;
import it.santosalvatore.Product;
import it.santosalvatore.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.util.List;
import java.util.Optional;

public class CarrelloController implements DataInitializable {

    @FXML
    public Button bottone_acquista;
    ListaAvvisi avvisi = new ListaAvvisi();
    AzioniCliente Azioni = new AzioniCliente();
    GestoreDatabase database = new GestoreDatabase();
    private ContextMenu contextMenu;
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
    private ListView<Product> Listaview_carrello;
    private Utente utente;
    private List<Utente> listaUtenti;
    private List<Product> listaOrdini;
    private List<Product> listaProdotti;
    private List<Product> listaCarrello;

    public void initialize() {
        bottone_aggiungialCarrello.setOnAction(event -> aggiungialCarrello());
        bottone_visualizzaCarrello.setOnAction(event -> visualizzaCarrello());
        bottone_visualizzaOrdini.setOnAction(event -> visualizzaOrdini());
        bottone_visualizzaInfoAccount.setOnAction(event -> visualizzaInfoAccount());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());
        bottone_acquista.setOnAction(event -> acquista());
        bottone_indietro.setOnAction(event -> indietro());


        Listaview_carrello.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Product selectedProduct = Listaview_carrello.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    System.out.println("Hai cliccato su: " + selectedProduct.getName());
                    // Esegui altre azioni desiderate...
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                Product selectedProduct = Listaview_carrello.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    if (contextMenu != null && contextMenu.isShowing()) {
                        contextMenu.hide(); // Nasconde il menu se è già visualizzato
                    }

                    contextMenu = new ContextMenu();
                    // MenuItem buyOnlyItem = new MenuItem("Vuoi comprare solo questo");
                    MenuItem modifyItem = new MenuItem("Modifica Quantità");
                    MenuItem deleteItem = new MenuItem("Elimina dal carrello");
                    MenuItem cancelItem = new MenuItem("Annulla");


                    modifyItem.setOnAction(e -> {

                        // Creazione di una finestra di dialogo per la modifica della quantità
                        TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedProduct.getAmount()));
                        dialog.setTitle("Modifica Quantità");
                        dialog.setHeaderText(null);
                        dialog.setContentText("Inserisci la nuova quantità:");

                        // Mostra la finestra di dialogo e aspetta che l'utente inserisca la quantità
                        Optional<String> result = dialog.showAndWait();

                        // Gestisci la risposta dell'utente
                        result.ifPresent(newQuantity -> {
                            try {
                                // Converti la stringa in un numero intero
                                int quantity = Integer.parseInt(newQuantity);

                                // Aggiorna la quantità dell'ordine selezionato
                                boolean inserito = database.modificaOneCarrelloDB(selectedProduct, utente, quantity);
                                if (!inserito) {
                                    avvisi.quantitaMaggiore();
                                } else {
                                    avvisi.avvisoAcquistoSuccesso(selectedProduct, quantity);

                                    Azioni.visualizzaCarrello(bottone_visualizzaCarrello, utente);

                                }

                                // Esegui ulteriori azioni, ad esempio, aggiorna la visualizzazione o il database

                            } catch (NumberFormatException ex) {
                                // Gestisci l'eccezione se l'utente ha inserito un valore non valido
                                System.out.println("Inserire un numero valido per la quantità.");
                            }
                        });

                    });


                    cancelItem.setOnAction(e -> {
                    });

                    deleteItem.setOnAction(e -> {
                        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
                        conferma.setTitle("Cancella da carrello");
                        conferma.setHeaderText("Dettagli dell'acquisto:\n\n Nome:" + selectedProduct.getName() + "\n Prezzo" + selectedProduct.getPrice() + "\n Quantità" + selectedProduct.getAmount() + "\n Colore" + selectedProduct.getColor());
                        conferma.setContentText("Vuoi cancellarlo dal carrello?");

                        conferma.showAndWait().ifPresent(response -> {
                            if (response == javafx.scene.control.ButtonType.OK) {

                                database.clearOneCarrelloDB(selectedProduct, utente); // TRIGGER SQL AGGIORNERANNO GLI ALTRI DATABASE

                                Azioni.visualizzaCarrello(bottone_visualizzaCarrello, utente);
                            }


                        });

                    });

                    contextMenu.getItems().addAll(modifyItem, cancelItem);
                    contextMenu.show(Listaview_carrello, event.getScreenX(), event.getScreenY());
                }
            }
        });


    }


    private void acquista() {

        if (listaCarrello.isEmpty()) {
            avvisi.carrelloVuoto();
        } else {


            StringBuilder testoLista = new StringBuilder();
            double totale = 0;

            for (Product product : listaCarrello) {
                testoLista.append(", Nome: ").append(product.getName())
                        .append(", Prezzo: ").append(product.getPrice())
                        .append(", Quantità: ").append(product.getAmount())
                        .append(", Colore: ").append(product.getColor())
                        .append("\n");
                totale += product.getPrice() * product.getAmount(); // Calcolo del totale basato sui prezzi e quantità
            }


            Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
            conferma.setTitle("Scelta pagamento");
            conferma.setHeaderText("Dettagli dell'acquisto:\n\n" + testoLista + "\nTotale: " + calcolaTotaleCarrello(listaCarrello) + "$");
            conferma.setContentText("Vuoi andare al menu scelta metodo di pagamento?");

            conferma.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    Azioni.vaiSceltaPagamento(bottone_visualizzaCarrello, utente);

                } else {

                }
            });


            System.out.println("Somma Totale da Pagare:" + calcolaTotaleCarrello(listaCarrello));
            System.out.println("In attesa di scelta metodo di pagamento");


        }
    }

    public double calcolaTotaleCarrello(List<Product> listaCarrello) {
        double totale = 0;
        int moltiplicatore_quantita = 0;
        for (Product product : listaCarrello) {
            moltiplicatore_quantita = product.getAmount();
            totale += product.getPrice() * moltiplicatore_quantita;
        }
        return totale;
    }

    public void initData(Utente utente) {
        this.utente = utente;
        if (listaOrdini != null) {
            listaUtenti.clear();
            listaCarrello.clear();
            listaProdotti.clear();
            listaOrdini.clear();
        }
        listaUtenti = database.getAllUtentiDB();
        listaCarrello = database.getAllCarrelloDB(utente);
        listaProdotti = database.getAllProductsDB();
        listaOrdini = database.getAllOrdiniDB(utente);
        if (listaCarrello.isEmpty()) {
            System.out.println("lista vuota");
        }

        ObservableList<Product> observableListOrdini = FXCollections.observableArrayList();
        Listaview_carrello.setCellFactory(param -> new OrderListCell());

        observableListOrdini.addAll(listaCarrello);
        Listaview_carrello.setItems(observableListOrdini);

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