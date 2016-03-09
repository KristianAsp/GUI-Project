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
    
    public Day(String n, String mn, String dn){
        name = n;
        monthName = mn;
        dayNumber= dn;
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

}