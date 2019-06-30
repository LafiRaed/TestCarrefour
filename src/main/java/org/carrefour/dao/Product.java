package org.carrefour.dao;

public class Product {


    private String  id_product ;

    private int qte ;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Product(String id_product, int qte) {
        this.id_product = id_product;
        this.qte = qte;
    }
}
