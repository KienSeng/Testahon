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
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
    @FXML private FlowPane layout_FlowPane_Main;

    static boolean paneIsActive = false;

    int listOfSubBuild = 5;
    int buildListHeight = 100;
    int buildListWidth = 230;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            paneIsActive = true;
            generatePane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePane() throws Exception {
        layout_FlowPane_Main.setVgap(20);
        layout_FlowPane_Main.setHgap(20);
        layout_FlowPane_Main.setPadding(new Insets(10,10,10,10));

        populateDummyBuildPane();
    }

    private void populateDummyBuildPane() throws Exception{
        String[] allBuildName = Global.propertyMap.get(Global.product + "_Build_List").split(",");

        //loop main job list
        for(int i = 0; i < allBuildName.length; i++){
            //populate container of main job
            VBox layout_FlowPane_MainJobContainer = new VBox();

            layout_FlowPane_MainJobContainer.setPadding(new Insets(5,5,5,5));
            layout_FlowPane_MainJobContainer.setSpacing(10);
            layout_FlowPane_MainJobContainer.setAlignment(Pos.TOP_LEFT);
            layout_FlowPane_MainJobContainer.setPrefWidth(buildListWidth);
            layout_FlowPane_MainJobContainer.setStyle("-fx-border-color: grey;");

            Separator spr_buildInfoSeparator = new Separator();
            spr_buildInfoSeparator.setOrientation(Orientation.HORIZONTAL);
            spr_buildInfoSeparator.setPrefWidth(buildListWidth);

            VBox flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest("NA", "", "LOADING...", "NA", false);
            layout_FlowPane_MainJobContainer.getChildren().addAll(flowPane_LatestBuildInfoContainer, spr_buildInfoSeparator);

            //loop last N build of main job
            //to be move to thread
            for(int j = 0; j < listOfSubBuild; j++){
                VBox flowPane_buildInfoContainer = generateBuildInfoFlowPaneLatest("NA", "", "LOADING...", "NA", false);
                layout_FlowPane_MainJobContainer.getChildren().add(flowPane_buildInfoContainer);
            }

            Thread t = createThread(String.valueOf(i), allBuildName[i]);
            t.start();

            //get total height required to resize
            layout_FlowPane_MainJobContainer.setMinHeight(buildListHeight * 7);
            layout_FlowPane_MainJobContainer.setMinWidth(buildListWidth + 20);
            layout_FlowPane_Main.getChildren().add(layout_FlowPane_MainJobContainer);
        }
    }

    private VBox generateBuildInfoFlowPaneLatest(String buildNumber, String triggerTime, String triggerBy, String buildStatus, boolean isLatest) throws Exception{
        VBox layout_Vbox_buildInfoContainer = new VBox();
        FlowPane flowPane_buildNumberContainer = new FlowPane();
        FlowPane flowPane_buildStatusContainer = new FlowPane();

        Label lbl_buildNumber = new Label(buildNumber);
        Label lbl_buildInfo = new Label(triggerBy + "\n");
        Label lbl_buildDate = new Label(triggerTime);
        Label lbl_buildStatus = new Label(buildStatus);

        Button btn_deploy = new Button("DEPLOY THIS BUILD");
        btn_deploy.setOnAction(buttonEventHandler);
        btn_deploy.setId(buildNumber);
        btn_deploy.getStyleClass().addAll("button_standard", "button_standard_positive");

        layout_Vbox_buildInfoContainer.setPadding(new Insets(0,0,5,0));
        layout_Vbox_buildInfoContainer.setAlignment(Pos.TOP_CENTER);
        layout_Vbox_buildInfoContainer.setSpacing(10);

        if(buildStatus.equalsIgnoreCase("success")){
            layout_Vbox_buildInfoContainer.getStyleClass().add("deployment_buildInfo_ok");
            flowPane_buildNumberContainer.getStyleClass().add("deployment_buildStatus_ok");
            flowPane_buildStatusContainer.getStyleClass().add("deployment_buildStatus_ok");
        } else if(buildStatus.equalsIgnoreCase("fail") || buildStatus.equalsIgnoreCase("unstable")){
            layout_Vbox_buildInfoContainer.getStyleClass().add("deployment_buildInfo_fail");
            flowPane_buildNumberContainer.getStyleClass().add("deployment_buildStatus_fail");
            flowPane_buildStatusContainer.getStyleClass().add("deployment_buildStatus_fail");
        } else{
            layout_Vbox_buildInfoContainer.getStyleClass().add("deployment_buildInfo_idle");
            flowPane_buildNumberContainer.getStyleClass().add("deployment_buildStatus_idle");
            flowPane_buildStatusContainer.getStyleClass().add("deployment_buildStatus_idle");
        }

        flowPane_buildNumberContainer.setAlignment(Pos.CENTER);
        flowPane_buildStatusContainer.setAlignment(Pos.CENTER);

        lbl_buildInfo.setWrapText(true);
        lbl_buildInfo.setPrefHeight(buildListHeight/1.5);

        flowPane_buildNumberContainer.getChildren().add(lbl_buildNumber);
        flowPane_buildStatusContainer.getChildren().add(lbl_buildStatus);


        if(isLatest){
            layout_Vbox_buildInfoContainer.setMinHeight(buildListHeight + 50);
            layout_Vbox_buildInfoContainer.setMinWidth(buildListWidth);

            layout_Vbox_buildInfoContainer.getChildren().addAll(flowPane_buildNumberContainer, lbl_buildInfo, lbl_buildDate, flowPane_buildStatusContainer, btn_deploy);

        }else{
            layout_Vbox_buildInfoContainer.setMinHeight(buildListHeight);
            layout_Vbox_buildInfoContainer.setMinWidth(buildListWidth);

            layout_Vbox_buildInfoContainer.getChildren().addAll(flowPane_buildNumberContainer, lbl_buildInfo, lbl_buildDate, flowPane_buildStatusContainer);
        }

        return layout_Vbox_buildInfoContainer;
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

    private Thread createThread(String threadNumber, String mainBuildJobName){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    while(true){
                        if(!paneIsActive){
                            break;
                        }

                        final int threadNum = Integer.parseInt(threadNumber);

                        JenkinsApi jenkins = new JenkinsApi();

                        jenkins.getResponseFromJenkins("http://jenkins.jobstreet.com:8080/view/SiVA_DEV/job/" + mainBuildJobName + "/api/json", "GET");
                        ArrayList<String> buildList = jenkins.getLastBuildNumberExcludeLatest(listOfSubBuild);
                        String latestBuildUrl = jenkins.getMainJobBuildInfo().get("LatestBuildUrl");

                        jenkins.getResponseFromJenkins(latestBuildUrl + "api/json", "GET");
                        HashMap<String, String> mainJobBuildInfo = jenkins.getBuildInfo();

                        //get latest build info
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    VBox mainbuild_FlowPane = (VBox) layout_FlowPane_Main.getChildren().get(threadNum);

                                    mainbuild_FlowPane.getChildren().remove(0);
                                    VBox flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest(mainJobBuildInfo.get("FullDisplayName"), mainJobBuildInfo.get("TriggerDateTime"), mainJobBuildInfo.get("TriggerBy"), mainJobBuildInfo.get("Result"), true);
                                    mainbuild_FlowPane.getChildren().add(0, flowPane_LatestBuildInfoContainer);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                        //get sub build info
                        for(int i = 0; i < buildList.size(); i++){
                            if(!paneIsActive){
                                break;
                            }

                            final int j = i;
                            String url = buildList.get(i).split("\\|")[1] + "api/json";
                            jenkins.getResponseFromJenkins(url, "GET");
                            HashMap<String, String> buildInfo = jenkins.getBuildInfo();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        VBox mainbuild_FlowPane = (VBox) layout_FlowPane_Main.getChildren().get(threadNum);

                                        mainbuild_FlowPane.getChildren().remove(j + 2);
                                        VBox flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest(buildInfo.get("BuildNumber"), buildInfo.get("TriggerDateTime"), buildInfo.get("TriggerBy"), buildInfo.get("Result"), false);
                                        mainbuild_FlowPane.getChildren().add(j + 2, flowPane_LatestBuildInfoContainer);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        Thread.sleep(20000);
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

    public void setProduct(String product) throws Exception{
        Global.product = product;
    }
}
