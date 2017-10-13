package weather.lyra.com.weatherapp.repository

import io.reactivex.Single
import koin.sampleapp.service.json.getDailyForecasts
import koin.sampleapp.service.json.getLocation
import weather.lyra.com.weatherapp.datasource.WeatherDatasource
import weather.lyra.com.weatherapp.model.DailyForecastModel

/**
 * Created by tweissbeck on 06/10/2017.
 */
interface WeatherRepository {
    fun weather(address: String): Single<List<DailyForecastModel>>
    // Overkill Rx
    fun loadLast(): Single<List<DailyForecastModel>>
    fun loadDetail(id: String): Single<DailyForecastModel>
}

class WeatherRepositoryImpl(val weatehrDataSource: WeatherDatasource) : WeatherRepository {
    override fun loadDetail(id: String) =
            loadLast().map { it.first { it.id == id } }


    var cacheResult: Pair<String, List<DailyForecastModel>>? = null

    override fun weather(address: String): Single<List<DailyForecastModel>> {
        return if (cacheResult != null && cacheResult!!.first == address) {
            Single.just(cacheResult!!.second)
        } else weatehrDataSource.geocode(address)
                .map { it.getLocation() ?: error("No locations for $address") }
                .flatMap { location -> weatehrDataSource.weather(location.lat, location.lng, "en") }
                .map { it.getDailyForecasts() }
                .doOnSuccess { cacheResult = Pair(address, it) }
    }

    override fun loadLast(): Single<List<DailyForecastModel>> {
        return Single.just(cacheResult!!.second)
    }

}