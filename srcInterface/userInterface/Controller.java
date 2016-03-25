package userInterface;

import PropertiesFile.PropertiesFileReader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    @FXML
    private static Accordion side_parent_nav;


    public static void addTitledPane()throws Exception{
        TitledPane pane = new TitledPane();

        PropertiesFileReader file = new PropertiesFileReader();

        file.setFile("Layout.properties");
        file.loadAllPropertyToMap();

        //Get parent entry for side navigation bar, filter and get total number of entries
        HashMap<String, String> layoutMap = PropertiesFileReader.returnAllValueFromPropertyMap();
        int parentPane = 0;
        ArrayList<String> parentPaneTitle = new ArrayList<>();

        for (Map.Entry<String, String> entry : layoutMap.entrySet()) {
            if(entry.getKey().contains("left_nav_bar_item_parent_")){
                parentPaneTitle.add(entry.getKey());

                parentPane++;
            }
        }

        for(int i = 0; i < parentPaneTitle.size(); i++){
            String[] button = layoutMap.get("left_nav_bar_item_child_" + parentPaneTitle.get(i)).split(",");
            pane.setText(parentPaneTitle.get(i));

            for(int j = 0; j < button.length; j++){
                pane.setContent(new Button(button[i]));
            }
        }
        side_parent_nav.getPanes().addAll(pane);
    }
}
