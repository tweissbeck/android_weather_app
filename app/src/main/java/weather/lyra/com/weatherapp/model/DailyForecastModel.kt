package weather.lyra.com.weatherapp.model

data class DailyForecastModel(val forecastString: String, val icon: String, val temperatureLow: String, val temperatureHigh: String) {

    val temperatureString = "$temperatureLow°C - $temperatureHigh°C"

}
