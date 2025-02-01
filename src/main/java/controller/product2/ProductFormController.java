package controller.product2;

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

import java.io.IOException;
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
    private TableColumn<Product, Integer> colStock;

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

    @FXML
    void btnAddonAction(ActionEvent event) {
        // Add product logic here
        System.out.println("Add button clicked.");
    }

    @FXML
    void btnDeleteonAction(ActionEvent event) {
        // Delete product logic here
        System.out.println("Delete button clicked.");
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        // Set up the table column mappings
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
        System.out.println("Product table reloaded.");
    }

    @FXML
    void btnSearchonAction(ActionEvent event) {
        // Search product logic here
        System.out.println("Search button clicked.");
    }
}