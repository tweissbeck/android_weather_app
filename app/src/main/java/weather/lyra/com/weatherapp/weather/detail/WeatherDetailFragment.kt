package weather.lyra.com.weatherapp.weather.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_detail.*
import org.koin.android.ext.android.app.inject
import org.koin.android.ext.android.app.release
import weather.lyra.com.weatherapp.R
import weather.lyra.com.weatherapp.di.MainModule
import weather.lyra.com.weatherapp.model.DailyForecastModel

/**
 * @author tweissbeck
 */
class WeatherDetailFragment : Fragment(), WeatherDetailContract.View {
    override val presenter: WeatherDetailContract.Presenter by inject()

    val forcastId by lazy { arguments.getString("id") }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_weather_detail, container, false) as ViewGroup
        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
        presenter.loadDetail(forcastId)
    }

    override fun onPause() {
        super.onPause()
        release(MainModule.CTX_DETAIL)
    }

    override fun displayDetail(model: DailyForecastModel) {
        weatherIemTemp.text = model.forecastString
        weatherItemIcon.text = model.icon
        weatherItemText.text = model.temperatureString
    }

    companion object {
        fun create(id: String): WeatherDetailFragment {
            val fragment = WeatherDetailFragment()
            val bundle = Bundle()
            bundle.putString("id", id)
            fragment.arguments = bundle

            return fragment
        }
    }

}