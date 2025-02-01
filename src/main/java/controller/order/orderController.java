
package controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.bill.billcontroller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Product;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class orderController implements Initializable {

    @FXML
    private TableColumn<OrderItem, String> colitemcode;

    @FXML
    private TableColumn<OrderItem, String> colname;

    @FXML
    private TableColumn<OrderItem, Integer> colqty;

    @FXML
    private TableColumn<OrderItem, Double> colunitprice;

    @FXML
    private TableColumn<OrderItem, Double> coltotal;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXButton btnaddtocart;

    @FXML
    private JFXButton btndelete;

    @FXML
    private JFXButton btnplaceorder;

    @FXML
    private JFXComboBox<String> customerid;

    @FXML
    private JFXTextField customername;

    @FXML
    private Label lbldate;

    @FXML
    private JFXComboBox<String> itemcode;

    @FXML
    private JFXTextField orderid;

    @FXML
    private JFXTextField inqty;

    @FXML
    private JFXTextField stock;

    @FXML
    private TableView<OrderItem> tblproducts;

    @FXML
    private Label time;

    @FXML
    private JFXTextField unitprice;

    @FXML
    private Label lbltotal;

    @FXML
    private Label lblcusname;

    @FXML
    private Label lblproname;

    @FXML
    void backtohome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent newSceneRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading home.fxml", e);
        }
    }

    @FXML
    void btnaddtocartaction(ActionEvent event) {
        String selectedItemCode = itemcode.getSelectionModel().getSelectedItem();
        if (selectedItemCode == null || selectedItemCode.isEmpty()) {
            System.out.println("Please select an item.");
            return;
        }

        int orderQty;
        double unitPriceValue;

        try {
            orderQty = Integer.parseInt(inqty.getText());
            unitPriceValue = Double.parseDouble(unitprice.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity or unit price.");
            return;
        }

        double total = unitPriceValue * orderQty;
        int existingRow = isAlreadyExists(selectedItemCode);

        // Get product name from label
        String productName = lblproname.getText();  // This should have been set when itemcode changed

        if (existingRow == -1) {
            // If the item is new, add it to the TableView
            OrderItem newItem = new OrderItem(selectedItemCode, productName, orderQty, unitPriceValue, total);
            tblproducts.getItems().add(newItem);
        } else {
            // If the item already exists, update the quantity and total
            OrderItem existingItem = tblproducts.getItems().get(existingRow);
            int newQty = existingItem.getQuantity() + orderQty;
            double newTotal = newQty * unitPriceValue;

            existingItem.setQuantity(newQty);
            existingItem.setTotal(newTotal);

            tblproducts.refresh(); // Refresh TableView to update changes
        }

        inqty.clear();
        calculateTotal();
    }


    @FXML
    void placeorderonaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bill.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the controller for bill.fxml
            billcontroller billController = loader.getController();

            // Pass the order ID to the bill controller
            if (orderid != null) {
                billController.setOrderId(orderid.getText());
            } else {
                System.out.println("Error: Order ID field is null");
            }

            if (customername != null) {
                billController.setCustomerName(customername.getText());
            } else {
                System.out.println("Error: Customer Name field is null");
            }

            if (lbltotal != null) {
                billController.setTotal(lbltotal.getText());
            } else {
                System.out.println("Error :(");
            }

            // Pass the order items from tblproducts to billController's tblproducts
            ObservableList<OrderItem> orderItems = tblproducts.getItems();
            billController.setOrderItems(orderItems);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading bill.fxml", e);
        }
    }

    @FXML
    private void delete(ActionEvent event) {

        OrderItem selectedItem = tblproducts.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        tblproducts.getItems().remove(selectedItem);
        calculateTotal();

    }

    @FXML
    private void productidchanged(ActionEvent event) {
        try {
            String selectedProductId = itemcode.getSelectionModel().getSelectedItem(); // Get selected product ID

            if (selectedProductId != null) {
                Product product = controller.searchProduct(selectedProductId); // Fetch product details

                if (product != null) {
                    lblproname.setText(String.valueOf(product.getProductName()));
                    inqty.setText("1");
                    unitprice.setText(String.valueOf(product.getPrice()));
                    stock.setText(String.valueOf(product.getStock()));// Set unit price from product details
                } else {
                    System.out.println("Product not found.");
                    unitprice.clear();
                }
            } else {
                System.out.println("No product selected.");
                unitprice.clear();
            }
       } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print error for debugging
        }
    }

    @FXML
    private void cmbCustomerIdItemStateChanged(ActionEvent event) {
        try {
            String customerId = customerid.getSelectionModel().getSelectedItem(); // Get selected customer ID
            if (customerId != null) {
                Customer customer = controller.searchCustomer(customerId); // Fetch customer details
                if (customer != null) {
                    lblcusname.setText(customer.getName());
                    customername.setText(customer.getName()); // Set customer name
                    address.setText(customer.getAddress()); // Set customer address
                } else {
                    System.out.println("Customer not found.");
                    customername.clear();
                    address.clear();
                }
            } else {
                System.out.println("No customer selected.");
                customername.clear();
                address.clear();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print error for debugging
        }
    }

    private void customerIds(){
            try {
                customerid.getItems().addAll(controller.getAllCustomerIds());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace(); // Print error for debugging
            }
    }

    private void loadAllItemCodes(){
        try {
            itemcode.getItems().addAll(controller.loadAllItemCodes());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Print error for debugging
        }

    }

    private void setOrderId(){
        try {
            String lastOrderId = controller.getLastOrderId();
            if (lastOrderId != null) {
                lastOrderId = lastOrderId.split("[A-Z]")[1]; // D001 ==> 001
                System.out.println(lastOrderId);
                lastOrderId = String.format("D%03d", (Integer.parseInt(lastOrderId) + 1));
                orderid.setText(lastOrderId);
            } else {
                orderid.setText("D001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Log the exception
        }
    }

    private void setdateandtime() {
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String format = dateFormat.format(date);
            lbldate.setText(format);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String timeString = timeFormat.format(date);
            time.setText(timeString);
        } catch (NullPointerException e) {
            System.err.println("FXML components not linked properly: " + e.getMessage());
        }
    }

    private void updateTime() {
        // Create a timeline to update time every second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> setdateandtime()) // Call setdateandtime every second
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Keep the timeline running indefinitely
        timeline.play(); // Start the timeline
    }

    private int isAlreadyExists(String code) {
        for (int i = 0; i < tblproducts.getItems().size(); i++) {
            OrderItem item = tblproducts.getItems().get(i); // Get item from TableView
            if (item.getItemCode().equals(code)) {
                return i; // Return the index where the item exists
            }
        }
        return -1; // Item not found
    }

    private void calculateTotal() {
        double total = 0;

        for (OrderItem item : tblproducts.getItems()) { // Get each OrderItem
            total += item.getTotal(); // Use getter method to get the total price
        }

        lbltotal.setText(String.format("%.2f", total)); // Format to 2 decimal places
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setdateandtime();
        updateTime(); // Start updating the time
        customerIds();
        loadAllItemCodes();
        setOrderId();
        calculateTotal();

        colitemcode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemCode()));
        colname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        colqty.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colunitprice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getUnitPrice()).asObject());
        coltotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject());


    }
}