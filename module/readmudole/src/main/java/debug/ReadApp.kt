package debug

import android.app.Application

/**
 *
 * @author any
 * @time 2019/9/19 17.41
 * @details
 */
class ReadApp :Application(){


    override fun onCreate() {
        super.onCreate()

        BaseApp.initData(this)
    }

}