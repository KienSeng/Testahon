package userInterface;

import JenkinsDeployment.JenkinsApi;
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

    ArrayList<String> downstreamBuild = new ArrayList<>();

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

    public void getParentDownstreamBuild(String url) throws Exception{
        FlowPane layout_FlowPane_MainBuildContainer = new FlowPane();

        layout_FlowPane_MainBuildContainer.setHgap(0);
        layout_FlowPane_MainBuildContainer.setPadding(new Insets(10,10,10,10));

        JenkinsApi jenkins = new JenkinsApi();

        jenkins.getResponseFromJenkins(url, "GET");
        downstreamBuild = jenkins.getDownstreamBuild();

        for(int i = 0; i < downstreamBuild.size(); i++){
            Separator spr_splitLatestAndSub = new Separator();
            FlowPane layout_FlowPane_LatestBuildContainer;

            String[] splittedString = downstreamBuild.get(i).split("\\|");
            String buildName = splittedString[0];
            String buildUrl = splittedString[1];
            HashMap<String, String> buildInfo;

            jenkins.getResponseFromJenkins(buildUrl,"GET");
            buildInfo = jenkins.getBuildInfo();
            String triggerTime = buildInfo.get("TriggerDateTime").replace("+0800", "").trim();

            layout_FlowPane_MainBuildContainer.setOrientation(Orientation.VERTICAL);
            layout_FlowPane_MainBuildContainer.setVgap(10);
            layout_FlowPane_MainBuildContainer.setAlignment(Pos.TOP_CENTER);

            layout_FlowPane_LatestBuildContainer = populateLatestBuildPane(buildName, buildInfo.get("TriggerBy"), triggerTime, false);
            /*
            map.put("BuildName", api.getValueFromResponse(response, "number"));
            map.put("FullDisplayName", api.getValueFromResponse(response, "fullDisplayName"));
            map.put("Result", api.getValueFromResponse(response, "result"));
            map.put("URL", api.getValueFromResponse(response, "url"));
            map.put("TriggerBy", api.getValueFromResponse(response, "culprits.fullName").replace("[","").replace("]",""));
             */

            spr_splitLatestAndSub.setPadding(new Insets(10,10,10,10));

            layout_FlowPane_SubBuildContainer = new FlowPane();
            layout_FlowPane_SubBuildContainer.setId("layout_FlowPane_SubBuildContainer_" + buildName);
            layout_FlowPane_SubBuildContainer.setHgap(15);
            layout_FlowPane_SubBuildContainer.setAlignment(Pos.CENTER);
            layout_FlowPane_SubBuildContainer.setOrientation(Orientation.VERTICAL);
            layout_FlowPane_SubBuildContainer.setPadding(new Insets(10,10,10,10));

            layout_FlowPane_MainBuildContainer.getChildren().addAll(layout_FlowPane_LatestBuildContainer, spr_splitLatestAndSub, layout_FlowPane_SubBuildContainer);
            layout_FlowPane_Main.getChildren().add(layout_FlowPane_MainBuildContainer);
        }
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
}