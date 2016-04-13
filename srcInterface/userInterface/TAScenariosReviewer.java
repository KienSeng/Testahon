package userInterface;

import Global.Global;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
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
        Separator spr_SearchContent = new Separator();
        HBox layout_Hbox_SearchButton = new HBox();
        Button btn_search = new Button();
        Button btn_clear = new Button();

        layout_FlowPane_Main.setPadding(new Insets(15,15,15,15));
        layout_FlowPane_Main.setRowValignment(VPos.TOP);

        layout_FlowPane_SearchSection.setOrientation(Orientation.HORIZONTAL);
        layout_FlowPane_SearchSection.setRowValignment(VPos.TOP);
        layout_FlowPane_SearchSection.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());
        layout_FlowPane_SearchSection.setHgap(350);
        layout_FlowPane_SearchSection.setVgap(20);

        layout_Hbox_SearchContentList.setSpacing(20);
        layout_Hbox_SearchContentList.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth);

        layout_Hbox_SearchContentTextBox.setSpacing(20);
        layout_Hbox_SearchContentTextBox.setMinWidth(Global.standardLabelWidth + Global.standardTextBoxWidth);

        layout_Hbox_SearchButton.setPadding(new Insets(15,0,15,0));
        layout_Hbox_SearchButton.spacingProperty().bind(layout_FlowPane_Main.widthProperty().divide(6));
        layout_Hbox_SearchButton.setAlignment(Pos.CENTER);
        layout_Hbox_SearchButton.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());

        lbl_searchFromList.setText("Search from list:");
        lbl_searchByTestClass.setText("Search by Test Class:");

        lbl_searchFromList.setPrefWidth(Global.standardLabelWidth + 50);
        lbl_searchByTestClass.setPrefWidth(Global.standardLabelWidth + 50);
        cmb_searchFromList.setPrefWidth(Global.standardTextBoxWidth + 50);
        txt_searchByTestClass.setPrefWidth(Global.standardTextBoxWidth + 50);

        btn_search.setId("btn_search");
        btn_search.setText("Search");
        btn_search.setMinWidth(Global.standardButtonWidth);
        btn_search.setAlignment(Pos.CENTER);

        btn_clear.setId("btn_clear");
        btn_clear.setText("Clear");
        btn_clear.setMinWidth(Global.standardButtonWidth);
        btn_clear.setAlignment(Pos.CENTER);

        spr_SearchContent.setOrientation(Orientation.HORIZONTAL);
        spr_SearchContent.setPadding(new Insets(0,30,0,0));
        spr_SearchContent.minWidthProperty().bind(layout_FlowPane_Main.widthProperty());

        layout_Hbox_SearchContentList.getChildren().addAll(lbl_searchFromList, cmb_searchFromList);
        layout_Hbox_SearchContentTextBox.getChildren().addAll(lbl_searchByTestClass, txt_searchByTestClass);
        layout_Hbox_SearchButton.getChildren().addAll(btn_search, btn_clear);
        layout_FlowPane_SearchSection.getChildren().addAll(layout_Hbox_SearchContentList, layout_Hbox_SearchContentTextBox);
        layout_FlowPane_Main.getChildren().add(0, layout_FlowPane_SearchSection);
        layout_FlowPane_Main.getChildren().add(1, layout_Hbox_SearchButton);
        layout_FlowPane_Main.getChildren().add(2, spr_SearchContent);
    }

    private void getAllTestClassFromDb() throws Exception{

    }
}
