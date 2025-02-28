package it.santosalvatore;

/* ConcreteVenditoreFactory. Classe concreta del pattern Factory che implementa l'interfaccia VenditoreFactory. Effettua l'override del metodo createVenditore ritornando un Venditore concreto. */


public class ConcreteVenditoreFactory implements VenditoreFactory {
    public Venditore createVenditore(String username, String password) {
        return new ConcreteVenditore(username, password);
    }
}