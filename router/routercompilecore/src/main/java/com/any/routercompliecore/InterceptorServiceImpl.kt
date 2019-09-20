package com.any.routercompliecore

import android.content.Context
import android.util.Log
/**
 *
 * @author any
 * @time 2019/07/10 15.28
 * @details 监听器拦截
 */

class InterceptorServiceImpl : InterceptorService {

    private val listInterceptor by lazy { Router.getInterceptorList() }

    override fun init(context: Context) {


        listInterceptor.forEach {
            it.iInterceptor.init(context)
        }
    }


    override fun doInterceptions(
        routerConfig: RouterConfig,
        callback: InterceptorCallback
    ) {
        execute(0, routerConfig, callback)
    }


    private fun execute(index: Int, routerConfig: RouterConfig, callback: InterceptorCallback) {
        //继续拦截
        Log.e("msg", "... ${listInterceptor.size}  $index")
        if (index < listInterceptor.size) {
            val intIterator = listInterceptor[index]
            intIterator.iInterceptor.process(routerConfig, object : InterceptorCallback {
                override fun onContinue(routerConfig: RouterConfig) {
                    execute((index + 1), routerConfig, callback)
                }

                override fun onInterrupt(exception: Throwable?) {
                    callback.onInterrupt(exception)
                }
            })
        } else {
            callback.onContinue(routerConfig)
        }

    }

}