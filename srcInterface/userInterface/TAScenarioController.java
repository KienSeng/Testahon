package userInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;

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
    @FXML private Label lbl_testCaseDescription;
    @FXML private Label lbl_caseDataDescription;
    @FXML private Label lbl_testPackage;
    @FXML private Label lbl_testClass;
    @FXML private Label lbl_pageObjectTable;
    @FXML private TextField  txt_existTestClassId;
    @FXML private TextField  txt_existTestCaseId;
    @FXML private TextField  txt_existTestSuiteId;
    @FXML private TextField  txt_existTestMatrixId;
    @FXML private TextField  txt_existLoginId;
    @FXML private TextField  txt_testCaseDescription;
    @FXML private TextField  txt_caseDataDescription;
    @FXML private TextField  txt_testPackage;
    @FXML private TextField  txt_testClass;
    @FXML private TextField  txt_pageObjectTable;
    @FXML private Button btn_clearAll;
    @FXML private Button btn_add;


    @Override
    public void initialize(URL location, java.util.ResourceBundle resources) {

    }

    public void showPane() throws Exception{
        Double radioButtonWidth = 100.0;
        Double standardLabelWidth = 150.0;
        Double standardTextBoxWidth = 200.0;

        layout_mainFlowPane.setHgap(15);
        layout_mainFlowPane.setVgap(15);
        layout_mainFlowPane.setPadding(new Insets(15,15,15,15));
        layout_mainFlowPane.setStyle("-fx-background-color: #C0C0C0");
        layout_mainFlowPane.setPrefWidth(Double.MAX_VALUE);
        layout_mainFlowPane.setOrientation(Orientation.VERTICAL);

        layout_flowPane_ScenarioContainer.setOrientation(Orientation.VERTICAL);
        layout_flowPane_ScenarioContainer.setStyle("-fx-border-color: black");

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

        layout_separator_Scenario.setPadding(new Insets(5,0,5,0));

        grd_content.setStyle("-fx-border-color: black");
        grd_content.setPadding(new Insets(5,0,5,0));
        grd_content.setVgap(5);

        lbl_existTestClassId.setMinWidth(standardLabelWidth);
        lbl_existTestCaseId.setMinWidth(standardLabelWidth);
        lbl_existTestSuiteId.setMinWidth(standardLabelWidth);
        lbl_existTestMatrixId.setMinWidth(standardLabelWidth);
        lbl_existLoginId.setMinWidth(standardLabelWidth);
        lbl_testCaseDescription.setMinWidth(standardLabelWidth);
        lbl_caseDataDescription.setMinWidth(standardLabelWidth);
        lbl_testPackage.setMinWidth(standardLabelWidth);
        lbl_testClass.setMinWidth(standardLabelWidth);
        lbl_pageObjectTable.setMinWidth(standardLabelWidth);

        txt_existTestClassId.setMinWidth(standardTextBoxWidth);
        txt_existTestCaseId.setMinWidth(standardTextBoxWidth);
        txt_existTestSuiteId.setMinWidth(standardTextBoxWidth);
        txt_existTestMatrixId.setMinWidth(standardTextBoxWidth);
        txt_existLoginId.setMinWidth(standardTextBoxWidth);
        txt_testCaseDescription.setMinWidth(standardTextBoxWidth);
        txt_caseDataDescription.setMinWidth(standardTextBoxWidth);
        txt_testPackage.setMinWidth(standardTextBoxWidth);
        txt_testClass.setMinWidth(standardTextBoxWidth);
        txt_pageObjectTable.setMinWidth(standardTextBoxWidth);
    }

    private void disableTextbox(String existOrNew) throws Exception{
        if(existOrNew.equalsIgnoreCase("exist")){
            txt_existTestClassId.setEditable(true);
            txt_existTestCaseId.setEditable(true);
            txt_existTestSuiteId.setEditable(true);
            txt_existTestMatrixId.setEditable(true);
            txt_existLoginId.setEditable(true);
            txt_testCaseDescription.setEditable(false);
            txt_caseDataDescription.setEditable(true);
            txt_testPackage.setEditable(false);
            txt_testClass.setEditable(false);
            txt_pageObjectTable.setEditable(false);
        } else if(existOrNew.equalsIgnoreCase("new")){
            txt_existTestClassId.setEditable(false);
            txt_existTestCaseId.setEditable(true);
            txt_existTestSuiteId.setEditable(true);
            txt_existTestMatrixId.setEditable(true);
            txt_existLoginId.setEditable(true);
            txt_testCaseDescription.setEditable(true);
            txt_caseDataDescription.setEditable(true);
            txt_testPackage.setEditable(true);
            txt_testClass.setEditable(true);
            txt_pageObjectTable.setEditable(true);
        }
    }

    private void clearAllTextBox() throws Exception{
        txt_existTestClassId.clear();
        txt_existTestCaseId.clear();
        txt_existTestSuiteId.clear();
        txt_existTestMatrixId.clear();
        txt_existLoginId.clear();
        txt_testCaseDescription.clear();
        txt_caseDataDescription.clear();
        txt_testPackage.clear();
        txt_testClass.clear();
        txt_pageObjectTable.clear();
    }

    private void showAddAllButton() throws Exception{

    }
}
