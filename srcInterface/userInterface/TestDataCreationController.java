package userInterface;

import PropertiesFile.PropertiesFileReader;
import TestDataCreation.Myjs;
import TestDataCreation.Siva;
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
import sun.reflect.annotation.ExceptionProxy;

/**
 * Created by kienseng on 5/1/2016.
 */
public class TestDataCreationController implements Initializable{
    @FXML private FlowPane layout_FlowPane_Main;
    private TextArea txt_console;
    private TextField txt_myjsFirstName;
    private TextField txt_myjsLastName;
    private TextField txt_myjsEmail;
    private TextField txt_myjsPassword;
    private ComboBox cmb_product;
    private ComboBox cmb_environment;
    private ComboBox cmb_myjsPreset;
    private ComboBox cmb_jobPreset;
    private TextField txt_jobUsername;
    private TextField txt_jobPassword;
    private TextField txt_jobJobTitle;

    private static HashMap<String, String> settingMap = new HashMap<>();
    public static String consoleMessage = "";

    private Myjs myjs;
    private Siva siva;

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
        GridPane layout_GridPane_ProductContainer = new GridPane();

        Label lbl_Environment = new Label("Environment: ");
        Separator spr_ProductSeparator = new Separator();

        cmb_product = new ComboBox();
        Label lbl_product = new Label("Select a module: ");

        lbl_Environment.setPrefWidth(Global.standardLabelWidth);
        lbl_product.setPrefWidth(Global.standardLabelWidth);

        cmb_product.setId("cmb_product");
        cmb_product.setPrefWidth(Global.standardTextBoxWidth);
        cmb_product.getItems().addAll(settingMap.get("UI_Products").split(","));
        cmb_product.setOnAction(comboBoxListener);

        cmb_environment = new ComboBox();
        cmb_environment.setId("cmb_environment");
        cmb_environment.setPrefWidth(Global.standardTextBoxWidth);
        cmb_environment.getItems().addAll(settingMap.get("UI_Environment").split(","));
        cmb_environment.setOnAction(comboBoxListener);

        layout_GridPane_ProductContainer.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        layout_GridPane_ProductContainer.setVgap(10);
        layout_GridPane_ProductContainer.add(lbl_Environment,0,0);
        layout_GridPane_ProductContainer.add(cmb_environment,1,0);
        layout_GridPane_ProductContainer.add(lbl_product,0,1);
        layout_GridPane_ProductContainer.add(cmb_product,1,1);

        spr_ProductSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_ProductSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));

        layout_FlowPane_Main.getChildren().addAll(layout_GridPane_ProductContainer,spr_ProductSeparator);
    }

    private void generateJobPane() throws Exception{
        GridPane layout_GridPane_JobInfoPane = new GridPane();

        layout_GridPane_JobInfoPane.setHgap(10);
        layout_GridPane_JobInfoPane.setVgap(10);

        Label lbl_jobPreset = new Label("Preset:");
        Label lbl_jobUsername = new Label("Login ID:");
        Label lbl_jobPassword = new Label("Password:");
        Label lbl_jobJobTitle = new Label("Job Title:");

        Button btn_sivaStart = new Button("Start");
        Button btn_sivaClear = new Button("Clear");

        btn_sivaStart.setId("btn_sivaStart");
        btn_sivaClear.setId("btn_sivaclear");
        btn_sivaStart.setOnAction(buttonEvent);
        btn_sivaClear.setOnAction(buttonEvent);
        btn_sivaStart.setPrefWidth(Global.standardButtonWidth + 80);
        btn_sivaClear.setPrefWidth(Global.standardButtonWidth + 80);
        btn_sivaStart.setPrefHeight(35);
        btn_sivaClear.setPrefHeight(35);
        btn_sivaStart.getStyleClass().addAll("button_standard_positive", "button_standard");
        btn_sivaClear.getStyleClass().addAll("button_standard_negative", "button_standard");

        cmb_jobPreset = new ComboBox();
        txt_jobUsername = new TextField();
        txt_jobPassword = new TextField();
        txt_jobJobTitle = new TextField();

        Separator spr_jobSeparator = new Separator();

        lbl_jobPreset.setPrefWidth(Global.standardLabelWidth);
        lbl_jobUsername.setPrefWidth(Global.standardLabelWidth);
        lbl_jobPassword.setPrefWidth(Global.standardLabelWidth);
        lbl_jobJobTitle.setPrefWidth(Global.standardLabelWidth);

        txt_jobUsername.setPrefWidth(Global.standardTextBoxWidth);
        txt_jobPassword.setPrefWidth(Global.standardTextBoxWidth);
        txt_jobJobTitle.setPrefWidth(Global.standardTextBoxWidth);
        cmb_jobPreset.setPrefWidth(Global.standardTextBoxWidth);

        cmb_jobPreset.getItems().addAll(settingMap.get("UI_Job_Preset").split(","));

        layout_GridPane_JobInfoPane.add(lbl_jobPreset,0,0);
        layout_GridPane_JobInfoPane.add(lbl_jobUsername,0,1);
//        layout_GridPane_JobInfoPane.add(lbl_jobPassword,0,2);
        layout_GridPane_JobInfoPane.add(lbl_jobJobTitle,0,2);

        layout_GridPane_JobInfoPane.add(cmb_jobPreset,1,0);
        layout_GridPane_JobInfoPane.add(txt_jobUsername,1,1);
//        layout_GridPane_JobInfoPane.add(txt_jobPassword,1,2);
        layout_GridPane_JobInfoPane.add(txt_jobJobTitle,1,2);

        spr_jobSeparator.setOrientation(Orientation.HORIZONTAL);
        spr_jobSeparator.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));

        VBox layout_FlowPane_ButtonContainer = new VBox();
        layout_FlowPane_ButtonContainer.setPadding(new Insets(10,0,0,50));
        layout_FlowPane_ButtonContainer.setSpacing(10);
        layout_FlowPane_ButtonContainer.getChildren().addAll(btn_sivaStart, btn_sivaClear);

        FlowPane layout_FlowPane_JobContainer = new FlowPane();
        layout_FlowPane_JobContainer.setHgap(20);
        layout_FlowPane_JobContainer.setVgap(20);
        layout_FlowPane_JobContainer.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));
        layout_FlowPane_JobContainer.getChildren().addAll(layout_GridPane_JobInfoPane, layout_FlowPane_ButtonContainer);
        layout_FlowPane_Main.getChildren().addAll(layout_FlowPane_JobContainer, spr_jobSeparator);
    }

    private void generateCandidatePane() throws Exception{
        Label lbl_myjsPreset = new Label("Preset:");
        cmb_myjsPreset = new ComboBox();

        Separator spr_candidateSeparator = new Separator();

        lbl_myjsPreset.setPrefWidth(Global.standardLabelWidth);

        cmb_myjsPreset.setId("cmb_myjsPreset");
        cmb_myjsPreset.setPrefWidth(Global.standardTextBoxWidth);
        cmb_myjsPreset.getItems().addAll(settingMap.get("UI_Myjs_Preset").split(","));

        Label lbl_myjsFirstName = new Label("First Name:");
        Label lbl_myjsLastName = new Label("Last Name:");
        Label lbl_myjsEmail = new Label("Email:");
        Label lbl_myjsPassword = new Label("Password:");

        txt_myjsFirstName = new TextField();
        txt_myjsLastName = new TextField();
        txt_myjsEmail = new TextField();
        txt_myjsPassword = new TextField();

        lbl_myjsFirstName.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsLastName.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsEmail.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsPassword.setPrefWidth(Global.standardLabelWidth);

        txt_myjsFirstName.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsLastName.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsEmail.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsPassword.setPrefWidth(Global.standardTextBoxWidth);

        Button btn_myjsStart = new Button("Start");
        Button btn_myjsClear = new Button("Clear");
        btn_myjsStart.setId("btn_myjsStart");
        btn_myjsClear.setId("btn_myjsClear");
        btn_myjsStart.setOnAction(buttonEvent);
        btn_myjsClear.setOnAction(buttonEvent);
        btn_myjsStart.setPrefWidth(Global.standardButtonWidth + 80);
        btn_myjsClear.setPrefWidth(Global.standardButtonWidth + 80);
        btn_myjsStart.setPrefHeight(35);
        btn_myjsClear.setPrefHeight(35);
        btn_myjsStart.getStyleClass().addAll("button_standard_positive", "button_standard");
        btn_myjsClear.getStyleClass().addAll("button_standard_negative", "button_standard");

        VBox layout_Vbox_ButtonContainer = new VBox();
        layout_Vbox_ButtonContainer.setPadding(new Insets(25,0,0,50));
        layout_Vbox_ButtonContainer.setSpacing(50);
        layout_Vbox_ButtonContainer.getChildren().addAll(btn_myjsStart, btn_myjsClear);

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

        FlowPane layout_FlowPane_MyjsContainer = new FlowPane();
        layout_FlowPane_MyjsContainer.setHgap(20);
        layout_FlowPane_MyjsContainer.setVgap(20);
        layout_FlowPane_MyjsContainer.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(20));
        layout_FlowPane_MyjsContainer.getChildren().addAll(layaout_GridPane_TestDataContainer, layout_Vbox_ButtonContainer);

        layout_FlowPane_Main.getChildren().addAll(layout_FlowPane_MyjsContainer, spr_candidateSeparator);
    }

    private void generateConsole() throws Exception{
        ScrollPane layout_ScrollPane_Console = new ScrollPane();
        VBox layout_vbox_container = new VBox();

        layout_vbox_container.setAlignment(Pos.CENTER);
        layout_vbox_container.setSpacing(10);

        Button btn_clearConsole = new Button("Clear Console");

        btn_clearConsole.setId("btn_clearConsole");
        btn_clearConsole.setPrefWidth(Global.standardButtonWidth + 40);
        btn_clearConsole.setPrefHeight(40);
        btn_clearConsole.getStyleClass().addAll("button_standard_positive", "button_standard");

        txt_console = new TextArea();
        txt_console.setEditable(false);
        txt_console.setWrapText(true);
        txt_console.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        txt_console.getStyleClass().add("testData_console_textArea");
        txt_console.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                layout_ScrollPane_Console.setVvalue(Double.MAX_VALUE);
            }
        });

        layout_ScrollPane_Console.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(40));
        layout_ScrollPane_Console.setPrefHeight(250);
        layout_ScrollPane_Console.getStyleClass().add("testData_console_textArea");
        layout_ScrollPane_Console.setContent(txt_console);

        layout_vbox_container.getChildren().addAll(layout_ScrollPane_Console, btn_clearConsole);
        layout_FlowPane_Main.getChildren().addAll(layout_vbox_container);
    }

    private EventHandler buttonEvent = event -> {
        try{
            Button btn = (Button) event.getSource();

            switch(btn.getId()){
                case "btn_myjsStart":
                    switch(cmb_myjsPreset.getSelectionModel().getSelectedItem().toString().toLowerCase()){
                        case "verified candidate with no resumes":
                            createNewCandidateWithVerification();
                            break;

                        case "candidate without email validation":
                            createNewCandidateNoVerification();
                            break;
                    }
                    break;

                case "btn_sivaStart":
                    switch(cmb_jobPreset.getSelectionModel().getSelectedItem().toString().toLowerCase()){
                        case "normal job posting":
                            createNormalJob();
                            break;

                        case "internship job":
//                            createInternshipJob();
                            break;
                    }
                    break;

                case "btn_myjsClear":
                    txt_myjsEmail.clear();
                    txt_myjsFirstName.clear();
                    txt_myjsLastName.clear();
                    txt_myjsPassword.clear();
                    break;

                case "btn_sivaClear":
                    txt_myjsEmail.clear();
                    txt_myjsFirstName.clear();
                    txt_myjsLastName.clear();
                    txt_myjsPassword.clear();
                    break;

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

    private EventHandler comboBoxListener = event -> {
        ComboBox cmb = (ComboBox) event.getSource();

        switch(cmb.getId()){
            case "cmb_product":
                try{
                    layout_FlowPane_Main.getChildren().remove(2, layout_FlowPane_Main.getChildren().size());

                    if(cmb_product.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("siva")){
                        generateJobPane();
                        generateConsole();
                    }else if(cmb_product.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("myjs")){
                        generateCandidatePane();
                        generateConsole();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    };

    public void displayInConsole(final String message) throws Exception{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txt_console.appendText(message + "\n");
            }
        });
    }

    private void createNewCandidateWithVerification() throws Exception{
        String firstName = txt_myjsFirstName.getText();
        String lastName = txt_myjsLastName.getText();
        String email = txt_myjsEmail.getText();
        String password = txt_myjsPassword.getText();

        myjs = new Myjs();
        myjs.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    myjs.candidateSignUp(firstName, lastName, email, password);
                    myjs.updateCandidateVerificationStatus(email, 1);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void createNewCandidateNoVerification() throws Exception{
        String firstName = txt_myjsFirstName.getText();
        String lastName = txt_myjsLastName.getText();
        String email = txt_myjsEmail.getText();
        String password = txt_myjsPassword.getText();

        myjs = new Myjs();
        myjs.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    myjs.candidateSignUp(firstName, lastName, email, password);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void createNormalJob() throws Exception{
        String username = txt_jobUsername.getText();
        String jobTitle = txt_jobJobTitle.getText();

        Siva siva = new Siva();
        siva.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    siva.setUsername(username);
                    siva.createSavedJob(jobTitle);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
