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

    private static Stage theStage = new Stage();
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


    public double rotate = 0;
    public double xValue;
    public String currentHour = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());


    //public List<String> hours = new {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"}

    public void setxValue(){
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xValue = event.getX();
            }
        });
    }

    public void dragCircle(){
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getX() > xValue){
                    //xValue = event.getX();
                    dragCircleRight();
                    wheelTime();

                }
                else if(event.getX() < xValue){
                    //xValue = event.getX();
                    dragCircleLeft();
                    wheelTime();
                }
            }
        });
    }

    public void dragCircleRight(){
        System.out.println("right");
        circle.setRotate(rotate);
        rotate += 1;
        if(rotate == 360 || rotate == -360){
            rotate = 0;
        }
    }

    public void dragCircleLeft(){
        System.out.println("left");
        circle.setRotate(rotate);
        rotate -= 1;
        if(rotate == 360 || rotate == -360){
            rotate = 0;
        }
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
   
    
    public void switchScreen(KeyEvent event) throws IOException{
        if(event.getCode() == KeyCode.ENTER){
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
