WeatherForecas integration.

To get the weather for a specific city create a new WeatherForecast object by passing
a string with the city name to the constructor. The information is then stored in two
string arrays; current (getCurrent()) and forecast (getForecast()).The forecast holds
the information for the next 3 days. The information is stored within the array like this:

Position:   Current>Content         Forecast>Content
0           -- Empty                -- Empty  
1           Weekday                 Weekday
2           Day #                   Day #
3           Month                   Month
4           City                    High Temp
5           Temp                    Low Temp
6           Humidity                Condition (eg. 'Partly cloudy' 'Sunny') 
7           Visibility              Humidity
8           Feels like              -- Empty
9           Icon Url                Weekday
10                                  Day #
11                                  Month
12                                  High Temp
13                                  Low Temp
14                                  Condition
15                                  Humidity
16                                  -- Empy
17                                  Weekday
18                                  Day #
19                                  Month
20                                  High Temp
21                                  Low Temp
22                                  Condition
23                                  Humidity    