import java.net.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;

public class WeatherAPI
{
	static String theWeatherRSS;
	static String theCity;
	static ArrayList<Forecast> weatherForecastList;
	
	public class Forecast
	{
		String lowTemp;
		String highTemp;
	}

	public WeatherAPI(String city)
	{
		theCity = city;
		theWeatherRSS = getWeatherAsRSS(city);
		parseWeather(theWeatherRSS);
	}

	void parseWeather(String weatherHTML)
	{
		weatherForecastList = new ArrayList<Forecast>();
		int startIndex = 0;
		while(startIndex != -1)
		{
			startIndex = weatherHTML.indexOf("<yweather:forecast", startIndex);
			if(startIndex != -1)
			{ // found a weather forecast
				int endIndex = weatherHTML.indexOf(">", startIndex);
				String weatherForecast = weatherHTML.substring(startIndex, endIndex+1);

				// get temp forecast				
				String lowString = getValueForKey(weatherForecast, "low");
				String highString = getValueForKey(weatherForecast, "high");
				
				Forecast fore = new Forecast();
				fore.lowTemp = lowString;
				fore.highTemp = highString;
				weatherForecastList.add(fore);
				
				// move to end of this forecast
				startIndex = endIndex;
			}
		}
	}

	String getValueForKey(String theString, String keyString)
	{
		int startIndex = theString.indexOf(keyString);
		startIndex = theString.indexOf("\"", startIndex);
		int endIndex = theString.indexOf("\"", startIndex+1);
		String resultString = theString.substring(startIndex+1, endIndex);
		return resultString;
	}

	String getWeatherAsRSS(String city)
	{
		try{
			/*
			Adapted from: http://stackoverflow.com/questions/1381617/simplest-way-to-correctly-load-html-from-web-page-into-a-string-in-java
			Answer provided by: erickson
			*/
			URL url = new URL("http://weather.yahooapis.com/forecastrss?w="+city+"&u=c");
			URLConnection con = url.openConnection();
			Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
			Matcher m = p.matcher(con.getContentType());
			/* If Content-Type doesn't match this pre-conception, choose default and 
			 * hope for the best. */
			String charset = m.matches() ? m.group(1) : "ISO-8859-1";
			Reader r = new InputStreamReader(con.getInputStream(), charset);
			StringBuilder buf = new StringBuilder();
			while (true) {
			  int ch = r.read();
			  if (ch < 0)
				break;
			  buf.append((char) ch);
			}
			String str = buf.toString();
			return(str);
		}
		catch(Exception e) {System.err.println("Weather API Exception: "+e);}
		return null;
	}

}
