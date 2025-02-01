package controller.product;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductFormController {

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, String> colId;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TableColumn<Product, String> colSize;

    @FXML
    private TableColumn<Product, Integer> colStock; // Ensure it's Integer

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtStock;

    @FXML
    void backtohome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent mainPageRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainPageRoot));
            stage.show();
            System.out.println("Navigated back to home.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean addProduct(Product product) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Products VALUES (?, ?, ?, ?, ?, ?)");
        stm.setString(1, product.getProductID());  // Ensure product ID is a String
        stm.setString(2, product.getProductName());
        stm.setString(3, product.getCategory());
        stm.setString(4, product.getSize());
        stm.setDouble(5, product.getPrice()); // Ensure price is a Double
        stm.setInt(6, product.getStock()); // Ensure stock is an Integer
        return stm.executeUpdate() > 0;
    }

    @FXML
    void btnAddonAction(ActionEvent event) {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String category = txtCategory.getText().trim();
        String size = txtSize.getText().trim();  // Ensure size is not null

        if (id.isEmpty() || name.isEmpty() || category.isEmpty() || size.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled!");
            return;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            Product product = new Product(id, name, category, size, price, stock);
            boolean isAdded = addProduct(product);

            if (isAdded) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product Added Successfully!");
                loadTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add product!");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numeric values for price and stock!");
        } catch (SQLException | ClassNotFoundException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    @FXML
    void btnDeleteonAction(ActionEvent event) {
        Product selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a product to delete!");
            return;
        }

        try {
            boolean isDeleted = deleteProduct(selectedProduct.getProductID());
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product Deleted Successfully!");
                loadTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Deletion Failed", "Could not delete product!");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    public static boolean deleteProduct(String id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("DELETE FROM Products WHERE productID = ?");
        stm.setString(1, id); // Ensure product ID is handled as a String
        return stm.executeUpdate() > 0;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Load product data into the table
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        List<Product> productList = new ProductController().getAll();
        productObservableList.addAll(productList);

        tblProducts.setItems(productObservableList);
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
        System.out.println("Product table reloaded.");
    }

    @FXML
    void btnSearchonAction(ActionEvent event) {
        System.out.println("Search button clicked.");
    }
}