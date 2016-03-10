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
public class Day extends WeatherObject{
    private String name;
    private String monthName;
    private String dayNumber;
    private String sunrise;
    private String sunset;
    private String precip;
    private String highTemp;
    private String lowTemp;
    
    public Day(String n, String mn, String dn, String sr, String sn, String pre, String ht, String lt){
        name = n;
        monthName = mn;
        dayNumber= dn;
        sunrise = sr;
        sunset = sn;
        precip = pre;
        highTemp = ht;
        lowTemp = lt;
    }           


    public String getName(){
        return name;
    }
    
    public String getMonthName(){
        return monthName;
    }
    
    public String getDayNumber(){
        return dayNumber;
    }
    
    public String getSunrise(){
        return sunrise;
    }
    
    public String getSunset(){
        return sunset;
    }
    
    public String getPrecip(){
        return precip;
    }
    
    public String getHighTemo(){
        return highTemp;
    }
    
    public String getLowTemp(){
        return lowTemp;
    }

}