package debug

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.any.org.commonlibrary.auto.AutoSizeManager
import com.squareup.leakcanary.LeakCanary

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

        // 添加监听
        initLeakCanary()
    }



    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }


    /**
     *    是否支持悬浮
     */
     fun checkoutOverlay() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.data =
                    Uri.parse("package:${packageName}") //应用的包名，可直接跳转到这个应用的悬浮窗设置；
                startActivity(intent)
            } else {
                Log.e("msg",".... error 设备不支持 错误")
            }
        } else {
            Log.e("msg",".... error 系统版本不支持")
        }
    }
}