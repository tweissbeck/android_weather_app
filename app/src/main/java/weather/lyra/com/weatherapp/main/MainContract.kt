package weather.lyra.com.weatherapp.main

import koin.sampleapp.BasePresenter
import koin.sampleapp.BaseView

/**
 * Created by tweissbeck on 06/10/2017.
 */
interface MainContract {
    interface View : BaseView<Presenter> {
        fun goToDisplayWeatherResult()
    }

    interface Presenter : BasePresenter<View> {
        fun searchWeather(address: String)
    }
}