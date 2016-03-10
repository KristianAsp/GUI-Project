import urllib2
import json
from sys import argv

site = ['hourly10day', 'astronomy', 'conditions', 'forecast10day']

def getUrls(city, site):
	url = []
	for x in site:
		ur = 'http://api.wunderground.com/api/57d979bd71526100/%s/q/%s.json' %(x ,city)
		url.append(ur) 
	return url;

script, city = argv
if city =="London":
	urls = getUrls('England/London' , site)
elif city =="Rome":
	urls = getUrls('Italy/Rome' , site)
elif city == "Madrid":
	urls = getUrls('Spain/Madrid' , site)
elif city =="Paris":
	urls = getUrls('France/Paris' , site)
else:
	urls = getUrls('Germany/Berlin' , site)

infoStream = urllib2.urlopen(urls[0])
json_string = infoStream.read()
parsed_json = json.loads(json_string)

infoStream2 = urllib2.urlopen(urls[1])
json_string2 = infoStream2.read()
parsed_json2 = json.loads(json_string2)

infoStream3 = urllib2.urlopen(urls[2])
json_string3 = infoStream3.read()
parsed_json3 = json.loads(json_string3)

infoStream4 = urllib2.urlopen(urls[3])
json_string4 = infoStream4.read()
parsed_json4 = json.loads(json_string4)


def write_weather( filename, start, end ):
	target = open(filename, 'w')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['weekday_name_abbrev'] + ',')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['month_name_abbrev'] + ',')
	target.write(parsed_json['hourly_forecast'][start]['FCTTIME']['mday'] + ",")
	target.write(parsed_json2['sun_phase']['sunrise']['hour'] + ',')
	target.write(parsed_json2['sun_phase']['sunrise']['minute'] + ',')
	target.write(parsed_json2['sun_phase']['sunset']['hour'] + ',')
	target.write(parsed_json2['sun_phase']['sunset']['minute'] + ',')
	target.write(parsed_json3['current_observation']['precip_today_metric'] + ',')
	target.write(parsed_json4['forecast']['simpleforecast']['forecastday'][0]['high']['celsius'] + ",")
	target.write(parsed_json4['forecast']['simpleforecast']['forecastday'][0]['low']['celsius'] + "\n")

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