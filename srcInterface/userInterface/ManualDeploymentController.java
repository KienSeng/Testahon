package userInterface;

import Global.Global;
import JenkinsDeployment.JenkinsApi;
import PropertiesFile.PropertiesFileReader;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by kienseng on 4/19/2016.
 */
public class ManualDeploymentController implements Initializable {
    @FXML
    private FlowPane layout_FlowPane_Main;

    boolean paneIsActive = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            generatePane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePane() throws Exception {
        layout_FlowPane_Main.setHgap(20);
        layout_FlowPane_Main.setPadding(new Insets(10,10,10,10));
    }

    public void getBuildListFromPropertyMap() throws Exception{
        String[] allBuildName = Global.propertyMap.get("Siva_Dev_Build_List").split(",");

        //loop main job list
        for(int i = 0; i < allBuildName.length; i++){
            //populate container of main job
            FlowPane layout_FlowPane_MainJobContainer = new FlowPane();
            layout_FlowPane_MainJobContainer.setPadding(new Insets(5,5,5,5));
            layout_FlowPane_MainJobContainer.setOrientation(Orientation.VERTICAL);
            layout_FlowPane_MainJobContainer.setVgap(10);

            Separator spr_buildInfoSeparator = new Separator();
            spr_buildInfoSeparator.setOrientation(Orientation.HORIZONTAL);
            spr_buildInfoSeparator.setPadding(new Insets(5,5,5,5));

            //connect to main job to get info
            String mainBuildName = allBuildName[i];
            String url = "http://jenkins.jobstreet.com:8080/view/SiVA_DEV/job/" + mainBuildName + "/api/json";
            JenkinsApi jenkins = new JenkinsApi();
            jenkins.getResponseFromJenkins(url, "GET");

            HashMap<String, String>  mainJobBuildInfo = jenkins.getMainJobBuildInfo();
            String latestBuildNumber = mainJobBuildInfo.get("LatestBuild");
            ArrayList<String> buildList = jenkins.getLastBuildNumberExcludeLatest(5);

            //get latest build info
            jenkins.getResponseFromJenkins(mainJobBuildInfo.get("Url"), "GET");
            FlowPane flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest("NA", "NA", "NA", "SUCCESSFUL", true);
            //assign a thread
            layout_FlowPane_MainJobContainer.getChildren().addAll(flowPane_LatestBuildInfoContainer, spr_buildInfoSeparator);

            //loop last N build of main job
            //to be move to thread
            for(int j = 0; j < buildList.size(); j++){
                FlowPane flowPane_buildInfoContainer = generateBuildInfoFlowPaneLatest("NA", "NA", "NA", "SUCCESSFUL", false);
                layout_FlowPane_MainJobContainer.getChildren().add(flowPane_buildInfoContainer);
            }
        }
    }

    private FlowPane generateBuildInfoFlowPaneLatest(String buildNumber, String triggerTime, String triggerBy, String buildStatus, boolean isLatest) throws Exception{
        FlowPane layout_FlowPane_buildInfoContainer = new FlowPane(Orientation.VERTICAL);
        Label buildInfo = new Label("Build Number:  " + buildNumber + "\n" +
                                    "Trigger Time:  " + triggerTime + "\n" +
                                    "Trigger By  :  " + triggerBy + "\n" +
                                    "Build Status:  " + buildStatus);

        if(isLatest){

        }else{

        }

        return layout_FlowPane_buildInfoContainer;
    }

    private EventHandler<ActionEvent> buttonEventHandler = event -> {
        try{
            Button btn = (Button) event.getSource();

            switch(btn.getId()){

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    };

    private Thread createParentJobsThread(String url){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    while(true){
                        if(!paneIsActive){
                            break;
                        }


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
}