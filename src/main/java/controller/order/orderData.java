package controller.order;

public class orderData {

    private static orderData instance;
    private String orderId;

    private orderData() {} // Private constructor

    public static orderData getInstance() {
        if (instance == null) {
            instance = new orderData();
        }
        return instance;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}