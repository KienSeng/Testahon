package userInterface;

import TestDataCreation.Siva;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static void main(String[] args) throws Exception{
        launch(args);
//        Siva siva = new Siva();
//        siva.setEnvironment("TA");
//        siva.setUsername("main_api");
//        siva.createSavedJob("Testathon test job");
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/userInterface/sample.fxml"));
        Parent root = fxml.load();
        Controller controller = fxml.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root);

        File[] dir = new File("./Image").listFiles();
        for(int i = 0; i < dir.length; i++){
            if(dir[i].getName().contains("icon")){
                primaryStage.getIcons().add(new Image(new File(dir[i].getPath()).toURI().toString()));
            }
        }

        scene.getStylesheets().add(getClass().getResource("StyleSheet.css").toExternalForm());
//        try{
//            primaryStage.getIcons().add(new Image("http://www.seek.com.au/content/images/logos/logo-seek-share.gif"));
//        }catch(Exception e){
//
//        }
        primaryStage.setTitle("JSQA Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1024);
        primaryStage.setMinHeight(768);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
