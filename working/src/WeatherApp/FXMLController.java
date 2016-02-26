package WeatherApp;

import WeatherAPI.WeatherCondition;
import WeatherAPI.WeatherForecast;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 *
 * @author cintia
 */
public class FXMLController extends Application implements Initializable{

    //private static Stage theStage = new Stage();
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





    public void initialize(URL url, ResourceBundle rb) {
        weather = new WeatherForecast("44418");
        condition = new WeatherCondition("44418");

        tempField.setText("" + condition.weatherConditionList.get(0).currentTemp + "°C");
        curCondition.setText(condition.weatherConditionList.get(0).textDesc);
        if(curCondition.getText().equals("Unknown")){
            curCondition.setText("Partly Cloudy");
        }
        currDate.setText(weather.weatherForecastList.get(0).date);
        feelsLike.setText("Feels like: " + condition.weatherConditionList.get(0).feelsLike  + "°C");
        menu.setVisible(false); 
    }
    
    public void callMenu(){
        menu.setVisible(true);
        button.setVisible(false);
    }

    public void closeMenu(){
        menu.setVisible(false);
        button.setVisible(true);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
   
    public void start(Stage primaryStage) throws Exception{
        //theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        scene1 = new Scene(root);

        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
}
