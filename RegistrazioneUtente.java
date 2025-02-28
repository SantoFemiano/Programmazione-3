package it.santosalvatore;

/*La classe gestisce il processo di registrazione degli utenti nel sistema.*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegistrazioneUtente {
    private static final String SELECT_ALL_UTENTI = "SELECT * FROM utenti WHERE username = ?;";
    private final List<Utente> listaUtenti;
    private final UtenteFactory utenteFactory = new ConcreteUtenteFactory();
    private final GestoreDatabase database = new GestoreDatabase();
    private Utente nuovoUtente;


    public RegistrazioneUtente(List<Utente> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }


    // Gestisce il processo di registrazione di un nuovo utente.
    public Utente datiRegistrazione(String username, String password, String indirizzo, String email, String numtel) {
        if (userExists(username)) {
            return null;
        } else {
            nuovoUtente = utenteFactory.createUtente(username, password, indirizzo, email, numtel);
            listaUtenti.add(nuovoUtente);
            database.setUtenteDB(nuovoUtente, listaUtenti);
            return nuovoUtente;
        }


    }

    //Il metodo UserExists verifica se l'utente esiste già nel database utilizzando una query SQL parametrica
    private boolean userExists(String username) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_UTENTI)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Restituisce true se l'utente esiste, altrimenti false
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // In caso di errore, ritorna false
        }
    }

}
