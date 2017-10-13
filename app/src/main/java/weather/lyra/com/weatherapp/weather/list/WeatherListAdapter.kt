package weather.lyra.com.weatherapp.weather.list

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
class WeatherListAdapter(val weathers: List<DailyForecastModel>, val clickCallBack: (DailyForecastModel) -> Unit) :
        RecyclerView.Adapter<WeatherListHolder>() {
    override fun onBindViewHolder(holder: WeatherListHolder, position: Int) {
        holder.display(weathers[position], clickCallBack)
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
    val icon = view.findViewById<IconTextView>(R.id.weatherItemIcon)
    val temp = view.findViewById<TextView>(R.id.weatherIemTemp)
    val text = view.findViewById<TextView>(R.id.weatherItemText)
    fun display(dailyForecastModel: DailyForecastModel,
                clickCallBack: (DailyForecastModel) -> Unit) {
        icon.text = dailyForecastModel.icon
        temp.text = dailyForecastModel.temperatureString
        text.text = dailyForecastModel.forecastString
        view.setOnClickListener {
            clickCallBack(dailyForecastModel)
        }
    }


}