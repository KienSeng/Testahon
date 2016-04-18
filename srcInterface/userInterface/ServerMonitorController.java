package userInterface;


import Global.Time;
import PropertiesFile.PropertiesFileReader;
import ServerMonitor.PingTool;
import com.sun.corba.se.spi.activation.Server;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sun.java2d.pipe.SpanShapeRenderer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ServerMonitorController implements Initializable{

    @FXML private FlowPane serverMonitor_FlowPane;
    @FXML private Label lbl_contents;
    @FXML private VBox serverMonitor_vBox;
    @FXML private Label lbl_healthStatus;

    int contentsFontSize = 13;
    int pingThreshold = 0;

    String serverName = "NA";
    String pingTime = "NA";
    String healthStatus = "NA";
    String lastCheck = "NA";

    static String[] singleServer;

    static boolean paneIsActive = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneIsActive = true;
        try {
            listAllPanePropertyFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void listAllPanePropertyFile() throws Exception{
        serverMonitor_FlowPane.setOrientation(Orientation.HORIZONTAL);
        serverMonitor_FlowPane.setHgap(15);
        serverMonitor_FlowPane.setVgap(15);
        serverMonitor_FlowPane.setPadding(new Insets(15,15,15,15));
        serverMonitor_FlowPane.setRowValignment(VPos.TOP);
        serverMonitor_FlowPane.setStyle("-fx-background-color: #FFFFFF;");

        PropertiesFileReader propFile = new PropertiesFileReader();
        String serverList = propFile.readFromPropertyFile("DashboardSettings.properties", "Server_To_Monitor");
        pingThreshold = Integer.parseInt(propFile.readFromPropertyFile("DashboardSettings.properties", "Server_Monitor_Ping_Threshold"));

//        serverList = "vela.jobstreet.com,libra.jobstreet.com,orion.jobstreet.com,ta-controller.jobstreet.com, Drone, Lobster, Chiru, Duiker, Dule, Simian, Horde, Coley, Catla, Maleo, Millerbird, Morepork, Baryonyx, Poacher, Dove, Duck, Swarm, Filly, Maggot";
        singleServer = serverList.split(",");
        for(int i = 0; i < singleServer.length; i++) {
            serverName = singleServer[i].trim();

            lbl_contents = new Label();
            lbl_contents.setId("lbl_ServerName_" + serverName);
            lbl_contents.setText(updateServerDetailsLabel());
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
            serverMonitor_vBox.setStyle("-fx-background-color: #C0C0C0");
            serverMonitor_vBox.setSpacing(5);
            serverMonitor_vBox.setMinWidth(150);
            serverMonitor_vBox.setMinHeight(70);
            serverMonitor_vBox.setEffect(new DropShadow());

            SimpleStringProperty healthStatus = new SimpleStringProperty();
            healthStatus = (SimpleStringProperty) lbl_healthStatus.textProperty();
            healthStatus.addListener((observable, oldValue, newValue) -> {
                lbl_healthStatus.setText(newValue);
            });

            SimpleStringProperty stringProp = new SimpleStringProperty();
            stringProp = (SimpleStringProperty) lbl_contents.textProperty();
            stringProp.addListener((observable, oldValue, newValue) -> {
                lbl_contents.setText(newValue);
            });

            StringProperty styleProperty = new SimpleStringProperty();
            styleProperty = serverMonitor_vBox.styleProperty();
            styleProperty.addListener((observable, oldValue, newValue) -> {
                serverMonitor_vBox.setStyle(newValue);
            });

            serverMonitor_FlowPane.getChildren().add(serverMonitor_vBox);

            lbl_contents = new Label();
            lbl_healthStatus = new Label();
            serverMonitor_vBox = new VBox();

            Thread t = createThread(stringProp, healthStatus, styleProperty);
            t.start();
        }
    }

    private String updateServerDetailsLabel() throws Exception{
        StringBuilder str = new StringBuilder();

        if(pingTime.equals("-1")){
            pingTime = "NA";
        }

        str.append("Server: " + serverName + "\n");
        str.append("Ping Time: " + pingTime + "\n");
        str.append("Last Check: " + lastCheck + "\n");

        return str.toString();
    }

    private Thread createThread(SimpleStringProperty holder, SimpleStringProperty healthStatus, StringProperty styleProperty){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    while(true){
                        if(!paneIsActive){
                            break;
                        }
                        String serverName = holder.get().split(":")[1].trim().split("\n")[0];
                        PingTool monitor = new PingTool();
                        String ping = monitor.ping(serverName)[1];
                        final int pingInt = Integer.parseInt(ping);
                        if(ping .equalsIgnoreCase("-1")){
                            ping = "0";
                        }
                        StringBuilder str = new StringBuilder();
                        str.append("Server: " + serverName + "\n");
                        str.append("Ping Time: " + ping + "\n");
                        str.append("Last Check: " + Time.getCurrentTime("HH:mm:ss"));

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                holder.set(str.toString());

                                if(pingInt > pingThreshold){
                                    healthStatus.set("WARNING");
                                    styleProperty.set("-fx-background-color: #FFFF66;");
                                } else if(pingInt <= 0){
                                    healthStatus.set("OFFLINE");
                                    styleProperty.set("-fx-background-color: #FF6633;");
                                } else if(pingInt <= pingThreshold && pingInt > 0){
                                    healthStatus.set("ONLINE");
                                    styleProperty.set("-fx-background-color: #33CC00;");
                                }
                            }
                        });
                        Thread.sleep(10000);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        return thread;
    }

    public static void stopThread() throws Exception{
        paneIsActive = false;

    }
}
