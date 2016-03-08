package WeatherAPI;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author Petter
 */
public class WeatherForecast {
    private String city;
    private String temp;
    private String feelsLike;
    private String visibility;
    private String humidity;
    private String iconURL;
    private String [] forecast = new String[20];
    private final String currentFile = "currentWeatherInfo.txt";
    private final String forecastFile = "comingDaysWeatherInfo.txt";
    
    public static void main(String[] args){
        WeatherForecast wf = new WeatherForecast("dd");
        System.out.println(wf.getCity());
        System.out.println(wf.getTemp());
        System.out.println(wf.getFeelsLike());
        System.out.println(wf.getVisibility());
        System.out.println(wf.getHumidity());
        System.out.println(wf.getForecast()[0]);
    }
    
    public WeatherForecast(String city){
        try{
            Process p = Runtime.getRuntime().exec("python weather.py");
            while(p.isAlive()){
                //Wait til process is done
            }
        } 
        catch (IOException e){
            System.exit(0);
        }
           
        try{
            BufferedReader rd = new BufferedReader(new FileReader(currentFile));
            city = rd.readLine();
            temp = rd.readLine();
            humidity = rd.readLine();
            visibility = rd.readLine();
            feelsLike = rd.readLine();
            iconURL = rd.readLine();
            rd.close();
            
            String line;
            int count = 0;
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
    
    public String getCity() {
        return city;
    }
    
    public String getTemp() {
        return temp;
    }
    
    public String getFeelsLike() {
        return feelsLike;
    }
    
    public String getVisibility() {
        return visibility;
    }
    
    public String getHumidity() {
        return humidity;
    }
    
    public String[] getForecast() {
        return forecast;
    }
    //May need to get current date
    public String getCurrentDate(){
        return "";
    }
}
