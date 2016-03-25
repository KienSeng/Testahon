package userInterface;

import Global.Global;
import PropertiesFile.PropertiesFileReader;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kienseng.koh on 3/25/2016.
 */
public class Startup {

//    public static void addTitledPane() throws Exception{
//        TitledPane pane;
//        PropertiesFileReader file = new PropertiesFileReader();
//
//        file.setFile("Layout.properties");
//        file.loadAllPropertyToMap();
//
//        //Get parent entry for side navigation bar, filter and get total number of entries
//        HashMap<String, String> layoutMap = PropertiesFileReader.returnAllValueFromPropertyMap();
//        int paneCount = 0;
//
//        //Get number of pane to add
//        for (Map.Entry<String, String> entry : layoutMap.entrySet()) {
//            if(entry.getKey().contains("left_nav_bar_item_parent_")){
//                paneCount++;
//            }
//        }
//
//        //Loop for n number of time to set pane
//        for(int i = 0; i < paneCount; i++){
//            pane = new TitledPane();
//            pane.setText(layoutMap.get("left_nav_bar_item_parent_" + i));
//            System.out.println("1");
//            //Split comma separated child content into array
//            String[] childItems = layoutMap.get("left_nav_bar_item_child_" + i).split(",");
//
//            //Loop child content array to populate pane
//            for(int j = 0; j < childItems.length; j++){
//                System.out.println("2");
//                pane.setContent(new Button(childItems[j]));
//            }
//            //Add pane to an array
//            Controller.side_parent_nav.getPanes().addAll(pane);
//        }
//    }
}
