package controller.customer3;

import controller.customer.CustomerController;
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
import model.Customer;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController extends Component {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    void backtohome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent mainPageRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainPageRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean addCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?)");
        stm.setObject(1, customer.getId());
        stm.setObject(2, customer.getName());
        stm.setObject(3, customer.getAddress());
        stm.setObject(4, customer.getSalary());
        return stm.executeUpdate() > 0;
    }

    @FXML
    void btnAddonAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        try {
            double salary = Double.parseDouble(txtSalary.getText());
            Customer customer = new Customer(id, name, address, salary);
            boolean isAdded = addCustomer(customer);

            if (isAdded) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer Added Successfully!");
                loadTable();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid salary!");
        } catch (SQLException | ClassNotFoundException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    @FXML
    void btnDeleteonAction(ActionEvent event) {
        Customer selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a customer to delete!");
            return;
        }

        try {
            boolean isDeleted = deleteCustomer(selectedCustomer.getId());
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer Deleted Successfully!");
                loadTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Deletion Failed", "Could not delete customer!");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
        }
    }

    public static boolean deleteCustomer(String id) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("DELETE FROM Customer WHERE id = ?");
        stm.setString(1, id);
        return stm.executeUpdate() > 0;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        List<Customer> all = new CustomerController().getAll();
        customerObservableList.addAll(all);
        tblCustomers.setItems(customerObservableList);
    }

    @FXML
    private void btnSearchonAction(ActionEvent event){

    }


}
