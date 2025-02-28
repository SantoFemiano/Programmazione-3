package it.santosalvatore;
/* Concrete Product. Classe concreta che implementa l'interfaccia Product. Questa classe contiene gli attributi e i metodi(costruttore,metodi set e get) di un prodotto concreto */
public class ConcreteProduct implements Product {
    private int Id_Product;
    private String name;
    private double price;
    private int amount;

    private String color;

    private String image;


    public ConcreteProduct(int Id_Product, String name, double price, int amount, String color, String image) {
        this.Id_Product = Id_Product;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.color = color;
        this.image = image;
    }


    @Override
    public int getId_Product() {
        return Id_Product;
    }

    public void setId_Product(int Id_Product) {
        this.Id_Product = Id_Product;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}

