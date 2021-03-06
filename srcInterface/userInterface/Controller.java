package userInterface;

import Global.Global;
import PropertiesFile.PropertiesFileReader;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Controller{
    @FXML private VBox layout_vbox;
    @FXML private FlowPane layout_mainVPane;
    @FXML private ImageView img_header_top;
    @FXML private ImageView img_header_middle;
    @FXML private ImageView img_header_bottom;
    @FXML private Accordion side_nav_bar;
    @FXML private ScrollPane main_content_pane;
    @FXML private HBox container_content_hBox;
    @FXML private Stage stage;
    @FXML private Button btn_exit;
    @FXML private TitledPane pane;

    private int navigationBarFontSize = 15;
    private static int currentHeaderIndex = -1;
    private static ArrayList<String> headerImages = new ArrayList<>();

    ServerMonitorController serverMonitor;
    SolrMonitorController serviceMonitor;
    ServerChatMonitorController serverChatMonitor;
    TAScenarioController TAController;
    TAScenariosReviewer scenarioReviewerController;
    ManualDeploymentController manualDeploymentController;
    TestDataCreationController testDataCreationController;

    @FXML public void initialize() throws Exception{
        PropertiesFileReader file = new PropertiesFileReader();
        file.setFile("DashboardSettings.properties");
        file.loadAllPropertyToMap();

        //Add listener to auto resize header image
        layout_mainVPane.setMinWidth(Double.MAX_VALUE);
        layout_mainVPane.setMinHeight(Double.MAX_VALUE);
        layout_mainVPane.widthProperty().addListener(widthChangedlistener);
        layout_mainVPane.heightProperty().addListener(heightChangedlistener);
        layout_mainVPane.setOrientation(Orientation.VERTICAL);
        layout_mainVPane.setColumnHalignment(HPos.CENTER);
        layout_mainVPane.setVgap(10);
        layout_vbox.setAlignment(Pos.CENTER);

        container_content_hBox.setSpacing(20);
        container_content_hBox.setMinHeight(400);

        //Set Header image
        Image topHeader = null;
        Image bottomHeader = null;

        File[] dir = new File("./Image/Headers").listFiles();
        for(int i = 0; i < dir.length; i++){
            if(dir[i].getName().contains("_top")){
                topHeader = new Image(new File(dir[i].getPath()).toURI().toString());
            }
            if(dir[i].getName().contains("_bottom")){
                bottomHeader = new Image(new File(dir[i].getPath()).toURI().toString());
            }
            if(dir[i].getName().contains("icon")){
                stage.getIcons().add(new Image(new File(dir[i].getPath()).toURI().toString()));
            }
        }

        dir = new File("./Image/Headers").listFiles();

        for(int i = 0; i < dir.length - 1; i++){
            if(dir[i].isFile() && !dir[i].getName().contains("_bottom") && !dir[i].getName().contains("_top")){
                headerImages.add(dir[i].getPath());
            }
        }

        img_header_top.setImage(topHeader);
        img_header_middle.setImage(getRandomHeaderImage());
        img_header_bottom.setImage(bottomHeader);

        SequentialTransition headerAnimation = new SequentialTransition();
        FadeTransition fadeIn = fade(img_header_middle, 0.0, 1.0);
        PauseTransition pause = new PauseTransition(Duration.millis(Integer.parseInt(Global.propertyMap.get("Header_Change_Interval"))));
        FadeTransition fadeOut = fade(img_header_middle, 1.0, 0.0);
        headerAnimation.getChildren().addAll(fadeIn, pause, fadeOut);
        headerAnimation.setOnFinished(e -> {
            try {
                img_header_middle.setImage(getRandomHeaderImage());
                headerAnimation.play();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        headerAnimation.play();

        String[] navBarParentList = Global.propertyMap.get("left_nav_bar_item_parent").split(",");
        for(int i = 0; i < navBarParentList.length; i++){
            pane = new TitledPane();

            VBox side_bar_child_vbox = new VBox(10);
            side_bar_child_vbox.setFillWidth(true);
            side_bar_child_vbox.setAlignment(Pos.CENTER_LEFT);

            pane.setMaxHeight(Double.MAX_VALUE);
            pane.setText(navBarParentList[i]);
            pane.setFont(Font.font(navigationBarFontSize));

            String[] navBarChildList = Global.propertyMap.get("left_nav_bar_item_child_" + navBarParentList[i].trim().replace(" ", "_")).split(",");

            for(int j = 0; j < navBarChildList.length; j++){
                Button button = new Button();
                button.setId("btn_" + navBarChildList[j].trim());
                button.setText("  " + navBarChildList[j].trim());
                button.setFont(Font.font(navigationBarFontSize));
                button.setOnAction(buttonEventHandler);
                button.setMinWidth(180);
                side_bar_child_vbox.getChildren().add(button);

                pane.setContent(side_bar_child_vbox);
            }

            //Add pane to an array
            side_nav_bar.getPanes().addAll(pane);
            side_nav_bar.setMaxHeight(200);

            main_content_pane.setFitToHeight(false);
            main_content_pane.setFitToWidth(true);
            main_content_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            main_content_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            btn_exit.setId("btnExit");
            btn_exit.getStyleClass().addAll("button_standard", "button_standard_negative");
            btn_exit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stage.close();
                }
            });
        }
    }

    private Image getRandomHeaderImage() throws Exception{
        Random random = new Random();

        String imageFilePath;
        int nextHeaderIndex = -1;
        while(true){
            nextHeaderIndex = random.nextInt(headerImages.size());
            if(nextHeaderIndex != currentHeaderIndex){
                currentHeaderIndex = nextHeaderIndex;
                imageFilePath = headerImages.get(currentHeaderIndex);
                break;
            }
        }

//        File imageFile = new File(headerImages.get(random.nextInt(headerImages.size())));
        File imageFile = new File(imageFilePath);
        return new Image(imageFile.toURI().toString());
    }

    private FadeTransition fade(ImageView image, Double start, Double end) throws Exception{
        FadeTransition fade = new FadeTransition();
        fade.setNode(img_header_middle);
        fade.setDuration(new Duration(1500));
        fade.setFromValue(start);
        fade.setToValue(end);
        fade.setAutoReverse(false);

        return fade;
    }

    @FXML
    private EventHandler<ActionEvent> buttonEventHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                Button btn = (Button) event.getSource();
                FXMLLoader fxml = new FXMLLoader();

                switch(btn.getId()){
                    case "btn_Server Status":
                        fxml.setLocation(getClass().getResource("/userInterface/ServerMonitoring.fxml"));

                        try {
                            deactivateAllThread();

                            Global.product = "server";
                            main_content_pane.setContent(fxml.load());
                            serverMonitor = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_SOLR":
                        fxml.setLocation(getClass().getResource("/userInterface/ServerMonitoring.fxml"));

                        try {
                            deactivateAllThread();

                            Global.product = "solr";
                            main_content_pane.setContent(fxml.load());
                            serverMonitor = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_Serverchat":
                        fxml.setLocation(getClass().getResource("/userInterface/ServerMonitoring.fxml"));

                        try {
                            deactivateAllThread();

                            Global.product = "service";
                            main_content_pane.setContent(fxml.load());
                            serverMonitor = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_Add Test Scenario":
                        fxml.setLocation(getClass().getResource("/userInterface/TAScenarios.fxml"));

                        try {
                            deactivateAllThread();
                            main_content_pane.setContent(fxml.load());
                            TAController = fxml.getController();
                            TAController.showPane();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_Review Test Scenarios":
                        fxml.setLocation(getClass().getResource("/userInterface/TAScenariosReviewer.fxml"));

                        try {
                            deactivateAllThread();
                            main_content_pane.setContent(fxml.load());
                            scenarioReviewerController = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_SiVA":
                        fxml.setLocation(getClass().getResource("/userInterface/ManualDeployment.fxml"));

                        try {
                            deactivateAllThread();
                            Global.product = btn.getId().split("_")[1];
                            main_content_pane.setContent(fxml.load());
                            manualDeploymentController = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_MYJS":
                        fxml.setLocation(getClass().getResource("/userInterface/ManualDeployment.fxml"));

                        try {
                            deactivateAllThread();
                            Global.product = btn.getId().split("_")[1];
                            main_content_pane.setContent(fxml.load());
                            manualDeploymentController = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_JBOS":
                        fxml.setLocation(getClass().getResource("/userInterface/ManualDeployment.fxml"));

                        try {
                            deactivateAllThread();
                            Global.product = btn.getId().split("_")[1];
                            main_content_pane.setContent(fxml.load());
                            manualDeploymentController = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_PAPI":
                        fxml.setLocation(getClass().getResource("/userInterface/ManualDeployment.fxml"));

                        try {
                            deactivateAllThread();
                            Global.product = btn.getId().split("_")[1];
                            main_content_pane.setContent(fxml.load());
                            manualDeploymentController = fxml.getController();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "btn_Test Data Creation":
                        fxml.setLocation(getClass().getResource("/userInterface/TestDataCreation.fxml"));

                        try{
                            deactivateAllThread();
                            main_content_pane.setContent(fxml.load());
                            Global.testDataController = fxml.getController();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    default:
                        break;
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    };

    private final ChangeListener<Number> widthChangedlistener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            layout_vbox.setMinWidth((Double) newValue);
            main_content_pane.setMinWidth((Double) newValue - 230);
        }
    };

    private final ChangeListener<Number> heightChangedlistener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            container_content_hBox.setPrefHeight((Double) newValue - 330);
        }
    };

    private void deactivateAllThread() throws Exception{
        try{
            serverMonitor.stopThread();
            serviceMonitor.stopThread();
            serverChatMonitor.stopThread();
            manualDeploymentController.stopThread();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
