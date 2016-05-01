package userInterface;

import Common.DbConnector;
import Global.Global;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Kien Seng on 12-Apr-16.
 */
public class TAScenariosReviewer implements Initializable {
    @FXML private FlowPane layout_FlowPane_Main;
    private FlowPane layout_FlowPane_SearchSection;
    private HBox layout_Hbox_SearchContentList;
    private HBox layout_Hbox_SearchContentTextBox;
    private ComboBox cmb_searchFromList;
    private TextField txt_searchByTestClass;
    private Label lbl_searchFromList;
    private Label lbl_searchByTestClass;
    private Label lbl_testType;
    private Button btn_search;
    private Button btn_clear;
    private RadioButton rd_api;
    private RadioButton rd_gui;
    private ToggleGroup grp_testType;
    TableView layout_TableView_result = new TableView();
    StackPane layout_TableView_Container;

    String inputTestClass = "";

    DbConnector db;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            db = new DbConnector();
            db.connectDb("dbadmin_ta", "admin2tA", "orion.jobstreet.com", 1433, "TA");

            generatePane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generatePane() throws Exception{
        layout_FlowPane_SearchSection = new FlowPane();
        layout_Hbox_SearchContentList = new HBox();
        layout_Hbox_SearchContentTextBox = new HBox();
        cmb_searchFromList = new ComboBox();
        txt_searchByTestClass = new TextField();
        lbl_searchFromList = new Label();
        lbl_searchByTestClass = new Label();
        lbl_testType = new Label();
        Separator spr_SearchContent = new Separator();
        HBox layout_Hbox_SearchButton = new HBox();
        HBox layout_Hbox_TestType = new HBox();
        btn_search = new Button();
        btn_clear = new Button();
        rd_api = new RadioButton();
        rd_gui = new RadioButton();
        grp_testType = new ToggleGroup();

        layout_FlowPane_Main.setPadding(new Insets(15,15,15,15));
        layout_FlowPane_Main.setRowValignment(VPos.TOP);

        layout_FlowPane_SearchSection.setOrientation(Orientation.HORIZONTAL);
        layout_FlowPane_SearchSection.setRowValignment(VPos.TOP);
        layout_FlowPane_SearchSection.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());
        layout_FlowPane_SearchSection.setHgap(350);
        layout_FlowPane_SearchSection.setVgap(20);

        layout_Hbox_SearchContentList.setPadding(new Insets(15,0,15,0));
        layout_Hbox_SearchContentList.setSpacing(20);
        layout_Hbox_SearchContentList.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth);

        layout_Hbox_SearchContentTextBox.setPadding(new Insets(15,0,15,0));
        layout_Hbox_SearchContentTextBox.setSpacing(20);
        layout_Hbox_SearchContentTextBox.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth);

        layout_Hbox_TestType.setPadding(new Insets(15,0,15,0));
        layout_Hbox_TestType.setSpacing(20);
        layout_Hbox_TestType.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth);

        layout_Hbox_SearchButton.setPadding(new Insets(15,0,15,0));
        layout_Hbox_SearchButton.spacingProperty().bind(layout_FlowPane_Main.widthProperty().divide(6));
        layout_Hbox_SearchButton.setAlignment(Pos.CENTER);
        layout_Hbox_SearchButton.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());

        lbl_searchFromList.setText("Search from list:");
        lbl_searchByTestClass.setText("Search by Test Class:");
        lbl_testType.setText("Test Type:");

        cmb_searchFromList.setId("cmb_searchFromList");
        txt_searchByTestClass.setId("txt_searchByTestClass");

        lbl_searchFromList.setPrefWidth(Global.standardLabelWidth + 50);
        lbl_searchByTestClass.setPrefWidth(Global.standardLabelWidth + 50);
        lbl_testType.setPrefWidth(Global.standardLabelWidth + 50);
        cmb_searchFromList.setPrefWidth(Global.standardTextBoxWidth + 50);
        txt_searchByTestClass.setPrefWidth(Global.standardTextBoxWidth + 50);

        rd_api.setText("API");
        rd_api.setToggleGroup(grp_testType);
        rd_api.setMinWidth(Global.radioButtonWidth);
        rd_api.setOnAction(radioButtonClickedEvent);

        rd_gui.setText("GUI");
        rd_gui.setToggleGroup(grp_testType);
        rd_gui.setMinWidth(Global.radioButtonWidth);
        rd_gui.setOnAction(radioButtonClickedEvent);

        btn_search.setId("btn_search");
        btn_search.setText("Search");
        btn_search.setMinWidth(Global.standardButtonWidth);
        btn_search.setAlignment(Pos.CENTER);
        btn_search.setOnAction(buttonClickedEvent);
        btn_search.getStyleClass().addAll("button_standard", "button_standard_positive");

        btn_clear.setId("btn_clear");
        btn_clear.setText("Clear");
        btn_clear.setMinWidth(Global.standardButtonWidth);
        btn_clear.setAlignment(Pos.CENTER);
        btn_clear.setOnAction(buttonClickedEvent);
        btn_clear.getStyleClass().addAll("button_standard", "button_standard_negative");

        spr_SearchContent.setOrientation(Orientation.HORIZONTAL);
        spr_SearchContent.setPadding(new Insets(0,30,15,0));
        spr_SearchContent.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());

        layout_Hbox_TestType.getChildren().addAll(lbl_testType, rd_api, rd_gui);
        layout_Hbox_SearchContentList.getChildren().addAll(lbl_searchFromList, cmb_searchFromList);
        layout_Hbox_SearchContentTextBox.getChildren().addAll(lbl_searchByTestClass, txt_searchByTestClass);
        layout_Hbox_SearchButton.getChildren().addAll(btn_search, btn_clear);
        layout_FlowPane_SearchSection.getChildren().addAll(layout_Hbox_SearchContentList, layout_Hbox_SearchContentTextBox);
        layout_FlowPane_Main.getChildren().add(0, layout_Hbox_TestType);
        layout_FlowPane_Main.getChildren().add(1, layout_FlowPane_SearchSection);
        layout_FlowPane_Main.getChildren().add(2, layout_Hbox_SearchButton);
        layout_FlowPane_Main.getChildren().add(3, spr_SearchContent);
    }

    private void populateSearchResults(boolean isExist) throws Exception{
        ArrayList<String> parameterMap = new ArrayList<>();
        parameterMap.add("string|TestClass|" + inputTestClass);

        System.out.println("string|TestClass|" + inputTestClass);
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        ResultSet rs = null;

        if(!isExist){
            layout_TableView_Container = new StackPane();
            layout_TableView_result = new TableView();
            layout_FlowPane_Main.getChildren().add(3, layout_TableView_Container);

            rs = db.executeStoredProc("{call sproc_ListTestCoverageByTestSuiteNameOrTestCaseName (?)}", parameterMap);

            for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn column = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                final int j = i;
                column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                column.setPrefWidth(250);
                column.setEditable(false);
                layout_TableView_result.getColumns().add(column);
            }

            layout_TableView_Container.getChildren().add(layout_TableView_result);
            layout_TableView_Container.prefWidthProperty().bind(layout_FlowPane_Main.widthProperty().subtract(30));
            layout_TableView_result.prefWidthProperty().bind(layout_TableView_Container.widthProperty());
        } else {
            rs = db.executeStoredProc("{call sproc_ListTestCoverageByTestSuiteNameOrTestCaseName (?)}", parameterMap);
        }

        while(rs.next()){
            ObservableList<String> row = FXCollections.observableArrayList();

            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                row.add(rs.getString(i));
            }
            data.add(row);
        }

        layout_TableView_result.setItems(data);
        rs.close();
    }

    private void confirmedReviewOk(int CaseDataId) throws Exception{
        db.executeStatement("UPDATE Master_CaseData SET ReviewStatus = 1 WHERE CaseDataId = " + CaseDataId);
    }

    private void getAllTestClassFromDb(String testType) throws Exception{
        String query = "SELECT TestClass FROM Master_TestClass WITH (NOLOCK) WHERE TestPackage LIKE ('test" + testType.toLowerCase() + "%')";
        ResultSet rsTestClass = db.executeStatement(query);

        cmb_searchFromList.getItems().clear();
        cmb_searchFromList.getItems().add("");

        while(rsTestClass.next()){
            cmb_searchFromList.getItems().add(rsTestClass.getString(1));
        }

        rsTestClass.close();
    }

    private EventHandler buttonClickedEvent = event -> {
        try{
            Button btn = (Button) event.getSource();

            switch(btn.getId()){
                case "btn_search":
                    btn_search.setDisable(true);
                    if(cmb_searchFromList.getSelectionModel().getSelectedIndex() >= 1){
                        inputTestClass = cmb_searchFromList.getSelectionModel().getSelectedItem().toString();
                    }

                    if(!txt_searchByTestClass.getText().isEmpty()){
                        inputTestClass = txt_searchByTestClass.getText();
                    }

                    if(layout_FlowPane_Main.getChildren().contains(layout_TableView_Container)){
                        populateSearchResults(true);
                    } else{
                        populateSearchResults(false);
                    }

                    btn_search.setDisable(false);
                    break;

                case "btn_clear":
                    cmb_searchFromList.getSelectionModel().selectFirst();
                    txt_searchByTestClass.clear();
                    break;

                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    };

    private EventHandler radioButtonClickedEvent = event -> {
        RadioButton rd = (RadioButton) event.getSource();

        try {
            getAllTestClassFromDb(rd.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
