package userInterface;


import Global.Global;
import Global.Time;
import PropertiesFile.PropertiesFileReader;
import ServerMonitor.PingTool;
import ServerMonitor.ServiceCheck;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ServerMonitorController implements Initializable{
    @FXML private FlowPane serverMonitor_FlowPane;

    int pingThreshold = 0;
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
            switch(Global.product.toLowerCase()){
                case "server":
                    populateServerMonitorDummyPane();
                    break;

                case "service":
                    populateServiceMonitorDummyPane();
                    break;

                case "solr":
                    populateSolrMonitorDummyPane();
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateServerMonitorDummyPane() throws Exception{
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
                flowPane_ServerList_Container.getChildren().add(generateVbox(serverList[j], "NA", "NA", "LOADING"));
            }

            titledPane_Environment.setContent(flowPane_ServerList_Container);
            serverMonitor_FlowPane.getChildren().add(titledPane_Environment);

            //start thread
            Thread thread = createServerMonitorThread(i, serverList);
            thread.start();
        }
    }

    private void populateServiceMonitorDummyPane() throws Exception{
        String[] environmentList = Global.propertyMap.get("Service_Monitor_Environment").split(",");

        for(int i = 0; i < environmentList.length; i++) {
            TitledPane titledPane_Environment = new TitledPane();
            titledPane_Environment.setExpanded(true);
            titledPane_Environment.setText(environmentList[i]);
            titledPane_Environment.getStyleClass().add("titledPane_text");
            titledPane_Environment.prefWidthProperty().bind(serverMonitor_FlowPane.widthProperty().subtract(40));

            FlowPane flowPane_ServerList_Container = new FlowPane();
            flowPane_ServerList_Container.setPadding(new Insets(10, 10, 10, 10));
            flowPane_ServerList_Container.setVgap(10);
            flowPane_ServerList_Container.setHgap(10);

            titledPane_Environment.setContent(flowPane_ServerList_Container);

            HashMap<String, String[]> threadValue = new HashMap<>();

            for (Map.Entry<String, String> entry : Global.propertyMap.entrySet()) {
                if(entry.getKey().contains("Service_Monitor_" + environmentList[i])){
                    String[] serviceList = entry.getValue().split(",");
                    String serverName = entry.getKey().split("_")[3];
                    System.out.println(serverName);
                    threadValue.put(serverName, serviceList);

                    for(int j = 0; j < serviceList.length; j++){
                        flowPane_ServerList_Container.getChildren().add(generateVbox(serverName, serviceList[j], "NA", "LOADING"));
                    }
                }
            }

            serverMonitor_FlowPane.getChildren().add(titledPane_Environment);

            Thread thread = createServiceMonitorThread(i, threadValue);
            thread.start();
        }
    }

    private void populateSolrMonitorDummyPane() throws Exception{

    }

    private VBox generateVbox(String serverName, String misc, String lastCheck, String serverStatus) throws Exception{
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

        Label lbl_serverName = null;
        Label lbl_pingTime = null;
        Label lbl_lastCheck = null;
        Label lbl_status = null;
        Label lbl_coreName = null;
        Label lbl_serviceName = null;

        switch(Global.product.toLowerCase()){
            case "server":
                lbl_serverName = new Label(serverName);
                lbl_pingTime = new Label("Ping: " + misc);
                lbl_lastCheck = new Label("Last Check: " + lastCheck);
                lbl_status = new Label(serverStatus);

                flowPane_serverName.getChildren().add(lbl_serverName);
                flowPane_serverStatus.getChildren().add(lbl_status);
                vbox_Server_Info.getChildren().addAll(flowPane_serverName, lbl_pingTime, lbl_lastCheck, flowPane_serverStatus);
                break;

            case "service":
                lbl_serverName = new Label(serverName);
                lbl_serviceName = new Label(misc);
                lbl_lastCheck = new Label("Last Check: " + lastCheck);
                lbl_status = new Label(serverStatus);

                flowPane_serverName.getChildren().add(lbl_serverName);
                flowPane_serverStatus.getChildren().add(lbl_status);
                vbox_Server_Info.getChildren().addAll(flowPane_serverName, lbl_serviceName, lbl_lastCheck, flowPane_serverStatus);
                break;

            case "solr":
                lbl_serverName = new Label(serverName);
                lbl_coreName = new Label("Core: " + misc);
                lbl_lastCheck = new Label("Last Check: " + lastCheck);
                lbl_status = new Label(serverStatus);

                flowPane_serverName.getChildren().add(lbl_serverName);
                flowPane_serverStatus.getChildren().add(lbl_status);
                vbox_Server_Info.getChildren().addAll(flowPane_serverName, lbl_coreName, lbl_lastCheck, flowPane_serverStatus);
                break;

            default:
                break;
        }

        if(serverStatus.equalsIgnoreCase("offline") || serverStatus.equalsIgnoreCase("stopped")){
            vbox_Server_Info.getStyleClass().add("server_monitor_background_fail");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_fail");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_fail");
        }else if(serverStatus.equalsIgnoreCase("warning")){
            vbox_Server_Info.getStyleClass().add("server_monitor_background_warning");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_warning");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_warning");
        }else if(serverStatus.equalsIgnoreCase("online") || serverStatus.equalsIgnoreCase("running")){
            vbox_Server_Info.getStyleClass().add("server_monitor_background_pass");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_pass");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_pass");
        }else{
            vbox_Server_Info.getStyleClass().add("server_monitor_background_idle");
            flowPane_serverName.getStyleClass().add("server_monitor_status_background_idle");
            flowPane_serverStatus.getStyleClass().add("server_monitor_status_background_idle");
        }

        return vbox_Server_Info;
    }

    private Thread createServerMonitorThread(int threadNumber, String[] serverList){
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
                                        flowPane.getChildren().add(index, generateVbox(serverAddress, String.valueOf(pingTime), dateTime, buildStatus));
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

    private Thread createServiceMonitorThread(int threadNumber, HashMap<String, String[]> map){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    while(true){
                        ServiceCheck service = new ServiceCheck();
                        int index = 0;

                        for (Map.Entry<String, String[]> entry : map.entrySet()) {
                            for(int i = 0; i < entry.getValue().length; i++){
                                final String result = service.checkWindowService(entry.getKey(), entry.getValue()[i]);
                                final String serverName = entry.getKey();
                                final String serviceName = entry.getValue()[i];
                                final int j = index;

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            TitledPane titledPane = (TitledPane) serverMonitor_FlowPane.getChildren().get(threadNumber);
                                            FlowPane flowPane = (FlowPane) titledPane.getContent();
                                            flowPane.getChildren().remove(j);
                                            flowPane.getChildren().add(j, generateVbox(serverName, serviceName, Time.getCurrentTime("HH:mm:ss"), result));
                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                index++;
                            }
                        }

                        Thread.sleep(Integer.parseInt(Global.propertyMap.get("Serverchat_Check_Interval")));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        thread.setDaemon(true);
        return thread;
    }

    private Thread createSolrMonitorThread(int threadNumber, HashMap<String, String[]> map){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    while(true){

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
