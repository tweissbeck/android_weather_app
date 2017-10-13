package weather.lyra.com.weatherapp.weather

import koin.sampleapp.service.json.getDailyForecasts
import rx.SchedulerProvider
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * @author tweissbeck
 */
class WeatherListPresenter(val schedulerProvider: SchedulerProvider, val weatherRepository: WeatherRepository) :
        WeatherContract.Presenter {
    override fun start() {

    }

    override lateinit var view: WeatherContract.View

    override fun loadWeather() {

        weatherRepository.loadLast().map { it.getDailyForecasts() }.subscribeOn(schedulerProvider.io()).observeOn(
                schedulerProvider.ui()).subscribe(
                { success -> view.displayWeather(success) }, { error -> view.onError(error) })
    }

}