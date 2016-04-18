package userInterface;

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
public class SolrMonitorController implements Initializable {
    @FXML private FlowPane layout_MainFlowPane;
    @FXML private Label lbl_contents;
    @FXML private VBox serviceMonitor_vbox;
    @FXML private Label lbl_coreStatus;

    int contentsFontSize = 13;

    String serverName = "NA";
    String coreName = "NA";
    String coreStatus = "NA";
    String lastCheck = "NA";

    static boolean paneIsActive = false;

    String[] singleService;
    HashMap<String, String> serverAndService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneIsActive = true;
        try {
            showPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPane() throws Exception{
        layout_MainFlowPane.setOrientation(Orientation.HORIZONTAL);
        layout_MainFlowPane.setHgap(15);
        layout_MainFlowPane.setVgap(15);
        layout_MainFlowPane.setPadding(new Insets(15,15,15,15));
        layout_MainFlowPane.setRowValignment(VPos.TOP);
        layout_MainFlowPane.setStyle("-fx-background-color: #FFFFFF;");

        PropertiesFileReader propFile = new PropertiesFileReader();
        String serverList = propFile.readFromPropertyFile("DashboardSettings.properties", "SOLR_Service_To_Monitor");

        singleService = serverList.split(",");

        serverAndService = new HashMap<>();

        for (int i = 0; i < singleService.length; i++) {
            String cores = propFile.readFromPropertyFile("DashboardSettings.properties", singleService[i] + "_Solr_Core_Name");
            serverAndService.put(singleService[i].trim(), cores);
        }

        Iterator it = serverAndService.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            serverName = pair.getKey().toString();
            String[] listOfCoreName = pair.getValue().toString().split(",");

            for(int i = 0; i < listOfCoreName.length; i++){
                coreName = listOfCoreName[i].trim();
                lbl_contents = new Label();
                lbl_contents.setId("lbl_ServerName_" + serverName + "_" + coreName);
                lbl_contents.setText(updateServerDetailsLabel());
                lbl_contents.setFont(Font.font(contentsFontSize));
                lbl_contents.setAlignment(Pos.CENTER_LEFT);

                lbl_coreStatus = new Label();
                lbl_coreStatus.setText(coreStatus);
                lbl_coreStatus.setId("lbl_HealthStatus_" + serverName + "_" + coreName);
                lbl_coreStatus.setFont(Font.font(contentsFontSize + 10));
                lbl_coreStatus.setAlignment(Pos.CENTER);

                serviceMonitor_vbox = new VBox();
                serviceMonitor_vbox.getChildren().add(lbl_contents);
                serviceMonitor_vbox.getChildren().add(lbl_coreStatus);
                serviceMonitor_vbox.setStyle("-fx-background-color: #C0C0C0");
                serviceMonitor_vbox.setSpacing(5);
                serviceMonitor_vbox.setMinWidth(150);
                serviceMonitor_vbox.setMinHeight(70);
                serviceMonitor_vbox.setEffect(new DropShadow());

                SimpleStringProperty healthStatus = new SimpleStringProperty();
                healthStatus = (SimpleStringProperty) lbl_coreStatus.textProperty();
                healthStatus.addListener((observable, oldValue, newValue) -> {
                    lbl_coreStatus.setText(newValue);
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
                lbl_coreStatus = new Label();
                serviceMonitor_vbox = new VBox();

                Thread t = createThread(stringProp, healthStatus, styleProperty);
                t.start();
            }
        }
    }

    private String updateServerDetailsLabel() throws Exception{
        StringBuilder str = new StringBuilder();

        str.append("Server: " + serverName + "\n");
        str.append("Core: " + coreName + "\n");
        str.append("Last Check: " + lastCheck);

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
                        String coreName = holder.get().split(":")[2].trim().split("\n")[0];
                        String coreStatus = serviceMonitor.checkSolrServices(serverName, coreName);

                        StringBuilder str = new StringBuilder();
                        str.append("Server: " + serverName + "\n");
                        str.append("Core: " + coreName + "\n");
                        str.append("Last Check: " + Time.getCurrentTime("HH:mm:ss"));

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                holder.set(str.toString());

                                if(coreStatus.equals("STOPPED")){
                                    healthStatus.set("STOPPED");
                                    styleProperty.set("-fx-background-color: #FF6633;");
                                } else if(coreStatus.equals("RUNNING")){
                                    healthStatus.set("RUNNING");
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
