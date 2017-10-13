package weather.lyra.com.weatherapp.weather.list

import koin.sampleapp.BasePresenter
import koin.sampleapp.BaseView
import weather.lyra.com.weatherapp.model.DailyForecastModel


interface WeatherContract {
    interface View : BaseView<Presenter> {
        fun displayWeather(dailyForecastModel: List<DailyForecastModel>)
        fun onError(error: Throwable)
        fun displayDetail(id: String)
    }

    interface Presenter : BasePresenter<View> {
        fun loadWeather()
        fun loadDetail(dailyForecastModel: DailyForecastModel)
    }
}