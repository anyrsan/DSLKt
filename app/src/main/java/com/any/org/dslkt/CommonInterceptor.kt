package com.any.org.dslkt

import android.content.Context
import com.any.org.commonlibrary.log.KLog
import com.any.routerannotation.KInterceptor
import com.any.routercompliecore.IInterceptor
import com.any.routercompliecore.InterceptorCallback
import com.any.routercompliecore.RouterConfig

/**
 *
 * @author any
 * @time 2019/07/10 16.14
 * @details 普通拦截器，暂时不做业务 处理
 */
@KInterceptor(5)
class CommonInterceptor : IInterceptor {

    override fun init(context: Context) {
        KLog.e("我是 4 拦截 init")
    }

    override fun process(
        routerConfig: RouterConfig,
        callback: InterceptorCallback
    ) {
        KLog.e("CommonInterceptor 不太想拦截  ${routerConfig.target}")
        callback.onContinue(routerConfig)
    }

}