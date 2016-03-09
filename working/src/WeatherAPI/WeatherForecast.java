package WeatherAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Petter
 */
public class WeatherForecast {
    private String[] current = new String[11];
    private String [] forecast = new String[50];
    private final String currentFile = "currentWeatherInfo.txt";
    private final String forecastFile = "comingDaysWeatherInfo.txt";
    
    public static void main(String[] args){
        WeatherForecast wf = new WeatherForecast("London");
        System.out.println(wf.getCurrent()[10]);
        System.out.println(wf.getForecast1()[1]);
    }
    
    public WeatherForecast(String city){
        try{
            String exCmd = "python WeatherAPI/weather.py "+city;
            Process p = Runtime.getRuntime().exec(exCmd);
 
            while(p.isAlive()){
                //Wait til process is done
            }
        } 
        catch (IOException e){
            System.exit(0);
        }
           
        try{
            String line;
            int count = 0;
            BufferedReader rd = new BufferedReader(new FileReader(currentFile));
             while((line = rd.readLine()) !=null) {
                current[count] = line;
                count++;
            }
            rd.close();
            
            
            count = 0;
            rd = new BufferedReader(new FileReader(forecastFile));
            while((line = rd.readLine()) !=null) {
                forecast[count] = line;
                count++;
            }
        }
        catch(IOException e)
        {
            System.out.println("Not able to obtain the information from the files");
            System.exit(0);
        }
    }
    
    public String[] getCurrent() {
        return current;
    }
    
    public String[] getForecast1() {
        return forecast;
    } 
}
