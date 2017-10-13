package weather.lyra.com.weatherapp.main

import io.reactivex.disposables.Disposable
import weather.lyra.com.weatherapp.rx.SchedulerProvider
import weather.lyra.com.weatherapp.datastore.DataStore
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * Created by tweissbeck on 06/10/2017.
 */
class MainPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider,
                    val dataStore: DataStore) : MainContract.Presenter {
    var request: Disposable? = null

    override fun stop() {
        request?.dispose()
    }

    companion object {
        val lastSeachKey: String = "PREF_SEARCH_KEY"
    }

    override lateinit var view: MainContract.View

    override fun start() {
        val lastSearch = dataStore.get<String>(lastSeachKey)
        view.initLastSearch(lastSearch)
    }

    override fun searchWeather(address: String) {
        dataStore.save(lastSeachKey, address)
        request = weatherRepository.weather(address)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { result -> view.goToDisplayWeatherResult() },
                        { error -> view.displayError(error) })
    }


}