package weather.lyra.com.weatherapp.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.AndroidModule
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import weather.lyra.com.weatherapp.datasource.WeatherDatasource
import weather.lyra.com.weatherapp.main.MainActivity
import weather.lyra.com.weatherapp.main.MainContract
import weather.lyra.com.weatherapp.main.MainPresenter
import weather.lyra.com.weatherapp.repository.WeatherRepository
import weather.lyra.com.weatherapp.repository.WeatherRepositoryImpl
import java.util.concurrent.TimeUnit

fun allModules() = listOf(WebModule(), MainModule())

/*class WeatherModule : AndroidModule() {
    override fun context() =
            // Scope MainActivity
            declareContext(scope = MainActivity::class) {
                provide { WeatherPresenter(get(), get()) } bind (WeatherContract.Presenter::class)
            }
}*/

class MainModule : AndroidModule() {
    override fun context() =
            declareContext {
                scope { MainActivity::class }
                // Rx schedulers
                provide { MainPresenter(get()) } bind { MainContract.Presenter::class }
            }

}

class WebModule : AndroidModule() {
    override fun context() =
            declareContext {
                // provided web components
                provide { createClient() }
                // Fill property
                provide { createDataSource(get(), getProperty(SERVER_URL)) }
                provide { WeatherRepositoryImpl(get()) } bind { WeatherRepository::class }
            }

    private fun createClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build()
    }

    private fun createDataSource(okHttpClient: OkHttpClient, url: String): WeatherDatasource {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(WeatherDatasource::class.java)
    }

    companion object {
        const val SERVER_URL = "SERVER_URL"
    }
}