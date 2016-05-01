package userInterface;

import Common.DbConnector;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import Global.Global;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Kien Seng on 05-Apr-16.
 */
public class TAScenarioController implements Initializable {
    @FXML private StackPane layout_stackPane_MainContent;
    @FXML private FlowPane layout_mainFlowPane;
    @FXML private FlowPane layout_flowPane_ScenarioContainer;
    @FXML private FlowPane layout_flowPane_TestType;
    @FXML private FlowPane layout_flowPane_ExistNew;
    @FXML private FlowPane layout_flowPane_Confirmation;
    @FXML private GridPane grd_content;
    @FXML private Label lbl_testType;
    @FXML private Label lbl_existNew;
    @FXML private ToggleGroup grp_rd_testType;
    @FXML private ToggleGroup grp_rd_existNew;
    @FXML private RadioButton rd_testType_API;
    @FXML private RadioButton rd_testType_GUI;
    @FXML private RadioButton rd_exist;
    @FXML private RadioButton rd_new;
    @FXML private Separator layout_separator_Scenario;
    @FXML private Label lbl_existTestClassId;
    @FXML private Label lbl_existTestCaseId;
    @FXML private Label lbl_existTestSuiteId;
    @FXML private Label lbl_existTestMatrixId;
    @FXML private Label lbl_existLoginId;
    @FXML private Label lbl_apiKeyDev;
    @FXML private Label lbl_apiKeyTA;
    @FXML private Label lbl_testCaseDescription;
    @FXML private Label lbl_caseDataDescription;
    @FXML private Label lbl_testPackage;
    @FXML private Label lbl_testClass;
    @FXML private Label lbl_pageObjectTable;
    @FXML private TextField txt_existTestClassId;
    @FXML private TextField txt_existTestCaseId;
    @FXML private ComboBox cmb_existTestSuiteId;
    @FXML private ComboBox cmb_existTestMatrixId;
    @FXML private TextField txt_existLoginId;
    @FXML private TextField txt_apiKeyDev;
    @FXML private TextField txt_apiKeyTA;
    @FXML private TextField txt_testCaseDescription;
    @FXML private TextField txt_caseDataDescription;
    @FXML private TextField txt_testPackage;
    @FXML private TextField txt_testClass;
    @FXML private TextField txt_pageObjectTable;
    @FXML private Button btn_clearAll;
    @FXML private Button btn_add;
    @FXML private Separator sp_verticalSeparator;

    @FXML private FlowPane layout_flowPane_Review;
    @FXML private Label lbl_TestClassId_Summary;
    @FXML private Label lbl_TestCaseId_Summary;
    @FXML private Label lbl_TestSuiteId_Summary;
    @FXML private Label lbl_TestMatrixId_Summary;
    @FXML private Label lbl_LoginId_Summary;
    @FXML private Label lbl_CaseDataId_Summary;
    @FXML private Label lbl_TestClassId_Summary_data;
    @FXML private Label lbl_TestCaseId_Summary_data;
    @FXML private Label lbl_TestSuiteId_Summary_data;
    @FXML private Label lbl_TestMatrixId_Summary_data;
    @FXML private Label lbl_LoginId_Summary_data;
    @FXML private Label lbl_CaseDataId_Summary_data;
    @FXML private GridPane grd_review;
    @FXML private Button btn_summaryOk;
    @FXML private FlowPane layout_flowPane_ReviewContainer;

    @FXML private FlowPane layout_flowPane_DeleteScenario_Main;
    @FXML private FlowPane layout_flowPane_DeleteScenario_LightBox_Background;
    @FXML private FlowPane layout_flowPane_DeleteScenario_LightBox_Content;
    @FXML private GridPane layout_gridPane_DeleteScenario_Content;
    @FXML private Label lbl_deleteScenario_CaseDataId;
    @FXML private Label lbl_deleteScenario_CaseDataDescription;
    @FXML private Label lbl_deleteScenario_Title;
    @FXML private TextField txt_deleteScenario_CaseDataId;
    @FXML private Button btn_deleteScenario_ok;
    @FXML private Button btn_deleteScenario_clear;
    @FXML private Button btn_deleteScenario_cancel;
    @FXML private Button btn_deleteScenario_confirm;

    private int testClassId = 10000;
    private int testCaseId = 10000;
    private int testSuiteId = 10000;
    private int testMatrixId = 10000;
    private int loginId = 10000;
    private int caseDataId = 10000;


    private DbConnector db;


    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        try{
            connectToTADB();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showPane() throws Exception{
        layout_mainFlowPane.setHgap(15);
        layout_mainFlowPane.setVgap(15);
        layout_mainFlowPane.setPadding(new Insets(15,15,15,15));
        layout_mainFlowPane.setAlignment(Pos.TOP_LEFT);
        layout_mainFlowPane.setOrientation(Orientation.HORIZONTAL);

        layout_flowPane_ScenarioContainer.setId("layout_flowPane_ScenarioContainer");
        layout_flowPane_ScenarioContainer.setOrientation(Orientation.VERTICAL);
        layout_flowPane_ScenarioContainer.setAlignment(Pos.CENTER);
        layout_flowPane_ScenarioContainer.setMinHeight(600);
        layout_flowPane_ScenarioContainer.setMaxWidth(300);
        layout_flowPane_ScenarioContainer.setPadding(new Insets(5,0,5,0));

        layout_flowPane_ExistNew.setPadding(new Insets(5,0,5,0));
        layout_flowPane_TestType.setPadding(new Insets(5,0,5,0));

        lbl_testType.setMinWidth(Global.standardLabelWidth);
        lbl_existNew.setMinWidth(Global.standardLabelWidth);

        grp_rd_testType = new ToggleGroup();
        rd_testType_API.setToggleGroup(grp_rd_testType);
        rd_testType_GUI.setToggleGroup(grp_rd_testType);

        grp_rd_existNew = new ToggleGroup();
        rd_exist.setToggleGroup(grp_rd_existNew);
        rd_new.setToggleGroup(grp_rd_existNew);

        rd_testType_API.setMinWidth(Global.radioButtonWidth);
        rd_testType_GUI.setMinWidth(Global.radioButtonWidth);
        rd_exist.setMinWidth(Global.radioButtonWidth);
        rd_new.setMinWidth(Global.radioButtonWidth);

        rd_testType_API.setOnAction(testTypeRadioButtonActionEvent);
        rd_testType_GUI.setOnAction(testTypeRadioButtonActionEvent);
        rd_exist.setOnAction(existNewRadioButtonActionEvent);
        rd_new.setOnAction(existNewRadioButtonActionEvent);

        layout_separator_Scenario.setPadding(new Insets(5,0,5,0));

        grd_content.setPadding(new Insets(5,0,5,0));
        grd_content.setVgap(5);

        lbl_existTestClassId.setMinWidth(Global.standardLabelWidth);
        lbl_existTestCaseId.setMinWidth(Global.standardLabelWidth);
        lbl_existTestSuiteId.setMinWidth(Global.standardLabelWidth);
        lbl_existTestMatrixId.setMinWidth(Global.standardLabelWidth);
        lbl_existLoginId.setMinWidth(Global.standardLabelWidth);
        lbl_apiKeyDev.setMinWidth(Global.standardLabelWidth);
        lbl_apiKeyTA.setMinWidth(Global.standardLabelWidth);
        lbl_testCaseDescription.setMinWidth(Global.standardLabelWidth);
        lbl_caseDataDescription.setMinWidth(Global.standardLabelWidth);
        lbl_testPackage.setMinWidth(Global.standardLabelWidth);
        lbl_testClass.setMinWidth(Global.standardLabelWidth);
        lbl_pageObjectTable.setMinWidth(Global.standardLabelWidth);

        txt_existTestClassId.setMinWidth(Global.standardTextBoxWidth);
        txt_existTestCaseId.setMinWidth(Global.standardTextBoxWidth);
        cmb_existTestSuiteId.setMinWidth(Global.standardTextBoxWidth);
        cmb_existTestMatrixId.setMinWidth(Global.standardTextBoxWidth);
        txt_existLoginId.setMinWidth(Global.standardTextBoxWidth);
        txt_apiKeyDev.setMinWidth(Global.standardTextBoxWidth);
        txt_apiKeyTA.setMinWidth(Global.standardTextBoxWidth);
        txt_testCaseDescription.setMinWidth(Global.standardTextBoxWidth);
        txt_caseDataDescription.setMinWidth(Global.standardTextBoxWidth);
        txt_testPackage.setMinWidth(Global.standardTextBoxWidth);
        txt_testClass.setMinWidth(Global.standardTextBoxWidth);
        txt_pageObjectTable.setMinWidth(Global.standardTextBoxWidth);

        layout_flowPane_Confirmation.setAlignment(Pos.CENTER);
        layout_flowPane_Confirmation.setVgap(10);
        layout_flowPane_Confirmation.setHgap(60);

        btn_add.setId("btn_add");
        btn_clearAll.setId("btn_clearAll");
        btn_add.setMinWidth(Global.standardButtonWidth);
        btn_clearAll.setMinWidth(Global.standardButtonWidth);
        btn_add.setOnAction(addButtonClicked);
        btn_clearAll.setOnAction(clearButtonClicked);
        btn_add.getStyleClass().addAll("button_standard", "button_standard_positive");
        btn_clearAll.getStyleClass().addAll("button_standard", "button_standard_positive");

        layout_stackPane_MainContent.setAlignment(Pos.CENTER);
        layout_stackPane_MainContent.setMaxWidth(layout_flowPane_ScenarioContainer.getMaxWidth());
        layout_stackPane_MainContent.setMaxHeight(layout_flowPane_ScenarioContainer.getMinHeight());

        sp_verticalSeparator = new Separator();
        sp_verticalSeparator.setOrientation(Orientation.HORIZONTAL);
        sp_verticalSeparator.setPadding(new Insets(8,0,5,0));

        layout_flowPane_ScenarioContainer.getChildren().add(sp_verticalSeparator);

        generateDeleteScenarioPane();
        disableTextbox("all");
        populateTestMatrixIdComboBox();
    }

    private void disableTextbox(String existOrNew) throws Exception{
        if(existOrNew.equalsIgnoreCase("exist")){
            txt_existTestClassId.setDisable(false);
            txt_existTestCaseId.setDisable(false);
            cmb_existTestSuiteId.setDisable(false);
            cmb_existTestMatrixId.setDisable(false);
            txt_existLoginId.setDisable(false);
            txt_apiKeyDev.setDisable(false);
            txt_apiKeyTA.setDisable(false);
            txt_testCaseDescription.setDisable(true);
            txt_caseDataDescription.setDisable(false);
            txt_testPackage.setDisable(true);
            txt_testClass.setDisable(true);
            txt_pageObjectTable.setDisable(true);
        } else if(existOrNew.equalsIgnoreCase("new")){
            txt_existTestClassId.setDisable(true);
            txt_existTestCaseId.setDisable(false);
            cmb_existTestSuiteId.setDisable(false);
            cmb_existTestMatrixId.setDisable(false);
            txt_existLoginId.setDisable(false);
            txt_apiKeyDev.setDisable(false);
            txt_apiKeyTA.setDisable(false);
            txt_testCaseDescription.setDisable(false);
            txt_caseDataDescription.setDisable(false);
            txt_testPackage.setDisable(false);
            txt_testClass.setDisable(false);
            txt_pageObjectTable.setDisable(false);
        } else if(existOrNew.equalsIgnoreCase("all")){
            txt_existTestClassId.setDisable(true);
            txt_existTestCaseId.setDisable(true);
            cmb_existTestSuiteId.setDisable(true);
            cmb_existTestMatrixId.setDisable(true);
            txt_existLoginId.setDisable(true);
            txt_apiKeyDev.setDisable(true);
            txt_apiKeyTA.setDisable(true);
            txt_testCaseDescription.setDisable(true);
            txt_caseDataDescription.setDisable(true);
            txt_testPackage.setDisable(true);
            txt_testClass.setDisable(true);
            txt_pageObjectTable.setDisable(true);
        }
    }

    private void clearAllTextBox() throws Exception{
        txt_existTestClassId.clear();
        txt_existTestCaseId.clear();
        cmb_existTestSuiteId.getSelectionModel().select(0);
        cmb_existTestMatrixId.getSelectionModel().select(0);
        txt_existLoginId.clear();
        txt_apiKeyDev.clear();
        txt_apiKeyTA.clear();
        txt_testCaseDescription.clear();
        txt_caseDataDescription.clear();
        txt_testPackage.clear();
        txt_testClass.clear();
        txt_pageObjectTable.clear();
    }

    private void populateTestSuiteIdComboBox() throws Exception{
        ResultSet rs = null;
        if(rd_testType_API.isSelected()){
            rs = db.executeStatement("SELECT TestSuiteId, TestModule FROM Master_TestSuite WHERE TestType = 'API' AND SuiteType NOT IN ('POC','DEMO') AND TestProduct != 'POC' AND ActiveFlag != 0 ORDER BY TestSuiteId ASC");
        } else if (rd_testType_GUI.isSelected()){
            rs = db.executeStatement("SELECT TestSuiteId, TestModule FROM Master_TestSuite WHERE TestType = 'GUI' AND SuiteType NOT IN ('POC','DEMO') AND TestProduct != 'POC' AND ActiveFlag != 0 ORDER BY TestSuiteId ASC");
        }

        cmb_existTestSuiteId.getItems().clear();
        cmb_existTestSuiteId.getItems().add(0, "");

        while(rs.next()){
            cmb_existTestSuiteId.getItems().add(rs.getInt("TestSuiteId") + " - " + rs.getString("TestModule"));
        }

        db.closeStatement();
    }

    private void populateTestMatrixIdComboBox() throws Exception{
        ResultSet rs = null;
        rs = db.executeStatement("SELECT TestMatrixId, TestMatrixName FROM Master_TestMatrix ORDER BY TestMatrixId ASC");

        cmb_existTestMatrixId.getItems().clear();
        cmb_existTestMatrixId.getItems().add(0, "");

        while(rs.next()){
            cmb_existTestMatrixId.getItems().add(rs.getInt("TestMatrixId") + " - " + rs.getString("TestMatrixName"));
        }

        db.closeStatement();
    }

    private boolean insertToDb() throws Exception{
        String existingTestCaseId = txt_existTestCaseId.getText();
        String existingTestClassId = txt_existTestClassId.getText();
        String existingTestSuiteId = cmb_existTestSuiteId.getSelectionModel().getSelectedItem().toString().split(" - ")[0];
        String existingTestMatrixId = cmb_existTestMatrixId.getSelectionModel().getSelectedItem().toString().split(" - ")[0];
        String existingLoginId = txt_existLoginId.getText();
        String apiKeyDev = txt_apiKeyDev.getText();
        String apiKeyTA = txt_apiKeyTA.getText();
        String testCaseDescription = txt_testCaseDescription.getText();
        String caseDataDescription = txt_caseDataDescription.getText();
        String testPackage = txt_testPackage.getText();
        String testClass = txt_testClass.getText();
        String pageObjectTable = txt_pageObjectTable.getText();

        if(existingTestCaseId == null || existingTestCaseId.isEmpty()){
            existingTestCaseId = "null";
        }
        if (existingTestClassId == null || existingTestClassId.isEmpty()){
            existingTestClassId = "null";
        }
        if(existingTestSuiteId == null || existingTestSuiteId.isEmpty()){
            existingTestSuiteId = "null";
        }
        if(existingTestMatrixId == null || existingTestMatrixId.isEmpty()){
            existingTestMatrixId = "null";
        }
        if(existingLoginId == null || existingLoginId.isEmpty()){
            existingLoginId = "null";
        }
        if(apiKeyDev == null || apiKeyDev.isEmpty()){
            apiKeyDev = "null";
        }
        if(apiKeyTA == null && apiKeyTA.isEmpty()){
            apiKeyTA = "null";
        }
        if(testCaseDescription == null || testCaseDescription.isEmpty()){
            testCaseDescription = "null";
        }
        if(caseDataDescription == null || caseDataDescription.isEmpty()){
            caseDataDescription = "null";
        }
        if(testPackage == null || testPackage.isEmpty()){
            testPackage = "null";
        }
        if(testClass == null || testClass.isEmpty()){
            testClass = "null";
        }
        if(pageObjectTable == null || pageObjectTable.isEmpty()){
            pageObjectTable = "null";
        }


        ArrayList<String> parameterArray = new ArrayList<>();
        parameterArray.add("integer|" + "Existing_TestClassId" + "|" + existingTestClassId);
        parameterArray.add("integer|" + "Existing_TestCaseId" + "|" + existingTestCaseId);
        parameterArray.add("integer|" + "Existing_TestSuiteId" + "|" + existingTestSuiteId);
        parameterArray.add("integer|" + "Existing_TestMatrixId" + "|" + existingTestMatrixId);
        parameterArray.add("integer|" + "Existing_LoginId" + "|" + existingLoginId);
        parameterArray.add("String|" + "API_Key_DEV" + "|" + apiKeyDev);
        parameterArray.add("String|" + "API_Key_TA" + "|" + apiKeyTA);
        parameterArray.add("String|" + "TestCaseDescription" + "|" + testCaseDescription);
        parameterArray.add("String|" + "CaseDataDescription" + "|" + caseDataDescription);
        parameterArray.add("String|" + "TestPackage" + "|" + testPackage);
        parameterArray.add("String|" + "TestClass" + "|" + testClass);
        parameterArray.add("String|" + "PageObjectTable" + "|" + pageObjectTable);

        ResultSet rs = db.executeStoredProc("{call sproc_Template_InsertDataToMasterTable (?,?,?,?,?,?,?,?,?,?,?,?)}", parameterArray);

        while(rs.next()){
            testClassId = rs.getInt("testClassId");
            testCaseId = rs.getInt("testCaseId");
            testSuiteId = rs.getInt("testSuiteId");
            testMatrixId = rs.getInt("testMatrixId");
            loginId = rs.getInt("LoginTestDataId");
            caseDataId = rs.getInt("caseDataId");
        }
        db.closeStatement();

        if(rs != null){
            return true;
        }else{
            return false;
        }
    }

    private void generateReviewPane() throws Exception{
        layout_flowPane_Review = new FlowPane();
        grd_review = new GridPane();
        layout_flowPane_ReviewContainer = new FlowPane();
        btn_summaryOk = new Button();
        lbl_TestClassId_Summary = new Label();
        lbl_TestCaseId_Summary = new Label();
        lbl_TestSuiteId_Summary = new Label();
        lbl_TestMatrixId_Summary = new Label();
        lbl_LoginId_Summary = new Label();
        lbl_CaseDataId_Summary = new Label();
        lbl_TestClassId_Summary_data = new Label();
        lbl_TestCaseId_Summary_data = new Label();
        lbl_TestSuiteId_Summary_data = new Label();
        lbl_TestMatrixId_Summary_data = new Label();
        lbl_LoginId_Summary_data = new Label();
        lbl_CaseDataId_Summary_data = new Label();

        lbl_TestClassId_Summary.setId("lbl_gridContent");
        lbl_TestCaseId_Summary.setId("lbl_gridContent");
        lbl_TestSuiteId_Summary.setId("lbl_gridContent");
        lbl_TestMatrixId_Summary.setId("lbl_gridContent");
        lbl_LoginId_Summary.setId("lbl_gridContent");
        lbl_CaseDataId_Summary.setId("lbl_gridContent");
        lbl_TestClassId_Summary_data.setId("lbl_gridContent");
        lbl_TestCaseId_Summary_data.setId("lbl_gridContent");
        lbl_TestSuiteId_Summary_data.setId("lbl_gridContent");
        lbl_TestMatrixId_Summary_data.setId("lbl_gridContent");
        lbl_LoginId_Summary_data.setId("lbl_gridContent");
        lbl_CaseDataId_Summary_data.setId("lbl_gridContent");

        lbl_TestClassId_Summary.setText("TestClassId: ");
        lbl_TestCaseId_Summary.setText("TestCaseId: ");
        lbl_TestSuiteId_Summary.setText("TestSuiteId: ");
        lbl_TestMatrixId_Summary.setText("TestMatrixId: ");
        lbl_LoginId_Summary.setText("LoginId: ");
        lbl_CaseDataId_Summary.setText("CaseDataId: ");

        lbl_TestClassId_Summary_data.setText(String.valueOf(testClassId));
        lbl_TestCaseId_Summary_data.setText(String.valueOf(testCaseId));
        lbl_TestSuiteId_Summary_data.setText(String.valueOf(testSuiteId));
        lbl_TestMatrixId_Summary_data.setText(String.valueOf(testMatrixId));
        lbl_LoginId_Summary_data.setText(String.valueOf(loginId));
        lbl_CaseDataId_Summary_data.setText(String.valueOf(caseDataId));

        int reviewWidth = 100;
        lbl_TestClassId_Summary.setMinWidth(reviewWidth);
        lbl_TestCaseId_Summary.setMinWidth(reviewWidth);
        lbl_TestSuiteId_Summary.setMinWidth(reviewWidth);
        lbl_TestMatrixId_Summary.setMinWidth(reviewWidth);
        lbl_LoginId_Summary.setMinWidth(reviewWidth);
        lbl_CaseDataId_Summary.setMinWidth(reviewWidth);

        btn_summaryOk.setId("btn_summaryOk");
        btn_summaryOk.setText("OK");
        btn_summaryOk.setMinWidth(Global.standardButtonWidth);
        btn_summaryOk.setOnAction(summaryButtonOkClicked);
        btn_summaryOk.getStyleClass().add("button_standard_positive");

        grd_review.setId("grd_review");
        grd_review.add(lbl_CaseDataId_Summary,1,1);
        grd_review.add(lbl_TestClassId_Summary,1,2);
        grd_review.add(lbl_TestCaseId_Summary,1,3);
        grd_review.add(lbl_TestSuiteId_Summary,1,4);
        grd_review.add(lbl_TestMatrixId_Summary,1,5);
        grd_review.add(lbl_LoginId_Summary,1,6);

        grd_review.add(lbl_CaseDataId_Summary_data,2,1);
        grd_review.add(lbl_TestClassId_Summary_data,2,2);
        grd_review.add(lbl_TestCaseId_Summary_data,2,3);
        grd_review.add(lbl_TestSuiteId_Summary_data,2,4);
        grd_review.add(lbl_TestMatrixId_Summary_data,2,5);
        grd_review.add(lbl_LoginId_Summary_data,2,6);
        grd_review.setHgap(10);
        grd_review.setVgap(10);
        grd_review.setMinWidth(170);

        layout_flowPane_ReviewContainer.setId("layout_flowPane_ReviewContainer");
        layout_flowPane_ReviewContainer.setPadding(new Insets(5,0,5,0));
        layout_flowPane_ReviewContainer.setOrientation(Orientation.VERTICAL);
        layout_flowPane_ReviewContainer.setRowValignment(VPos.CENTER);
        layout_flowPane_ReviewContainer.setColumnHalignment(HPos.CENTER);
        layout_flowPane_ReviewContainer.setVgap(10);
        layout_flowPane_ReviewContainer.setMaxHeight(215);

        layout_flowPane_ReviewContainer.getChildren().add(grd_review);
        layout_flowPane_ReviewContainer.getChildren().add(btn_summaryOk);

        layout_flowPane_Review.setId("layout_flowPane_Review");
        layout_flowPane_Review.setVgap(5);
        layout_flowPane_Review.setHgap(5);
        layout_flowPane_Review.setOrientation(Orientation.VERTICAL);
        layout_flowPane_Review.setAlignment(Pos.CENTER);

        layout_flowPane_Review.getChildren().add(layout_flowPane_ReviewContainer);
        layout_stackPane_MainContent.getChildren().add(layout_flowPane_Review);
    }

    private void generateDeleteScenarioPane() throws Exception{
        layout_flowPane_DeleteScenario_Main = new FlowPane();
        layout_gridPane_DeleteScenario_Content = new GridPane();
        lbl_deleteScenario_CaseDataId = new Label();
        lbl_deleteScenario_CaseDataDescription = new Label();
        lbl_deleteScenario_Title = new Label();
        txt_deleteScenario_CaseDataId = new TextField();
        btn_deleteScenario_ok = new Button();
        btn_deleteScenario_clear = new Button();

        layout_flowPane_DeleteScenario_Main.setId("layout_flowPane_DeleteScenario_Main");
        layout_flowPane_DeleteScenario_Main.setPadding(new Insets(5,5,5,5));
        layout_flowPane_DeleteScenario_Main.setAlignment(Pos.CENTER);
        layout_flowPane_DeleteScenario_Main.setOrientation(Orientation.HORIZONTAL);
        layout_flowPane_DeleteScenario_Main.setVgap(10);
        layout_flowPane_DeleteScenario_Main.setHgap(10);
        layout_flowPane_DeleteScenario_Main.setMaxWidth(150);
        layout_flowPane_DeleteScenario_Main.setMaxHeight(130);

        lbl_deleteScenario_CaseDataId.setText("CaseDataId: ");
        lbl_deleteScenario_CaseDataId.setMinWidth(80);
        txt_deleteScenario_CaseDataId.setMinWidth(Global.standardTextBoxWidth);

        lbl_deleteScenario_Title.setText("Delete Test Scenarios");

        layout_gridPane_DeleteScenario_Content.add(lbl_deleteScenario_Title, 1, 1);
        layout_gridPane_DeleteScenario_Content.add(lbl_deleteScenario_CaseDataId, 1, 2);
        layout_gridPane_DeleteScenario_Content.add(txt_deleteScenario_CaseDataId, 2, 2);

        btn_deleteScenario_ok.setId("btn_deleteScenario_ok");
        btn_deleteScenario_ok.setText("Delete");
        btn_deleteScenario_ok.getStyleClass().addAll("button_standard", "button_standard_negative");

        btn_deleteScenario_clear.setId("btn_deleteScenario_clear");
        btn_deleteScenario_clear.setText("Clear");
        btn_deleteScenario_clear.getStyleClass().addAll("button_standard", "button_standard_positive");

        btn_deleteScenario_ok.setOnAction(deleteScenarioEvent);
        btn_deleteScenario_clear.setOnAction(deleteScenarioEvent);

        btn_deleteScenario_ok.setMinWidth(Global.standardButtonWidth);
        btn_deleteScenario_clear.setMinWidth(Global.standardButtonWidth);

        layout_flowPane_DeleteScenario_Main.getChildren().add(lbl_deleteScenario_Title);
        layout_flowPane_DeleteScenario_Main.getChildren().add(layout_gridPane_DeleteScenario_Content);
        layout_flowPane_DeleteScenario_Main.getChildren().add(btn_deleteScenario_ok);
        layout_flowPane_DeleteScenario_Main.getChildren().add(btn_deleteScenario_clear);

        layout_flowPane_ScenarioContainer.getChildren().add(layout_flowPane_DeleteScenario_Main);
    }

    private void generateDeleteScenarioConfirmationLightBox() throws Exception{
        lbl_deleteScenario_CaseDataDescription = new Label();
        layout_flowPane_DeleteScenario_LightBox_Background = new FlowPane();
        layout_flowPane_DeleteScenario_LightBox_Content = new FlowPane();
        btn_deleteScenario_cancel = new Button();
        btn_deleteScenario_confirm = new Button();

        layout_flowPane_DeleteScenario_LightBox_Background.setId("layout_flowPane_DeleteScenario_LightBox_Background");
        layout_flowPane_DeleteScenario_LightBox_Background.setAlignment(Pos.CENTER);
        layout_flowPane_DeleteScenario_LightBox_Background.setOrientation(Orientation.VERTICAL);

        layout_flowPane_DeleteScenario_LightBox_Content.setId("layout_flowPane_DeleteScenario_LightBox_Content");
        layout_flowPane_DeleteScenario_LightBox_Content.setPadding(new Insets(5,5,5,5));
        layout_flowPane_DeleteScenario_LightBox_Content.setAlignment(Pos.CENTER);
        layout_flowPane_DeleteScenario_LightBox_Content.setVgap(10);
        layout_flowPane_DeleteScenario_LightBox_Content.setHgap(10);
        layout_flowPane_DeleteScenario_LightBox_Content.setMaxHeight(80);
        layout_flowPane_DeleteScenario_LightBox_Content.setMaxWidth(120);

        btn_deleteScenario_cancel.setId("btn_deleteScenario_cancel");
        btn_deleteScenario_confirm.setId("btn_deleteScenario_confirm");
        btn_deleteScenario_cancel.setText("Cancel");
        btn_deleteScenario_confirm.setText("Confirm");
        btn_deleteScenario_cancel.setOnAction(deleteScenarioEvent);
        btn_deleteScenario_confirm.setOnAction(deleteScenarioEvent);
        btn_deleteScenario_cancel.getStyleClass().addAll("button_standard", "button_standard_positive");
        btn_deleteScenario_confirm.getStyleClass().addAll("button_standard", "button_standard_negative");

        lbl_deleteScenario_CaseDataDescription.setText("Confirm delete CaseDataID: " + txt_deleteScenario_CaseDataId.getText());

        layout_flowPane_DeleteScenario_LightBox_Content.getChildren().add(lbl_deleteScenario_CaseDataDescription);
        layout_flowPane_DeleteScenario_LightBox_Content.getChildren().add(btn_deleteScenario_cancel);
        layout_flowPane_DeleteScenario_LightBox_Content.getChildren().add(btn_deleteScenario_confirm);

        layout_flowPane_DeleteScenario_LightBox_Background.getChildren().add(layout_flowPane_DeleteScenario_LightBox_Content);
        layout_stackPane_MainContent.getChildren().add(layout_flowPane_DeleteScenario_LightBox_Background);
    }

    private void connectToTADB() throws Exception{
        db = new DbConnector();
        db.connectDb("dbadmin_ta", "admin2tA", "orion.jobstreet.com", 1433, "TA_Training");
    }

    private Integer getCaseDataIdToDelete() throws Exception{
        Integer caseDataId = null;
        try{
            caseDataId = Integer.parseInt(txt_deleteScenario_CaseDataId.getText());
        }catch(Exception e){
            caseDataId = null;
        }

        return caseDataId;
    }

    private EventHandler testTypeRadioButtonActionEvent = event -> {
        try{
            populateTestSuiteIdComboBox();
        }catch(Exception e){
            e.printStackTrace();
        }
    };

    private EventHandler existNewRadioButtonActionEvent = event -> {
        try{
            if(rd_exist.isSelected()){
                disableTextbox("exist");
            } else if (rd_new.isSelected()){
                disableTextbox("new");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    };

    private EventHandler addButtonClicked = event -> {
        try {
            boolean insertSuccess = false;
            insertSuccess = insertToDb();

            if(insertSuccess){
                clearAllTextBox();
                generateReviewPane();
            }else{

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private EventHandler clearButtonClicked = event -> {
        try {
            clearAllTextBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private EventHandler summaryButtonOkClicked = event -> {
        try {
            layout_stackPane_MainContent.getChildren().remove(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    private EventHandler deleteScenarioEvent = event -> {
        try{
            Button btn = (Button) event.getSource();

            switch(btn.getId()){
                case "btn_deleteScenario_ok":
                    generateDeleteScenarioConfirmationLightBox();
                    break;

                case "btn_deleteScenario_clear":
                    txt_deleteScenario_CaseDataId.clear();
                    break;

                case "btn_deleteScenario_cancel":
                    layout_stackPane_MainContent.getChildren().remove(1);
                    break;

                case "btn_deleteScenario_confirm":
                    ArrayList<String> parameterArray = new ArrayList<>();
                    parameterArray.add("integer|" + "CaseDataId" + "|" + getCaseDataIdToDelete());

                    db.executeStoredProc("{call sproc_Template_DeleteDataFromMasterTable (?)}", parameterArray);
                    layout_stackPane_MainContent.getChildren().remove(1);
                    break;

                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    };
}
