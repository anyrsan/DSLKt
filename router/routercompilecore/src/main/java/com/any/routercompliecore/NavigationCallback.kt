package com.any.routercompliecore
/**
 *
 * @author any
 * @time 2019/07/10 15.09
 * @details
 */
interface NavigationCallback {

    // 找不到目标页
    fun onFound(routerConfig: RouterConfig)

    // 到达目标页
    fun onGoDestination(routerConfig: RouterConfig)

    // 异常中断
    fun onInterrupt(routerConfig: RouterConfig)

}