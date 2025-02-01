package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import model.userdata;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import model.UserSession;


public class login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageviewbg;

    @FXML
    private ImageView imageviewicon;

    @FXML
    private ImageView imageviewleftmain;

    @FXML
    private JFXButton loginbtn;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton signup;

    @FXML
    private JFXTextField username;

    @FXML
    void gotosignup(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/signup.fxml"));
            Parent newSceneRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(newSceneRoot));
            stage.show();
        } catch (IOException e) {
            // Handle IOException in a single block
            e.printStackTrace();
            throw new RuntimeException("Error loading signup.fxml", e);
        }
    }



    @FXML
    void login(ActionEvent event) {
        String inputUser = username.getText();
        String inputPass = password.getText();

        UserSession.setUsername(inputUser);
        UserSession.setPassword(inputPass);

        boolean isValidUser = userdata.getUsers().stream()
                .anyMatch(user -> user.getUsername().equals(inputUser) && user.getPassword().equals(inputPass));

        if (isValidUser) {
            new Alert(Alert.AlertType.INFORMATION, "Logged in!").show();
            System.out.println("Login Successful: Username: " + inputUser);

            // Navigate to the main page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
                Parent mainPageRoot = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(mainPageRoot));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials!").show();
        }
    }

    @FXML
    void initialize() {
        assert imageviewbg != null : "fx:id=\"imageviewbg\" was not injected: check your FXML file 'login.fxml'.";
        assert imageviewicon != null : "fx:id=\"imageviewicon\" was not injected: check your FXML file 'login.fxml'.";
        assert imageviewleftmain != null : "fx:id=\"imageviewleftmain\" was not injected: check your FXML file 'login.fxml'.";
        assert loginbtn != null : "fx:id=\"loginbtn\" was not injected: check your FXML file 'login.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'login.fxml'.";
        assert signup != null : "fx:id=\"signup\" was not injected: check your FXML file 'login.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'login.fxml'.";

    }


}

