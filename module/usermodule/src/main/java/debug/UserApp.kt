package debug

import android.app.Application

/**
 *
 * @author any
 * @time 2019/9/19 17.42
 * @details
 */
class UserApp : Application() {

    override fun onCreate() {
        super.onCreate()
        BaseApp.initData(this)
    }
}