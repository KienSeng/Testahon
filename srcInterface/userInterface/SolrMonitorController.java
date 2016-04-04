package userInterface;

import Global.Time;
import PropertiesFile.PropertiesFileReader;
import ServerMonitor.ServiceCheck;
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
    String serverDisplayName = "NA";
    String coreName = "NA";
    String coreStatus = "NA";
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
            String[] listOfServiceName = pair.getValue().toString().split(",");

            for(int i = 0; i < listOfServiceName.length; i++){
                totalServiceToCheck++;

                serverName = listOfServiceName[i].trim();
                lbl_contents = new Label();
                lbl_contents.setId("lbl_ServerName_" + serverName + "_" + serverName);
                lbl_contents.setText(updateServerDetailsLabel());
                lbl_contents.setFont(Font.font(contentsFontSize));
                lbl_contents.setAlignment(Pos.CENTER_LEFT);

                lbl_coreStatus = new Label();
                lbl_coreStatus.setText(coreStatus);
                lbl_coreStatus.setId("lbl_HealthStatus_" + serverName + "_" + serverName);
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

                layout_MainFlowPane.getChildren().add(serviceMonitor_vbox);
            }
        }
    }

    private String updateServerDetailsLabel() throws Exception{
        StringBuilder str = new StringBuilder();

        if(coreStatus.equals("-1")){
            coreStatus = "NA";
        }

        str.append("Server: " + serverDisplayName + "\n");
        str.append("Server: " + coreName + "\n");
        str.append("Last Check: " + lastCheck + "\n");

        return str.toString();
    }

    public Runnable startCheck() throws Exception{
        ServiceCheck serviceMonitor = new ServiceCheck();

        for(int i = 0; i < singleService.length; i++){
            VBox vBox = (VBox) layout_MainFlowPane.getChildren().get(i);
            Label label = (Label) vBox.getChildren().get(0);
            String[] labelId = vBox.getChildren().get(0).getId().split("_");
            Label healthLabel = (Label) vBox.getChildren().get(1);

            serverName = labelId[2];
            coreName = labelId[3];

            coreStatus = serviceMonitor.checkSolrServices(labelId[1], labelId[2]);

            Time time = new Time();
            lastCheck = time.getCurrentTime("HH:mm:ss");

            if(coreStatus.equalsIgnoreCase("STOPPED")){
                vBox.setStyle("-fx-background-color: #FF0000");
                healthLabel.setTextFill(Color.web("#FFFFFF"));
                label.setTextFill(Color.web("#FFFFFF"));
                healthLabel.setText("STOPPED");
            } else if(coreStatus.equalsIgnoreCase("RUNNING")){
                vBox.setStyle("-fx-background-color: #00FF00");
                healthLabel.setTextFill(Color.web("#000000"));
                label.setTextFill(Color.web("#000000"));
                healthLabel.setText("RUNNING");
            }

            label.setText(updateServerDetailsLabel());
        }

        return null;
    }

    public static void stopThread() throws Exception{
        paneIsActive = false;

    }
}
