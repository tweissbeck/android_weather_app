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
import weather.lyra.com.weatherapp.datastore.AndroidDataStore
import weather.lyra.com.weatherapp.datastore.DataStore
import weather.lyra.com.weatherapp.datastore.TestDataStore
import weather.lyra.com.weatherapp.main.MainContract
import weather.lyra.com.weatherapp.main.MainPresenter
import weather.lyra.com.weatherapp.repository.WeatherRepository
import weather.lyra.com.weatherapp.repository.WeatherRepositoryImpl
import weather.lyra.com.weatherapp.weather.detail.WeatherDetailContract
import weather.lyra.com.weatherapp.weather.detail.WeatherDetailPresenter
import weather.lyra.com.weatherapp.weather.list.WeatherContract
import weather.lyra.com.weatherapp.weather.list.WeatherListPresenter
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
                context(name = CTX_MAIN) {
                    // Rx schedulers
                    provide { MainPresenter(get(), get(), get()) } bind MainContract.Presenter::class
                }
                context(CTX_LIST) {
                    provide {
                        WeatherListPresenter(get(), get())
                    } bind WeatherContract.Presenter::class
                    context(CTX_DETAIL) {
                        provide { WeatherDetailPresenter(get(), get()) } bind WeatherDetailContract.Presenter::class
                    }
                }
                provide { AndroidDataStore(applicationContext) } bind DataStore::class

            }

    companion object {
        val CTX_MAIN = "MainActivityContext"
        val CTX_LIST = "List"
        val CTX_DETAIL = "Detail"
    }

}


class TestModule : Module() {
    override fun context(): Context = applicationContext {
        context(name = MainModule.CTX_MAIN) {
            // Rx schedulers
            provide { MainPresenter(get(), get(), get()) } bind MainContract.Presenter::class
        }
        context(MainModule.CTX_LIST) {
            provide {
                WeatherListPresenter(get(), get())
            } bind WeatherContract.Presenter::class
            context(MainModule.CTX_DETAIL) {
                provide { WeatherDetailPresenter(get(), get()) } bind WeatherDetailContract.Presenter::class
            }
        }
        provide { TestSchedulerProvider() } bind SchedulerProvider::class
        provide { TestDataStore() } bind DataStore::class
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