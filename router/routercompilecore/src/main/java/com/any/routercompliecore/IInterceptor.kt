package com.any.routercompliecore


import android.content.Context
/**
 *
 * @author any
 * @time 2019/07/10 15.05
 * @details
 */
interface IInterceptor {

    fun init(context: Context)

    fun process(routerConfig: RouterConfig, callback: InterceptorCallback)
}