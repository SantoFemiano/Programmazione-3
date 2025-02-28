package it.santosalvatore;

// Interfaccia del Utente che contiene: attributi e metodi set e get.

public interface Utente {
    String getUsername();

    void setUsername(String Username);

    String getPassword();

    void setPassword(String Password);

    String getIndirizzo();

    void setIndirizzo(String Indirizzo);

    String getEmail();

    void setEmail(String Email);

    String getNumeroTel();

    void setNumeroTel(String NumeroTel);
}
