package it.santosalvatore;
/* ConcreteProductFactory. Classe concreta del pattern Factory che implementa l'interfaccia ProductFactory. Effettua l'override del metodo createProduct ritornando un prodotto concreto. */
public class ConcreteProductFactory implements ProductFactory {
    @Override
    public Product createProduct(int Id_Product, String name, double price, int amount, String color, String image) {
        return new ConcreteProduct(Id_Product, name, price, amount, color, image);
    }
}
