package weather.lyra.com.weatherapp.main

import rx.SchedulerProvider
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * Created by tweissbeck on 06/10/2017.
 */
class MainPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider) : MainContract.Presenter {

    override lateinit var view: MainContract.View

    override fun start() {

    }

    override fun searchWeather(address: String) {
        val subscribe = weatherRepository.weather(address)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { result -> view.goToDisplayWeatherResult() },
                        { error -> view.displayError(error) })
    }
}