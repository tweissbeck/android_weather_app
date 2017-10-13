package weather.lyra.com.weatherapp

import android.app.Application
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.WeathericonsModule
import org.koin.KoinContext
import org.koin.android.KoinContextAware
import org.koin.android.newKoinContext
import weather.lyra.com.weatherapp.di.WebModule
import weather.lyra.com.weatherapp.di.allModules

/**
 * Created by tweissbeck on 06/10/2017.
 */
class WeatherApplication : Application(), KoinContextAware {
    override val koinContext: KoinContext = newKoinContext(this, allModules())


    override fun onCreate() {
        super.onCreate()

        koinContext.setProperty(WebModule.SERVER_URL,getString(R.string.server_url))
        Iconify.with(WeathericonsModule())
    }
}