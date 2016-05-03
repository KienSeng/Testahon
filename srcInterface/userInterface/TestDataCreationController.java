package userInterface;

import PropertiesFile.PropertiesFileReader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import Global.Global;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by kienseng on 5/1/2016.
 */
public class TestDataCreationController implements Initializable{
    @FXML private FlowPane layout_FlowPane_Main;
    private TextArea txt_console;

    static HashMap<String, String> settingMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layout_FlowPane_Main.setPadding(new Insets(10,10,10,10));
        layout_FlowPane_Main.setVgap(10);
        layout_FlowPane_Main.setHgap(10);

        try{
            PropertiesFileReader prop = new PropertiesFileReader();
            settingMap = prop.getAllFromPropertyFile("TestDataCreationSettings.properties");
            generateModulePane();
            generateCandiatePane();
            generateConsole();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void generateModulePane() throws Exception{
        HBox layout_Hbox_ProductContainer = new HBox();
        GridPane layout_GridPane_ProductContainer = new GridPane();

        Label lbl_Environment = new Label("Environment: ");
        ComboBox cmb_Environment = new ComboBox();
        Separator spr_ProductSeparator = new Separator();

        ComboBox cmb_product = new ComboBox();
        Label lbl_product = new Label("Select a module: ");

        lbl_Environment.setPrefWidth(Global.standardLabelWidth);
        lbl_product.setPrefWidth(Global.standardLabelWidth);

        cmb_product.setId("cmb_product");
        cmb_product.setPrefWidth(Global.standardTextBoxWidth);
        cmb_product.getItems().addAll(settingMap.get("UI_Products").split(","));
        cmb_product.valueProperty().addListener(comboBoxListener);

        cmb_Environment.setId("cmb_Environment");
        cmb_Environment.setPrefWidth(Global.standardTextBoxWidth);
        cmb_Environment.getItems().addAll(settingMap.get("UI_Environment").split(","));
        cmb_Environment.valueProperty().addListener(comboBoxListener);

        layout_GridPane_ProductContainer.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        layout_GridPane_ProductContainer.setVgap(10);
        layout_GridPane_ProductContainer.add(lbl_Environment,0,0);
        layout_GridPane_ProductContainer.add(cmb_Environment,1,0);
        layout_GridPane_ProductContainer.add(lbl_product,0,1);
        layout_GridPane_ProductContainer.add(cmb_product,1,1);

//        layout_Hbox_ProductContainer.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
//        layout_Hbox_ProductContainer.getChildren().addAll(lbl_product, cmb_product);

        spr_ProductSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_ProductSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));

        layout_FlowPane_Main.getChildren().addAll(layout_GridPane_ProductContainer,spr_ProductSeparator);
    }

    private void generateJobPane() throws Exception{
        Label lbl_jobPreset = new Label("Preset:");
        ComboBox cmb_jobPreset = new ComboBox();
        Separator spr_jobSeparator = new Separator();

        lbl_jobPreset.setPrefWidth(Global.standardLabelWidth);
        cmb_jobPreset.setPrefWidth(Global.standardTextBoxWidth);

        cmb_jobPreset.getItems().addAll(settingMap.get("UI_Job_Preset").split(","));

        spr_jobSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_jobSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));
    }

    private void generateCandiatePane() throws Exception{
        Label lbl_myjsPreset = new Label("Preset:");
        ComboBox cmb_myjsPreset = new ComboBox();

        Separator spr_candidateSeparator = new Separator();

        lbl_myjsPreset.setPrefWidth(Global.standardLabelWidth);

        cmb_myjsPreset.setId("cmb_myjsPreset");
        cmb_myjsPreset.setPrefWidth(Global.standardTextBoxWidth);
        cmb_myjsPreset.getItems().addAll(settingMap.get("UI_Myjs_Preset").split(","));

        Label lbl_myjsFirstName = new Label("First Name:");
        Label lbl_myjsLastName = new Label("Last Name:");
        Label lbl_myjsEmail = new Label("Email:");
        Label lbl_myjsPassword = new Label("Password:");
        TextField txt_myjsFirstName = new TextField();
        TextField txt_myjsLastName = new TextField();
        TextField txt_myjsEmail = new TextField();
        TextField txt_myjsPassword = new TextField();

        lbl_myjsFirstName.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsLastName.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsEmail.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsPassword.setPrefWidth(Global.standardLabelWidth);

        txt_myjsFirstName.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsLastName.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsEmail.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsPassword.setPrefWidth(Global.standardTextBoxWidth);

        Button btn_myjsStart = new Button("Start Create Test Candidate");
        btn_myjsStart.setPrefWidth(Global.standardButtonWidth + 80);

        GridPane layaout_GridPane_TestDataContainer = new GridPane();
        layaout_GridPane_TestDataContainer.setHgap(10);
        layaout_GridPane_TestDataContainer.setVgap(10);

        layaout_GridPane_TestDataContainer.add(lbl_myjsPreset,0,0);
        layaout_GridPane_TestDataContainer.add(lbl_myjsEmail,0,1);
        layaout_GridPane_TestDataContainer.add(lbl_myjsPassword,0,2);
        layaout_GridPane_TestDataContainer.add(lbl_myjsFirstName,0,3);
        layaout_GridPane_TestDataContainer.add(lbl_myjsLastName,0,4);

        layaout_GridPane_TestDataContainer.add(cmb_myjsPreset,1,0);
        layaout_GridPane_TestDataContainer.add(txt_myjsEmail,1,1);
        layaout_GridPane_TestDataContainer.add(txt_myjsPassword,1,2);
        layaout_GridPane_TestDataContainer.add(txt_myjsFirstName,1,3);
        layaout_GridPane_TestDataContainer.add(txt_myjsLastName,1,4);

        spr_candidateSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_candidateSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));

        layout_FlowPane_Main.getChildren().addAll(layaout_GridPane_TestDataContainer, spr_candidateSeparator);
    }

    private void generateConsole() throws Exception{
        ScrollPane layout_ScrollPane_Console = new ScrollPane();
        VBox layout_vbox_container = new VBox();

        layout_vbox_container.setAlignment(Pos.CENTER);
        layout_vbox_container.setSpacing(10);

        Button btn_clearConsole = new Button("Clear Console");

        btn_clearConsole.setId("btn_clearConsole");
        btn_clearConsole.setPrefWidth(Global.standardButtonWidth + 80);
        btn_clearConsole.getStyleClass().add("button_standard_negative");

        txt_console = new TextArea();
        txt_console.setWrapText(true);
        txt_console.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        txt_console.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                layout_ScrollPane_Console.setVvalue(Double.MAX_VALUE);
            }
        });

        layout_ScrollPane_Console.getStyleClass().add("testDate_console_textArea");
        layout_ScrollPane_Console.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        layout_ScrollPane_Console.setPrefHeight(250);
        layout_ScrollPane_Console.setContent(txt_console);

        layout_vbox_container.getChildren().addAll(layout_ScrollPane_Console, btn_clearConsole);
        layout_FlowPane_Main.getChildren().addAll(layout_vbox_container);
    }

    public void displayInConsole(String message) throws Exception{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txt_console.appendText(message);
            }
        });
    }

    private EventHandler buttonEvent = event -> {
        try{
            Button btn = (Button) event.getSource();

            switch(btn.getId()){
                case "btn_clearConsole":
                    txt_console.clear();
                    break;

                default:
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    };

    private ChangeListener comboBoxListener = (ChangeListener<String>) (observable, oldValue, newValue) -> {

    };
}
