package userInterface;


import PropertiesFile.PropertiesFileReader;
import ServerMonitor.PingTool;
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
import java.awt.event.PaintEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServerMonitorController implements Initializable{

    @FXML private FlowPane serverMonitor_FlowPane;
    @FXML private Label lbl_contents;
    @FXML private VBox serverMonitor_vBox;
    @FXML private Label lbl_healthStatus;

    int contentsFontSize = 13;
    Double flowPaneChildHeight = 0.0;
    Double flowPaneChildWidth = 0.0;

    String serverName = "NA";
    String pingTime = "NA";
    String healthStatus = "NA";
    String lastCheck = "NA";

    Object mainController = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverMonitor_FlowPane.setOrientation(Orientation.HORIZONTAL);
//        serverMonitor_FlowPane.setPrefWidth(700);
        System.out.println("HAHAHAHAHAHAHA");
    }

    @FXML
    public void listAllPanePropertyFile() throws Exception{
        serverMonitor_FlowPane.setHgap(15);
        serverMonitor_FlowPane.setVgap(15);
        serverMonitor_FlowPane.setPadding(new Insets(15,15,15,15));
        serverMonitor_FlowPane.setRowValignment(VPos.TOP);

        PropertiesFileReader propFile = new PropertiesFileReader();
        String serverList = propFile.readFromPropertyFile("DashboardSettings.properties", "Server_To_Monitor");

//        String serverList = "vela.jobstreet.com,libra.jobstreet.com,orion.jobstreet.com,ta-controller.jobstreet.com, Drone, Lobster, Chiru, Duiker, Dule, Simian, Horde, Coley, Catla, Maleo, Millerbird, Morepork, Baryonyx, Poacher, Dove, Duck, Swarm, Filly, Maggot";
        String[] singleServer = serverList.split(",");

        for(int i = 0; i < singleServer.length; i++){
            serverName = singleServer[i].trim();

            lbl_contents = new Label();
            lbl_contents.setText(updateServerDetailsLabel());
            lbl_contents.setId("lbl_ServerName_" + serverName);
            lbl_contents.setFont(Font.font(contentsFontSize));
            lbl_contents.setAlignment(Pos.CENTER_LEFT);

            lbl_healthStatus = new Label();
            lbl_healthStatus.setText(healthStatus);
            lbl_healthStatus.setId("lbl_HealthStatus_" + serverName);
            lbl_healthStatus.setFont(Font.font(contentsFontSize + 10));
            lbl_healthStatus.setAlignment(Pos.CENTER);

            serverMonitor_vBox = new VBox();
            serverMonitor_vBox.getChildren().add(lbl_contents);
            serverMonitor_vBox.getChildren().add(lbl_healthStatus);
            serverMonitor_vBox.setStyle("-fx-background-color: #00FF00");
            serverMonitor_vBox.setSpacing(5);
            serverMonitor_vBox.setMinWidth(150);
            serverMonitor_vBox.setMinHeight(70);

            serverMonitor_FlowPane.getChildren().add(serverMonitor_vBox);
        }
    }

    private String updateServerDetailsLabel() throws Exception{
        StringBuilder str = new StringBuilder();

        str.append("Server: " + serverName + "\n");
        str.append("Ping Time: " + pingTime + "\n");
        str.append("Last Check: " + lastCheck + "\n");

        return str.toString();
    }

    private void pingOnce() throws Exception{
        PingTool svrMonitor = new PingTool();


    }

    public void setFlowPaneWidth(Double width) throws Exception{
        serverMonitor_FlowPane.setMinWidth(width);
    }

    public void setFlowPaneHeight(Double height) throws Exception{
        serverMonitor_FlowPane.setMinHeight(height);
    }
}
