import urllib2
import json
from sys import argv

site = ['hourly10day', 'astronomy', 'conditions', 'forecast10day']

cityToCodeMap = {
	"London": "England/London",
	"Rome": "Italy/Rome",
	"Madrid": "Spain/Madrid",
	"Paris": "France/Paris",
	"Berlin": "Germany/Berlin"
}


def getUrls(city):
	url = []
	for x in site:
		ur = 'http://api.wunderground.com/api/57d979bd71526100/%s/q/%s.json' %(x ,city)
		url.append(ur) 
	return url;

def get_json_from_url(url):
	infoStream = urllib2.urlopen(url)
	json_string = infoStream.read()
	infoStream.close()
	return json.loads(json_string)

script, city = argv

code = cityToCodeMap[city]
urls = getUrls(code)

hourly10day = get_json_from_url(urls[0])
astronomy = get_json_from_url(urls[1])
conditions = get_json_from_url(urls[2])
forecast10day = get_json_from_url(urls[3])

def write_weather( filename, start, end, day ):
	target = open(filename, 'w')
	target.write(forecast10day['forecast']['simpleforecast']['forecastday'][day]['date']['weekday_short'] + ',')
	target.write(forecast10day['forecast']['simpleforecast']['forecastday'][day]['date']['monthname_short'] + ',')
	target.write(str(forecast10day['forecast']['simpleforecast']['forecastday'][day]['date']['day']) + ',')
	target.write(astronomy['sun_phase']['sunrise']['hour'] + ',')
	target.write(astronomy['sun_phase']['sunrise']['minute'] + ',')
	target.write(astronomy['sun_phase']['sunset']['hour'] + ',')
	target.write(astronomy['sun_phase']['sunset']['minute'] + ',')
	target.write(conditions['current_observation']['precip_today_metric'] + ',')
	target.write(forecast10day['forecast']['simpleforecast']['forecastday'][day]['high']['celsius'] + ",")
	target.write(forecast10day['forecast']['simpleforecast']['forecastday'][day]['low']['celsius'] + "\n")

	for x in range(start,end):
		target.write(hourly10day['hourly_forecast'][x]['condition'] + ',')
		target.write(hourly10day['hourly_forecast'][x]['icon_url'] + ',')
		target.write(hourly10day['hourly_forecast'][x]['humidity'] + ',')
		target.write(hourly10day['hourly_forecast'][x]['feelslike']['metric'] + ',')
		target.write(hourly10day['hourly_forecast'][x]['temp']['metric'] + "\n")

	target.close()


filename = "hourlyWeatherInfo0.txt"
write_weather(filename, 0, 24, 0)

filename = "hourlyWeatherInfo1.txt"
write_weather(filename, 25, 49, 1)

filename = "hourlyWeatherInfo2.txt"
write_weather(filename, 50, 74, 2)

filename = "hourlyWeatherInfo3.txt"
write_weather(filename, 74, 99, 3)

filename = "hourlyWeatherInfo4.txt"
write_weather(filename, 101, 125, 4)

filename = "hourlyWeatherInfo5.txt"
write_weather(filename, 126, 150, 5)

