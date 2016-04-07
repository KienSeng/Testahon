package userInterface;

import Database.DbConnector;
import com.microsoft.sqlserver.jdbc.SQLServerResultSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sun.reflect.annotation.ExceptionProxy;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Kien Seng on 05-Apr-16.
 */
public class TAScenarioController implements Initializable {

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

    @FXML private FlowPane layout_flowPane_Review;
    @FXML private Label lbl_TestClassId_review;
    @FXML private Label lbl_TestCaseId_review;
    @FXML private Label lbl_TestSuiteId_review;
    @FXML private Label lbl_TestMatrixId_review;
    @FXML private Label lbl_LoginId_review;
    @FXML private Label lbl_CaseDataId_review;
    @FXML private Label lbl_TestClassId_review_data;
    @FXML private Label lbl_TestCaseId_review_data;
    @FXML private Label lbl_TestSuiteId_review_data;
    @FXML private Label lbl_TestMatrixId_review_data;
    @FXML private Label lbl_LoginId_review_data;
    @FXML private Label lbl_CaseDataId_review_data;
    @FXML private GridPane grd_review;

    int testClassId = 0;
    int testCaseId = 0;
    int testSuiteId = 0;
    int loginId = 0;
    int caseDataId = 0;

    DbConnector db;


    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {
        try{
            connectToTADB();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showPane() throws Exception{
        Double radioButtonWidth = 100.0;
        Double standardLabelWidth = 150.0;
        Double standardTextBoxWidth = 200.0;
        Double standardButtonWidth = 80.0;

        layout_mainFlowPane.setHgap(15);
        layout_mainFlowPane.setVgap(15);
        layout_mainFlowPane.setPadding(new Insets(15,15,15,15));
        layout_mainFlowPane.setStyle("-fx-background-color: #C0C0C0");
        layout_mainFlowPane.setPrefWidth(Double.MAX_VALUE);
        layout_mainFlowPane.setOrientation(Orientation.VERTICAL);

        layout_flowPane_ScenarioContainer.setOrientation(Orientation.VERTICAL);
        layout_flowPane_ScenarioContainer.setStyle("-fx-border-color: grey");
        layout_flowPane_ScenarioContainer.setMinHeight(500);

        layout_flowPane_ExistNew.setPadding(new Insets(5,0,5,0));
        layout_flowPane_TestType.setPadding(new Insets(5,0,5,0));


        lbl_testType.setMinWidth(standardLabelWidth);
        lbl_existNew.setMinWidth(standardLabelWidth);

        grp_rd_testType = new ToggleGroup();
        rd_testType_API.setToggleGroup(grp_rd_testType);
        rd_testType_GUI.setToggleGroup(grp_rd_testType);

        grp_rd_existNew = new ToggleGroup();
        rd_exist.setToggleGroup(grp_rd_existNew);
        rd_new.setToggleGroup(grp_rd_existNew);

        rd_testType_API.setMinWidth(radioButtonWidth);
        rd_testType_GUI.setMinWidth(radioButtonWidth);
        rd_exist.setMinWidth(radioButtonWidth);
        rd_new.setMinWidth(radioButtonWidth);

        rd_testType_API.setOnAction(testTypeRadioButtonActionEvent);
        rd_testType_GUI.setOnAction(testTypeRadioButtonActionEvent);
        rd_exist.setOnAction(existNewRadioButtonActionEvent);
        rd_new.setOnAction(existNewRadioButtonActionEvent);

        layout_separator_Scenario.setPadding(new Insets(5,0,5,0));

        grd_content.setStyle("-fx-border-color: black");
        grd_content.setPadding(new Insets(5,0,5,0));
        grd_content.setVgap(5);

        lbl_existTestClassId.setMinWidth(standardLabelWidth);
        lbl_existTestCaseId.setMinWidth(standardLabelWidth);
        lbl_existTestSuiteId.setMinWidth(standardLabelWidth);
        lbl_existTestMatrixId.setMinWidth(standardLabelWidth);
        lbl_existLoginId.setMinWidth(standardLabelWidth);
        lbl_apiKeyDev.setMinWidth(standardLabelWidth);
        lbl_apiKeyTA.setMinWidth(standardLabelWidth);
        lbl_testCaseDescription.setMinWidth(standardLabelWidth);
        lbl_caseDataDescription.setMinWidth(standardLabelWidth);
        lbl_testPackage.setMinWidth(standardLabelWidth);
        lbl_testClass.setMinWidth(standardLabelWidth);
        lbl_pageObjectTable.setMinWidth(standardLabelWidth);

        txt_existTestClassId.setMinWidth(standardTextBoxWidth);
        txt_existTestCaseId.setMinWidth(standardTextBoxWidth);
        cmb_existTestSuiteId.setMinWidth(standardTextBoxWidth);
        cmb_existTestMatrixId.setMinWidth(standardTextBoxWidth);
        txt_existLoginId.setMinWidth(standardTextBoxWidth);
        txt_apiKeyDev.setMinWidth(standardTextBoxWidth);
        txt_apiKeyTA.setMinWidth(standardTextBoxWidth);
        txt_testCaseDescription.setMinWidth(standardTextBoxWidth);
        txt_caseDataDescription.setMinWidth(standardTextBoxWidth);
        txt_testPackage.setMinWidth(standardTextBoxWidth);
        txt_testClass.setMinWidth(standardTextBoxWidth);
        txt_pageObjectTable.setMinWidth(standardTextBoxWidth);

        layout_flowPane_Confirmation.setAlignment(Pos.CENTER);
        layout_flowPane_Confirmation.setVgap(10);
        layout_flowPane_Confirmation.setHgap(60);

        btn_add.setMinWidth(standardButtonWidth);
        btn_clearAll.setMinWidth(standardButtonWidth);
        btn_add.setOnAction(addButtonClicked);
        btn_clearAll.setOnAction(clearButtonClicked);

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

    private void insertToDb() throws Exception{
        Integer existingTestCaseId = Integer.parseInt(txt_existTestCaseId.getText());
        Integer existingTestClassId = Integer.parseInt(txt_existTestClassId.getText());
        Integer existingTestSuiteId = Integer.parseInt(cmb_existTestSuiteId.getSelectionModel().getSelectedItem().toString().split(" - ")[0]);
        Integer existingTestMatrixId = Integer.parseInt(cmb_existTestMatrixId.getSelectionModel().getSelectedItem().toString().split(" - ")[0]);
        Integer existingLoginId = Integer.parseInt(txt_existLoginId.getText());
        String apiKeyDev = txt_apiKeyDev.getText();
        String apiKeyTA = txt_apiKeyTA.getText();
        String testCaseDescription = txt_testCaseDescription.getText();
        String caseDataDescription = txt_caseDataDescription.getText();
        String testPackage = txt_testPackage.getText();
        String testClass = txt_testClass.getText();
        String pageObjectTable = txt_pageObjectTable.getText();

        ArrayList<String> parameterArray = new ArrayList<>();
        parameterArray.add("integer|" + "@Existing_TestClassId" + "|" + existingTestClassId);
        parameterArray.add("integer|" + "@Existing_TestCaseId" + "|" + existingTestCaseId);
        parameterArray.add("integer|" + "@Existing_TestSuiteId" + "|" + existingTestSuiteId);
        parameterArray.add("integer|" + "@Existing_TestMatrixId" + "|" + existingTestMatrixId);
        parameterArray.add("integer|" + "@Existing_TestLoginId" + "|" + existingLoginId);
        parameterArray.add("String|" + "@API_Key_DEV" + "|" + apiKeyDev);
        parameterArray.add("String|" + "@API_Key_TA" + "|" + apiKeyTA);
        parameterArray.add("String|" + "@TestCase_Description" + "|" + testCaseDescription);
        parameterArray.add("String|" + "@CaseData_Description" + "|" + caseDataDescription);
        parameterArray.add("String|" + "@TestPackage" + "|" + testPackage);
        parameterArray.add("String|" + "@TestClass" + "|" + testClass);
        parameterArray.add("String|" + "@PageObjectTable" + "|" + pageObjectTable);

//        db.executeStoredProc("{call sproc_insertTestDataToDb (?,?,?,?,?,?,?,?,?,?,?,?)}", parameterArray);
    }

    private void generateReviewPane() throws Exception{
        layout_flowPane_Review.setPadding(new Insets(5,0,5,0));
        layout_flowPane_Review.setStyle("-fx-border-color: red");
        layout_flowPane_Review.setOrientation(Orientation.VERTICAL);

        lbl_TestClassId_review.setText("TestClassId: ");
        lbl_TestCaseId_review.setText("TestCaseId: ");
        lbl_TestSuiteId_review.setText("TestSuiteId: ");
        lbl_TestMatrixId_review.setText("TestMatrixId: ");
        lbl_LoginId_review.setText("LoginId: ");
        lbl_CaseDataId_review.setText("CaseDataId: ");

        lbl_TestClassId_review_data.setText(String.valueOf(testClassId));
        lbl_TestCaseId_review_data.setText(String.valueOf(testCaseId));
        lbl_TestSuiteId_review_data.setText(String.valueOf(testSuiteId));
        lbl_TestMatrixId_review_data.setText(String.valueOf(testClassId));
        lbl_LoginId_review_data.setText(String.valueOf(loginId));
        lbl_CaseDataId_review_data.setText(String.valueOf(caseDataId));

        grd_review.add(lbl_CaseDataId_review,1,1);
        grd_review.add(lbl_TestClassId_review,1,2);
        grd_review.add(lbl_TestCaseId_review,1,3);
        grd_review.add(lbl_TestSuiteId_review,1,4);
        grd_review.add(lbl_TestMatrixId_review,1,5);
        grd_review.add(lbl_LoginId_review,1,6);

        grd_review.add(lbl_CaseDataId_review_data,2,1);
        grd_review.add(lbl_TestClassId_review_data,2,2);
        grd_review.add(lbl_TestCaseId_review_data,2,3);
        grd_review.add(lbl_TestSuiteId_review_data,2,4);
        grd_review.add(lbl_TestMatrixId_review_data,2,5);
        grd_review.add(lbl_LoginId_review_data,2,6);
    }

    private void connectToTADB() throws Exception{
        db = new DbConnector();
        db.connectDb("dbadmin_ta", "admin2tA", "orion.jobstreet.com", 1433, "TA_Training");
    }

    EventHandler testTypeRadioButtonActionEvent = event -> {
        try{
            populateTestSuiteIdComboBox();
        }catch(Exception e){
            e.printStackTrace();
        }
    };

    EventHandler existNewRadioButtonActionEvent = event -> {
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

    EventHandler addButtonClicked = event -> {
        try {
            insertToDb();
            clearAllTextBox();
            generateReviewPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    EventHandler clearButtonClicked = event -> {
        try {
            clearAllTextBox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
