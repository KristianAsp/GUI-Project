package WeatherAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Petter
 */
public class WeatherForecast {
    private WeatherObject[][] hourly = new WeatherObject[5][25]; 
    private final String [] files = {"hourlyWeatherInfo0.txt", "hourlyWeatherInfo1.txt", 
                                     "hourlyWeatherInfo2.txt", "hourlyWeatherInfo3.txt", 
                                     "hourlyWeatherInfo3.txt", "hourlyWeatherInfo5.txt"};

    public static void main(String[] args){
        WeatherForecast wf = new WeatherForecast("London");
        Day d = (Day) wf.getHourlyWeather()[0][0];
        System.out.println(d.getDayNumber());
        System.out.println(d.getMonthName());
        System.out.println(d.getName());
        HourWeather hw = (HourWeather) wf.getHourlyWeather()[0][1];
        System.out.println(hw.getCondition());
        System.out.println(hw.getFeelsLike());
        System.out.println(hw.getHumidity());
        System.out.println(hw.getIconUrl());
        
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
        for(int i=0; i<5;i++)
        {
            hourly[i] = readInfo(files[i]);
        }     
    }
    
    private WeatherObject[] readInfo(String filename){
        WeatherObject [] data = new WeatherObject[25];
        try{         
            String line;
            int count = 0;
            
            BufferedReader rd = new BufferedReader(new FileReader(filename));
            
            while((line = rd.readLine()) !=null) {
                String[] temp = line.split(",");
                
                if(count==0){
                    data[count] = new Day(temp[0], temp[1], temp[2]);
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
