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
8           Feels like              Condition
9           Icon Url                -- Empty
10          Condition               Weekday
11                                  Day #
12                                  Month
13                                  High Temp
14                                  Low Temp
15                                  Condition
16                                  Humidity
17                                  Condition
18                                  -- Empy
19                                  Weekday
20                                  Day #
21                                  Month
22                                  High Temp
23                                  Low Temp
24                                  Condition
25                                  Humidity
26                                  Condition