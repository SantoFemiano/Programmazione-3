package it.santosalvatore;

/* ConcreteVenditore. Classe concreta che implementa l'interfaccia Venditore. Questa classe contiene gli attributi e i metodi(costruttore,metodi set e get) di un venditore concreto */

public class ConcreteVenditore implements Venditore {
    private String username;
    private String password;

    public ConcreteVenditore(String username, String password) {
        this.username = username;
        this.password = password;

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


}


