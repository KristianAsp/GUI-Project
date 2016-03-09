/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WeatherAPI;

/**
 *
 * @author Petter
 */
public class HourWeather extends WeatherObject{
    private String condition;
    private String iconUrl;
    private String humidity;
    private String feelsLike;
    private String temp;
    
    public HourWeather(String c, String i, String h, String f, String t){
        condition = c;
        iconUrl = i;
        humidity = h;
        feelsLike = f;
        temp = t;
    }
    
    public String getCondition(){
        return condition;
    }
    
    public String getIconUrl(){
        return iconUrl;
    }
   
    public String getHumidity(){
        return humidity;
    }
    
    public String getFeelsLike(){
        return feelsLike;
    }
    
    public String getTemp(){
        return temp;
    }
}
