package debug

import android.app.Application
import com.any.org.commonlibrary.auto.AutoSizeManager

/**
 *
 * @author any
 * @time 2019/11/11 16.45
 * @details
 */
class OneApp :Application(){

    override fun onCreate() {
        super.onCreate()
        //进行绑定
        AutoSizeManager(this).registerLifecycleCallbacks()
    }
}