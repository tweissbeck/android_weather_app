package weather.lyra.com.weatherapp.weather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.joanzapata.iconify.IconDrawable
import com.joanzapata.iconify.fonts.FontAwesomeIcons
import weather.lyra.com.weatherapp.NavigationHelper
import weather.lyra.com.weatherapp.R
import weather.lyra.com.weatherapp.util.setupActionBar
import weather.lyra.com.weatherapp.weather.list.WeatherListFragment

class WeatherDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_display)
        NavigationHelper.openFragment(this, R.id.weatherContainer, WeatherListFragment.newWeahterListFragment())
        setupActionBar(R.id.toolbar) {
            val a = IconDrawable(this@WeatherDisplayActivity, FontAwesomeIcons.fa_chevron_left)
            a.colorRes(R.color.accent)
                    .actionBarSize()
            setHomeAsUpIndicator(a)
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
