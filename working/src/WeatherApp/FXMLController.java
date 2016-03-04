package WeatherApp;

import WeatherAPI.WeatherCondition;
import WeatherAPI.WeatherForecast;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
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
import javafx.stage.Stage;


/**
 *
 * @author cintia
 */
public class FXMLController extends Application implements Initializable{

    private static Stage theStage;
    private Scene scene1;
    WeatherForecast weather;
    WeatherCondition condition;
    
    @FXML Label tempField = new Label();
    @FXML Label curCondition = new Label();
    @FXML Label currDate = new Label();
    @FXML Label feelsLike = new Label();
    @FXML Pane menu;


    @FXML Button button;
    @FXML Button menuButton;
    @FXML Button dragMenu;
    @FXML ImageView circle;
    @FXML AnchorPane backgroundPane = new AnchorPane();
    @FXML Pane infoPane;


    public double rotate = 0;
    public double translate = 0;
    public double translation = 0;
    Thread th;

    public double xValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
    
    public void setxValue(MouseEvent event){
                System.out.println(event.getX());
                xValue = event.getX();
    }

    public void handleKeys(KeyEvent event){
        if(event.getCode() == KeyCode.S){
            System.out.println("left");
            dragCircleLeft();
            wheelTime();
        }
        else if(event.getCode() == KeyCode.A){
            System.out.println("right");
            dragCircleRight();
            wheelTime();
        }
        else if(event.getCode() == KeyCode.ENTER){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXML2.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void handleKeysAgain(){
        backgroundPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if(event.getCode() == KeyCode.S){
                    System.out.println("left");
                    dragCircleLeft();
                    wheelTime();
                }
                else if(event.getCode() == KeyCode.A){
                    System.out.println("right");
                    dragCircleRight();
                    wheelTime();
                }
                else if(event.getCode() == KeyCode.ENTER){
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
        });
        
        System.out.println("it's working ");
        
    }

    public void dragCircleRight(){
        System.out.println("right");
        rotate += 15;
        circle.setRotate(rotate);
        if(rotate == 360 || rotate == -360){
            rotate = 0;
        }
    }

    public void dragCircleLeft(){
        System.out.println("left");
        rotate -= 15;
        circle.setRotate(rotate);
        if(rotate == 360 || rotate == -360){
            rotate = 0;
        }
    }

    public void dragInfoPane() throws InterruptedException{
        System.out.println("dragging pane");
        th = new Thread(){
            @Override
            public void run(){
        if(translate == 0){
            while(translate > -430){
                translate -= 3.5;
                movePanel();
                infoPane.setTranslateY(translate);
                System.out.println(translate);
                try { 
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            infoPane.setTranslateY(0);
            translate = 0;
        }
    }
                };
        th.start();
    }
    
    public void movePanel(){
        infoPane.setTranslateY(translate);
    }
 
    
    public void wheelTime(){
        int currentHour;
                
        if((int)Math.round(circle.getRotate() / -15) >= 0){
            currentHour = (int)Math.round(circle.getRotate() / -15);
        }
        else{
            currentHour = 24 - (int)Math.round(circle.getRotate() / 15);
        }
        System.out.println(currentHour);
        updateValues();
    }
    public void updateValues(){
        System.out.println("Updating values");
    }

    public void initialize(URL url, ResourceBundle rb) {
        weather = new WeatherForecast("44418");
        condition = new WeatherCondition("44418");

        tempField.setText("" + condition.weatherConditionList.get(0).currentTemp + "°C");
        currDate.setText(weather.weatherForecastList.get(0).date);
        feelsLike.setText("Feels like: " + condition.weatherConditionList.get(0).feelsLike  + "°C");
        curCondition.setText("Stargazing Conditions: Good");
        menu.setVisible(false);
        circle.setRotate(-15 * Double.parseDouble(currentHour));
        rotate = -15 * Double.parseDouble(currentHour);
        button.setFocusTraversable(false);
        dragMenu.setFocusTraversable(false);
        backgroundPane.requestFocus();        
    }
    
    public void callMenu(){
        menu.setVisible(true);
        button.setVisible(false);
    }

    public void closeMenu(){
        menu.setVisible(false);
        button.setVisible(true);
    }
    
    public void switchScreen2(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ENTER){
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
    
    public static void main(String[] args) {
        launch(args);
    }
   
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        scene1 = new Scene(root);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
