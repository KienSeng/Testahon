package userInterface;

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
import java.util.EventListener;
import java.util.ResourceBundle;

/**
 * Created by kienseng on 4/19/2016.
 */
public class ManualDeploymentController implements Initializable {
    @FXML
    private FlowPane layout_FlowPane_Main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            generatePane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePane() throws Exception {
        VBox layout_Vbox_SivaDev = new VBox();
        TilePane layout_TilePane_DeployButton = new TilePane();
        Separator spr_separator = new Separator();
        FlowPane layout_FlowPane_ButtonContainer = new FlowPane();
        FlowPane layout_FlowPane_BuildContainer = new FlowPane();

        Label lbl_devStartedBy = new Label("Started By:");
        Label lbl_devBuildNumber = new Label("Build Number:");
        Label lbl_devBuildName = new Label();
        Label lbl_devBuildDate = new Label("Date Started:");
        Label lbl_devStartDescription = new Label();

        Button btn_deployDev = new Button("Deploy to DEV");
        Button btn_deployQA = new Button("Deploy to QA");
        Button btn_deployRCJbos = new Button("Deploy to Siva RC JBOS");
        Button btn_deployTA = new Button("Deploy to TA");

        Double vBoxWidth = 300.0;
        Double buttonWidth = 100.0;
        Double buttonHeight = 80.0;

        layout_Vbox_SivaDev.setId("layout_Vbox_SivaDev");
        layout_Vbox_SivaDev.setPadding(new Insets(15, 15, 15, 15));
        layout_Vbox_SivaDev.setMaxWidth(vBoxWidth);
        layout_Vbox_SivaDev.setAlignment(Pos.TOP_CENTER);

        layout_FlowPane_Main.setPadding(new Insets(10,10,10,10));
        layout_FlowPane_Main.setRowValignment(VPos.TOP);

        layout_FlowPane_ButtonContainer.setAlignment(Pos.TOP_CENTER);
        layout_FlowPane_ButtonContainer.setHgap(20);
        layout_FlowPane_ButtonContainer.setVgap(20);
        layout_FlowPane_ButtonContainer.getChildren().addAll(btn_deployDev, btn_deployQA, btn_deployRCJbos, btn_deployTA);

        layout_FlowPane_BuildContainer.setOrientation(Orientation.VERTICAL);
        layout_FlowPane_BuildContainer.setPadding(new Insets(10, 0, 10, 0));

        spr_separator.setPadding(new Insets(10,10,10,10));

        btn_deployDev.setId("btn_deployDev");
        btn_deployQA.setId("btn_deployQA");
        btn_deployRCJbos.setId("btn_deployRCJbos");
        btn_deployTA.setId("btn_deployTA");

        btn_deployDev.setWrapText(true);
        btn_deployQA.setWrapText(true);
        btn_deployRCJbos.setWrapText(true);
        btn_deployTA.setWrapText(true);

        btn_deployDev.textAlignmentProperty().set(TextAlignment.CENTER);
        btn_deployQA.textAlignmentProperty().set(TextAlignment.CENTER);
        btn_deployRCJbos.textAlignmentProperty().set(TextAlignment.CENTER);
        btn_deployTA.textAlignmentProperty().set(TextAlignment.CENTER);

        btn_deployDev.setPrefSize(buttonWidth, buttonHeight);
        btn_deployQA.setPrefSize(buttonWidth, buttonHeight);
        btn_deployRCJbos.setPrefSize(buttonWidth, buttonHeight);
        btn_deployTA.setPrefSize(buttonWidth, buttonHeight);

        layout_Vbox_SivaDev.getChildren().add(layout_FlowPane_ButtonContainer);
        layout_Vbox_SivaDev.getChildren().add(spr_separator);
        layout_Vbox_SivaDev.getChildren().add(layout_FlowPane_BuildContainer);

        layout_FlowPane_Main.getChildren().add(0, layout_Vbox_SivaDev);
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