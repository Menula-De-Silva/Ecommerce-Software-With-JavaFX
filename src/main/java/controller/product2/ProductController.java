package controller.product2;

import db.DBConnection;
import model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductController implements ProductService {

    @Override
    public boolean addProduct(Product product) {
        // Implementation for adding a product (optional)
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        // Implementation for updating a product (optional)
        return false;
    }

    @Override
    public Product searchProduct(String productID) {
        // Implementation for searching a product (optional)
        return null;
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        try {
            // Get a database connection
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            // Ensure table name matches the database schema
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Products");

            while (resultSet.next()) {
                // Adjust column names and types if needed
                Product product = new Product(
                        resultSet.getString("ProductID"),          // Column: ID (int)
                        resultSet.getString("ProductName"),     // Column: Name (String)
                        resultSet.getString("Category"), // Column: Category (String)
                        // Column: Category (String)
                        resultSet.getString("size"),
                        resultSet.getDouble("Price"),    // Column: Price (double)
                        resultSet.getInt("Stock")     // Column: Quantity (int)
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            // Detailed error logging
            System.err.println("Error fetching products: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error fetching products: " + e.getMessage(), e);
        }
        return productList;
    }

    @Override
    public boolean deleteProduct(String productID) {
        // Implementation for deleting a product by String ID (optional)
        return false;
    }

    @Override
    public boolean deleteProduct(Integer productID) {
        // Implementation for deleting a product by Integer ID (optional)
        return false;
    }
}