package weather.lyra.com.weatherapp.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.AndroidModule
import org.koin.dsl.context.Context
import org.koin.dsl.module.Module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.ApplicationSchedulerProvider
import rx.SchedulerProvider
import rx.TestSchedulerProvider
import weather.lyra.com.weatherapp.datasource.WeatherDatasource
import weather.lyra.com.weatherapp.main.MainContract
import weather.lyra.com.weatherapp.main.MainPresenter
import weather.lyra.com.weatherapp.repository.WeatherRepository
import weather.lyra.com.weatherapp.repository.WeatherRepositoryImpl
import weather.lyra.com.weatherapp.weather.WeatherContract
import weather.lyra.com.weatherapp.weather.WeatherListPresenter
import java.util.concurrent.TimeUnit

fun allModules() = listOf(WebModule(), MainModule(), RxModule())

class RxModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        provide { ApplicationSchedulerProvider() } bind SchedulerProvider::class
    }
}


class MainModule : AndroidModule() {
    override fun context() =
            applicationContext {
                context(name = "MainActivity") {
                    // Rx schedulers
                    provide { MainPresenter(get(), get()) } bind MainContract.Presenter::class
                }
                context("WeatherActivity") {
                    provide { WeatherListPresenter(get(), get()) } bind WeatherContract.Presenter::class
                }
            }

}

class RxTestModule : Module() {
    override fun context(): Context = applicationContext {
        provide { TestSchedulerProvider() } bind SchedulerProvider::class
    }


}

class WebModule : AndroidModule() {
    override fun context() =
            applicationContext {
                // provided web components
                provide { createClient() }
                // Fill property
                provide { createDataSource(get(), getProperty(SERVER_URL)) }
                provide { WeatherRepositoryImpl(get()) } bind WeatherRepository::class
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
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(WeatherDatasource::class.java)
    }

    companion object {
        const val SERVER_URL = "SERVER_URL"
    }
}