package it.santosalvatore;

/* Ordini Observer. E' un interfaccia che permette l'implementazione del metodo "updateordini" */
public interface OrdiniObserver {
    String updateOrdini(Venditore venditore, String message);
}
