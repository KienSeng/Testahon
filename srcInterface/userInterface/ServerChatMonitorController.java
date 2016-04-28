package userInterface;

import Global.Global;
import Global.Time;
import PropertiesFile.PropertiesFileReader;
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
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by kienseng.koh on 4/01/2016.
 */
public class ServerChatMonitorController implements Initializable {
    @FXML private FlowPane layout_MainFlowPane;
    @FXML private Label lbl_contents;
    @FXML private VBox serviceMonitor_vbox;
    @FXML private Label lbl_serviceStatus;

    int contentsFontSize = 13;

    String serverName = "NA";
    String serviceName = "NA";
    String serviceStatus = "NA";
    String lastCheck = "NA";

    static boolean paneIsActive = false;

    String[] singleService;
    HashMap<String, String> serverAndService;

    int totalServiceToCheck;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneIsActive = true;
    }

    public void showPane() throws Exception{
        layout_MainFlowPane.setOrientation(Orientation.HORIZONTAL);
        layout_MainFlowPane.setHgap(15);
        layout_MainFlowPane.setVgap(15);
        layout_MainFlowPane.setPadding(new Insets(15,15,15,15));
        layout_MainFlowPane.setRowValignment(VPos.TOP);
        layout_MainFlowPane.setStyle("-fx-background-color: #FFFFFF;");

        PropertiesFileReader propFile = new PropertiesFileReader();
        String serverList = propFile.readFromPropertyFile("DashboardSettings.properties", "Serverchat_To_Monitor");

        singleService = serverList.split(",");

        serverAndService = new HashMap<>();

        for (int i = 0; i < singleService.length; i++) {
            String service = propFile.readFromPropertyFile("DashboardSettings.properties", singleService[i] + "_Service_Name");
            serverAndService.put(singleService[i].trim(), service);
        }

        Iterator it = serverAndService.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();

            serverName = pair.getKey().toString();
            String[] listOfServiceName = pair.getValue().toString().split(",");

            for(int i = 0; i < listOfServiceName.length; i++){
                totalServiceToCheck++;

                serviceName = listOfServiceName[i].trim();
                lbl_contents = new Label();
                lbl_contents.setId("lbl_ServerName_" + serverName + "_" + serviceName);
                lbl_contents.setText(updateServerDetailsLabel());
                lbl_contents.setFont(Font.font(contentsFontSize));
                lbl_contents.setAlignment(Pos.CENTER_LEFT);

                lbl_serviceStatus = new Label();
                lbl_serviceStatus.setText(serviceStatus);
                lbl_serviceStatus.setId("lbl_HealthStatus_" + serverName + "_" + serviceName);
                lbl_serviceStatus.setFont(Font.font(contentsFontSize + 10));
                lbl_serviceStatus.setAlignment(Pos.CENTER);

                serviceMonitor_vbox = new VBox();
                serviceMonitor_vbox.getChildren().add(lbl_contents);
                serviceMonitor_vbox.getChildren().add(lbl_serviceStatus);
                serviceMonitor_vbox.setStyle("-fx-background-color: #C0C0C0");
                serviceMonitor_vbox.setSpacing(5);
                serviceMonitor_vbox.setMinWidth(150);
                serviceMonitor_vbox.setMinHeight(70);
                serviceMonitor_vbox.setEffect(new DropShadow());

                SimpleStringProperty healthStatus = new SimpleStringProperty();
                healthStatus = (SimpleStringProperty) lbl_serviceStatus.textProperty();
                healthStatus.addListener((observable, oldValue, newValue) -> {
                    lbl_serviceStatus.setText(newValue);
                });

                SimpleStringProperty stringProp = new SimpleStringProperty();
                stringProp = (SimpleStringProperty) lbl_contents.textProperty();
                stringProp.addListener((observable, oldValue, newValue) -> {
                    lbl_contents.setText(newValue);
                });

                StringProperty styleProperty = new SimpleStringProperty();
                styleProperty = serviceMonitor_vbox.styleProperty();
                styleProperty.addListener((observable, oldValue, newValue) -> {
                    serviceMonitor_vbox.setStyle(newValue);
                });

                layout_MainFlowPane.getChildren().add(serviceMonitor_vbox);

                lbl_contents = new Label();
                lbl_serviceStatus = new Label();
                serviceMonitor_vbox = new VBox();

                Thread t = createThread(stringProp, healthStatus, styleProperty);
                t.start();
            }
        }
    }

    private String updateServerDetailsLabel() throws Exception{
        StringBuilder str = new StringBuilder();

        if(serviceStatus.equals("-1")){
            serviceStatus = "NA";
        }

        str.append("Server: " + serverName + "\n");
        str.append("Service Name: " + serviceName + "\n");
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
                        ServiceCheck serviceMonitor = new ServiceCheck();
                        String serverName = holder.get().split(":")[1].trim().split("\n")[0];
                        String serviceName = holder.get().split(":")[2].trim().split("\n")[0];
                        String serviceStatus = serviceMonitor.checkWindowService(serverName, serviceName);

                        StringBuilder str = new StringBuilder();
                        str.append("Server: " + serverName + "\n");
                        str.append("Service Name: " + serviceName + "\n");
                        str.append("Last Check: " + Time.getCurrentTime("HH:mm:ss"));

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                holder.set(str.toString());

                                if(serviceStatus.equals("STOPPED")){
                                    healthStatus.set("STOPPED");
                                    styleProperty.set("-fx-background-color: #FF6633;");
                                } else if(serviceStatus.equals("RUNNING")){
                                    healthStatus.set("RUNNING");
                                    styleProperty.set("-fx-background-color: #33CC00;");
                                }
                            }
                        });
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

    public static void stopThread() throws Exception{
        paneIsActive = false;
    }
}
