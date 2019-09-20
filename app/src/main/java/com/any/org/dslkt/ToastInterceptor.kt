package com.any.org.dslkt

import android.content.Context
import com.any.org.commonlibrary.log.KLog
import com.any.routerannotation.KInterceptor
import com.any.routercompliecore.IInterceptor
import com.any.routercompliecore.InterceptorCallback
import com.any.routercompliecore.RouterConfig

/**
 * @author any
 * @time 2019/07/10 16.14
 * @details 弹出框拦截器
 */
@KInterceptor(1)
class ToastInterceptor : IInterceptor {

    override fun init(context: Context) {
        KLog.e("我是 10 拦截 init")
    }

    override fun process(
        routerConfig: RouterConfig,
        callback: InterceptorCallback
    ) {
        KLog.e("ToastInterceptor  ${routerConfig.target}")
        callback.onContinue(routerConfig)
    }

}