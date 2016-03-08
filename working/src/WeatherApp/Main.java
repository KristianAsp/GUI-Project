/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WeatherApp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sean
 */
public class Main extends Application{
    
    public static Stage theStage;
    public static Scene scene1;
    
    public static void main(String[] args) {
        launch(args);
    }
   
    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        System.out.println("Setting the stage");
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        System.out.println("loaded fxml");
        scene1 = new Scene(root);
        System.out.println("loading fxml and setting scene");
        primaryStage.setScene(scene1);
        primaryStage.show();
        System.out.println("Showing gui");
    }
}
