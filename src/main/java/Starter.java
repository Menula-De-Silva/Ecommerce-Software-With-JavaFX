import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Starter extends Application {
    private double xOffset = 0; // For window drag support
    private double yOffset = 0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        BorderPane root = new BorderPane(loader.load());



        // Create the Scene
        Scene scene = new Scene(root);

        // Set up the Stage
        stage.initStyle(StageStyle.DECORATED); // Remove default OS title bar
        stage.setResizable(false); // Allow resizing if needed
        stage.setScene(scene);
        stage.setTitle("Clothify Clothing Store");
        stage.centerOnScreen();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("img/appiconc.png")));
        stage.show();
    }
}