package it.santosalvatore;

// Interfaccia del prodotto che contiene: attributi e metodi set e get.
public interface Product {

    int getId_Product();

    void setId_Product(int idProduct);

    String getName();

    void setName(String Name);

    double getPrice();

    void setPrice(double price);

    int getAmount();

    void setAmount(int amount);

    String getColor();

    void setColor(String color);

    String getImage();

    void setImage(String image);

}
