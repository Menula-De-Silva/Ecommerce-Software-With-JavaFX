package model;

public class Product {
    private String productID;
    private String productName;
    private String category;
    private String size;
    private double price;
    private int stock;

    public Product(String productID, String productName, String category, String text, double price, int stock) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.size = size;
        this.price = price;
        this.stock = stock;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() { // Ensure this returns Integer, not String
        return stock;
    }
}