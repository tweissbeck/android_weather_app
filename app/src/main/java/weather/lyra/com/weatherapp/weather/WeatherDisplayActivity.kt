package weather.lyra.com.weatherapp.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import weather.lyra.com.weatherapp.NavigationHelper
import weather.lyra.com.weatherapp.R
import weather.lyra.com.weatherapp.weather.list.WeatherListFragment

class WeatherDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_display)
        NavigationHelper.openFragment(this, R.id.weatherContainer, WeatherListFragment.newWeahterListFragment())

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
