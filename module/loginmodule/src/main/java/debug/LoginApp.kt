package debug

import android.app.Application

/**
 *
 * @author any
 * @time 2019/9/19 16.15
 * @details
 */
class LoginApp : Application() {


    override fun onCreate() {
        super.onCreate()
         BaseApp.initData(this)
    }


}