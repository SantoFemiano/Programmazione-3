package it.santosalvatore;

/* ConcreteUtenteFactory. Classe concreta del pattern Factory che implementa l'interfaccia UtenteFactory. Effettua l'override del metodo createUtente ritornando un Utente concreto. */

public class ConcreteUtenteFactory implements UtenteFactory {
    public Utente createUtente(String username, String password, String indirizzo, String email, String numerotel) {
        return new ConcreteUtente(username, password, indirizzo, email, numerotel);
    }
}
