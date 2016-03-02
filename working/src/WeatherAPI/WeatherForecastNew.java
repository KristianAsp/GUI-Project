/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WeatherAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Petter
 */
public class WeatherForecastNew {
    private String city;
    private String temp;
    private String feelsLike;
    private String visibility;
    private String humidity;
    private String [] forecast = new String[8];
    private final String currentFile = "currentWeatherInfo.txt";
    private final String forecastFile = "comingDaysWeatherInfo.txt";
    
    public static void main(String[] args)
    {
        try{
            WeatherForecastNew wf = new WeatherForecastNew("dd");
            System.out.println(wf.getCity());
            System.out.println(wf.getTemp());
            System.out.println(wf.getFeelsLike());
            System.out.println(wf.getVisibility());
            System.out.println(wf.getHumidity());
            System.out.println(wf.getForecast()[0]);
        }
        catch(Exception e)
        {
            System.exit(0);
        }
    }
    public WeatherForecastNew(String city) throws IOException
    {
        Process p = Runtime.getRuntime().exec("python weather.py");
        while(!p.isAlive())
        {
            //Wait til process is done
        }
            
        try{
            BufferedReader rd = new BufferedReader(new FileReader(currentFile));
            city = rd.readLine();
            temp = rd.readLine();
            humidity = rd.readLine();
            visibility = rd.readLine();
            feelsLike = rd.readLine();
            rd.close();
            
            String line;
            int count = 0;
            rd = new BufferedReader(new FileReader(forecastFile));

            while((line = rd.readLine()) !=null)
            {
                forecast[count] = line;
                count++;
            }
        }
        catch(Exception e)
        {
            System.out.println("Not able to obtain the information from the files");
            System.exit(0);
        }
    }
    
    public String getCity()
    {
        return city;
    }
    
    public String getTemp()
    {
        return temp;
    }
    
    public String getFeelsLike()
    {
        return feelsLike;
    }
    
    public String getVisibility()
    {
        return visibility;
    }
    
    public String getHumidity()
    {
        return humidity;
    }
    
    public String[] getForecast()
    {
        return forecast;
    }
}
