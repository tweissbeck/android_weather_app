package weather.lyra.com.weatherapp.weather.list

import io.reactivex.disposables.Disposable
import weather.lyra.com.weatherapp.rx.SchedulerProvider
import weather.lyra.com.weatherapp.model.DailyForecastModel
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * @author tweissbeck
 */
class WeatherListPresenter(val schedulerProvider: SchedulerProvider, val weatherRepository: WeatherRepository) :
        WeatherContract.Presenter {
    var request: Disposable? = null
    override fun stop() {
        request?.dispose()
    }

    override fun start() {

    }

    override lateinit var view: WeatherContract.View

    override fun loadWeather() {

        request = weatherRepository.loadLast().subscribeOn(schedulerProvider.io()).observeOn(
                schedulerProvider.ui()).subscribe(
                { success -> view.displayWeather(success) }, { error -> view.onError(error) })
    }

    override fun loadDetail(dailyForecastModel: DailyForecastModel) {
        view.displayDetail(dailyForecastModel.id)
    }
}