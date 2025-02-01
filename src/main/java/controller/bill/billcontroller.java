package controller.bill;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.order.OrderItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class billcontroller implements Initializable {

    @FXML
    private TableColumn<OrderItem, String> colitemcodebill;

    @FXML
    private TableColumn<OrderItem, String> description;

    @FXML
    private TableColumn<OrderItem, Integer> colqtybill;

    @FXML
    private TableColumn<OrderItem, Double> colunitpricebill;

    @FXML
    private TableColumn<OrderItem, Double> coltotalbill;

    @FXML
    private Label lblbilltotal;

    @FXML
    private TableView<OrderItem> tblproducts;

    @FXML
    private Label txtcustomername;

    @FXML
    private Label txtdate;

    @FXML
    private Label txtorderid;

    @FXML
    private Label txttime;

    private String orderId;
    private String customerName;
    private String finalTotal;

    @FXML
    void backtohome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/order.fxml"));
            Parent newSceneRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading order.fxml", e);
        }
    }

    @FXML
    void btnprintaction(ActionEvent event) {
        String filePath = "Bills/Bill.pdf";

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(new File(filePath)));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            document.add(new Paragraph("Customer Bill").setBold().setFontSize(18));

            // Order details
            document.add(new Paragraph("Order ID: " + orderId));
            document.add(new Paragraph("Customer Name: " + customerName));
            document.add(new Paragraph("Date: " + txtdate.getText() + " | Time: " + txttime.getText()));
            document.add(new Paragraph("\n")); // Empty line

            // Create Table for Products
            Table table = new Table(5);
            table.addCell(new Cell().add(new Paragraph("Item Code")));
            table.addCell(new Cell().add(new Paragraph("Description")));
            table.addCell(new Cell().add(new Paragraph("Quantity")));
            table.addCell(new Cell().add(new Paragraph("Unit Price")));
            table.addCell(new Cell().add(new Paragraph("Total")));

            // Add Order Items (Wrap Strings in Paragraph)
            for (OrderItem item : tblproducts.getItems()) {
                table.addCell(new Cell().add(new Paragraph(item.getItemCode())));
                table.addCell(new Cell().add(new Paragraph(item.getDescription())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getUnitPrice()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getTotal()))));
            }

            document.add(table);
            document.add(new Paragraph("\nTotal: " + lblbilltotal.getText()).setBold().setFontSize(14));

            document.close();
            System.out.println("Bill saved as PDF: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error generating bill PDF.");
        }
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
        if (txtorderid != null) {
            txtorderid.setText(orderId);
        }
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
        if (txtcustomername != null) {
            txtcustomername.setText(customerName);
        }
    }

    public void setTotal(String finalTotal) {
        this.finalTotal = finalTotal;
        if (lblbilltotal != null) {
            lblbilltotal.setText(finalTotal);
        }
    }

    private void setDateAndTime() {
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = dateFormat.format(date);
            txtdate.setText(formattedDate);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedTime = timeFormat.format(date);
            txttime.setText(formattedTime);
        } catch (NullPointerException e) {
            System.err.println("FXML components not linked properly: " + e.getMessage());
        }
    }

    private void updateTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> setDateAndTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDateAndTime();
        updateTime();

        // Set column mappings
        colitemcodebill.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        colqtybill.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colunitpricebill.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        coltotalbill.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Update labels if data is already set before initialization
        if (orderId != null && txtorderid != null) {
            txtorderid.setText(orderId);
        }

        if (customerName != null && txtcustomername != null) {
            txtcustomername.setText(customerName);
        }

        if (finalTotal != null && lblbilltotal != null) {
            lblbilltotal.setText(finalTotal);
        }
    }

    public void setOrderItems(ObservableList<OrderItem> orderItems) {
        if (tblproducts != null) {
            tblproducts.setItems(orderItems);
        }
    }
}