package it.santosalvatore;

import java.util.List;

public class GestoreCarrello {
    private final Utente utente;
    private List<Product> listaCarrello;
    private List<Product> listaProdotti;
    private List<Product> listaOrdini;
    private ProductFactory factory;


    public GestoreCarrello(List<Product> listaCarrello, List<Product> listaProdotti, List<Product> listaOrdini, Utente utente, List<Utente> listaUtenti) {
        this.listaCarrello = listaCarrello;
        this.listaProdotti = listaProdotti;
        this.listaOrdini = listaOrdini;
        this.utente = utente;
        this.factory = new ConcreteProductFactory();
    }


    public double calcolaTotaleCarrello(List<Product> listaCarrello) {
        double totale = 0;
        int moltiplicatore_quantità = 0;
        for (Product product : listaCarrello) {
            moltiplicatore_quantità = product.getAmount();
            totale += product.getPrice() * moltiplicatore_quantità;
        }
        return totale;
    }


}
