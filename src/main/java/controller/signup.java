package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import model.userdata;
import model.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class signup{

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
    private JFXButton signupbtn;

    @FXML
    private JFXTextField username;

    @FXML
    void gotologin(ActionEvent event) {
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
        System.out.println("Login LInk");
    }

    @FXML
    void signup(ActionEvent event) {
        String outuser = username.getText();
        String outpass = password.getText();

        if (outuser.isEmpty() || outpass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields!").show();
            return;
        }

        // Add user to userdata
        userdata.addUser(new user(outuser, outpass));
        new Alert(Alert.AlertType.INFORMATION, "Sign Up Success!").show();
        System.out.println("New User Added: Username: " + outuser);

        // Clear fields after sign-up
        username.clear();
        password.clear();
    }

    @FXML
    void initialize() {
        assert imageviewbg != null : "fx:id=\"imageviewbg\" was not injected: check your FXML file 'signup.fxml'.";
        assert imageviewicon != null : "fx:id=\"imageviewicon\" was not injected: check your FXML file 'signup.fxml'.";
        assert imageviewleftmain != null : "fx:id=\"imageviewleftmain\" was not injected: check your FXML file 'signup.fxml'.";
        assert loginbtn != null : "fx:id=\"loginbtn\" was not injected: check your FXML file 'signup.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'signup.fxml'.";
        assert signupbtn != null : "fx:id=\"signupbtn\" was not injected: check your FXML file 'signup.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'signup.fxml'.";

    }

}
