package userInterface;

import PropertiesFile.PropertiesFileReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Controller{
    @FXML private VBox layout_vbox;
    @FXML private FlowPane layout_mainVPane;
    @FXML private ImageView img_header;
    @FXML private Accordion side_nav_bar;
    @FXML private ScrollPane main_content_pane;
    @FXML private HBox container_content_hBox;
    @FXML private Stage stage;
    @FXML private Button btn_exit;

    private int navigationBarFontSize = 15;

    ServerMonitorController serverMonitor;

    @FXML public void initialize() throws Exception{
        //Add listener to auto resize header image
        layout_mainVPane.setMinWidth(Double.MAX_VALUE);
        layout_mainVPane.setMinHeight(Double.MAX_VALUE);
        layout_mainVPane.widthProperty().addListener(widthChangedlistener);
        layout_mainVPane.heightProperty().addListener(heightChangedlistener);
        layout_mainVPane.setOrientation(Orientation.VERTICAL);
        layout_mainVPane.setColumnHalignment(HPos.CENTER);
        layout_mainVPane.setVgap(10);

        layout_vbox.setStyle("-fx-background-color: DAE6F3;");
        layout_vbox.setAlignment(Pos.CENTER);

        container_content_hBox.setSpacing(20);
        container_content_hBox.setMinHeight(400);

        //Set Header image
        File imageFile = new File("/../../img_header.jpg");
        Image headerImage = new Image(imageFile.toURI().toString());
        img_header.setImage(headerImage);

        //Populate navigation bar
        TitledPane pane;
        PropertiesFileReader file = new PropertiesFileReader();

        file.setFile("Layout.properties");
        file.loadAllPropertyToMap();

        //Get parent entry for side navigation bar, filter and get total number of entries
        HashMap<String, String> layoutMap = PropertiesFileReader.returnAllValueFromPropertyMap();
        int paneCount = 0;

        //Get number of pane to add
        for (Map.Entry<String, String> entry : layoutMap.entrySet()) {
            if(entry.getKey().contains("left_nav_bar_item_parent_")){
                paneCount++;
            }
        }

        //Loop for n number of time to set pane
        for(int i = 0; i < paneCount; i++){
            pane = new TitledPane();

            GridPane grid = new GridPane();
            grid.setVgap(4);
            grid.setPadding(new Insets(5, 5, 5, 30));

            pane.setMaxHeight(Double.MAX_VALUE);
            pane.setText(layoutMap.get("left_nav_bar_item_parent_" + i));
            pane.setFont(Font.font(navigationBarFontSize));

            //Split comma separated child content into array
            String[] childItems = layoutMap.get("left_nav_bar_item_child_" + i).split(",");

            //Loop child content array to populate pane
            for(int j = 0; j < childItems.length; j++){
                Button button = new Button();
                button.setText(childItems[j]);
                button.setAlignment(Pos.CENTER_LEFT);
                button.setFont(Font.font(navigationBarFontSize));
                button.setOnAction(buttonEventHandler);

                grid.add(button, 0, j);
                pane.setContent(grid);
            }
            //Add pane to an array
            side_nav_bar.getPanes().addAll(pane);
            side_nav_bar.setMaxHeight(400);

            main_content_pane.setStyle("-fx-background-color: DAE6F3;");
            main_content_pane.setFitToHeight(false);
            main_content_pane.setFitToWidth(true);
            main_content_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            main_content_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            btn_exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stage.close();
                }
            });
        }
    }

    @FXML
    private EventHandler<ActionEvent> buttonEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(event.getTarget().toString().contains("Server")){
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("/userInterface/ServerMonitoring.fxml"));
                try {
                    main_content_pane.setContent(fxml.load());
                    serverMonitor = fxml.getController();
                    serverMonitor.listAllPanePropertyFile();
                    serverMonitor.startPing();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    final ChangeListener<Number> widthChangedlistener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            layout_vbox.setMinWidth((Double) newValue);
            main_content_pane.setMinWidth((Double) newValue - 230);
        }
    };

    final ChangeListener<Number> heightChangedlistener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            container_content_hBox.setPrefHeight((Double) newValue - 330);
        }
    };

    public void setStage(Stage stage) throws Exception{
        this.stage = stage;
    }
}
