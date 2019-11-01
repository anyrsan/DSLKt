package com.any.org.dslkt

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.any.routercompliecore.Router
import io.reactivex.plugins.RxJavaPlugins

/**
 *
 * @author any
 * @time 2019/08/05 14.35
 * @details
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Router.init(this)
        //设置默认处理器
        handlerRxError()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
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