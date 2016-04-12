package userInterface;

import Global.Global;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kien Seng on 12-Apr-16.
 */
public class TAScenariosReviewer implements Initializable {
    @FXML private FlowPane layout_FlowPane_Main;
    @FXML private VBox layout_Vbox_SearchSection;
    @FXML private FlowPane layout_FlowPane_SearchContent;

    @FXML private ComboBox cmb_searchFromList;
    @FXML private TextField txt_searchByTestClass;

    @FXML private Label lbl_searchFromList;
    @FXML private Label lbl_searchByTestClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            generatePane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePane() throws Exception{
        layout_Vbox_SearchSection = new VBox();
        layout_FlowPane_SearchContent = new FlowPane();

        cmb_searchFromList = new ComboBox();
        txt_searchByTestClass = new TextField();

        lbl_searchFromList = new Label();
        lbl_searchByTestClass = new Label();

        layout_FlowPane_Main.setPadding(new Insets(15,15,15,15));
        layout_FlowPane_Main.setOrientation(Orientation.VERTICAL);
        layout_FlowPane_Main.setRowValignment(VPos.TOP);
        layout_FlowPane_Main.setColumnHalignment(HPos.LEFT);
        layout_FlowPane_Main.setStyle("-fx-background-color: red;");

        layout_FlowPane_SearchContent.setVgap(10);
        layout_FlowPane_SearchContent.setMaxWidth(Global.standardLabelWidth + Global.standardTextBoxWidth);
        layout_FlowPane_SearchContent.getChildren().addAll(lbl_searchFromList,
                cmb_searchFromList, lbl_searchByTestClass, txt_searchByTestClass);

        lbl_searchFromList.setText("Search from list:");
        lbl_searchByTestClass.setText("Search by Test Class:");

        lbl_searchFromList.setPrefWidth(Global.standardLabelWidth);
        lbl_searchByTestClass.setPrefWidth(Global.standardLabelWidth);
        cmb_searchFromList.setPrefWidth(Global.standardTextBoxWidth);
        txt_searchByTestClass.setPrefWidth(Global.standardTextBoxWidth);

        layout_Vbox_SearchSection.getChildren().add(layout_FlowPane_SearchContent);
        layout_FlowPane_Main.getChildren().add(layout_Vbox_SearchSection);
    }
}
