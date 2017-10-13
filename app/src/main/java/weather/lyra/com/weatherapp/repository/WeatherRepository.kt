package weather.lyra.com.weatherapp.repository

import io.reactivex.Single
import koin.sampleapp.service.json.getLocation
import koin.sampleapp.service.json.weather.Weather
import weather.lyra.com.weatherapp.datasource.WeatherDatasource

/**
 * Created by tweissbeck on 06/10/2017.
 */
interface WeatherRepository {
    fun weather(address: String): Single<Weather>
    fun loadLast(): Single<Weather>
}

class WeatherRepositoryImpl(val weatehrDataSource: WeatherDatasource) : WeatherRepository {

    var cacheResult: Pair<String, Weather>? = null

    override fun weather(address: String): Single<Weather> {
        return if (cacheResult != null && cacheResult!!.first == address) {
            Single.just(cacheResult!!.second)
        } else weatehrDataSource.geocode(address)
                .map { it.getLocation() ?: error("No locations for $address") }
                .flatMap { location -> weatehrDataSource.weather(location.lat, location.lng, "en") }
                .doOnSuccess { cacheResult = Pair(address, it) }
    }

    override fun loadLast(): Single<Weather> {
        return Single.just(cacheResult!!.second)
    }

}