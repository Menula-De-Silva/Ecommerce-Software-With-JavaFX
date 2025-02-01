package controller.product;

import model.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product Product);
    boolean updateProduct(Product Product);
    Product searchProduct(String ProductID);
    List<Product> getAll();
    boolean deleteProduct(String ProductID);

    boolean deleteProduct(Integer productID);
}