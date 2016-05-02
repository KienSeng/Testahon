package userInterface;

import TestDataCreation.Myjs;
import TestDataCreation.SeleniumAction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.By;

public class Main extends Application {

    public static void main(String[] args) throws Exception{
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/userInterface/sample.fxml"));
        Parent root = fxml.load();
        Controller controller = fxml.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
        primaryStage.setTitle("JSQA Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(768);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
