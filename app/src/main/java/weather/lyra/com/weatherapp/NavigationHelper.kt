package weather.lyra.com.weatherapp


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

object NavigationHelper {

    fun startActivity(ctx: Activity, activity: Class<out Activity>, extras: Bundle? = null, clearStack: Boolean = false,
                      animation: Boolean = true) {
        val i = Intent(ctx, activity)
        if (clearStack) {
            i.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (extras != null) {
            i.putExtras(extras)
        }
        val options: ActivityOptionsCompat?
        if (animation) {
            options = ActivityOptionsCompat.makeCustomAnimation(ctx, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            options = null
        }
        ActivityCompat.startActivity(ctx, i, options?.toBundle())
    }

    fun slideFragment(ctx: AppCompatActivity, container: Int, f: Fragment) {
        ctx.supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.fade_in, android.R.anim.slide_out_right)
                .replace(container, f).addToBackStack(null)
                .commit()
    }

    fun openFragment(ctx: AppCompatActivity, container: Int, f: Fragment) {
        ctx.supportFragmentManager.beginTransaction()
//                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(container, f)
                .commit()
    }
}

