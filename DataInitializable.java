package com.example.grafico;

/*Questa interfaccia consente di garantire un'interfaccia comune per l'inizializzazione dei dati dell'utente tra diversi controller delle schermate, migliorando la coerenza e la manutenibilità del codice.*/

import it.santosalvatore.Utente;

public interface DataInitializable {
    void initData(Utente utente);
}
