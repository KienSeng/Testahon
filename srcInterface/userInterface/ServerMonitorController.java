package userInterface;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sun.reflect.annotation.ExceptionProxy;

import java.awt.*;
import java.awt.event.ActionEvent;
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
        serverMonitor_FlowPane.setMinWidth(Double.MAX_VALUE);
        System.out.println("HAHAHAHAHAHAHA");
    }

    @FXML
    public void listAllPanePropertyFile() throws Exception{
        serverMonitor_FlowPane.setHgap(15);
        serverMonitor_FlowPane.setVgap(15);
        serverMonitor_FlowPane.setPadding(new Insets(15,15,15,15));
        serverMonitor_FlowPane.setRowValignment(VPos.TOP);

        String serverList = "vela.jobstreet.com, orion.jobstreet.com,libra.jobstreet.com,ta-controller, ta-node-01";
        String[] singleServer = serverList.split(",");

        for(int i = 0; i < singleServer.length; i++){
            lbl_contents = new Label();
            lbl_contents.setText("Server: " + singleServer[i].trim());
            lbl_contents.setId("ServerName_" + singleServer[i].trim());
            lbl_contents.setFont(Font.font(contentsFontSize));

            serverMonitor_vBox = new VBox();
            serverMonitor_vBox.getChildren().add(lbl_contents);
            serverMonitor_vBox.setStyle("-fx-background-color: #00FF00");
            serverMonitor_vBox.setSpacing(5);
            serverMonitor_vBox.setMaxHeight(30);
            serverMonitor_FlowPane.getChildren().add(serverMonitor_vBox);
        }
    }

    private void getAllpingInfo() throws Exception{

    }
}
