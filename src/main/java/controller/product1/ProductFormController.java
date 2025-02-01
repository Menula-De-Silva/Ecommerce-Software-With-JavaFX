package controller.product1;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ProductFormController {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSize;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtStock;

    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TableColumn<Product, String> colSize;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colStock;

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
            JOptionPane.showMessageDialog(null, "Error loading home page: " + e.getMessage());
        }
    }

    @FXML
    void btnAddonAction(ActionEvent event) {
        // Add product logic (implementation required)
        System.out.println("Add button clicked.");
    }

    @FXML
    void btnDeleteonAction(ActionEvent event) {
        // Delete product logic (implementation required)
        System.out.println("Delete button clicked.");
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        try {
            // Set up the table column mappings
            colId.setCellValueFactory(new PropertyValueFactory<>("productID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

            // Load product data into the table
            ObservableList<Product> productObservableList = FXCollections.observableArrayList();
            List<Product> productList = new ProductController().getAll(); // Ensure this method exists
            productObservableList.addAll(productList);

            tblProducts.setItems(productObservableList);
            System.out.println("Product table reloaded.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reloading products: " + e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            // Creating a Product object based on input fields
            Product product = new Product(
                    txtId.getText(),
                    txtName.getText(),
                    txtCategory.getText(),
                    txtSize.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
            );
            boolean isUpdated = updateProduct(product);
            if (isUpdated) {
                JOptionPane.showMessageDialog(null, "Update Success");
            } else {
                JOptionPane.showMessageDialog(null, "Update Failed");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver not found...");
        }
    }

    public static boolean updateProduct(Product product) throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "UPDATE products SET productname=?, category=?, size=?, price=?, stock=? WHERE productid=?"
        );
        stm.setObject(1, product.getProductName());
        stm.setObject(2, product.getCategory());
        stm.setObject(3, product.getSize());
        stm.setObject(4, product.getPrice());
        stm.setObject(5, product.getStock());
        stm.setObject(6, product.getProductID());
        return stm.executeUpdate() > 0;
    }

    @FXML
    private void Search(ActionEvent evt) {


    }

    @FXML
    private void idTextActionPerformed(ActionEvent evt) {
        try {
            Product product = searchProduct(txtId.getText());
            if (product != null) {
                txtName.setText(product.getProductName());
                txtPrice.setText(String.valueOf(product.getPrice()));
                txtCategory.setText(product.getCategory());
                txtSize.setText(product.getSize());
                txtStock.setText(String.valueOf(product.getStock()));
            } else {
                JOptionPane.showMessageDialog(null, "Product not found...");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver not found...");
        }
    }

    public static Product searchProduct(String id) throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM products WHERE productid=?"
        );
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            Product product = new Product(
                    rst.getString("productid"),
                    rst.getString("productname"),
                    rst.getString("category"),
                    rst.getString("size"),
                    rst.getDouble("price"),
                    rst.getInt("stock")
            );
            return product;
        }
        return null;
    }
}
