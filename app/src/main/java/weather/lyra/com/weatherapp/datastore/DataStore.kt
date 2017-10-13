package weather.lyra.com.weatherapp.datastore

import android.content.Context

/**
 * @author tweissbeck
 */
interface DataStore {

    fun save(key: String, value: Any)
    fun <T> get(key: String): T
}

class AndroidDataStore(context: Context) : DataStore {
    companion object {
        val fileName = "weather"
    }

    val sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)


    override fun save(key: String, value: Any) {
        sharedPref.edit().putString(key, value.toString()).apply()
    }

    override fun <T> get(key: String): T = sharedPref.getString(key, "") as T
}