package WeatherApp;

import WeatherAPI.WeatherForecast;
import static WeatherApp.Main.scene1;
import static WeatherApp.Main.theStage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author cintia
 */
public class FXMLController implements Initializable {

    WeatherForecast weather;
    
    @FXML Label location = new Label();
    @FXML Label tempField = new Label();
    @FXML Label curCondition = new Label();
    @FXML Label currDate = new Label();
    @FXML Label feelsLike = new Label();
    @FXML Pane menu;
    
    private boolean menuToggleOpen;
    private String activeCity;

    @FXML
    Button button;
    @FXML
    Button menuButton;
    @FXML
    Button dragMenu;
    @FXML
    ImageView circle;
    @FXML
    AnchorPane backgroundPane;
    @FXML
    AnchorPane backgroundPane2;
    @FXML
    Pane infoPane;
    @FXML
    Pane labelPane;

    public double rotate = 0;
    public double translate = 0;
    public double translate2 = 0;
    public int daysAhead = 0;
    Thread th;
    public int setBack;

    public double xValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());

    private FadeTransition fadeTransition;
    private TranslateTransition translateTransition;
    private ParallelTransition parallelTransition;
            
    public void setxValue(MouseEvent event) {
        System.out.println(event.getX());
        xValue = event.getX();
    }

    public void handleKeys(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.S) {
            System.out.println("left");
            dragCircleLeft();
        } else if (event.getCode() == KeyCode.A) {
            System.out.println("right");
            dragCircleRight();
        } else if (event.getCode() == KeyCode.ENTER) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXML2.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
                scene1.getRoot().requestFocus();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getCode() == KeyCode.K) {
            dragLeft();
        } else if (event.getCode() == KeyCode.L) {
            dragRight();
        }
    }

    public void handleKeysAgain(KeyEvent event) {

        if (event.getCode() == KeyCode.S) {
            System.out.println("left");
            dragCircleLeft();
            wheelTime();
        } else if (event.getCode() == KeyCode.A) {
            System.out.println("right");
            dragCircleRight();
            wheelTime();
        } else if (event.getCode() == KeyCode.ENTER) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
                scene1.getRoot().requestFocus();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("it's working ");
    }

    public void dragCircleRight() {
        th = new Thread() {
            @Override
            public void run() {
                int innerRotation = 0;
                while (innerRotation < 15) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    rotate += 1;
                    Platform.runLater(() -> circle.setRotate(rotate));
                    innerRotation += 1;

                    if (rotate == 360 || rotate == -360) {
                        rotate = 0;
                    }
                }
                wheelTime();
            }
        };
        th.start();
    }

    public void dragCircleLeft() {
        th = new Thread() {
            @Override
            public void run() {
                int innerRotation = 0;
                while (innerRotation < 15) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    rotate -= 1;
                    Platform.runLater(() -> circle.setRotate(rotate));
                    innerRotation += 1;

                    if (rotate == 360 || rotate == -360) {
                        rotate = 0;
                    }
                }
                wheelTime();
            }
        };
        th.start();
        /*System.out.println("left");
         rotate -= 15;
         circle.setRotate(rotate);
         if(rotate == 360 || rotate == -360){
         rotate = 0;
         }*/
    }

    public void dragInfoPane() throws InterruptedException {
        System.out.println("dragging pane");
        th = new Thread() {
            @Override
            public void run() {
                if (translate == 0) {
                    while (translate > -430) {
                        translate -= 3.5;
                        movePanel();
                        Platform.runLater(() -> infoPane.setTranslateY(translate));
                        System.out.println(translate);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    infoPane.setTranslateY(0);
                    translate = 0;
                }
            }
        };
        th.start();
    }

    public void movePanel() {
        Platform.runLater(() -> infoPane.setTranslateY(translate));
    }

    public void wheelTime() {
        int currentHour;

        if ((int) Math.round(circle.getRotate() / -15) >= 0) {
            currentHour = ((int) Math.round(circle.getRotate() / -15));
        } else {
            currentHour = (24 - (int) Math.round(circle.getRotate() / 15));
        }
        System.out.println(currentHour);
        updateValues();
    }
    
    public void updateValues() {
        System.out.println("Updating values");
        WeatherForecast weather = new WeatherForecast(activeCity);
        String[] weatherArray = weather.getCurrent();
        location.setText(activeCity);
        tempField.setText("" + weatherArray[5] + "°C");
        currDate.setText(weatherArray[1] + " " + weatherArray[2] + ". " + weatherArray[3]);
        feelsLike.setText("Feels like: " + weatherArray[8]  + "°C");
        System.out.println(weatherArray[10]);
        String conditions = "Good";
        if(weatherArray[10].equals("Overcast") || weatherArray[10].equals("Chance of Rain")){
            conditions = "Poor";
        }
        else if(weatherArray[10].equals("Clear")){
            conditions = "Good";
        }
        curCondition.setText("Stargazing Conditions: " + conditions);
    }

    public void updateForecastValues(int daysAhead){
        System.out.println("Hey");
        System.out.println(daysAhead);
        WeatherForecast weather = new WeatherForecast(activeCity);
        String[] weatherArray = weather.getForecast1();
        String[] array;
        if(daysAhead == 1){
            array = new String[]{weatherArray[1], weatherArray[2], weatherArray[3], weatherArray[4], weatherArray[5], weatherArray[6], weatherArray[7], weatherArray[8]};
            System.out.println(weatherArray[1]);
        }
        else if(daysAhead == 2){
            array = new String[]{weatherArray[10], weatherArray[11], weatherArray[12], weatherArray[13], weatherArray[14], weatherArray[15], weatherArray[16], weatherArray[17]};
        }
        else if(daysAhead == 3){
            array = new String[]{weatherArray[19], weatherArray[20], weatherArray[21], weatherArray[22], weatherArray[23], weatherArray[24], weatherArray[25], weatherArray[26]};
        }
        else{
            array = new String[]{"", "", "", "", "", "", "", ""};
            System.out.println("Days ahead is 0");
        }

        location.setText(activeCity);
        tempField.setText("" + array[3] + "°C");
        currDate.setText(array[1] + " " + array[2] + ". " + array[3]);
        feelsLike.setText("Feels like: " + array[4]  + "°C");
        String conditions = "Good";
        if(array[5].equals("Overcast") || array[5].equals("Chance of Rain")){
            conditions = "Poor";
        }
        else if(array[5].equals("Clear")){
            conditions = "Good";
        }
        curCondition.setText("Stargazing Conditions: " + conditions);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //weather = new WeatherForecast("44418");
        //condition = new WeatherCondition("44418");
        menuToggleOpen = false;
        //tempField.setText("" + condition.weatherConditionList.get(0).currentTemp + "°C");
        //currDate.setText(weather.getCurrentDate());
        //feelsLike.setText("Feels like: " + condition.weatherConditionList.get(0).feelsLike  + "°C");
        //curCondition.setText("Stargazing Conditions: Good");
        setUpMenu();
        activeCity = "London";
        updateValues();
        menuToggleOpen = false;
        circle.setRotate(-15 * Double.parseDouble(currentHour));
        rotate = -15 * Double.parseDouble(currentHour);
        button.setFocusTraversable(false);
        dragMenu.setFocusTraversable(false);
        theStage.setResizable(false);
        //circle.setRotate(3);

    }
    // handle setup of the menu position and opacity and animation Objects
    public void setUpMenu() {
        menu.setLayoutX(-menu.getPrefWidth() + 70);
        menu.setLayoutY(-menu.getPrefHeight() + 50);
        menu.setOpacity(0.0f);
        menu.setVisible(false);
        fadeTransition = new FadeTransition(Duration.millis(300), menu);
        translateTransition = new TranslateTransition(Duration.millis(300), menu);
        parallelTransition = new ParallelTransition();
    }

    //Method to toggle the side menu open/close
    public void toggleMenu(){
        menu.setVisible(true);
        //reset parallel animation elements
        parallelTransition.getChildren().removeAll(fadeTransition,translateTransition);
        //Play fade and translate animation in parallel on menu button click
        //if already open then close the menu
        if(menuToggleOpen){
            fadeTransition.setFromValue(0.9f);
            fadeTransition.setToValue(0.0f);
            translateTransition.setFromX(menu.getPrefWidth() - 70);
            translateTransition.setFromY(menu.getPrefHeight() - 50);
            translateTransition.setToX(-menu.getPrefWidth() + 70); 
            translateTransition.setToY( -menu.getPrefWidth() + 50);
            translateTransition.setDelay(Duration.millis(100));
            parallelTransition.getChildren().addAll(
                fadeTransition,
                translateTransition
            );
            parallelTransition.play();
            menuToggleOpen = false;
            menu.toBack();
        } //otherwise open the menu
        else{
            fadeTransition.setFromValue(0.0f);
            fadeTransition.setToValue(0.9f);
            fadeTransition.setDelay(Duration.millis(150));
            translateTransition.setFromX(-menu.getPrefWidth() + 70);
            translateTransition.setFromY(-menu.getPrefWidth() + 50);
            translateTransition.setToX(menu.getPrefWidth() - 70);
            translateTransition.setToY(menu.getPrefHeight() - 50);
            parallelTransition.getChildren().addAll(
                fadeTransition,
                translateTransition
            );
            parallelTransition.play();
            menuToggleOpen = true;
            menu.toFront();
            button.toFront();
        }
    }

    //Handle menu button click
    public void handleMenuButtonClick(ActionEvent e) throws IOException{
        String [] seg = e.getSource().toString().split(Pattern.quote("'"));
        activeCity = seg[seg.length-1];
        updateValues();
        //call updateGUI method -- args weatherfore
   
    }

    public void switchScreen2(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void dragLeft() throws InterruptedException {
        System.out.println("going left");
                th = new Thread(){
                @Override
                public void run(){
                    double originalPos = 109;
                    boolean atPosition = false;
                    setBack = 0;
                    translate2 = 0;
                    labelPane.setTranslateX(0);
                    double x;

                    while(!atPosition){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        translate2 += 1;
                        Platform.runLater(() -> labelPane.setTranslateX(translate2));
                        System.out.println("Original position: " + originalPos);

                        x = labelPane.getLayoutX() + labelPane.getTranslateX();
                        System.out.println("Position of label pane: " + x);


                        if(x == originalPos) {
                            daysAhead--;
                            atPosition = true;
                            System.out.println("Its here");
                            Platform.runLater(() -> labelPane.setTranslateX(0));
                            Platform.runLater(() -> updateForecastValues(daysAhead));
                        }
                        if(x > 320){
                            setBack = -350;
                            if(daysAhead > 1){
                                //setBack -= 100;
                            }
                            Platform.runLater(() -> labelPane.setLayoutX(setBack));
                            //System.out.println("Greater than 320");
                        }
                    }
                }
        };
        th.start();
    }
    
    public void dragRight(){
        th = new Thread(){
            @Override
            public void run(){
                double originalPos = 109;
                boolean atPosition = false;
                labelPane.setTranslateX(0);
                //double x = labelPane.getLayoutX() + labelPane.getTranslateX();

                while(!atPosition){
                    translate2 -= 1.09;
                    Platform.runLater(() -> labelPane.setTranslateX(translate2));

                    //System.out.println("Original position: " + originalPos);
                    double x = labelPane.getLayoutX() + labelPane.getTranslateX();
                    //System.out.println("Position of label pane: " + x);


                    if(x > originalPos && x < originalPos + 1) {
                        atPosition = true;
                        labelPane.setTranslateX(0);
                        daysAhead++;
                        System.out.println(atPosition);
                        Platform.runLater(() -> updateForecastValues(daysAhead));
                    }
                    if(x < -100){

                        setBack = 500;
                        if(daysAhead > 1){
                            //setBack += 100;
                        }
                        Platform.runLater(() -> labelPane.setLayoutX(setBack));
                        System.out.println("Less than -200");
                    }
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };
        th.start();
    }
}
