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
    FlowPane layout_FlowPane_SubBuildContainer;
    FlowPane layout_FlowPane_MainBuildContainer;

    ArrayList<String> downstreamBuild = new ArrayList<>();
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
    }

    public void getBuildListFromPropertyMap() throws Exception{
        String mainBuildName = Global.propertyMap.get("Siva_Dev_Build_List");
        String url = "http://jenkins.jobstreet.com:8080/view/SiVA_DEV/job/" + mainBuildName + "/api/json";

        JenkinsApi jenkins = new JenkinsApi();
        jenkins.getResponseFromJenkins(url, "GET");


    }

    private ArrayList<FlowPane> generateJobLastBuildList(String url) throws Exception{
        JenkinsApi jenkins = new JenkinsApi();
        jenkins.getResponseFromJenkins(url, "GET");

        ArrayList<String> buildArray = jenkins.getLastBuildNumberExcludeLatest(5);
        ArrayList<FlowPane> buildPaneList = new ArrayList<>();

        for(int i = 0; i < buildArray.size(); i++){
            String[] urlInfo = buildArray.get(i).split("\\|");
            JenkinsApi jenkins2 = new JenkinsApi();
            jenkins2.getResponseFromJenkins(urlInfo[1], "GET");
            HashMap<String, String> buildInfo = jenkins2.getBuildInfo();

            layout_FlowPane_SubBuildContainer = new FlowPane();
            layout_FlowPane_SubBuildContainer.setPadding(new Insets(5,5,5,5));
            layout_FlowPane_SubBuildContainer.setOrientation(Orientation.VERTICAL);

            Label lbl_buildNumber = new Label("Build Number: " + buildInfo.get("BuildNumber"));
            Label lbl_buildTime = new Label("Triggered Time: " + buildInfo.get("TriggerDateTime").replace("+0800","").trim());
            Label lbl_buildStatus = new Label("Build Results: " + buildInfo.get("Result").toUpperCase());

            layout_FlowPane_SubBuildContainer.getChildren().addAll(lbl_buildNumber, lbl_buildTime, lbl_buildStatus);
            buildPaneList.add(layout_FlowPane_SubBuildContainer);
        }

        return buildPaneList;
    }

    private FlowPane populateLatestBuildPane(String buildName, String triggerBy, String triggerDateTime, boolean deployable) throws Exception{
        FlowPane layout_FlowPane_BuildContainer = new FlowPane();
        FlowPane layout_FlowPane_ButtonContainer = new FlowPane();
        VBox layout_Vbox_BuildTitleContainer = new VBox();

        Label lbl_buildName = new Label(buildName);
        Label lbl_triggerBy = new Label(triggerBy);
        Label lbl_triggerDateTime = new Label(triggerDateTime);

        layout_Vbox_BuildTitleContainer.setAlignment(Pos.CENTER);
        layout_Vbox_BuildTitleContainer.setPadding(new Insets(5,5,5,5));
        layout_Vbox_BuildTitleContainer.getChildren().add(lbl_buildName);

        layout_FlowPane_BuildContainer.setPadding(new Insets(10,10,10,10));

        if(deployable){
            Button btn_deploy = new Button("Deploy");
            layout_FlowPane_ButtonContainer.setAlignment(Pos.CENTER);
            layout_FlowPane_ButtonContainer.setPadding(new Insets(10,0,10,0));
            layout_FlowPane_ButtonContainer.getChildren().add(btn_deploy);

            layout_FlowPane_BuildContainer.getChildren().addAll(layout_Vbox_BuildTitleContainer, lbl_triggerBy, lbl_triggerDateTime, layout_FlowPane_ButtonContainer);
        } else{
            layout_FlowPane_BuildContainer.getChildren().addAll(layout_Vbox_BuildTitleContainer, lbl_triggerBy, lbl_triggerDateTime);
        }

        return layout_FlowPane_BuildContainer;
    }

    private FlowPane populateSubsequentBuildPane(String buildNumber, String triggerBy, String triggerDateTime, boolean deployable) throws Exception{
        FlowPane layout_FlowPane_BuildContainer = new FlowPane();
        FlowPane layout_FlowPane_ButtonContainer = new FlowPane();
        VBox layout_Vbox_BuildTitleContainer = new VBox();

        Label lbl_buildName = new Label(buildNumber);
        Label lbl_triggerBy = new Label(triggerBy);
        Label lbl_triggerDateTime = new Label(triggerDateTime);

        layout_Vbox_BuildTitleContainer.setAlignment(Pos.CENTER);
        layout_Vbox_BuildTitleContainer.setPadding(new Insets(5,5,5,5));
        layout_Vbox_BuildTitleContainer.getChildren().add(lbl_buildName);

        layout_FlowPane_BuildContainer.setPadding(new Insets(10,10,10,10));

        if(deployable){
            Button btn_deploy = new Button("Deploy");
            layout_FlowPane_ButtonContainer.setAlignment(Pos.CENTER);
            layout_FlowPane_ButtonContainer.setPadding(new Insets(10,0,10,0));
            layout_FlowPane_ButtonContainer.getChildren().add(btn_deploy);

            layout_FlowPane_BuildContainer.getChildren().addAll(layout_Vbox_BuildTitleContainer, lbl_triggerBy, lbl_triggerDateTime, layout_FlowPane_ButtonContainer);
        } else{
            layout_FlowPane_BuildContainer.getChildren().addAll(layout_Vbox_BuildTitleContainer, lbl_triggerBy, lbl_triggerDateTime);
        }

        return layout_FlowPane_BuildContainer;
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

                        JenkinsApi jenkins = new JenkinsApi();
                        jenkins.getResponseFromJenkins(url, "GET");
                        HashMap<String, String> buildInfo = jenkins.getBuildInfo();

                            /*
                        map.put("BuildNumber", api.getValueFromResponse(response, "number"));
                        map.put("FullDisplayName", api.getValueFromResponse(response, "fullDisplayName"));
                        map.put("Result", api.getValueFromResponse(response, "result"));
                        map.put("URL", api.getValueFromResponse(response, "url"));
                        map.put("TriggerBy", api.getValueFromResponse(response, "culprits.fullName").replace("[","").replace("]",""));
                        map.put("TriggerDateTime", api.getValueFromResponse(response, "date"));
                             */

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FlowPane layout_FlowPane_LatestBuildContainer = populateLatestBuildPane(buildInfo.get("FullDisplayName"), buildInfo.get("TriggerBy"), buildInfo.get("TriggerDateTime").replace("+0800", "").trim(), false);
                                    //layout_FlowPane_MainBuildContainer
                                    layout_FlowPane_MainBuildContainer.getChildren().remove(0);
                                    layout_FlowPane_MainBuildContainer.getChildren().add(0, layout_FlowPane_LatestBuildContainer);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        ArrayList<String> lastBuildList = jenkins.getLastBuildNumberExcludeLatest(5);
                        for(int i = 0; i < lastBuildList.size(); i++){
                            String[] lastBuildUrl = lastBuildList.get(i).split("\\|");
                            jenkins.getResponseFromJenkins(lastBuildUrl[1], "GET");
                            HashMap<String, String> lastBuildInfo = jenkins.getBuildInfo();

                            final int j = i;

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        FlowPane flowPane_BuildList = populateSubsequentBuildPane(lastBuildInfo.get("BuildNumber"), lastBuildInfo.get("TriggerBy"), lastBuildInfo.get("TriggerDateTime").replace("+0800", "").trim(), false);
                                        layout_FlowPane_MainBuildContainer.getChildren().remove(j + 2);
                                        layout_FlowPane_MainBuildContainer.getChildren().add(j + 2, flowPane_BuildList);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
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