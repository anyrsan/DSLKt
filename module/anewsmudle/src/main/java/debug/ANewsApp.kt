package debug

import android.app.Application
import android.util.Log
import com.any.org.ankolibrary.handlerException
import com.any.org.eanewsmudle.di.appModules
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *
 * @author any
 * @time 2019/10/25 19.11
 * @details
 */
class ANewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ANewsApp)
            androidLogger()
            modules(appModules)
        }

        //处理错误
        handlerRxError()
    }



    private fun handlerRxError(){
        val onError: (t: Throwable) -> Unit = {
            it.printStackTrace()
            Log.e("error", "全局异常处理，对于一些权限过期异常，放到这里处理")
        }
        //处理不了的这里处理
        RxJavaPlugins.setErrorHandler(onError)
    }

}