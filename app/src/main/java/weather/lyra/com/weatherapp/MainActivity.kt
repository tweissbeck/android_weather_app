package weather.lyra.com.weatherapp

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()

        val waitTask = object : AsyncTask<Void, Void, Boolean>() {
            override fun onPostExecute(result: Boolean?) {
                goToWeatherActivity()
            }


            override fun doInBackground(vararg params: Void?): Boolean {
                Thread.sleep(1000)
                return true
            }

        }
        waitTask.execute()
    }

    fun goToWeatherActivity() {
        NavigationHelper.startActivity(this, WeatherActivity::class.java, clearStack = true)
    }
}
