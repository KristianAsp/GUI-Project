import urllib2
import json
from sys import argv

script, city = argv
if city =="London":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/England/London.json'
elif city =="Rome":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/Italy/Rome.json'
elif city == "Madrid":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/Spain/Madrid.json'
elif city =="Paris":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/France/Paris.json'
else:
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/Germany/Berlin.json'


infoStream = urllib2.urlopen(url)
json_string = infoStream.read()
parsed_json = json.loads(json_string)

def write_weather( filename, start, end ):
	target = open(filename, 'w')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['weekday_name_abbrev'] + ',')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['month_name_abbrev'] + ',')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['mday'] + "\n")

	for x in range(start,end):
		target.write(parsed_json['hourly_forecast'][x]['condition'] + ',')
		target.write(parsed_json['hourly_forecast'][x]['icon_url'] + ',')
		target.write(parsed_json['hourly_forecast'][x]['humidity'] + ',')
		target.write(parsed_json['hourly_forecast'][x]['feelslike']['metric'] + ',')
		target.write(parsed_json['hourly_forecast'][x]['temp']['metric'] + "\n")

	target.close()

filename = "hourlyWeatherInfo0.txt"
write_weather(filename, 0, 24)

filename = "hourlyWeatherInfo1.txt"
write_weather(filename, 24, 48)

filename = "hourlyWeatherInfo2.txt"
write_weather(filename, 48, 72)

filename = "hourlyWeatherInfo3.txt"
write_weather(filename, 72, 96)

filename = "hourlyWeatherInfo4.txt"
write_weather(filename, 96, 120)

filename = "hourlyWeatherInfo5.txt"
write_weather(filename, 120, 144)

infoStream.close()