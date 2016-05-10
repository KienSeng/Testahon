package userInterface;

import Global.Global;
import PropertiesFile.PropertiesFileReader;
import TestDataCreation.Myjs;
import TestDataCreation.Siva;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

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
    private ComboBox cmb_jobPackage;
    private TextField txt_jobUsername;
    private TextField txt_jobPassword;
    private TextField txt_jobJobTitle;
    private TextField txt_myjsPositionTitle;
    private TextField txt_myjsCompanyName;
    private Button btn_sivaStart;
    private Button btn_sivaClear;
    private Button btn_myjsStart;
    private Button btn_myjsClear;
    private Button btn_sivaGetPackage;

    private static HashMap<String, String> settingMap = new HashMap<>();
    private static boolean packageButtonClicked = false;

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
        Label lbl_jobPackage = new Label("Package:");

        btn_sivaStart = new Button("Start");
        btn_sivaClear = new Button("Clear");
        btn_sivaGetPackage = new Button("GET Packages");

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

        btn_sivaGetPackage.setId("btn_sivaGetPackage");
        btn_sivaGetPackage.setPrefWidth(Global.standardButtonWidth + 40);
        btn_sivaGetPackage.setOnAction(buttonEvent);
        btn_sivaGetPackage.getStyleClass().addAll("button_standard_positive", "button_standard");

        cmb_jobPreset = new ComboBox();
        cmb_jobPackage = new ComboBox();
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
        cmb_jobPackage.setPrefWidth(Global.standardTextBoxWidth);

        cmb_jobPreset.getItems().addAll(settingMap.get("UI_Job_Preset").split(","));

        layout_GridPane_JobInfoPane.add(lbl_jobPreset,0,0);
        layout_GridPane_JobInfoPane.add(lbl_jobUsername,0,1);
//        layout_GridPane_JobInfoPane.add(lbl_jobPassword,0,2);
        layout_GridPane_JobInfoPane.add(lbl_jobJobTitle,0,2);
        layout_GridPane_JobInfoPane.add(lbl_jobPackage,0,3);

        layout_GridPane_JobInfoPane.add(cmb_jobPreset,1,0);
        layout_GridPane_JobInfoPane.add(txt_jobUsername,1,1);
//        layout_GridPane_JobInfoPane.add(txt_jobPassword,1,2);
        layout_GridPane_JobInfoPane.add(txt_jobJobTitle,1,2);
        layout_GridPane_JobInfoPane.add(cmb_jobPackage,1,3);
        layout_GridPane_JobInfoPane.add(btn_sivaGetPackage,2,3);

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
        Label lbl_myjsPositionTitle = new Label("Position Title:");
        Label lbl_myjsCompanyName = new Label("Company Name:");

        txt_myjsFirstName = new TextField();
        txt_myjsLastName = new TextField();
        txt_myjsEmail = new TextField();
        txt_myjsPassword = new TextField();
        txt_myjsPositionTitle = new TextField();
        txt_myjsCompanyName = new TextField();

        lbl_myjsFirstName.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsLastName.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsEmail.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsPassword.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsPositionTitle.setPrefWidth(Global.standardLabelWidth);
        lbl_myjsCompanyName.setPrefWidth(Global.standardLabelWidth);

        txt_myjsFirstName.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsLastName.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsEmail.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsPassword.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsPositionTitle.setPrefWidth(Global.standardTextBoxWidth);
        txt_myjsCompanyName.setPrefWidth(Global.standardTextBoxWidth);

        btn_myjsStart = new Button("Start");
        btn_myjsClear = new Button("Clear");
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
        layaout_GridPane_TestDataContainer.add(lbl_myjsPositionTitle,2,0);
        layaout_GridPane_TestDataContainer.add(lbl_myjsCompanyName,2,1);

        layaout_GridPane_TestDataContainer.add(cmb_myjsPreset,1,0);
        layaout_GridPane_TestDataContainer.add(txt_myjsEmail,1,1);
        layaout_GridPane_TestDataContainer.add(txt_myjsPassword,1,2);
        layaout_GridPane_TestDataContainer.add(txt_myjsFirstName,1,3);
        layaout_GridPane_TestDataContainer.add(txt_myjsLastName,1,4);
        layaout_GridPane_TestDataContainer.add(txt_myjsPositionTitle,3,0);
        layaout_GridPane_TestDataContainer.add(txt_myjsCompanyName,3,1);

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
//        ScrollPane layout_ScrollPane_Console = new ScrollPane();
        VBox layout_vbox_container = new VBox();

        layout_vbox_container.setSpacing(10);
        layout_vbox_container.setAlignment(Pos.CENTER);

        Button btn_clearConsole = new Button("Clear Console");

        btn_clearConsole.setId("btn_clearConsole");
        btn_clearConsole.setPrefWidth(Global.standardButtonWidth + 40);
        btn_clearConsole.setPrefHeight(40);
        btn_clearConsole.getStyleClass().addAll("button_standard_negative", "button_standard");
        btn_clearConsole.setOnAction(buttonEvent);

        txt_console = new TextArea();
        txt_console.setEditable(false);
        txt_console.setWrapText(true);
        txt_console.setPrefWidth(650);
        txt_console.setPrefHeight(350);
        txt_console.getStyleClass().add("testData_console_textArea");

        layout_vbox_container.getChildren().addAll(txt_console, btn_clearConsole);
        layout_FlowPane_Main.getChildren().addAll(layout_vbox_container);
    }

    private EventHandler buttonEvent = event -> {
        try{
            Button btn = (Button) event.getSource();

            switch(btn.getId()){
                case "btn_myjsStart":
                    disableButton(true);
                    txt_console.clear();
                    switch(cmb_myjsPreset.getSelectionModel().getSelectedItem().toString().toLowerCase()){
                        case "verified candidate with no resumes":
                            createNewCandidate(true, false);
                            break;

                        case "candidate without email validation":
                            createNewCandidate(false, false);
                            break;

                        case "verified candidate with resumes":
                            createNewCandidate(true, true);
                            break;

                        default:
                            break;
                    }
                    break;

                case "btn_sivaStart":
                    disableButton(true);
                    txt_console.clear();
                    switch(cmb_jobPreset.getSelectionModel().getSelectedItem().toString().toLowerCase()){
                        case "normal job posting":
                            createNormalJob();
                            break;

                        case "internship job":
                            createInternshipJob();
                            break;

                        case "normal job posting with sol":
                            createJobWithSol();
                            break;
                    }
                    break;

                case "btn_sivaGetPackage":
                    txt_console.clear();
                    siva = new Siva();
                    siva.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());
                    cmb_jobPackage.getItems().clear();
                    cmb_jobPackage.getItems().addAll(siva.getAdvertiserOrderList(txt_jobUsername.getText()));
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
                    break;
                }

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

    private void createNewCandidate(boolean verifyCandidate, boolean createResumes) throws Exception{
        String firstName = txt_myjsFirstName.getText();
        String lastName = txt_myjsLastName.getText();
        String email = txt_myjsEmail.getText();
        String password = txt_myjsPassword.getText();
        String positionTitle = txt_myjsPositionTitle.getText();
        String companyName = txt_myjsCompanyName.getText();

        myjs = new Myjs();
        myjs.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    myjs.candidateSignUp(firstName, lastName, email, password);

                    if(verifyCandidate){
                        myjs.updateCandidateVerificationStatus(email, 1);
                    }

                    if(createResumes){
                        String accessToken = myjs.getCandidateAccessToken(email);
                        myjs.updateExperience(accessToken, companyName, positionTitle);
                        myjs.updatePersonalInfo(accessToken, firstName, lastName);
                        myjs.updateEducation(accessToken);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    try{
                        disableButton(false);
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void createNormalJob() throws Exception{
        String username = txt_jobUsername.getText();
        String jobTitle = txt_jobJobTitle.getText();

        String tmp_packageName = "";

        try{
            tmp_packageName = cmb_jobPackage.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
            tmp_packageName = "";
        }

        final String packageName = tmp_packageName;

        Siva siva = new Siva();
        siva.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    siva.setUsername(username);
                    String jobId = siva.createNormalJob(jobTitle);

                    if(!packageName.equals("")){
                        siva.postJob(packageName.split(" - ")[0], jobId);
                    }

                    disableButton(false);
                }catch(Exception e){
                    e.printStackTrace();
                    try{
                        disableButton(false);
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void createInternshipJob() throws Exception{
        String username = txt_jobUsername.getText();
        String jobTitle = txt_jobJobTitle.getText();

        String tmp_packageName = "";

        try{
            tmp_packageName = cmb_jobPackage.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
            tmp_packageName = "";
        }

        final String packageName = tmp_packageName;

        Siva siva = new Siva();
        siva.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    siva.setUsername(username);
                    String jobId = siva.createInternshipJob(jobTitle);

                    if(!packageName.equals("")){
                        siva.postJob(packageName.split(" - ")[0], jobId);
                    }

                    disableButton(false);
                }catch(Exception e){
                    e.printStackTrace();
                    try{
                        disableButton(false);
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void createJobWithSol() throws Exception{
        String username = txt_jobUsername.getText();
        String jobTitle = txt_jobJobTitle.getText();

        String tmp_packageName = "";

        try{
            tmp_packageName = cmb_jobPackage.getSelectionModel().getSelectedItem().toString();
        }catch(Exception e){
            tmp_packageName = "";
        }

        final String packageName = tmp_packageName;

        Siva siva = new Siva();
        siva.setEnvironment(cmb_environment.getSelectionModel().getSelectedItem().toString());

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    siva.setUsername(username);
                    String jobId = siva.createJobWithSol(jobTitle);

                    if(!packageName.equals("")){
                        siva.postJob(packageName.split(" - ")[0], jobId);
                    }

                    disableButton(false);
                }catch(Exception e){
                    e.printStackTrace();
                    try{
                        disableButton(false);
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void disableButton(boolean flag) throws Exception{
        if(cmb_jobPreset.isVisible()){
            btn_sivaStart.setDisable(flag);
            btn_sivaClear.setDisable(flag);
            txt_jobJobTitle.setDisable(flag);
            txt_jobUsername.setDisable(flag);
            btn_sivaGetPackage.setDisable(flag);
            cmb_jobPreset.setDisable(flag);
        } else if(cmb_myjsPreset.isVisible()){
            btn_myjsStart.setDisable(flag);
            btn_myjsClear.setDisable(flag);
            txt_myjsCompanyName.setDisable(flag);
            txt_myjsEmail.setDisable(flag);
            txt_myjsPassword.setDisable(flag);
            txt_myjsPositionTitle.setDisable(flag);
            txt_myjsFirstName.setDisable(flag);
            txt_myjsLastName.setDisable(flag);
            cmb_myjsPreset.setDisable(flag);
        }
    }
}
