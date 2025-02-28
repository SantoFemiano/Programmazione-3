package it.santosalvatore;
 /*La classe loginUtente gestisce il processo di accesso degli utenti all'interno del sistema */
import java.util.List;

public class LoginUtente {
    private final List<?> listaUtenti;

    public LoginUtente(List<?> listaUtenti) {
        this.listaUtenti = listaUtenti;

    }

    //Il metodo EffettuaLogin(String username, String password) tenta di effettuare l'accesso confrontando le credenziali fornite con quelle degli utenti presenti nella lista.
    public Object EffettuaLogin(String username, String password) {

        for (Object utentegenerale : listaUtenti) {
            if (utentegenerale instanceof Venditore utentevenditore) {

                if (username.equals(utentevenditore.getUsername())) {
                    if (password.equals(utentevenditore.getPassword())) {

                        return utentevenditore;
                    } else {

                        return null;
                    }
                }
            } else if (utentegenerale instanceof Utente utentecliente) {

                if (username.equals(utentecliente.getUsername())) {
                    if (password.equals(utentecliente.getPassword())) {

                        return utentecliente;
                    } else {

                        return null;
                    }
                }

            }
        }
        return null;
    }
}
