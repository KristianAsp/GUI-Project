import urllib2
import json
	
filename = "comingDaysWeatherInfo.txt"
target = open(filename, 'w')

infoStream = urllib2.urlopen('http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/England/London.json')
json_string = infoStream.read()
parsed_json = json.loads(json_string)

#Getting location and current weather conditions
location = parsed_json['location']['city']
c_temp_c = parsed_json['current_observation']['temp_c']
c_humidity = parsed_json['current_observation']['relative_humidity']
c_visibility = parsed_json['current_observation']['visibility_km']
c_feelsLike = parsed_json['current_observation']['feelslike_c']

#Getting the weather for the next 4 days and writing it to the 'comingDaysWeatherInfo.text' file.
for x in range(0,6,2):
	day = parsed_json['forecast']['txt_forecast']['forecastday'][x]['title']
	weather = parsed_json['forecast']['txt_forecast']['forecastday'][x]['fcttext']
	target.write(day)
	target.write("\n")
	target.write(weather)
	target.write("\n")

target.close();

filename = "currentWeatherInfo.txt"
target = open(filename, 'w')
target.write(str(location))
target.write("\n")
target.write(str(c_temp_c))
target.write("\n")
target.write(c_humidity)
target.write("\n")
target.write(c_visibility)
target.write("\n")
target.write(c_feelsLike )
target.close()
infoStream.close()