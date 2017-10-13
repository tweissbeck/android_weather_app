package weather.lyra.com.weatherapp.weather

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.joanzapata.iconify.widget.IconTextView
import weather.lyra.com.weatherapp.R
import weather.lyra.com.weatherapp.model.DailyForecastModel

/**
 * @author tweissbeck
 */
class WeatherListAdapter(val weathers: List<DailyForecastModel>) : RecyclerView.Adapter<WeatherListHolder>() {
    override fun onBindViewHolder(holder: WeatherListHolder, position: Int) {
        holder.display(weathers[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_result_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return WeatherListHolder(v)
    }

    override fun getItemCount(): Int = weathers.size
}

class WeatherListHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val icon = view.findViewById(R.id.weatherItemIcon) as IconTextView
    val temp = view.findViewById(R.id.weatherIemTemp) as TextView
    val text = view.findViewById(R.id.weatherItemText) as TextView
    fun display(dailyForecastModel: DailyForecastModel) {
        icon.text = dailyForecastModel.icon
        temp.text = dailyForecastModel.temperatureString
        text.text = dailyForecastModel.forecastString
    }


}