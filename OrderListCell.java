package com.example.grafico;
/*Questa classe OrderListCell estende ListCell<Product> e viene utilizzata per personalizzare la visualizzazione degli elementi all'interno di una lista di prodotti.
* In questo modo, ogni cella della lista visualizzerà le informazioni del prodotto insieme alla sua immagine associata, se disponibile. Questa personalizzazione migliora l'esperienza utente fornendo una rappresentazione visiva dei prodotti all'interno della lista.
*  */


import it.santosalvatore.Product;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


class OrderListCell extends javafx.scene.control.ListCell<Product> {

    private final ImageView imageView = new ImageView();

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);
        if (empty || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            VBox vBox = new VBox(5); // Spaziatura verticale tra le etichette
            vBox.getChildren().addAll(
                    new Label("ID: " + product.getId_Product()),
                    new Label("Name: " + product.getName()),
                    new Label("Price: €" + product.getPrice()),
                    new Label("Amount: " + product.getAmount()),
                    new Label("Color: " + product.getColor())
            );

            Image image;
            // Verifica se l'URL è un percorso file locale o un URL Internet

            // URL Internet
            image = new Image(product.getImage());


            imageView.setImage(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            HBox hbox = new HBox(10); // Spaziatura orizzontale tra l'immagine e le etichette
            hbox.getChildren().addAll(imageView, vBox);

            setGraphic(hbox);
        }
    }
}
