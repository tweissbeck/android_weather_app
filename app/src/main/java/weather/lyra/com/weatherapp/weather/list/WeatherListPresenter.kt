package weather.lyra.com.weatherapp.weather.list

import koin.sampleapp.service.json.getDailyForecasts
import rx.SchedulerProvider
import weather.lyra.com.weatherapp.model.DailyForecastModel
import weather.lyra.com.weatherapp.repository.WeatherRepository
import weather.lyra.com.weatherapp.weather.list.WeatherContract

/**
 * @author tweissbeck
 */
class WeatherListPresenter(val schedulerProvider: SchedulerProvider, val weatherRepository: WeatherRepository) :
        WeatherContract.Presenter {
    override fun start() {

    }

    override lateinit var view: WeatherContract.View

    override fun loadWeather() {

        weatherRepository.loadLast().subscribeOn(schedulerProvider.io()).observeOn(
                schedulerProvider.ui()).subscribe(
                { success -> view.displayWeather(success) }, { error -> view.onError(error) })
    }

    override fun loadDetail(dailyForecastModel: DailyForecastModel) {
        view.displayDetail(dailyForecastModel.id)
    }
}