package com.any.org.dslkt

import android.content.Context
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.LOGIN
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.provide.ShareDataProvide
import com.any.routerannotation.KInterceptor
import com.any.routercompliecore.*

/**
 *
 * @author any
 * @time 2019/07/10 16.55
 * @details  全局拦截登录状态
 */
@KInterceptor(10)
class GlobalInterceptor : IInterceptor {

    override fun init(context: Context) {
        KLog.e( "我是 10 拦截 init")
    }

    override fun process(
        routerConfig: RouterConfig,
        callback: InterceptorCallback
    ) {
        KLog.e("对没有登录进行拦截    ${routerConfig.target}")
        if (hasLogin(routerConfig.target)) {
            KLog.e("发现没有登录，开始拦截处理")
            val rKey = "loginKey"
            routerConfig.resumeKey = rKey
            Router.addResumeRouter(routerConfig)
            KLog.e( "后续拦截器不再拦截")
            CustomToast.showMsg(routerConfig.context,"检查到没有登录，去登录")
            //跳转到目标
            Router.jump(routerConfig.context) {
                target = LOGIN
                setParams("rKey" to rKey)
                callDefaultBack {
                    KLog.e( "是否成功？===》${it == CODE_RESULT_OK}")
                }
            }
        } else {
            callback.onContinue(routerConfig)
        }
    }

    private fun hasLogin(target: String): Boolean =
        (target != LOGIN) and (!ShareDataProvide.getInstance().hasLogin)

}