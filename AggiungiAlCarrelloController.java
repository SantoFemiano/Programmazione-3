package com.example.grafico;

/*La classe AggiungiAlCarrelloController rappresenta un controller per la vista che consente agli utenti di visualizzare i prodotti disponibili e aggiungerli al proprio carrello.
* Gestisce gli eventi dei pulsanti nella vista, come il click sul pulsante "Aggiungi al carrello", "Visualizza Carrello", "Visualizza Ordini", "Visualizza Info Account", "Visualizza Lista Prodotti" e "Indietro"
Quando un prodotto viene selezionato nella lista dei prodotti disponibili, viene mostrato un dialog che consente all'utente di inserire la quantità desiderata per l'acquisto.
L'utente può scegliere di aggiungere il prodotto al carrello o acquistarlo immediatamente tramite il menu contestuale che appare con un click destro sul prodotto.
 */
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


public class AggiungiAlCarrelloController implements DataInitializable {
    ListaAvvisi avvisi = new ListaAvvisi();

    AzioniCliente Azioni = new AzioniCliente();
    GestoreDatabase database = new GestoreDatabase();
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
    private ListView<Product> Listaview_listaProdotti;
    private Utente utente;
    private List<Product> listaProdotti;
    private ContextMenu contextMenu;


    public void initialize() {
        bottone_aggiungialCarrello.setOnAction(event -> aggiungialCarrello());
        bottone_visualizzaCarrello.setOnAction(event -> visualizzaCarrello());
        bottone_visualizzaOrdini.setOnAction(event -> visualizzaOrdini());
        bottone_visualizzaInfoAccount.setOnAction(event -> visualizzaInfoAccount());
        bottone_visualizzaListaProdotti.setOnAction(event -> visualizzaListaProdotti());
        bottone_indietro.setOnAction(event -> indietro());
        Listaview_listaProdotti.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                Product selectedProduct = Listaview_listaProdotti.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    Dialog<Integer> dialog = new Dialog<>();
                    dialog.setTitle("Aggiungi al carrello");
                    dialog.setHeaderText("Dettagli dell'acquisto:\n\n Nome:" + selectedProduct.getName() + "\n Prezzo" + selectedProduct.getPrice() + "\n Quantità" + selectedProduct.getAmount() + "\n Colore" + selectedProduct.getColor());

                    // Aggiungi campo di input per la quantità al dialog
                    TextField quantityField = new TextField();
                    quantityField.setPromptText("Inserisci la quantità");
                    dialog.getDialogPane().setContent(quantityField);

                    // Aggiungi bottoni Conferma e Annulla al dialog
                    ButtonType confermaButton = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(confermaButton, ButtonType.CANCEL);

                    // Conferma la quantità quando l'utente preme il pulsante "Conferma"
                    dialog.setResultConverter(buttonType -> {
                        if (buttonType == confermaButton) {
                            try {
                                // Restituisci la quantità inserita come risultato del dialog
                                return Integer.parseInt(quantityField.getText());
                            } catch (NumberFormatException e) {
                                // Gestisci il caso in cui l'utente non inserisce un numero valido
                                return null;
                            }
                        }
                        return null;
                    });

                    Optional<Integer> result = dialog.showAndWait();
                    result.ifPresent(quantita -> {

                        if (database.setOneCarrelloAmountDB(utente, selectedProduct, quantita)) {

                            avvisi.avvisoCarrelloSuccesso(selectedProduct, quantita);

                            Azioni.visualizzaCarrello(bottone_visualizzaCarrello, utente);
                        } else {
                            avvisi.quantitaMaggiore();
                        }
                    });
                }
            } else if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                Product selectedProduct = Listaview_listaProdotti.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    if (contextMenu != null && contextMenu.isShowing()) {
                        contextMenu.hide(); // Nasconde il menu se è già visualizzato
                    }

                    contextMenu = new ContextMenu();
                    MenuItem buyNowItem = new MenuItem("Compra ora");
                    MenuItem cancelItem = new MenuItem("Annulla");

                    buyNowItem.setOnAction(e -> {

                        Dialog<Integer> dialog = new Dialog<>();
                        dialog.setTitle("Compra");
                        dialog.setHeaderText("Dettagli dell'acquisto:\n\n Nome:" + selectedProduct.getName() + "\n Prezzo" + selectedProduct.getPrice() + "\n Quantità" + selectedProduct.getAmount() + "\n Colore" + selectedProduct.getColor());

                        // Aggiungi campo di input per la quantità al dialog
                        TextField quantityField = new TextField();
                        quantityField.setPromptText("Inserisci la quantità");
                        dialog.getDialogPane().setContent(quantityField);

                        // Aggiungi bottoni Conferma e Annulla al dialog
                        ButtonType confermaButton = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().addAll(confermaButton, ButtonType.CANCEL);

                        // Conferma la quantità quando l'utente preme il pulsante "Conferma"
                        dialog.setResultConverter(buttonType -> {
                            if (buttonType == confermaButton) {
                                try {
                                    // Restituisci la quantità inserita come risultato del dialog
                                    return Integer.parseInt(quantityField.getText());
                                } catch (NumberFormatException a) {
                                    // Gestisci il caso in cui l'utente non inserisce un numero valido
                                    return null;
                                }
                            }
                            return null;
                        });

                        Optional<Integer> result = dialog.showAndWait();
                        result.ifPresent(quantita -> {
                            boolean riuscito = database.setOneOrdiniAmountDB(utente, selectedProduct, quantita);
                            if (!riuscito) {
                                avvisi.quantitaMaggiore();
                            } else {
                                avvisi.avvisoAcquistoSuccesso(selectedProduct, quantita);

                                Azioni.visualizzaCarrello(bottone_visualizzaCarrello, utente);
                            }
                        });
                    });


                    cancelItem.setOnAction(e -> System.out.println("cancel"));
                    contextMenu.getItems().addAll(buyNowItem, cancelItem);
                    contextMenu.show(Listaview_listaProdotti, event.getScreenX(), event.getScreenY());
                }
            }


        });
    }

    public void initData(Utente utente) {
        this.utente = utente;
        if (listaProdotti != null) {
            listaProdotti.clear();
        }
        listaProdotti = database.getProductsNonFinitiDB();

        ObservableList<Product> observableListOrdini = FXCollections.observableArrayList();
        Listaview_listaProdotti.setCellFactory(param -> new OrderListCell());

        observableListOrdini.addAll(listaProdotti);
        Listaview_listaProdotti.setItems(observableListOrdini);
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


