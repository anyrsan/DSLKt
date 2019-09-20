package com.any.routercompliecore

import android.content.Context
/**
 *
 * @author any
 * @time 2019/07/10 15.14
 * @details
 */
interface InterceptorService {

    fun init(context: Context)

    fun doInterceptions(routerConfig: RouterConfig, callback: InterceptorCallback)
}