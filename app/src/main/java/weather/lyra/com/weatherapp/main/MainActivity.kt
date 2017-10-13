package weather.lyra.com.weatherapp.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.app.inject
import org.koin.android.ext.android.app.release
import weather.lyra.com.weatherapp.NavigationHelper
import weather.lyra.com.weatherapp.R
import weather.lyra.com.weatherapp.di.MainModule
import weather.lyra.com.weatherapp.weather.WeatherDisplayActivity

class MainActivity : AppCompatActivity(), MainContract.View {


    override val presenter: MainContract.Presenter by inject()


    override fun goToDisplayWeatherResult() {
        //Snackbar.make(this.currentFocus, "Go to", Snackbar.LENGTH_SHORT).show()
        NavigationHelper.startActivity(this, WeatherDisplayActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wheater_button.setOnClickListener { view ->
            presenter.searchWeather(editText.text.toString())
            editText.setText("")
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
        presenter.start()
    }


    override fun onPause() {
        release(MainModule.CTX_MAIN)
        super.onPause()
    }

    override fun displayError(error: Throwable) {
        Snackbar.make(this.currentFocus, "/No good", Snackbar.LENGTH_SHORT).show()
    }

    override fun initLastSearch(lastSearch: String) {
        editText.hint = lastSearch
    }
}
