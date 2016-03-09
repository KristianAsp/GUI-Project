import urllib2
import json
from sys import argv

script, city = argv
if city =="London":
	url = 'http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/England/London.json'
elif city =="Rome":
	url = 'http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/Italy/Rome.json'
elif city == "Madrid":
	url = 'http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/Spain/Madrid.json'
elif city =="Paris":
	url = 'http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/France/Paris.json'
else:
	url = 'http://api.wunderground.com/api/57d979bd71526100/geolookup/conditions/forecast/q/Germany/Berlin.json'

	
filename = "comingDaysWeatherInfo.txt"
target = open(filename, 'w')

infoStream = urllib2.urlopen(url)
json_string = infoStream.read()
parsed_json = json.loads(json_string)

#Getting the weather for the next 4 days and writing it to the 'comingDaysWeatherInfo.text' file.
for x in range(1,4):
	wday = parsed_json['forecast']['simpleforecast']['forecastday'][x]['date']['weekday_short']
	day = parsed_json['forecast']['simpleforecast']['forecastday'][x]['date']['day']
	month = parsed_json['forecast']['simpleforecast']['forecastday'][x]['date']['monthname_short']
	htemp = parsed_json['forecast']['simpleforecast']['forecastday'][x]['high']['celsius']
	ltemp = parsed_json['forecast']['simpleforecast']['forecastday'][x]['low']['celsius']
	cond = parsed_json['forecast']['simpleforecast']['forecastday'][x]['conditions']
	hum = parsed_json['forecast']['simpleforecast']['forecastday'][x]['avehumidity']
	target.write("\n")
	target.write(wday)
	target.write("\n")
	target.write(str(day))
	target.write("\n")
	target.write(month)
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
c_wday = parsed_json['forecast']['simpleforecast']['forecastday'][0]['date']['weekday_short']
c_day = parsed_json['forecast']['simpleforecast']['forecastday'][0]['date']['day']
c_month = parsed_json['forecast']['simpleforecast']['forecastday'][0]['date']['monthname_short']
location = parsed_json['location']['city']
c_temp_c = parsed_json['current_observation']['temp_c']
c_humidity = parsed_json['current_observation']['relative_humidity']
c_visibility = parsed_json['current_observation']['visibility_km']
c_feelsLike = parsed_json['current_observation']['feelslike_c']
icon_url = parsed_json['current_observation']['icon_url']

filename = "currentWeatherInfo.txt"
target = open(filename, 'w')
target.write("\n")
target.write(c_wday)
target.write("\n")
target.write(str(c_day))
target.write("\n")
target.write(c_month)
target.write("\n")
target.write(location)
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