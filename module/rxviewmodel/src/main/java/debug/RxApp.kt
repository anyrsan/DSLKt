package debug

import android.app.Application
import android.os.StrictMode
import com.any.org.commonlibrary.log.KLog
import com.any.org.rxviewmodel.di.appModules
import com.squareup.leakcanary.LeakCanary
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *
 * @author any
 * @time 2019/11/6 16.28
 * @details
 */
class RxApp :Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RxApp)
            androidLogger()
            modules(appModules)

        }

        RxJavaPlugins.setErrorHandler {
            KLog.e("有异常了  $it")
        }

//        enabledStrictMode()

        initLeakCanary()
    }


    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }


}