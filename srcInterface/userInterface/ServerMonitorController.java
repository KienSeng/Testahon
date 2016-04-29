package userInterface;


import Global.Global;
import Global.Time;
import PropertiesFile.PropertiesFileReader;
import ServerMonitor.PingTool;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
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
    final int vboxWidth = 150;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneIsActive = true;

        serverMonitor_FlowPane.setPadding(new Insets(10,10,10,10));
        serverMonitor_FlowPane.setVgap(10);
        serverMonitor_FlowPane.setHgap(10);

        pingThreshold = Integer.parseInt(Global.propertyMap.get("Server_Monitor_Ping_Threshold"));

        try {
            populateDummyPane();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void populateDummyPane() throws Exception{
        String[] environmentList = Global.propertyMap.get("Server_Monitor_Environment").split(",");

        for(int i = 0; i < environmentList.length; i++){
            TitledPane titledPane_Environment = new TitledPane();
            titledPane_Environment.setExpanded(true);
            titledPane_Environment.setText(environmentList[i]);
            titledPane_Environment.getStyleClass().add("titledPane_text");
            titledPane_Environment.prefWidthProperty().bind(serverMonitor_FlowPane.widthProperty().subtract(40));

            FlowPane flowPane_ServerList_Container = new FlowPane();
            flowPane_ServerList_Container.setPadding(new Insets(10,10,10,10));
            flowPane_ServerList_Container.setVgap(10);
            flowPane_ServerList_Container.setHgap(10);

            titledPane_Environment.setContent(flowPane_ServerList_Container);

            String[] serverList = Global.propertyMap.get("Server_Monitor_" + environmentList[i] + "_Server").split(",");

            for(int j = 0; j < serverList.length; j++){
                flowPane_ServerList_Container.getChildren().add(generateServerMonitorVbox(serverList[j], "NA", "NA", "LOADING"));
            }

            titledPane_Environment.setContent(flowPane_ServerList_Container);
            serverMonitor_FlowPane.getChildren().add(titledPane_Environment);

            //start thread
            Thread thread = createThread(i, serverList);
            thread.start();
        }
    }

    private VBox generateServerMonitorVbox(String serverName, String pingTime, String lastCheck, String serverStatus) throws Exception{
        VBox vbox_Server_Info = new VBox();
        vbox_Server_Info.setSpacing(5);
        vbox_Server_Info.setAlignment(Pos.CENTER_LEFT);
        vbox_Server_Info.setMaxWidth(vboxWidth);
        vbox_Server_Info.setPadding(new Insets(5,5,5,5));

        FlowPane flowPane_serverName = new FlowPane();
        flowPane_serverName.setPadding(new Insets(5,0,5,0));
        flowPane_serverName.setAlignment(Pos.CENTER);

        FlowPane flowPane_serverStatus = new FlowPane();
        flowPane_serverStatus.setPadding(new Insets(5,0,5,0));
        flowPane_serverStatus.setAlignment(Pos.CENTER);

        Label lbl_serverName = new Label(serverName);
        Label lbl_pingTime = new Label("Ping: " + pingTime);
        Label lbl_lastCheck = new Label("Last Check: " + lastCheck);
        Label lbl_status = new Label(serverStatus);

        if(serverStatus.equalsIgnoreCase("offline")){
            vbox_Server_Info.getStyleClass().add("server_monitor_background_fail");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_fail");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_fail");
        }else if(serverStatus.equalsIgnoreCase("warning")){
            vbox_Server_Info.getStyleClass().add("server_monitor_background_warning");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_warning");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_warning");
        }else if(serverStatus.equalsIgnoreCase("online")){
            vbox_Server_Info.getStyleClass().add("server_monitor_background_pass");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_pass");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_pass");
        }else{
            vbox_Server_Info.getStyleClass().add("server_monitor_background_idle");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_idle");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_idle");
        }

        flowPane_serverName.getChildren().add(lbl_serverName);
        flowPane_serverStatus.getChildren().add(lbl_status);
        vbox_Server_Info.getChildren().addAll(flowPane_serverName, lbl_pingTime, lbl_lastCheck, flowPane_serverStatus);

        return vbox_Server_Info;
    }

    private Thread createThread(int threadNumber, String[] serverList){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    while(true){
                        PingTool ping = new PingTool();

                        for(int i = 0; i < serverList.length; i++){
                            if(!paneIsActive){
                                break;
                            }

                            String[] result = ping.ping(serverList[i]);

                            final int index = i;
                            final String serverAddress = result[0];
                            final int pingTime = Integer.parseInt(result[1]);
                            final String dateTime = Time.getCurrentTime("HH:mm:ss");
                            final String buildStatus;

                            if(pingTime < 1){
                                buildStatus = "OFFLINE";
                            }else if(pingTime > pingThreshold){
                                buildStatus = "WARNING";
                            }else{
                                buildStatus = "ONLINE";
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        TitledPane titledPane = (TitledPane) serverMonitor_FlowPane.getChildren().get(threadNumber);
                                        FlowPane flowPane = (FlowPane) titledPane.getContent();
                                        flowPane.getChildren().remove(index);
                                        flowPane.getChildren().add(index, generateServerMonitorVbox(serverAddress, String.valueOf(pingTime), dateTime, buildStatus));
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        Thread.sleep(Integer.parseInt(Global.propertyMap.get("Server_Check_Interval")));
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
