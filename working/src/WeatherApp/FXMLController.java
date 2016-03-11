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

    @FXML Pane menu;


    ArrayList<String> backgrounds = new ArrayList<>();
    private boolean menuToggleOpen;
    private boolean dragMenuOpen = false;
    private String activeCity;

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
    Random rand = new Random();
    private int n = 7;

    public double xValue;
    public double yValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
    public int currentTime = Integer.parseInt(currentHour);
    @FXML private ImageView clouds;

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
        if (event.getCode() == KeyCode.S && !th.isAlive()) {
            //System.out.println("left");
            dragCircleLeft();
        } else if (event.getCode() == KeyCode.A && !th.isAlive()) {
            //System.out.println("right");
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
        } else if (event.getCode() == KeyCode.K && daysAhead != 0 && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            dragLeft();
        } else if (event.getCode() == KeyCode.L && daysAhead != 5 && !th.isAlive() && !menuToggleOpen && !dragMenuOpen) {
            dragRight();
        }
    }

    public void handleKeysAgain(KeyEvent event) {

        if (event.getCode() == KeyCode.S) {
            //System.out.println("left");
            dragCircleLeft();
            wheelTime();
        } else if (event.getCode() == KeyCode.A) {
            //System.out.println("right");
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

        //System.out.println("it's working ");
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
                Platform.runLater(() -> wheelTime());
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
                Platform.runLater(() -> wheelTime());
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
    public void updateInfoPane(){
        if(daysAhead == 0){
            //if(hw instanceof HourWeather){
            //    System.out.println("true");
            //}
            hw = (HourWeather) info[0][currentTime];
            dw = (Day) info2[0][0];
            lightPollution.setText("20%");
            tempFieldLabel.setText("" + hw.getTemp() + "°C");
            sunsetSunrise.setText("" + dw.getSunrise() + "/" + dw.getSunset());
            comet.setText("2%");
            cloudCover.setText("70%");
            tempHL.setText("" + dw.getHighTemo() + "°C/" + "" + dw.getLowTemp() + "°C");
            rainfall.setText(dw.getPrecip());
            humidity.setText(hw.getHumidity());
        }
        else{
            hw = (HourWeather) info[daysAhead][currentTime];
            dw = (Day) info2[daysAhead][0];
            lightPollution.setText("20%");
            tempFieldLabel.setText("" + hw.getTemp() + "°C");
            sunsetSunrise.setText("" + dw.getSunrise() + "/" + dw.getSunset());
            comet.setText("2%");
            cloudCover.setText("70%");
            tempHL.setText("" + dw.getHighTemo() + "°C/" + "" + dw.getLowTemp() + "°C");
            rainfall.setText(dw.getPrecip());
            humidity.setText(hw.getHumidity());
        }
    }
    public void dragInfoPane() throws InterruptedException {
        //System.out.println("dragging pane");
        th = new Thread() {
            @Override
            public void run() {
                if (translate == 0) {
                    Platform.runLater(() -> updateInfoPane());
                    System.out.println("updates infoPane");
                    while (translate > -430) {
                        translate -= 3.5;
                        Platform.runLater(() -> movePanel());
                        Platform.runLater(() -> infoPane.setTranslateY(translate));
                        //System.out.println(translate);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    dragMenuOpen = true;

                } else {
                    Platform.runLater(() -> infoPane.setTranslateY(0));
                    translate = 0;
                    dragMenuOpen = false;
                }
            }
        };
        th.start();
    }

    public void movePanel() {
        Platform.runLater(() -> infoPane.setTranslateY(translate));
    }

    public void wheelTime() {

        if ((int) Math.round(circle.getRotate() / -15) >= 0) {
            currentTime = ((int) Math.round(circle.getRotate() / -15));
        } else {
            currentTime = (24 - (int) Math.round(circle.getRotate() / 15));
        }
        //System.out.println(currentHour);
        updateForecastValues();
    }

    public void updateValues() {
        dw = (Day) info2[0][0];
        if(currentTime == 0){
            hw = (HourWeather) info[0][24];
        }
        else {
            hw = (HourWeather) info[0][currentTime];
        }
        clouds.setImage(new Image(hw.getIconUrl()));
        clouds.setPreserveRatio(true);
        clouds.setFitHeight(42);
        clouds.setFitWidth(42);
        //System.out.println("Updating values");
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
            System.out.println(dw.getDayNumber());
        }
        else{
            dw = (Day) info2[daysAhead][0];
            System.out.println(dw.getDayNumber());
        }
        if(currentTime != 0) {
            hw = (HourWeather) info[daysAhead][currentTime];
        }
        else{
            hw = (HourWeather) info[daysAhead][24];
        }

        clouds.setImage(new Image(hw.getIconUrl()));
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
            circle.setRotate(-15 * (Double.parseDouble(currentHour) + 1));
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
        n = rand.nextInt(5);
        if(n == previousN){
            changeBackground();
        }
        String backgroundPath = backgrounds.get(n);
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
            button.setVisible(true);
            closeMenuButton.setVisible(false);
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
            closeMenuButton.toFront();
            closeMenuButton.setVisible(true);
            button.setVisible(false);
        }
    }

    //Handle menu button click
    public void handleMenuButtonClick(ActionEvent e) throws IOException{
        String [] seg = e.getSource().toString().split(Pattern.quote("'"));
        activeCity = seg[seg.length-1];
        //System.out.println(activeCity);
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
        th = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                int labelWidth = (int)labelPane.getWidth();

                boolean atPos = false;
                while (!atPos) {
                    translate += 1;
                    Platform.runLater(() -> labelPane.setTranslateX(translate));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(originalPos + labelPane.getTranslateX() == originalPos){
                        atPos = true;
                        Platform.runLater(() -> changeBackground());
                    }
                    else if(originalPos + labelPane.getTranslateX() == 320){
                        daysAhead--;
                        translate = (originalPos + labelWidth) * -1;
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                        Platform.runLater(() -> updateForecastValues());
                    }

                }
            }
        };
        th.start();
    }

    /*
    Method when clicking the L key. Next day
     */
    public void dragRight(){
        th = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                //System.out.println(originalPos);
                int labelWidth = (int)labelPane.getWidth();

                boolean atPos = false;
                while (!atPos) {
                    translate -= 1;
                    Platform.runLater(() -> labelPane.setTranslateX(translate));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(originalPos + labelPane.getTranslateX() == originalPos){
                        atPos = true;
                        //System.out.println("Goes to if");
                        Platform.runLater(() -> changeBackground());

                    }
                    else if(originalPos + labelPane.getTranslateX() == -labelWidth){
                        daysAhead++;
                        translate = backgroundPane.getWidth() - originalPos;
                        //System.out.println(translate);
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                        //System.out.println("Goes to else if");
                        Platform.runLater(() -> updateForecastValues());
                    }

                }
            }
        };
        th.start();

    }
}
