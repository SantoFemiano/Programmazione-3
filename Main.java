package it.santosalvatore;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //avvio connessione al database e inizializza le strutture per l'avvio
        Connection connection = DatabaseConnector.getConnection();
        GestoreDatabase database = new GestoreDatabase();

        List<Utente> listaUtenti = new ArrayList<>();
        List<Venditore> listaVenditore = new ArrayList<>();
        database.getAllUtentiDB(listaUtenti);
        database.getAllVenditoriDB(listaVenditore);

    }
}


