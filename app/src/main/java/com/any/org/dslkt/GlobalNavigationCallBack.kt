package com.any.org.dslkt

import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.log.KLog
import com.any.routerannotation.KNavigation
import com.any.routercompliecore.NavigationCallback
import com.any.routercompliecore.RouterConfig

/**
 *
 * @author any
 * @time 2019/9/18 20.26
 * @details 全局导航回调  ,只支持一次配置，多次是覆盖，不存在多次
 */
// 自动 注入
@KNavigation
class GlobalNavigationCallBack : NavigationCallback {

    override fun onFound(routerConfig: RouterConfig) {
        KLog.e("找不到页面吗？？？？")
        CustomToast.showMsg(routerConfig.context,"没有找到 ${routerConfig.target} 对应的页面")
    }

    override fun onGoDestination(routerConfig: RouterConfig) {
        KLog.e("正确打开了。。。${routerConfig.target}")
    }

    override fun onInterrupt(routerConfig: RouterConfig) {

    }
}