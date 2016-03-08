package WeatherApp;

import WeatherAPI.WeatherCondition;
import WeatherAPI.WeatherForecast;
//import WeatherAPI.WeatherForecastNew;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
    
    @FXML Label location = new Label();
    @FXML Label tempField = new Label();
    @FXML Label curCondition = new Label();
    @FXML Label currDate = new Label();
    @FXML Label feelsLike = new Label();
    @FXML Pane menu;
    
    private boolean menuToggleOpen;

    @FXML Button button;
    @FXML Button menuButton;
    @FXML Button dragMenu;
    @FXML ImageView circle;
    @FXML AnchorPane backgroundPane;
    @FXML AnchorPane backgroundPane2;
    @FXML Pane infoPane;


    public double rotate = 0;
    public double translate = 0;
    Thread th;

    public double xValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
	
	public static void main(String[] args) {
        launch(args);
    }
   
    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        scene1 = new Scene(root);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
    public void setxValue(MouseEvent event){
                System.out.println(event.getX());
                xValue = event.getX();
    }

    public void handleKeys(KeyEvent event){
        if(event.getCode() == KeyCode.S){
            System.out.println("left");
            dragCircleLeft();
        }
        else if(event.getCode() == KeyCode.A){
            System.out.println("right");
            dragCircleRight();
        }
        else if(event.getCode() == KeyCode.ENTER){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXML2.fxml"));
                scene1 = new Scene(root);
                theStage.setScene(scene1);
                theStage.show();
                scene1.getRoot().requestFocus();
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void handleKeysAgain(KeyEvent event){

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
                        scene1.getRoot().requestFocus();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        
        System.out.println("it's working ");
    }

    public void dragCircleRight(){
        th = new Thread(){
            @Override
            public void run(){
                int innerRotation = 0;
                while(innerRotation < 15){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    rotate += 1;
                    Platform.runLater(() -> circle.setRotate(rotate));
                    innerRotation += 1;
                    
                    if(rotate == 360 || rotate == -360){
                        rotate = 0;
                    }
                }
                wheelTime();
            }
        };
        th.start();
    }

    public void dragCircleLeft(){
        th = new Thread(){
            @Override
            public void run(){
                int innerRotation = 0;
                while(innerRotation < 15){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    rotate -= 1;
                    Platform.runLater(() -> circle.setRotate(rotate));
                    innerRotation += 1;
                    
                    if(rotate == 360 || rotate == -360){
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
            currentHour = ((int)Math.round(circle.getRotate() / -15));
        }
        else{
            currentHour = (24 - (int)Math.round(circle.getRotate() / 15));
        }
        System.out.println(currentHour);
        updateValues();
    }
    public void updateValues(){
        System.out.println("Updating values");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        weather = new WeatherForecast("44418");
        condition = new WeatherCondition("44418");
        menuToggleOpen = false;
        tempField.setText("" + condition.weatherConditionList.get(0).currentTemp + "°C");
        //currDate.setText(weather.weatherForecastList.get(0).date);
        feelsLike.setText("Feels like: " + condition.weatherConditionList.get(0).feelsLike  + "°C");
        curCondition.setText("Stargazing Conditions: Good");
        menu.setVisible(false);
        circle.setRotate(-15 * Double.parseDouble(currentHour));
        rotate = -15 * Double.parseDouble(currentHour);
        button.setFocusTraversable(false);
        dragMenu.setFocusTraversable(false);
        theStage.setResizable(false);
        //circle.setRotate(3);
    }
    //Method to toggle the side menu open/close
    public void toggleMenu(){
        if(menuToggleOpen){
            menu.setVisible(false);
            menuToggleOpen = false;
        }
        else{
            menu.setVisible(true);
            menuToggleOpen = true;
            button.toFront(); 
        }
    }
    //Handle menu button click
    public void handleMenuButtonClick(ActionEvent e) throws IOException{
        String [] seg = e.getSource().toString().split(Pattern.quote("'"));
        System.out.println(seg[seg.length-1]);
        WeatherForecast wforeNew = new WeatherForecast(seg[seg.length-1]);
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
    
    public void switchDay(MouseEvent e){
        
        if(xValue < e.getX()){
            dragLeft();
        }
        else if(xValue > e.getX()){
            dragRight();
        }
    }
    
    public void dragLeft(){
        System.out.println("going left");
        location.setTranslateX(320-location.getLayoutX());
        currDate.setTranslateX(320-currDate.getLayoutX());
        tempField.setTranslateX(320-tempField.getLayoutX());
        feelsLike.setTranslateX(320-feelsLike.getLayoutX());
        curCondition.setTranslateX(320-curCondition.getLayoutX());
        
    }
    
    public void dragRight(){
        System.out.println("going right");
        location.setTranslateX(location.getLayoutX()-400);
        currDate.setTranslateX(currDate.getLayoutX()-400);
        tempField.setTranslateX(tempField.getLayoutX()-400);
        feelsLike.setTranslateX(feelsLike.getLayoutX()-400);
        curCondition.setTranslateX(curCondition.getLayoutX()-400);
       
    }
    
}
