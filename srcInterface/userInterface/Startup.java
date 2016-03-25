package userInterface;

import Global.Global;
import PropertiesFile.PropertiesFileReader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kienseng.koh on 3/25/2016.
 */
public class Startup {

    public void startUp() throws Exception{
//        PropertiesFileReader file = new PropertiesFileReader();
//
//        file.setFile("Layout.properties");
//        file.loadAllPropertyToMap();
//
//        //Get parent entry for side navigation bar, filter and get total number of entries
//        HashMap<String, String> layoutMap = PropertiesFileReader.returnAllValueFromPropertyMap();
//        int parentPane = 0;
//        ArrayList<String> parentPaneTitle = new ArrayList<>();
//
//        for (Map.Entry<String, String> entry : layoutMap.entrySet()) {
//            if(entry.getKey().contains("left_nav_bar_item_parent_")){
//                parentPaneTitle.add(entry.getKey());
//                parentPane++;
//            }
//        }
//
//        for(int i = 0; i < parentPaneTitle.size(); i++){
//            String[] buttonName = layoutMap.get("left_nav_bar_item_child_" + parentPaneTitle.get(i).split(",");
//
//            for(int j = 0; j < buttonName.length; j++){
//
//            })
//            Controller.addTitledPane(parentPaneTitle.get(i),new Button(layoutMap.get("left_nav_bar_item_child_" + parentPaneTitle.get(i))));
//        }
    }
}
