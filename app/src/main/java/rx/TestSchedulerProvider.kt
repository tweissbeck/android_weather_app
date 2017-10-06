package rx

import io.reactivex.schedulers.Schedulers

/**
 * Created by tweissbeck on 06/10/2017.
 */
class TestSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun ui() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()
}