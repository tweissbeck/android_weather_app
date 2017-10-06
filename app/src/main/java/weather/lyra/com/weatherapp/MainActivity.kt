package weather.lyra.com.weatherapp

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }

    override fun onStart() {
        super.onStart()

        val waitTask = object : AsyncTask<Void, Int, Boolean>() {


            override fun onPostExecute(result: Boolean?) {
                loader.visibility = View.GONE
                Thread.sleep(500)
                goToWeatherActivity()
            }

            override fun onPreExecute() {
                super.onPreExecute()
                loader.visibility = View.VISIBLE
            }

            override fun doInBackground(vararg params: Void?): Boolean {

                (1..5).map { Thread.sleep(it * 100L)
                 publishProgress(it * 20)
                }


                return true
            }

            override fun onProgressUpdate(vararg values: Int?) {
               loader_progress_text.text = "${values[0]}/100"

            }
        }
        waitTask.execute()
    }

    fun goToWeatherActivity() {
        NavigationHelper.startActivity(this, WeatherActivity::class.java, clearStack = true)
    }
}
