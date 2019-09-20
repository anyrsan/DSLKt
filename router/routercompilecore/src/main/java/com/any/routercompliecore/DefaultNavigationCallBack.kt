package com.any.routercompliecore


/**
 *
 * @author any
 * @time 2019/07/10 15.59
 * @details 默认转换导航回调，简化实现  可自己实现全局配置
 */
class DefaultNavigationCallBack(private val callBack: (code: Int) -> Unit) : NavigationCallback {

    override fun onFound(routerConfig: RouterConfig) {
        callBack.invoke(CODE_RESULT_NOT_FIND)
    }

    override fun onInterrupt(routerConfig: RouterConfig) {
        callBack.invoke(CODE_RESULT_INTERCEPTOR)
    }

    override fun onGoDestination(routerConfig: RouterConfig) {
        callBack.invoke(CODE_RESULT_OK)
    }
}