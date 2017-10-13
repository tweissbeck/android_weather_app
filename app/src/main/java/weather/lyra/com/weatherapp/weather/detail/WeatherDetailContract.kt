package weather.lyra.com.weatherapp.weather.detail

import koin.sampleapp.BasePresenter
import koin.sampleapp.BaseView
import weather.lyra.com.weatherapp.model.DailyForecastModel

/**
 * @author tweissbeck
 */
interface WeatherDetailContract {
    interface View : BaseView<Presenter> {
        fun displayDetail(model: DailyForecastModel)
    }

    interface Presenter : BasePresenter<View> {
        fun loadDetail(id: String)
    }
}