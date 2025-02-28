package it.santosalvatore;
/*La classe VenditoreFactory è l'interfaccia del pattern Factory che contiene un metodo "createVenditore" di tipo Venditore che viene implementato dalle classi concrete*/

public interface VenditoreFactory {
    Venditore createVenditore(String username, String password);


}