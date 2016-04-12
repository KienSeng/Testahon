package userInterface;

import Global.Global;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kien Seng on 12-Apr-16.
 */
public class TAScenariosReviewer implements Initializable {
    @FXML private FlowPane layout_FlowPane_Main;
    @FXML private FlowPane layout_FlowPane_SearchSection;
    @FXML private HBox layout_Hbox_SearchContentList;
    @FXML private HBox layout_Hbox_SearchContentTextBox;

    @FXML private ComboBox cmb_searchFromList;
    @FXML private TextField txt_searchByTestClass;

    @FXML private Label lbl_searchFromList;
    @FXML private Label lbl_searchByTestClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
//            generatePane();
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

        layout_FlowPane_Main.setPadding(new Insets(15,15,15,15));
        layout_FlowPane_Main.setOrientation(Orientation.VERTICAL);
        layout_FlowPane_Main.setRowValignment(VPos.TOP);
//        layout_FlowPane_Main.setColumnHalignment(HPos.LEFT);
        layout_FlowPane_Main.setStyle("-fx-background-color: red;");
        layout_FlowPane_Main.setPrefWidth(Double.MAX_VALUE);

        layout_FlowPane_Main.setPadding(new Insets(5,5,5,5));
        layout_FlowPane_SearchSection.setOrientation(Orientation.HORIZONTAL);
        layout_FlowPane_SearchSection.setRowValignment(VPos.TOP);
        layout_FlowPane_SearchSection.setHgap(100);
        layout_FlowPane_SearchSection.setVgap(20);
//        layout_FlowPane_SearchSection.setStyle("-fx-background-color: blue;");
//        layout_FlowPane_SearchSection.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());

        layout_Hbox_SearchContentList.setSpacing(20);
        layout_Hbox_SearchContentList.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth + 80);
//        layout_Hbox_SearchContentList.setAlignment(Pos.CENTER_LEFT);
        layout_Hbox_SearchContentList.setStyle("-fx-background-color: blue;");

        layout_Hbox_SearchContentTextBox.setSpacing(20);
        layout_Hbox_SearchContentTextBox.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth + 80);
//        layout_Hbox_SearchContentTextBox.setAlignment(Pos.CENTER_LEFT);
        layout_Hbox_SearchContentTextBox.setStyle("-fx-background-color: yellow;");

        lbl_searchFromList.setText("Search from list:");
        lbl_searchByTestClass.setText("Search by Test Class:");

        lbl_searchFromList.setPrefWidth(Global.standardLabelWidth);
        lbl_searchByTestClass.setPrefWidth(Global.standardLabelWidth);
        cmb_searchFromList.setPrefWidth(Global.standardTextBoxWidth);
        txt_searchByTestClass.setPrefWidth(Global.standardTextBoxWidth);

        layout_Hbox_SearchContentList.getChildren().addAll(lbl_searchFromList, cmb_searchFromList);
        layout_Hbox_SearchContentTextBox.getChildren().addAll(lbl_searchByTestClass, txt_searchByTestClass);
        layout_FlowPane_SearchSection.getChildren().addAll(layout_Hbox_SearchContentList, layout_Hbox_SearchContentTextBox);
        layout_FlowPane_Main.getChildren().add(layout_FlowPane_SearchSection);

    }

    private void getAllTestClassFromDb() throws Exception{

    }
}
