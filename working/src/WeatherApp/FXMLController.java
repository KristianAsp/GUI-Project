package WeatherApp;

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


    @FXML Label location = new Label();
    @FXML Label tempField = new Label();
    @FXML Label curCondition = new Label();
    @FXML Label currDate = new Label();
    @FXML Label feelsLike = new Label();
    @FXML Pane menu;

    ArrayList<String> backgrounds = new ArrayList<>();
    private boolean menuToggleOpen;
    private String activeCity;

    @FXML
    Button button;
    @FXML
    Button menuButton;
    @FXML 
    Button closeMenuButton;
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

    private double rotate = 0;
    private double translate = 0;
    private int daysAhead = 0;
    Thread th = new Thread();
    Random rand = new Random();

    public double xValue;
    public double yValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());

    private FadeTransition fadeTransition;
    private TranslateTransition translateTransition;
    private ParallelTransition parallelTransition;
    WeatherForecast weather = new WeatherForecast("London");
            
    public void setxValue(MouseEvent event) {
        System.out.println(event.getX());
        xValue = event.getX();
        yValue = event.getY();
        // close menu if open and click was outside boundary
        if(menuToggleOpen && (xValue>140 || yValue>310)){
            //close the menu
            toggleMenu();
        }
    }

    public void handleKeys(KeyEvent event) throws InterruptedException {
        System.out.println(th.isAlive());
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
        } else if (event.getCode() == KeyCode.K && daysAhead != 0 && !th.isAlive()) {
            dragLeft();
        } else if (event.getCode() == KeyCode.L && daysAhead != 3 && !th.isAlive()) {
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

        String[] weatherArray = weather.getForecast1();
        String[] array;
        if(daysAhead == 0){
            updateValues();
            return;
        }
        if(daysAhead == 1){
            array = new String[]{weatherArray[1], weatherArray[2], weatherArray[3], weatherArray[4], weatherArray[5], weatherArray[6], weatherArray[7], weatherArray[8]};
        }
        else if(daysAhead == 2){
            array = new String[]{weatherArray[9], weatherArray[10], weatherArray[11], weatherArray[12], weatherArray[13], weatherArray[14], weatherArray[15], weatherArray[16]};
            System.out.println(array[0]);
            System.out.println(array[1]);
            System.out.println(array[2]);
            System.out.println(array[3]);
            System.out.println(array[4]);
            System.out.println(weatherArray[9]);
        }
        else{
            array = new String[]{weatherArray[17], weatherArray[18], weatherArray[19], weatherArray[20], weatherArray[21], weatherArray[22], weatherArray[23], weatherArray[24]};
        }

        location.setText(activeCity);
        tempField.setText("" + array[3] + "°C");
        currDate.setText(array[0] + " " + array[1] + ". " + array[2]);
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
    public void updateCity(String city){
        weather = new WeatherForecast(activeCity);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuToggleOpen = false;
        setUpMenu();
        activeCity = "London";
        updateValues();
        menuToggleOpen = false;
        circle.setRotate(-15 * Double.parseDouble(currentHour));
        rotate = -15 * Double.parseDouble(currentHour);
        button.setFocusTraversable(false);
        dragMenu.setFocusTraversable(false);
        theStage.setResizable(false);
        addBackgrounds();
        int n = rand.nextInt(5);
        String backgroundPath = backgrounds.get(n);
        System.out.println(backgroundPath);
        backgroundPane.setStyle("-fx-background-image: url('" + backgroundPath + "');");
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
        System.out.println(activeCity);
        updateCity(activeCity);
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
    /*
    Method when clicking the K key. Previous day
     */
    public void dragLeft() throws InterruptedException {
        th = new Thread() {
            @Override
            public void run() {
                double originalPos = labelPane.getLayoutX();
                System.out.println(originalPos);
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
                        System.out.println("Goes to if");
                        System.out.println(labelPane.getTranslateX());
                    }
                    else if(originalPos + labelPane.getTranslateX() == 320){
                        daysAhead--;
                        translate = (originalPos + labelWidth) * -1;
                        System.out.println(translate);
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                        System.out.println("Goes to else if");
                        Platform.runLater(() -> updateForecastValues(daysAhead));
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
                System.out.println(originalPos);
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
                        System.out.println("Goes to if");

                    }
                    else if(originalPos + labelPane.getTranslateX() == -labelWidth){
                        daysAhead++;
                        translate = backgroundPane.getWidth() - originalPos;
                        System.out.println(translate);
                        Platform.runLater(() -> labelPane.setTranslateX(translate));
                        System.out.println("Goes to else if");
                        Platform.runLater(() -> updateForecastValues(daysAhead));
                    }

                }
            }
        };
        th.start();

    }
}
