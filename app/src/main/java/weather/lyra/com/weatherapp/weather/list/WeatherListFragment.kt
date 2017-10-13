package weather.lyra.com.weatherapp.weather.list

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_weather_result.*
import org.koin.android.ext.android.app.inject
import org.koin.android.ext.android.app.release
import weather.lyra.com.weatherapp.util.NavigationHelper
import weather.lyra.com.weatherapp.R
import weather.lyra.com.weatherapp.di.MainModule
import weather.lyra.com.weatherapp.model.DailyForecastModel
import weather.lyra.com.weatherapp.weather.detail.WeatherDetailFragment

/**
 * @author tweissbeck
 */
class WeatherListFragment : Fragment(), WeatherContract.View {
    override fun displayWeather(dailyForecastModel: List<DailyForecastModel>) {
        weatherResultRecyclerView.adapter = WeatherListAdapter(
                dailyForecastModel, this::onWeatherClick)
        weatherResultRecyclerView.adapter.notifyDataSetChanged()
    }

    fun onWeatherClick(dailyForecastModel: DailyForecastModel){
        presenter.loadDetail(dailyForecastModel)
    }

    override fun onPause() {
        release(MainModule.CTX_LIST)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
        presenter.loadWeather()
    }

    override fun displayDetail(id: String) {
        NavigationHelper.slideFragment(this.activity as AppCompatActivity, R.id.weatherContainer,
                WeatherDetailFragment.create(id))
    }

    override fun onError(error: Throwable) {
        Snackbar.make(this.view!!, "Pas de bol! ${error.message}", Snackbar.LENGTH_LONG).show()
    }

    override val presenter: WeatherContract.Presenter by inject()


    companion object {
        fun newWeahterListFragment(s: String = "abc"): WeatherListFragment {
            val fragement = WeatherListFragment()
            val bundle = Bundle()
            bundle.putString("abc", s)
            fragement.arguments = Bundle()
            return fragement
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_weather_result, container, false) as ViewGroup
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherResultRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        weatherResultRecyclerView.adapter = WeatherListAdapter(emptyList(),
                this::onWeatherClick)
        weatherResultRecyclerView.addItemDecoration(
                DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))

    }
}