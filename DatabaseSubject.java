package it.santosalvatore;

//La classe DatabaseSubject contiene una lista di osservatori (ordiniObservers), che verranno notificati quando avviene un evento di inserimento nella tabella degli ordini del database.

import java.util.ArrayList;
import java.util.List;

public class DatabaseSubject {
    private final List<OrdiniObserver> ordiniObservers = new ArrayList<>();

    //Il metodo addOrdiniObserver(OrdiniObserver observer) consente di aggiungere un osservatore alla lista degli osservatori.
    public void addOrdiniObserver(OrdiniObserver observer) {
        ordiniObservers.add(observer);
    }

    //Il metodo removeOrdiniObserver(OrdiniObserver observer) consente di rimuovere un osservatore dalla lista degli osservatori.
    public void removeOrdiniObserver(OrdiniObserver observer) {
        ordiniObservers.remove(observer);
    }

    //Il metodo notifyOrdiniObservers(Venditore venditore, String message) attraversa la lista degli osservatori e chiama il metodo updateOrdini di ciascun osservatore, passando il venditore e un messaggio come parametri.
    private void notifyOrdiniObservers(Venditore venditore, String message) {
        for (OrdiniObserver observer : ordiniObservers) {
            observer.updateOrdini(venditore, message);
        }
    }

    // Il metodo ordiniInsertDetected(Venditore venditore) è chiamato quando viene rilevato un inserimento nella tabella degli ordini del database.
    // Questo metodo notifica tutti gli osservatori registrati chiamando notifyOrdiniObservers con il venditore coinvolto e un messaggio indicanteche è stato inserito un nuovo ordine.
    public void ordiniInsertDetected(Venditore venditore) {
        String message = "Nuovo ordine inserito nella tabella 'ordini'!";
        notifyOrdiniObservers(venditore, message);
    }
}
