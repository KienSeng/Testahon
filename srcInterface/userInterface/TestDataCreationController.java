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
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void generateModulePane() throws Exception{
        HBox layout_Hbox_ProductContainer = new HBox();
        Separator spr_ProductSeparator = new Separator();

        ComboBox cmb_product = new ComboBox();
        Label lbl_product = new Label("Select a module: ");

        lbl_product.setPrefWidth(Global.standardLabelWidth);

        cmb_product.setId("cmb_product");
        cmb_product.setPrefWidth(Global.standardTextBoxWidth);
        cmb_product.getItems().addAll(settingMap.get("UI_Products").split(","));
        cmb_product.valueProperty().addListener(comboBoxListener);

        layout_Hbox_ProductContainer.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        layout_Hbox_ProductContainer.getChildren().addAll(lbl_product, cmb_product);

        spr_ProductSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_ProductSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));

        layout_FlowPane_Main.getChildren().addAll(layout_Hbox_ProductContainer,spr_ProductSeparator);
    }

    private void generateJobPane() throws Exception{
        Label lbl_jobPreset = new Label("Preset: ");
        ComboBox cmb_jobPreset = new ComboBox();
        Separator spr_jobSeparator = new Separator();

        lbl_jobPreset.setPrefWidth(Global.standardLabelWidth);
        cmb_jobPreset.setPrefWidth(Global.standardTextBoxWidth);

        cmb_jobPreset.getItems().addAll(settingMap.get("UI_Job_Preset").split(","));

        spr_jobSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_jobSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));
    }

    private void generateCandiatePane() throws Exception{
        Label lbl_myjsPreset = new Label("Preset: ");
        ComboBox cmb_myjsPreset = new ComboBox();
        Separator spr_candidateSeparator = new Separator();

        lbl_myjsPreset.setPrefWidth(Global.standardLabelWidth);

        cmb_myjsPreset.setId("cmb_myjsPreset");
        cmb_myjsPreset.setPrefWidth(Global.standardTextBoxWidth);

        cmb_myjsPreset.getItems().addAll(settingMap.get("UI_Myjs_Preset").split(","));

        Label lbl_username = new Label("Username: ");
        Label lbl_password = new Label("Password: ");
        TextField txt_username = new TextField();
        TextField txt_password = new TextField();

        lbl_username.setPrefWidth(Global.standardLabelWidth);
        lbl_password.setPrefWidth(Global.standardLabelWidth);
        txt_username.setPrefWidth(Global.standardTextBoxWidth);
        txt_password.setPrefWidth(Global.standardTextBoxWidth);

        spr_candidateSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_candidateSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));
    }

    private void generateConsole() throws Exception{
        ScrollPane layout_ScrollPane_Console = new ScrollPane();
        VBox layout_vbox_container = new VBox();

        layout_vbox_container.setAlignment(Pos.CENTER);
        layout_vbox_container.setSpacing(10);

        Button btn_clearConsole = new Button("Clear Console");

        btn_clearConsole.setPrefWidth(Global.standardButtonWidth);
        btn_clearConsole.getStyleClass().add("button_standard_negative");

        txt_console = new TextArea();
        txt_console.setWrapText(true);
        txt_console.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                layout_ScrollPane_Console.setVvalue(Double.MAX_VALUE);
            }
        });

        layout_ScrollPane_Console.getStyleClass().add("testDate_console_textArea");
        layout_ScrollPane_Console.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        layout_ScrollPane_Console.setContent(txt_console);

        layout_vbox_container.getChildren().addAll(layout_ScrollPane_Console, btn_clearConsole);
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
