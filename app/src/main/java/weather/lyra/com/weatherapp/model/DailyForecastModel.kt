package weather.lyra.com.weatherapp.model

import java.util.*

data class DailyForecastModel(val forecastString: String, val icon: String, val temperatureLow: String, val temperatureHigh: String) {

    val temperatureString = "$temperatureLow°C - $temperatureHigh°C"
    val id = UUID.randomUUID().toString()

}
