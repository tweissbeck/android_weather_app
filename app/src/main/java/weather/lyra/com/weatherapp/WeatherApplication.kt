package weather.lyra.com.weatherapp

import android.app.Application
import org.koin.KoinContext
import org.koin.android.KoinContextAware
import org.koin.android.newKoinContext
import weather.lyra.com.weatherapp.di.allModules

/**
 * Created by tweissbeck on 06/10/2017.
 */
class WeatherApplication : Application(), KoinContextAware {
    override val koinContext: KoinContext = newKoinContext(this, allModules())


    override fun onCreate() {
        super.onCreate()


    }
}