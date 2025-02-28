package it.santosalvatore;

/* ConcreteOrdiniObserver. E' una classe concreta(che implementa l'interfaccia OrdiniObserver) che permette di specificare il tipo di utente "venditore" che "osserverà i subject (ovservable)"
e sovrascrive il metodo "updateordini" facendo Ovveride*/

public class ConcreteOrdiniObserver implements OrdiniObserver {
    private final Venditore venditore;

    public ConcreteOrdiniObserver(Venditore venditore) {
        this.venditore = venditore;
    }

    @Override
    public String updateOrdini(Venditore venditore, String message) {
        String notificavenditore = (venditore.getUsername() + " ha ricevuto la notifica: Acquistato/i prodotto/i" +message);
        return notificavenditore;
    }

}
