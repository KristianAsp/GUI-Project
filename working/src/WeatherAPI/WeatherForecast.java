package WeatherAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Petter
 */
public class WeatherForecast {
    private WeatherObject[][] hourly = new WeatherObject[6][25]; 
    private final String [] files = {"hourlyWeatherInfo0.txt", "hourlyWeatherInfo1.txt", 
                                     "hourlyWeatherInfo2.txt", "hourlyWeatherInfo3.txt", 
                                     "hourlyWeatherInfo3.txt", "hourlyWeatherInfo5.txt"};

    public static void main(String[] args){
        WeatherForecast wf = new WeatherForecast("London");
        Day d = (Day) wf.getHourlyWeather()[3][0];
        System.out.println(d.getDayNumber());
        System.out.println(d.getMonthName());
        System.out.println(d.getName());
        System.out.println(d.getSunrise());
        System.out.println(d.getSunset());
        System.out.println(d.getPrecip());
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
        for(int i=0; i<6;i++)
        {
            hourly[i] = readInfo(files[i]);
        }     
    }
    
    private WeatherObject[] readInfo(String filename){
        WeatherObject [] data = new WeatherObject[30];
        try{         
            String line;
            int count = 0;
            
            BufferedReader rd = new BufferedReader(new FileReader(filename));
            
            while((line = rd.readLine()) !=null) {
                String[] temp = line.split(",");
                
                if(count==0){
                    String sunrise = temp[3]+":"+temp[4];
                    String sunset = temp[5]+":"+temp[5];
                    data[count] = new Day(temp[0], temp[1], temp[2], sunrise,sunset, temp[7]);
                }
                else{
                    data[count]= new HourWeather(temp[0], temp[1], temp[2], temp[3], temp[4]);
                }
                count++;
            }
            rd.close();
        }
        catch(IOException e)
        {
            System.out.println("Not able to obtain the information from the files");
            System.exit(0);
        }
        return data;
    }
    
    public WeatherObject[][] getHourlyWeather(){
        return hourly;
    }

}
