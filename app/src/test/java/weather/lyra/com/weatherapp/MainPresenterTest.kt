package weather.lyra.com.weatherapp

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.Koin
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import weather.lyra.com.weatherapp.di.MainModule
import weather.lyra.com.weatherapp.di.RxTestModule
import weather.lyra.com.weatherapp.di.WebModule
import weather.lyra.com.weatherapp.main.MainContract
import weather.lyra.com.weatherapp.main.MainPresenter

/**
 * Created by tweissbeck on 06/10/2017.
 */

class MainPresenterTest {

    val url = "https://my-weather-api.herokuapp.com/"
    lateinit var presenter: MainPresenter
    @Mock lateinit var view: MainContract.View



    @Before
    fun before(){
        MockitoAnnotations.initMocks(this)
        val context = Koin().build(listOf(WebModule(), MainModule(), RxTestModule()))
        context.setProperty(WebModule.SERVER_URL, url)
        presenter = context.get()
        presenter.view  = view

    }

    @Test
    fun testDisplayWeahter(){
        presenter.searchWeather("paris")
        Mockito.verify(view).goToDisplayWeatherResult()
    }
}