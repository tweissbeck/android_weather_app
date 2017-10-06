package weather.lyra.com.weatherapp.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.AndroidModule
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import weather.lyra.com.weatherapp.service.WeatherWS
import java.util.concurrent.TimeUnit

fun allModules() = listOf(WebModule())

/*class WeatherModule : AndroidModule() {
    override fun context() =
            // Scope MainActivity
            declareContext(scope = MainActivity::class) {
                provide { WeatherPresenter(get(), get()) } bind (WeatherContract.Presenter::class)
            }
}*/

/*class MainModule : AndroidModule() {
   override fun context() =
           declareContext {
               // Rx schedulers
               provide { ApplicationSchedulerProvider() } bind (SchedulerProvider::class)
           }

}*/

class WebModule : AndroidModule() {
    override fun context() =
            declareContext {
                // provided web components
                provide { createClient() }
                // Fill property
                provide { retrofitWS(get(), getProperty(SERVER_URL)) }
            }

    private fun createClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
                return OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor).build()
    }

    private fun retrofitWS(okHttpClient: OkHttpClient, url: String): WeatherWS {
        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build()
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(WeatherWS::class.java)
    }

    companion object {
        const val SERVER_URL = "SERVER_URL"
    }
}