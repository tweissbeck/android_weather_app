package weather.lyra.com.weatherapp.weather

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import weather.lyra.com.weatherapp.NavigationHelper
import weather.lyra.com.weatherapp.R

class WeatherDisplayActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_display)
        NavigationHelper.openFragment(this, R.id.weatherContainer, WeatherListFragment.newWeahterListFragment())

    }
}
