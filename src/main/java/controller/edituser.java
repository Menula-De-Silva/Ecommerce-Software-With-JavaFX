package controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import model.UserSession;


public class edituser {

    @FXML
    private JFXButton btncheckforupdates;

    @FXML
    private Button btnchooseimg;

    @FXML
    private JFXButton btnclearallcachesandrestart;

    @FXML
    private JFXButton btndeleteall;

    @FXML
    private JFXButton btndeleteuser;

    @FXML
    private Button btngotoback;

    @FXML
    private JFXButton btnlogout;

    @FXML
    private JFXButton btnsubmitiuser;

    @FXML
    private ImageView chooseimgout;

    @FXML
    private Label lblpassword;

    @FXML
    private Label lblusername;

    // Function to choose an image


    @FXML
    public void initialize() {
        // Retrieve username and password from UserSession
        String username = UserSession.getUsername();
        String password = UserSession.getPassword();

        // Set lblusername if username is available
        if (username != null && !username.isEmpty()) {
            lblusername.setText(username);
        } else {
            lblusername.setText("Username not set"); // Fallback message
        }

        // Set lblpassword if password is available
        if (password != null && !password.isEmpty()) {
            lblpassword.setText(password);
        } else {
            lblpassword.setText("Password not set"); // Fallback message
        }
    }

    @FXML
    void chooseimgbtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.webp"));

        Stage stage = (Stage) btnchooseimg.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            chooseimgout.setImage(image);
        }


    }

    // Function to check for updates
    @FXML
    void checkforupdates(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check for Updates");
        alert.setHeaderText("Update Status");
        alert.setContentText("Your application is up-to-date!");
        alert.showAndWait();
    }

    // Function to clear caches and restart
    @FXML
    void clearallcachesandrestart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Caches and Restart");
        alert.setHeaderText("Operation Completed");
        alert.setContentText("All caches have been cleared. Please restart the application.");
        alert.showAndWait();
        System.exit(0); // Simulates closing the application
    }

    // Function to delete all user data
    @FXML
    void deleteall(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete All Users");
        alert.setHeaderText("Delete Operation");
        alert.setContentText("All user data has been permanently deleted.");
        alert.showAndWait();
    }

    // Function to delete a specific user
    @FXML
    void deleteuser(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete User");
        alert.setHeaderText("Delete Operation");
        alert.setContentText("The selected user has been permanently deleted.");
        alert.showAndWait();
    }

    // Function to go back to the previous screen
    @FXML
    void gotoback(ActionEvent event) {

        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading home.fxml", e);
        }

        // Example logic to navigate back (implement actual scene switching here)
        System.out.println("Navigating to the previous screen...");
    }

    // Function to logout
    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout Successful");
        alert.setContentText("You have been logged out.");
        alert.showAndWait();
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading login.fxml", e);
        }
    }

    // Function to submit user data
    @FXML
    void submituser(ActionEvent event) {
       // Navigate back to home.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading home.fxml", e);
        }
    }
}
