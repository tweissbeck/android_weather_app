package weather.lyra.com.weatherapp.repository

import koin.sampleapp.service.json.getLocation
import koin.sampleapp.service.json.weather.Weather
import weather.lyra.com.weatherapp.datasource.WeatherDatasource

/**
 * Created by tweissbeck on 06/10/2017.
 */
interface WeatherRepository {
    fun weather(address: String): Weather
}

class WeatherRepositoryImpl(val weatehrDataSource: WeatherDatasource) : WeatherRepository {

    var cacheResult: Pair<String, Weather>? = null

    fun getLoc(address: String): Weather {
        val location = weatehrDataSource.geocode(address).execute().body().getLocation()
        if (location != null) {
            val weather = weatehrDataSource.weather(location.lat, location.lng, "EN").execute().body()
            return weather
        } else error("no location for $address")
    }

    override fun weather(address: String): Weather {
        return if (cacheResult != null && cacheResult!!.first == address) {
             cacheResult?.second!!
        } else {
            val res = getLoc(address)
            cacheResult = Pair(address, res)
             res
        }

    }

}