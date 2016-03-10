import urllib2
import json
from sys import argv

script, city = argv
if city =="London":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/England/London.json'
	url2 = 'http://api.wunderground.com/api/57d979bd71526100/astronomy/q/England/London.json'
	url3 = 'http://api.wunderground.com/api/57d979bd71526100/conditions/q/England/London.json'
elif city =="Rome":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/Italy/Rome.json'
	url2 = 'http://api.wunderground.com/api/57d979bd71526100/astronomy/q/Italy/Rome.json'
	url3 = 'http://api.wunderground.com/api/57d979bd71526100/conditions/q/Italy/Rome.json'
elif city == "Madrid":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/Spain/Madrid.json'
	url2 = 'http://api.wunderground.com/api/57d979bd71526100/astronomy/q/Spain/Madrid.json'
	url3 = 'http://api.wunderground.com/api/57d979bd71526100/conditions/q/Spain/Madrid.json'
elif city =="Paris":
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/France/Paris.json'
	url2 = 'http://api.wunderground.com/api/57d979bd71526100/astronomy/q/France/Paris.json'
	url3 = 'http://api.wunderground.com/api/57d979bd71526100/conditions/q/France/Paris.json'
else:
	url = 'http://api.wunderground.com/api/57d979bd71526100/hourly10day/q/Germany/Berlin.json'
	url2 = 'http://api.wunderground.com/api/57d979bd71526100/astronomy/q/Germany/Berlin.json'
	url3 = 'http://api.wunderground.com/api/57d979bd71526100/conditions/q/Germany/Berlin.json'


infoStream = urllib2.urlopen(url)
json_string = infoStream.read()
parsed_json = json.loads(json_string)

infoStream2 = urllib2.urlopen(url2)
json_string2 = infoStream2.read()
parsed_json2 = json.loads(json_string2)

infoStream3 = urllib2.urlopen(url3)
json_string3 = infoStream3.read()
parsed_json3 = json.loads(json_string3)

def write_weather( filename, start, end ):
	target = open(filename, 'w')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['weekday_name_abbrev'] + ',')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['month_name_abbrev'] + ',')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['mday'] + ",")
	target.write(parsed_json2['sun_phase']['sunrise']['hour'] + ',')
	target.write(parsed_json2['sun_phase']['sunrise']['minute'] + ',')
	target.write(parsed_json2['sun_phase']['sunset']['hour'] + ',')
	target.write(parsed_json2['sun_phase']['sunset']['minute'] + ",")
	target.write(parsed_json3['current_observation']['precip_today_metric'] + "\n")

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
write_weather(filename, 25, 49)

filename = "hourlyWeatherInfo2.txt"
write_weather(filename, 50, 74)

filename = "hourlyWeatherInfo3.txt"
write_weather(filename, 74, 99)

filename = "hourlyWeatherInfo4.txt"
write_weather(filename, 101, 125)

filename = "hourlyWeatherInfo5.txt"
write_weather(filename, 126, 150)

infoStream.close()