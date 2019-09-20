package debug

import android.app.Application

/**
 *
 * @author any
 * @time 2019/9/19 17.40
 * @details
 */
class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        BaseApp.initData(this)
    }
}