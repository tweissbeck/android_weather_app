package weather.lyra.com.weatherapp

import io.reactivex.Single
import koin.sampleapp.service.json.weather.Weather
import org.junit.Before
import org.junit.Test
import org.koin.Koin
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when` as whenever
import org.mockito.MockitoAnnotations
import weather.lyra.com.weatherapp.di.MainModule
import weather.lyra.com.weatherapp.di.RxTestModule
import weather.lyra.com.weatherapp.di.WebModule
import weather.lyra.com.weatherapp.main.MainContract
import weather.lyra.com.weatherapp.main.MainPresenter
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * Created by tweissbeck on 06/10/2017.
 */

class MainPresenterMockTest {

    val url = "https://my-weather-api.herokuapp.com/"
    lateinit var presenter: MainPresenter
    @Mock lateinit var view: MainContract.View
    @Mock lateinit var repository : WeatherRepository

    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        val context = Koin().build(listOf(MainModule(), RxTestModule()))
        context.provide { repository }
        context.setProperty(WebModule.SERVER_URL, url)
        presenter = context.get()
        presenter.view  = view

    }

    @Test
    fun testDisplayWeahter(){
        val adrs = "Paris"

        val weather = Mockito.mock(Weather::class.java)
        whenever(repository.weather(adrs)).thenReturn(Single.just(weather))

        presenter.searchWeather(adrs)

        Mockito.verify(view).goToDisplayWeatherResult()
    }
}