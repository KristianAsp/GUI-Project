package WeatherApp;

/**
 * Created by Kristian on 11/03/16.
 */
public class booleanStorage {
    private boolean fxmlActive = true;
    private static booleanStorage ourInstance = new booleanStorage();

    public static booleanStorage getInstance() {
        return ourInstance;
    }

    private booleanStorage() {
    }

    public void setfxmlActive(boolean a){
        fxmlActive = a;
    }

    public boolean getfxmlActive(){
        return fxmlActive;
    }

}
