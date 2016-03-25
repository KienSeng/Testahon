package userInterface;

//import UnitTest.PropertiesFileTest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception{

//        PropertiesFileTest.testRetrieveAllValue();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/userInterface/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
//        Startup start = new Startup();
//        start.startUp();
        Controller.addTitledPane();
        primaryStage.show();

    }
}
