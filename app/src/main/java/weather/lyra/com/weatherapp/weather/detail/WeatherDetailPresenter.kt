package weather.lyra.com.weatherapp.weather.detail

import android.util.Log
import io.reactivex.disposables.Disposable
import rx.SchedulerProvider
import weather.lyra.com.weatherapp.repository.WeatherRepository

/**
 * @author tweissbeck
 */
class WeatherDetailPresenter(val repo: WeatherRepository, val scheduler: SchedulerProvider) :
        WeatherDetailContract.Presenter {
    var request: Disposable? = null
    override fun stop() {
        request?.dispose()
    }

    override fun start() {
    }

    override fun loadDetail(id: String) {
        request = repo.loadDetail(id).subscribeOn(scheduler.io()).observeOn(scheduler.ui()).subscribe(
                { success -> view.displayDetail(success) }, { error -> Log.e("WeatherDetail", "", error) }
        )

    }

    override lateinit var view: WeatherDetailContract.View

}