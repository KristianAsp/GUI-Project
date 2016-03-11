package WeatherApp;

import WeatherAPI.Day;
import WeatherAPI.HourWeather;
import WeatherAPI.WeatherForecast;
import static WeatherApp.Main.scene1;
import static WeatherApp.Main.theStage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import WeatherAPI.WeatherObject;
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
import javafx.scene.image.Image;
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


    @FXML Label location;
    @FXML Label feelsLike;
    @FXML Label tempField;
    @FXML Label curCondition;
    @FXML Label currDate;
    @FXML Label rainfall;
    @FXML Label sunsetSunrise;
    @FXML Label tempHL;
    @FXML Label comet;
    @FXML Label cloudCover;
    @FXML Label humidity;
    @FXML Label tempFieldLabel;
    @FXML Label lightPollution;

    @FXML Label temp0;
    @FXML Label temp1;
    @FXML Label temp2;
    @FXML Label temp3;
    @FXML Label temp4;
    @FXML Label temp5;
    @FXML Label temp6;

    @FXML Label precip0;
    @FXML Label precip1;
    @FXML Label precip2;
    @FXML Label precip3;
    @FXML Label precip4;
    @FXML Label precip5;
    @FXML Label precip6;

    @FXML Label day0;
    @FXML Label day1;
    @FXML Label day2;
    @FXML Label day3;
    @FXML Label day4;
    @FXML Label day5;
    @FXML Label day6;
    @FXML Label day7;
    @FXML Label day8;
    @FXML Label day9;
    @FXML Label day10;
    @FXML Label day11;
    @FXML Label day12;
    @FXML Label day13;

    @FXML Pane menu;


    ArrayList<String> backgrounds = new ArrayList<>();
    ArrayList<String> backgroundsLarge = new ArrayList<>();

    private boolean menuToggleOpen;
    private boolean dragMenuOpen = false;

    private String activeCity;
    private booleanStorage storeBool = booleanStorage.getInstance();

    @FXML Button button;
    @FXML Button menuButton;
    @FXML Button closeMenuButton;
    @FXML Button dragMenu;
    @FXML ImageView circle;
    @FXML AnchorPane backgroundPane;
    @FXML AnchorPane backgroundPane2;
    @FXML Pane infoPane;
    @FXML Pane labelPane;

    private double rotate = 0;
    private double translate = 0;
    private int daysAhead = 0;
    Thread th = new Thread();
    Thread hh = new Thread();
    Random rand = new Random();
    Random a2 = new Random();
    private int a = 10;
    private int n = 9;

    public double xValue;
    public double yValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
    public int currentTime = Integer.parseInt(currentHour);
    @FXML private Pane clouds;

    private FadeTransition fadeTransition;
    private TranslateTransition translateTransition;
    private ParallelTransition parallelTransition;

    WeatherForecast weather = new WeatherForecast("London");
    private WeatherObject[][] info = weather.getHourlyWeather();
    private WeatherObject[][] info2 = weather.getHourlyWeather();
    Day dw;
    HourWeather hw;

    public void setxValue(MouseEvent event) {

        //System.out.println(event.getX());
        xValue = event.getX();
        yValue = event.getY();
        // close menu if open and click was outside boundary
        if(menuToggleOpen && (xValue>140 || yValue>310)){
            //close the menu
            toggleMenu();
        }
    }

    public void handleKeys(KeyEvent event) throws InterruptedException {
        //System.out.println(th.isAlive());
        if (event.getCode() == KeyCode.S && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            //System.out.println("left");
            dragCircleLeft();
        } else if (event.getCode() == KeyCode.A && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            //System.out.println("right");
            dragCircleRight();
        } else if (event.getCode() == KeyCode.ENTER && !menuToggleOpen && !dragMenuOpen) {
            try {
                storeBool.setfxmlActive(false);
                Parent root = FXMLLoader.load(getClass().getResource("FXML2.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
                theStage.centerOnScreen();
                scene1.getRoot().requestFocus();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getCode() == KeyCode.K && daysAhead != 0 && !hh.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            dragLeft();
        } else if (event.getCode() == KeyCode.L && daysAhead != 5 && !hh.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            dragRight();
        }
    }

    public void handleKeysAgain(KeyEvent event) throws InterruptedException {
        if (event.getCode() == KeyCode.S && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            //System.out.println("left");
            dragCircleLeft();
        } else if (event.getCode() == KeyCode.A && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            //System.out.println("right");
            dragCircleRight();
        } else if (event.getCode() == KeyCode.ENTER && !menuToggleOpen && !dragMenuOpen) {
            try {
                storeBool.setfxmlActive(false);
                Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
                theStage.centerOnScreen();
                scene1.getRoot().requestFocus();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getCode() == KeyCode.K && daysAhead != 0 && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            dragLeftLarge();
        } else if (event.getCode() == KeyCode.L && daysAhead != 5 && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            dragRightLarge();
        }
    }

    public void dragCircleRight() {
        th = new Thread() {
            @Override
            public void run() {
                if(daysAhead == 0 && currentTime == Integer.parseInt(currentHour)){
                    System.out.println("returns");
                    return;
                }
                
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
                //System.out.println(currentTime);
                if((currentTime == 23) && !hh.isAlive()){
                        Platform.runLater(() -> {
                            try {
                                dragLeft();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    
                }
            }
        };
        th.start();
    }

    public void dragCircleLeft() {
        th = new Thread() {
            @Override
            public void run() {
                if(daysAhead == 5 && currentTime == 23){
                    System.out.println("returns");
                    return;
                }
                else if(daysAhead > 5){
                    return;
                }
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
                        Platform.runLater(() -> circle.setRotate(0));
                    }                    

                }

                wheelTime();

                if((currentTime == 0 || currentTime == 24) && !hh.isAlive()){
                        currentTime = 0;
                        dragRight();
                }

            }
        };
        th.start();

    }
    public void updateInfoPane(){
        if(daysAhead == 0){

            if(info[0][currentTime] instanceof HourWeather){
                hw = (HourWeather) info[0][currentTime];
            }
            else if(currentTime == 0){
                hw = (HourWeather) info[0][1];
            }
            
            dw = (Day) info2[0][0];
            lightPollution.setText("20%");
            tempFieldLabel.setText("" + hw.getTemp() + "°C");
            sunsetSunrise.setText("" + dw.getSunrise() + "/" + dw.getSunset());
            comet.setText("2%");
            cloudCover.setText("70%");
            tempHL.setText("" + dw.getHighTemo() + "°C/" + "" + dw.getLowTemp() + "°C");
            rainfall.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            humidity.setText(hw.getHumidity() + "%");
            if(dw.getName().equals("Mon")){
                day0.setText("Mon");
                day1.setText("Tue");
                day2.setText("Wed");
                day3.setText("Thu");
                day4.setText("Fri");
                day5.setText("Sat");
                day6.setText("Sun");
                day7.setText("Mon");
                day8.setText("Tue");
                day9.setText("Wed");
                day10.setText("Thu");
                day11.setText("Fri");
                day12.setText("Sat");
                day13.setText("Sun");
            }
            else if(dw.getName().equals("Tue")){
                day0.setText("Tue");
                day1.setText("Wed");
                day2.setText("Thu");
                day3.setText("Fri");
                day4.setText("Sat");
                day5.setText("Sun");
                day6.setText("Mon");
                day7.setText("Tue");
                day8.setText("Wed");
                day9.setText("Thu");
                day10.setText("Fri");
                day11.setText("Sat");
                day12.setText("Sun");
                day13.setText("Mon");
            }
            else if(dw.getName().equals("Wed")){
                day0.setText("Wed");
                day1.setText("Thu");
                day2.setText("Fri");
                day3.setText("Sat");
                day4.setText("Sun");
                day5.setText("Mon");
                day6.setText("Tue");
                day7.setText("Wed");
                day8.setText("Thu");
                day9.setText("Fri");
                day10.setText("Sat");
                day11.setText("Sun");
                day12.setText("Mon");
                day13.setText("Tue");
            }
            else if(dw.getName().equals("Thu")){
                day0.setText("Thu");
                day1.setText("Fri");
                day2.setText("Sat");
                day3.setText("Sun");
                day4.setText("Mon");
                day5.setText("Tue");
                day6.setText("Wed");
                day7.setText("Thu");
                day8.setText("Fri");
                day9.setText("Sat");
                day10.setText("Sun");
                day11.setText("Mon");
                day12.setText("Tue");
                day13.setText("Wed");
            }
            else if(dw.getName().equals("Fri")){
                day0.setText("Fri");
                day1.setText("Sat");
                day2.setText("Sun");
                day3.setText("Mon");
                day4.setText("Tue");
                day5.setText("Wed");
                day6.setText("Thu");
                day7.setText("Fri");
                day8.setText("Sat");
                day9.setText("Sun");
                day10.setText("Mon");
                day11.setText("Tue");
                day12.setText("Wed");
                day13.setText("Thu");
            }
            else if(dw.getName().equals("Sat")){
                day0.setText("Sat");
                day1.setText("Sun");
                day2.setText("Mon");
                day3.setText("Tue");
                day4.setText("Wed");
                day5.setText("Thu");
                day6.setText("Fri");
                day7.setText("Sat");
                day8.setText("Sun");
                day9.setText("Mon");
                day10.setText("Tue");
                day11.setText("Wed");
                day12.setText("Thu");
                day13.setText("Fri");
            }
            else if(dw.getName().equals("Sun")){
                day0.setText("Sun");
                day1.setText("Mon");
                day2.setText("Tue");
                day3.setText("Wed");
                day4.setText("Thu");
                day5.setText("Fri");
                day6.setText("Sat");
                day7.setText("Sun");
                day8.setText("Mon");
                day9.setText("Tue");
                day10.setText("Wed");
                day11.setText("Thu");
                day12.setText("Fri");
                day13.setText("Sat");
            }

            temp0.setText("" + hw.getTemp() + "°C");
            hw = (HourWeather) info[1][currentTime];
            temp1.setText("" + hw.getTemp() + "°C");
            hw = (HourWeather) info[2][currentTime];
            temp2.setText("" + hw.getTemp() + "°C");
            hw = (HourWeather) info[3][currentTime];
            temp3.setText("" + hw.getTemp() + "°C");
            hw = (HourWeather) info[4][currentTime];
            temp4.setText("" + hw.getTemp() + "°C");
            hw = (HourWeather) info[5][currentTime];
            temp5.setText("" + hw.getTemp() + "°C");
            temp6.setText("" + hw.getTemp() + "°C");

            precip0.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            precip1.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            precip2.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            precip3.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            precip4.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            precip5.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            precip6.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
        }
        else{ 
            
            if(info[daysAhead][currentTime] instanceof HourWeather){
                hw = (HourWeather) info[daysAhead][currentTime];
            }
            else if(currentTime == 0){
                hw = (HourWeather) info[daysAhead][1];
            }
           
            dw = (Day) info2[daysAhead][0];
            lightPollution.setText("20%");
            tempFieldLabel.setText("" + hw.getTemp() + "°C");
            sunsetSunrise.setText("" + dw.getSunrise() + "/" + dw.getSunset());
            comet.setText("2%");
            cloudCover.setText("70%");
            tempHL.setText("" + dw.getHighTemo() + "°C/" + "" + dw.getLowTemp() + "°C");
            rainfall.setText((int)Double.parseDouble(dw.getPrecip()) + "mm");
            humidity.setText(hw.getHumidity() + "%");
        }
    }
    public void dragInfoPane() throws InterruptedException {
        th = new Thread() {
            @Override
            public void run() {
                if(menuToggleOpen){
                    return;
                }
                if (translate == 0) {
                    Platform.runLater(() -> updateInfoPane());
                    while (translate > -(scene1.getHeight() - 50)) {
                        translate -= 3.5;
                        Platform.runLater(() -> infoPane.setTranslateY(translate));
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dragMenuOpen = true;
                    Platform.runLater(() -> dragMenu.setRotate(180));

                } else {
                    Platform.runLater(() -> infoPane.setTranslateY(0));
                    translate = 0;
                    dragMenuOpen = false;
                    Platform.runLater(() -> dragMenu.setRotate(0));

                }
            }
        };
        th.start();
    }

    public void dragInfoPaneLarge() throws InterruptedException {
        th = new Thread() {
            @Override
            public void run() {
                if (translate == 0) {
                    Platform.runLater(() -> updateInfoPane());
                    while (translate > -718) {
                        translate -= 3.5;
                        Platform.runLater(() -> infoPane.setTranslateY(translate));

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dragMenuOpen = true;
                    Platform.runLater(() -> dragMenu.setRotate(180));

                } else {
                    Platform.runLater(() -> infoPane.setTranslateY(0));
                    translate = 0;
                    dragMenuOpen = false;
                    Platform.runLater(() -> dragMenu.setRotate(0));
                }
            }
        };
        th.start();
    }

    public void wheelTime() {
        if ((int) Math.round(circle.getRotate() / -15) >= 0) {
            currentTime = ((int) Math.round(circle.getRotate() / -15));
        } else {
            currentTime = (24 - (int) Math.round(circle.getRotate() / 15));
        }
        Platform.runLater(() -> updateForecastValues());
    }

    public void updateValues() {
        dw = (Day) info2[0][0];
        if(currentTime == 0){
            hw = (HourWeather) info[0][24];
        }
        else {
            hw = (HourWeather) info[0][currentTime];
        }
        clouds.setStyle("-fx-background-image: url(\"WeatherApp/resources/sun.png\");" +
                "    -fx-background-size: cover;");
        location.setText(activeCity);
        tempField.setText("" + hw.getTemp() + "°C");
        currDate.setText(dw.getName() + " " + dw.getDayNumber() + ". " + dw.getMonthName());
        feelsLike.setText("Feels like: " + hw.getFeelsLike()  + "°C");
        String conditions = "Good";
        if(hw.getCondition().equals("Overcast") || hw.getCondition().equals("Chance of Rain")){
            conditions = "Poor";
        }
        else if(hw.getCondition().equals("Clear")){
            conditions = "Good";
        }
        curCondition.setText("Stargazing Conditions: " + conditions);
    }

    public void updateForecastValues(){
        if(daysAhead == 0){
            updateValues();
            return;
        }
        location.setText(activeCity);
        if(daysAhead == 4){
            dw = (Day) info2[4][0];
        }
        else{
            dw = (Day) info2[daysAhead][0];
        }
        if(currentTime != 0) {
            hw = (HourWeather) info[daysAhead][currentTime];
        }
        else{
            hw = (HourWeather) info[daysAhead][24];
        }

        currDate.setText(dw.getName() + " " + dw.getDayNumber() + ". " + dw.getMonthName());
        tempField.setText("" + hw.getTemp() + "°C");

        feelsLike.setText("Feels like: " + hw.getFeelsLike()  + "°C");
        String conditions = "Good";
        if(hw.getCondition().equals("Overcast") || hw.getCondition().equals("Chance of Rain")){
            conditions = "Poor";
        }
        else if(hw.getCondition().equals("Clear")){
            conditions = "Good";
        }
        curCondition.setText("Stargazing Conditions: " + conditions);
    }
    public void updateCity(){
        weather = new WeatherForecast(activeCity);
        updateWheelPos(currentHour);
        info = weather.getHourlyWeather();
    }

    public void updateWheelPos(String currentHour){
        if(!activeCity.equals("London")) {
            circle.setRotate(-15 * (Double.parseDouble(currentHour)));
        }
        else{
            circle.setRotate(-15 * Double.parseDouble(currentHour));

        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuToggleOpen = false;
        setUpMenu();
        activeCity = "London";
        updateValues();
        menuToggleOpen = false;
        updateWheelPos(currentHour);
        rotate = -15 * Double.parseDouble(currentHour);
        button.setFocusTraversable(false);
        dragMenu.setFocusTraversable(false);
        theStage.setResizable(false);
        addBackgrounds();
        changeBackground();
        //circle.setRotate(3);
    }

    public void changeBackground(){
        int previousN = n;
        n = rand.nextInt(8);

        if(n == previousN){
            changeBackground();
        }

        String backgroundPath;
        if(storeBool.getfxmlActive()) {
            backgroundPath = backgrounds.get(n);
        }
        else{
            backgroundPath = backgroundsLarge.get(n);
        }
        //System.out.println(backgroundPath);
        backgroundPane.setStyle("-fx-background-image: url('" + backgroundPath + "');");
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

    public void addBackgrounds(){
        backgrounds.add("WeatherApp/resources/1.png");
        backgrounds.add("WeatherApp/resources/2.png");
        backgrounds.add("WeatherApp/resources/3.png");
        backgrounds.add("WeatherApp/resources/4.png");
        backgrounds.add("WeatherApp/resources/5.png");
        backgrounds.add("WeatherApp/resources/6.png");
        backgrounds.add("WeatherApp/resources/7.png");
        backgrounds.add("WeatherApp/resources/8.png");

        backgroundsLarge.add("WeatherApp/resources/9.png");
        backgroundsLarge.add("WeatherApp/resources/10.png");
        backgroundsLarge.add("WeatherApp/resources/11.png");
        backgroundsLarge.add("WeatherApp/resources/12.png");
        backgroundsLarge.add("WeatherApp/resources/13.png");
        backgroundsLarge.add("WeatherApp/resources/14.png");
        backgroundsLarge.add("WeatherApp/resources/15.png");
        backgroundsLarge.add("WeatherApp/resources/16.png");
        backgroundsLarge.add("WeatherApp/resources/17.png");
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
            translateTransition.setFromX(menu.getPrefWidth() - (menu.getPrefWidth()*0.5));
            translateTransition.setFromY(menu.getPrefHeight() - (menu.getPrefHeight()/6.12));
            translateTransition.setToX(-menu.getPrefWidth() + (menu.getPrefWidth()*0.5));
            translateTransition.setToY( -menu.getPrefWidth() + (menu.getPrefHeight()/6.12));
            translateTransition.setDelay(Duration.millis(100));
            parallelTransition.getChildren().addAll(
                    fadeTransition,
                    translateTransition
            );
            parallelTransition.play();
            menuToggleOpen = false;
            menu.toBack();
            button.setVisible(true);
            closeMenuButton.setVisible(false);
        } //otherwise open the menu
        else{
            fadeTransition.setFromValue(0.0f);
            fadeTransition.setToValue(0.9f);
            fadeTransition.setDelay(Duration.millis(150));
            translateTransition.setFromX(-menu.getPrefWidth() + (menu.getPrefWidth()*0.5));
            translateTransition.setFromY(-menu.getPrefWidth() + (menu.getPrefHeight()/6.12));
            translateTransition.setToX(menu.getPrefWidth() - (menu.getPrefWidth()*0.5));
            translateTransition.setToY(menu.getPrefHeight() - (menu.getPrefHeight()/6.12));
            parallelTransition.getChildren().addAll(
                    fadeTransition,
                    translateTransition
            );
            parallelTransition.play();
            menuToggleOpen = true;
            menu.toFront();
            closeMenuButton.toFront();
            closeMenuButton.setVisible(true);
            button.setVisible(false);
        }
    }


    //Handle menu button click
    public void handleMenuButtonClick(ActionEvent e) throws IOException{
        String [] seg = e.getSource().toString().split(Pattern.quote("'"));
        activeCity = seg[seg.length-1];
        location.setText(activeCity);
        updateCity();
        updateForecastValues();
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
    /*
    Method when clicking the K key. Previous day
     */
    public void dragLeft() throws InterruptedException {
        hh = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                int labelWidth = (int)labelPane.getWidth();
                int counter = 1;
                boolean atPos = false;
                while (!atPos) {
                    translate += 1;
                    Platform.runLater(() -> labelPane.setTranslateX(translate));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(counter == 640){
                        daysAhead--;
                        System.out.println("daysAhead is " + daysAhead);
                        atPos = true;
                        Platform.runLater(() -> changeBackground());
                    }
                    else if(originalPos + labelPane.getTranslateX() >= labelWidth){
                        translate = (originalPos + labelWidth) * -1;
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                        Platform.runLater(() -> updateForecastValues());
                    }
                    counter++;

                }
                
            }
        };
        hh.start();
    }

    /*
    Method when clicking the L key. Next day
     */
    public void dragRight(){
        hh = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                int labelWidth = (int)labelPane.getWidth();
                int counter = 1;
                boolean atPos = false;
                while (!atPos) {
                    
                    translate -= 1;
                    Platform.runLater(() -> labelPane.setTranslateX(translate));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(originalPos + labelPane.getTranslateX() <= -labelWidth){                  

                        translate = backgroundPane.getWidth() - originalPos;
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                    }
                    if(counter == 640){    
                        daysAhead++;
                        atPos = true;
                        System.out.println("DaysAhead being incremented " + daysAhead);
                        Platform.runLater(() -> labelPane.setTranslateX(0));
                        Platform.runLater(() -> updateForecastValues());
                        Platform.runLater(() -> changeBackground());
                    }
                    counter++;
                    
                    
                }
            }
            
        };
        hh.start();

    }
    
    /*
    Method when clicking the L key. Next day
     */
    public void dragRightLarge(){
        hh = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                int labelWidth = (int)labelPane.getWidth();
                int counter = 1;
                boolean atPos = false;
                while (!atPos) {
                    
                    translate -= 2;
                    Platform.runLater(() -> labelPane.setTranslateX(translate));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Counter is: " + counter);
                    if(originalPos + labelPane.getTranslateX() <= -labelWidth){                  

                        translate = backgroundPane.getWidth() - originalPos;
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                    }
                    if(counter == 1024){    
                        daysAhead++;
                        atPos = true;
                        System.out.println("DaysAhead being incremented " + daysAhead);
                        Platform.runLater(() -> labelPane.setTranslateX(0));
                        Platform.runLater(() -> updateForecastValues());
                        Platform.runLater(() -> changeBackground());
                    }
                    counter++;
                    
                    
                    
                }
            }
            
        };
        hh.start();

    }
    
    /*
    Method when clicking the K key. Previous day
     */
    public void dragLeftLarge() throws InterruptedException {
        hh = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                int labelWidth = (int)labelPane.getWidth();
                int counter = 1;
                boolean atPos = false;
                while (!atPos) {
                    translate += 2;
                    Platform.runLater(() -> labelPane.setTranslateX(translate));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(counter == 1024){
                        daysAhead--;
                        System.out.println("daysAhead is " + daysAhead);
                        atPos = true;
                        Platform.runLater(() -> changeBackground());
                    }
                    else if(originalPos + labelPane.getTranslateX() >= labelWidth){
                        translate = (originalPos + labelWidth) * -1;
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                        Platform.runLater(() -> updateForecastValues());
                    }
                    counter++;

                }
                
            }
        };
        hh.start();
    }
}
