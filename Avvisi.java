package com.example.grafico;

/*La classe è responsabile per la visualizzazione di messaggi di avviso all'utente*/

import it.santosalvatore.Product;
import javafx.scene.control.Alert;

public class Avvisi {

    public void AvvisoConSelectedProduct(Product selectedProduct, int quantita, String acquistoOcarrelloHeader, String acquistoOcarrelloText) {
        Alert inseritoNelCarrello = new Alert(Alert.AlertType.WARNING);
        inseritoNelCarrello.setTitle("Avviso");
        inseritoNelCarrello.setHeaderText(acquistoOcarrelloHeader);
        inseritoNelCarrello.setContentText(acquistoOcarrelloText + ":\n\n Nome:" + selectedProduct.getName() + "\n Prezzo" + selectedProduct.getPrice() + "\n Quantità" + quantita + "\n Colore" + selectedProduct.getColor());
        inseritoNelCarrello.showAndWait();
    }

    public void AvvisoSenzaSelectedProduct(String headerAvviso, String textAvviso) {
        Alert carrelloVuoto = new Alert(Alert.AlertType.WARNING);
        carrelloVuoto.setTitle("Avviso");
        carrelloVuoto.setHeaderText(headerAvviso);
        carrelloVuoto.setContentText(textAvviso);
        carrelloVuoto.showAndWait();
    }


}
