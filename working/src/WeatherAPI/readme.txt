WeatherForecast integration.

To get the weather for a specific city create a new WeatherForecast object by passing
a string with the city name to the constructor. The information will get stored in a 
two-dimensional array of WeatherObjects. Where each array in the array holds hourly 
information for each day. The first element in each array is a Day object, which 
contains 3 strings; 
 - name(name of day)
 - monthName 
 - dayNumber
 - sunrise
 - sunset
 - precipiration

The other 24 positions in the array contains HourWeather objects, which contains 5 strings:
 - condition
 - iconUrl
 - humidity
 - feelsLike
 - temp

