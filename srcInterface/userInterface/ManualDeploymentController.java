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
    int buildListHeight = 130;
    int buildListWidth = 150;


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
        String[] allBuildName = Global.propertyMap.get("Siva_Dev_Build_List").split(",");

        //loop main job list
        for(int i = 0; i < allBuildName.length; i++){
            //populate container of main job
            VBox layout_FlowPane_MainJobContainer = new VBox();

            layout_FlowPane_MainJobContainer.setPadding(new Insets(5,5,5,5));
//            layout_FlowPane_MainJobContainer.setOrientation(Orientation.VERTICAL);
//            layout_FlowPane_MainJobContainer.setVgap(10);
            layout_FlowPane_MainJobContainer.setSpacing(10);
            layout_FlowPane_MainJobContainer.setAlignment(Pos.TOP_LEFT);
            layout_FlowPane_MainJobContainer.setPrefWidth(buildListWidth);
            layout_FlowPane_MainJobContainer.setStyle("-fx-border-color: grey;");

            Separator spr_buildInfoSeparator = new Separator();
            spr_buildInfoSeparator.setOrientation(Orientation.HORIZONTAL);
            spr_buildInfoSeparator.setPrefWidth(buildListWidth);

            VBox flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest("NA", "NA", "NA", "SUCCESSFUL", true);
            layout_FlowPane_MainJobContainer.getChildren().addAll(flowPane_LatestBuildInfoContainer, spr_buildInfoSeparator);

            //loop last N build of main job
            //to be move to thread
            for(int j = 0; j < listOfSubBuild; j++){
                VBox flowPane_buildInfoContainer = generateBuildInfoFlowPaneLatest("NA", "NA", "NA", "NA", false);
                layout_FlowPane_MainJobContainer.getChildren().add(flowPane_buildInfoContainer);
            }
            Thread t = createThread(String.valueOf(i), allBuildName[i]);
            t.start();

            //get total height required to resize
//            layout_FlowPane_MainJobContainer.setPrefHeight((buildListHeight + 60) * 7);
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
        Label lbl_buildInfo = new Label(triggerBy + "\n" +
                                    "Trigger Time: " + triggerTime + "\n");
        Label lbl_buildStatus = new Label("buildStatus");

//        layout_FlowPane_buildInfoContainer.setVgap(10);
        layout_Vbox_buildInfoContainer.setAlignment(Pos.CENTER);
        layout_Vbox_buildInfoContainer.setMinHeight(buildListHeight);
        layout_Vbox_buildInfoContainer.setMinWidth(buildListWidth);
        layout_Vbox_buildInfoContainer.setSpacing(10);

        flowPane_buildNumberContainer.setAlignment(Pos.CENTER);
//        flowPane_buildNumberContainer.setPrefWidth(buildListWidth);
        flowPane_buildNumberContainer.setStyle("-fx-background-color: grey;");
        flowPane_buildStatusContainer.setAlignment(Pos.CENTER);
//        flowPane_buildStatusContainer.setPrefWidth(buildListWidth);
        flowPane_buildStatusContainer.setStyle("-fx-background-color: grey;");
        lbl_buildInfo.setWrapText(true);
//        lbl_buildInfo.setPrefWidth(buildListWidth);
//        lbl_buildInfo.setPrefHeight(buildListHeight);
//        flowPane_buildNumberContainer.setPrefHeight(30);
//        flowPane_buildStatusContainer.setPrefHeight(30);

        flowPane_buildNumberContainer.getChildren().add(lbl_buildNumber);
        flowPane_buildStatusContainer.getChildren().add(lbl_buildStatus);

        layout_Vbox_buildInfoContainer.getChildren().addAll(flowPane_buildNumberContainer, lbl_buildInfo, flowPane_buildStatusContainer);
        layout_Vbox_buildInfoContainer.setStyle("-fx-border-color: red;");

        if(isLatest){

        }else{

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
                        HashMap<String, String> mainJobBuildInfo = jenkins.getMainJobBuildInfo();
                        ArrayList<String> buildList = jenkins.getLastBuildNumberExcludeLatest(listOfSubBuild);

                        //get latest build info
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    VBox mainbuild_FlowPane = (VBox) layout_FlowPane_Main.getChildren().get(threadNum);

                                    mainbuild_FlowPane.getChildren().remove(0);
                                    VBox flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest(mainJobBuildInfo.get("DisplayName"), mainJobBuildInfo.get("TriggerDateTime"), mainJobBuildInfo.get("TriggerBy"), mainJobBuildInfo.get("Result"), true);
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
                                        VBox flowPane_LatestBuildInfoContainer = generateBuildInfoFlowPaneLatest(buildInfo.get("BuildNumber"), buildInfo.get("TriggerDateTime"), buildInfo.get("TriggerBy"), buildInfo.get("Result"), true);
                                        mainbuild_FlowPane.getChildren().add(j + 2, flowPane_LatestBuildInfoContainer);
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

    public static void stopThread() throws Exception{
        paneIsActive = false;
    }
}
