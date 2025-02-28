package com.example.grafico;

import it.santosalvatore.Product;
/*Questa interfaccia consente di garantire un'interfaccia comune per l'inizializzazione dei dati dell'utente tra diversi controller delle schermate, migliorando la coerenza e la manutenibilità del codice.*/

public interface DataInitializableSelectedProduct {
    void initData(Product product);
}
