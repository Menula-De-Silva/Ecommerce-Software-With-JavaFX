package controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.UserSession;

public class home {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnaddcustomer;

    @FXML
    private JFXButton btnaddproduct;

    @FXML
    private JFXButton btnorderhistory;

    @FXML
    private JFXButton btnplaceorder;

    @FXML
    private JFXButton btnreportmanager;

    @FXML
    private JFXButton btnupdatecustomer;

    @FXML
    private JFXButton btnupdateproduct;

    @FXML
    private JFXButton btnviewcustomer;

    @FXML
    private JFXButton btnviewproduct;

    @FXML
    private ImageView imgbanner;

    @FXML
    private ImageView imguserprofile;

    @FXML
    private JFXButton orderbtn;

    @FXML
    void addcustomer(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_customer.fxml"));
            Parent newSceneRoot = loader.load();


            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading customerForm.fxml", e);
        }
        System.out.println("Add Customer Form");
    }

    @FXML
    void addproduct(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_product.fxml"));
            Parent newSceneRoot = loader.load();


            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading add product.fxml", e);
        }
        System.out.println("Add Product Form");
    }

    @FXML
    void orderaction(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/order.fxml"));
            Parent newSceneRoot = loader.load();


            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading add order.fxml", e);
        }
        System.out.println("Order Form");
    }

    @FXML
    void managereports(ActionEvent event) {
        System.out.println("Report Manager");
    }

    @FXML
    void orderhistory(ActionEvent event) {
        System.out.println("Order History");
    }

    @FXML
    void placeorder(ActionEvent event) {
        System.out.println("Place Order");
    }

    @FXML
    void showuser(MouseEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editUser.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading editUser.fxml", e);
        }
        System.out.println("Edit User Settings");
    }

    @FXML
    void updatecustomer(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_customer.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading update_customer.fxml", e);
        }
        System.out.println("Update Customer Form");
    }

    @FXML
    void updateproduct(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update_product.fxml"));
            Parent newSceneRoot = loader.load();


            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading add product.fxml", e);
        }
        System.out.println("Update Product Form");


    }

    @FXML
    void viewcustomer(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_customer.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading viewCustomer.fxml", e);
        }
        System.out.println("View Customer Form");
    }

    @FXML
    void viewproduct(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/view_product.fxml"));
            Parent newSceneRoot = loader.load();


            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading add product.fxml", e);
        }
        System.out.println("View Product Form");
    }



}