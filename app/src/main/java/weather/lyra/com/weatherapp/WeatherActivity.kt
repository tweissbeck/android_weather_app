package weather.lyra.com.weatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        wheater_button.setOnClickListener { view ->
            loadCityWeather()
        }
    }

    private fun loadCityWeather() {

    }
}
