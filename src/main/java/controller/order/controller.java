package controller.order;

import db.DBConnection;
import model.Customer;
import model.Product;

import java.sql.*;
import java.util.ArrayList;

public class controller {

    public static ArrayList<String> getAllCustomerIds() throws ClassNotFoundException, SQLException {

        ResultSet rst  = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT id FROM Customer")
                .executeQuery();
        ArrayList<String> idSet= new ArrayList<>();
        System.out.println();
        while (rst.next()) {
            idSet.add(rst.getString(1));
        }
        return idSet;
    }


    public static ArrayList<String> loadAllItemCodes() throws SQLException, ClassNotFoundException{
        ResultSet set = DBConnection.getInstance().getConnection().prepareStatement("SELECT productid FROM products").executeQuery();
        ArrayList<String> ids = new ArrayList<>();
        while (set.next()) {
            ids.add(set.getString(1));
        }
        return ids;
    }

    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return rst.next() ? rst.getString("id") : null;
    }

    public static Customer searchCustomer(String id) throws ClassNotFoundException, SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE id=?");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        if(rst.next()){
            Customer customer=new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
            return customer;
        }
        return null;
    }

    public static Product searchProduct(String id) throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement("SELECT productid, productname, category, size, price, stock FROM products WHERE productid=?");
        stm.setString(1, id); // Set product ID
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
        return null; // Return null if no product is found
    }

}
