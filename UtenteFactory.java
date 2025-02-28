package it.santosalvatore;
/*La classe UtenteFactory è l'interfaccia del pattern Factory che contiene un metodo "createUtente" di tipo Utente che viene implementato dalle classi concrete*/

public interface UtenteFactory {
    Utente createUtente(String username, String password, String indirizzo, String email, String numerotel);

}