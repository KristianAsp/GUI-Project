import urllib2
import json
	
filename = "comingDaysWeatherInfo.txt"
target = open(filename, 'w')

infoStream = urllib2.urlopen('http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/England/London.json')
json_string = infoStream.read()
parsed_json = json.loads(json_string)

#Getting the weather for the next 4 days and writing it to the 'comingDaysWeatherInfo.text' file.
for x in range(0,4):
	day = parsed_json['forecast']['simpleforecast']['forecastday'][x]['date']['weekday']
	htemp = parsed_json['forecast']['simpleforecast']['forecastday'][x]['high']['celsius']
	ltemp = parsed_json['forecast']['simpleforecast']['forecastday'][x]['low']['celsius']
	cond = parsed_json['forecast']['simpleforecast']['forecastday'][x]['conditions']
	hum = parsed_json['forecast']['simpleforecast']['forecastday'][x]['avehumidity']
	target.write(day)s
	target.write("\n")
	target.write(htemp)
	target.write("\n")
	target.write(ltemp)
	target.write("\n")
	target.write(cond)
	target.write("\n")
	target.write(str(hum))
	target.write("\n")

target.close();

#Getting location and current weather conditions
location = parsed_json['location']['city']
c_temp_c = parsed_json['current_observation']['temp_c']
c_humidity = parsed_json['current_observation']['relative_humidity']
c_visibility = parsed_json['current_observation']['visibility_km']
c_feelsLike = parsed_json['current_observation']['feelslike_c']
icon_url = parsed_json['current_observation']['icon_url']

filename = "currentWeatherInfo.txt"
target = open(filename, 'w')
target.write(str(location))
target.write("\n")
target.write(str(c_temp_c))
target.write("\n")
target.write(str(c_humidity))
target.write("\n")
target.write(str(c_visibility))
target.write("\n")
target.write(str(c_feelsLike))
target.write("\n")
target.write(str(icon_url))
target.close()
infoStream.close()