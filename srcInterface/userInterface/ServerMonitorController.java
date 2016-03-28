package userInterface;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerMonitorController implements Initializable{

    @FXML private FlowPane serverMonitor_FlowPane;
    @FXML private Label lbl_contents;
    @FXML private VBox serverMonitor_vBox;

    int contentsFontSize = 13;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverMonitor_FlowPane.setOrientation(Orientation.HORIZONTAL);
        System.out.println("HAHAHAHAHAHAHA");
//        serverMonitor_FlowPane.setHgap(5);
//
////        listAllPanePropertyFile();
//
//        String serverList = "vela.jobstreet.com, orion.jobstreet.com,libra.jobstreet.com,ta-controller, ta-node-01";
//        String[] singleServer = serverList.split(",");
//
//        ArrayList<Node> vBoxArray = new ArrayList<>();
//
//        for(int i = 0; i < singleServer.length; i++){
//            lbl_contents = new Label();
//            lbl_contents.setText("Server: " + singleServer[i].trim());
//            lbl_contents.setFont(Font.font(contentsFontSize));
//
//            serverMonitor_vBox = new VBox();
//            serverMonitor_vBox.getChildren().add(lbl_contents);
////            serverMonitor_vBox.setStyle("-fx-background-color: #00FF00;");
//            serverMonitor_vBox.setSpacing(5);
//            serverMonitor_FlowPane.getChildren().add(serverMonitor_vBox);
//            System.out.println(singleServer[i]);
////            vBoxArray.add(serverMonitor_vBox);
//
//        }
//
//        serverMonitor_FlowPane.getChildren().add(vBoxArray);
    }

    @FXML
    public void listAllPanePropertyFile() throws Exception{

//        PropertiesFileReader propFile = new PropertiesFileReader();

        String serverList = "vela.jobstreet.com, orion.jobstreet.com,libra.jobstreet.com,ta-controller, ta-node-01";
        String[] singleServer = serverList.split(",");

        ArrayList<Node> vBoxArray = new ArrayList<>();

        for(int i = 0; i < singleServer.length; i++){
            lbl_contents = new Label();
            lbl_contents.setText("Server: " + singleServer[i].trim());
            lbl_contents.setFont(Font.font(contentsFontSize));

            serverMonitor_vBox = new VBox();
            serverMonitor_vBox.getChildren().add(lbl_contents);
            serverMonitor_vBox.setStyle("-fx-background-color: #00FF00");
            serverMonitor_vBox.setSpacing(5);
            serverMonitor_FlowPane.getChildren().add(serverMonitor_vBox);
        }
    }

    @FXML
    public void setFlowPaneDimension(Double height, Double width) throws Exception{
        serverMonitor_FlowPane.setMinHeight(height);
        serverMonitor_FlowPane.setMinWidth(width);
    }
}
