package weather.lyra.com.weatherapp.main

import android.os.AsyncTask
import koin.sampleapp.service.json.weather.Weather
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * Created by tweissbeck on 06/10/2017.
 */
class MainPresenter(val weatherRepository: WeatherRepository) : MainContract.Presenter {

    override lateinit var view: MainContract.View

    override fun start() {

    }

    override fun searchWeather(address: String) {
        val async = object : AsyncTask<Void, Void, Weather>() {
            override fun doInBackground(vararg params: Void?): Weather =
                    weatherRepository.weather(address)

            override fun onPostExecute(result: Weather) {
                super.onPostExecute(result)
                view.goToDisplayWeatherResult()
            }
        }
        async.execute()
    }
}