package controller.order;

import javafx.beans.property.*;

public class OrderItem {

    private StringProperty itemCode;
    private StringProperty description;
    private IntegerProperty quantity;
    private DoubleProperty unitPrice;
    private DoubleProperty total;

    public OrderItem(String itemCode, String description, int quantity, double unitPrice, double total) {
        this.itemCode = new SimpleStringProperty(itemCode);
        this.description = new SimpleStringProperty(description);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
        this.total = new SimpleDoubleProperty(total);  // Set total explicitly
    }

    // Getters for properties
    public StringProperty itemCodeProperty() { return itemCode; }
    public String getItemCode() { return itemCode.get(); }

    public StringProperty descriptionProperty() { return description; }
    public String getDescription() { return description.get(); }

    public IntegerProperty quantityProperty() { return quantity; }
    public int getQuantity() { return quantity.get(); }

    public DoubleProperty unitPriceProperty() { return unitPrice; }
    public double getUnitPrice() { return unitPrice.get(); }

    public DoubleProperty totalProperty() { return total; }
    public double getTotal() { return total.get(); }

    // Setters for properties (optional if needed for binding)
    public void setItemCode(String itemCode) { this.itemCode.set(itemCode); }
    public void setDescription(String description) { this.description.set(description); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setUnitPrice(double unitPrice) { this.unitPrice.set(unitPrice); }
    public void setTotal(double total) { this.total.set(total); }
}