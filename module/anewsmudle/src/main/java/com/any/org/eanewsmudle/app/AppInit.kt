package com.any.org.eanewsmudle.app

import android.app.Application
import android.content.Context
import com.any.org.eanewsmudle.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.lang.Exception

/**
 *
 * @author any
 * @time 2019/11/1 16.27
 * @details
 */
internal object AppInit {
    fun initKoin(application: Context?) {
        application?.let {
            startKoin {
                androidContext(it)
                androidLogger()
                modules(appModules)
            }
        } ?: throw Exception("Koin 初始化失败")

    }
}