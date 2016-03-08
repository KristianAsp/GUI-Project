package WeatherApp;


import WeatherAPI.WeatherForecast;
import static WeatherApp.Main.scene1;
import static WeatherApp.Main.theStage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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


/**
 *
 * @author cintia
 */
public class FXMLController implements Initializable{

    WeatherForecast weather;
    
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
    @FXML Pane labelPane;


    public double rotate = 0;
    public double translate = 0;
    Thread th;

    public double xValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
	
	
    
    public void setxValue(MouseEvent event){
                System.out.println(event.getX());
                xValue = event.getX();
    }

    public void handleKeys(KeyEvent event) throws InterruptedException{
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
        else if(event.getCode() == KeyCode.K){
            dragLeft();
        }
        else if(event.getCode() == KeyCode.L){
            dragRight();
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
        
        //weather = new WeatherForecast("44418");
        //condition = new WeatherCondition("44418");
        menuToggleOpen = false;
        //tempField.setText("" + condition.weatherConditionList.get(0).currentTemp + "°C");
        //currDate.setText(weather.getCurrentDate());
        //feelsLike.setText("Feels like: " + condition.weatherConditionList.get(0).feelsLike  + "°C");
        //curCondition.setText("Stargazing Conditions: Good");
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
            button.setVisible(false);
        }
    }
    //Handle menu button click
    public void handleMenuButtonClick(ActionEvent e) throws IOException{
        String [] seg = e.getSource().toString().split(Pattern.quote("'"));
        System.out.println(seg[seg.length-1]);
        WeatherForecast weatherfore = new WeatherForecast(seg[seg.length-1]);
        //call updateGUI method -- args weatherfore
         
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
    
    public void dragLeft()  throws InterruptedException{
        System.out.println("going left");
                th = new Thread(){
                @Override
                public void run(){
                    double originalPos = labelPane.getLayoutX();
                    labelPane.getTranslateX();
                    boolean atPosition = false;
                    double translate = 10.9;
                    //double x = labelPane.getLayoutX() + labelPane.getTranslateX();

                    while(!atPosition){
                        labelPane.setTranslateX(translate);
                        translate += 10.9;
                        
                        System.out.println("Original position: " + originalPos);
                        double x = labelPane.getLayoutX() + labelPane.getTranslateX();
                        System.out.println("Position of label pane: " + x);


                        if(x == originalPos)
                            atPosition = true;
                        if(x > 320){
                            labelPane.setLayoutX(0 - labelPane.getWidth());
                            System.out.println("Greater than 320");
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            };
        th.start();        
    }
    
    public void dragRight(){
        System.out.println("going right");
        location.setTranslateX(-location.getLayoutX()*2);
        currDate.setTranslateX(-currDate.getLayoutX()*2);
        tempField.setTranslateX(-tempField.getLayoutX()*2);
        feelsLike.setTranslateX(-feelsLike.getLayoutX()*2);
        curCondition.setTranslateX(-curCondition.getLayoutX()*2);
       
    }
    
}
