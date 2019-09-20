package com.any.org.dslkt

import android.app.Application
import com.any.routercompliecore.Router

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
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        MultiDex.install(base)
//    }
}