package it.santosalvatore;

/* ConcreteUtente. Classe concreta che implementa l'interfaccia Utente. Questa classe contiene gli attributi e i metodi(costruttore,metodi set e get) di un Utente concreto */


public class ConcreteUtente implements Utente {
    private String username;
    private String password;
    private String indirizzo;
    private String email;
    private String numerotel;

    public ConcreteUtente(String username, String password, String indirizzo, String email, String numerotel) {
        this.username = username;
        this.password = password;
        this.indirizzo = indirizzo;
        this.email = email;
        this.numerotel = numerotel;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getNumeroTel() {
        return numerotel;
    }

    public void setNumeroTel(String numerotel) {
        this.numerotel = numerotel;
    }


}
