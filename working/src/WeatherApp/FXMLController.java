package weatherapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author cintia
 */
public class FXMLController extends Application implements Initializable{

    private static Stage theStage = new Stage();
    private Scene scene1;
   
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
   
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        scene1 = new Scene(root);
        
        theStage.setScene(scene1);
        theStage.show();
    }
    
}
