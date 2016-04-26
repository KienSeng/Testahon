package userInterface;

import JenkinsDeployment.JenkinsApi;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception{
        launch(args);
//        ManualDeploymentController controller = new ManualDeploymentController();
//        controller.getParentDownstreamBuild("http://jenkins.jobstreet.com/view/SiVA_DEV/job/SIVA_SAND_UNIT_TEST/api/json");

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
