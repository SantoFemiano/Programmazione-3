package it.santosalvatore;
/*La classe ProductFactory è l'interfaccia del pattern Factory che contiene un metodo "createProduct" di tipo Product che viene implementato dalle classi concrete*/

public interface ProductFactory {
    Product createProduct(int id_Product, String name, double price, int amount, String color, String image);

}
