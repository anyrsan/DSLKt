package com.any.routercompliecore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.*

object Router {

    private val mapPage = mutableMapOf<String, Class<*>>()
    //拦截器
    private val interceptorList = mutableListOf<InterceptorBean>()
    // 可以通过SPI机制，或者 注解 进行初始化
    private var interceptorService: InterceptorService? = null
    //对于重定向的路由进行处理
    private val resumeMap = mutableMapOf<String, RouterConfig>()
    // 全局导航回调  // 后续优化，通过apt进行赋值处理  // 如果已有实现，则不执行
    private var nCallBack:NavigationCallback?=null

    /**
     * 此处用到SPI
     */
    fun init(context: Context) {
        if (interceptorService == null) {
            RouterAppInit.init()   // 实际上是生成
            interceptorService = ServiceLoader.load(InterceptorService::class.java).iterator().next()
            interceptorService?.init(context)
        } else {
            Log.e("msg", "不需要重复进行初始化")
        }
    }


    // 获取拦截器
    internal fun getInterceptorList(): List<InterceptorBean> = interceptorList


    //添加拦截监听  apt执行
    fun addInterceptorItem(priority: Int, iInterceptor: IInterceptor) {
        interceptorList.add(InterceptorBean(priority, iInterceptor))
        // 进行排序
        interceptorList.sortByDescending {
            it.priority
        }
    }

    //添加注册 apt执行
    fun registerPage(key: String, value: Class<*>) {
        mapPage[key] = value
    }

    //可以由注解处理器调用
    fun setNavigationCallBack(navigationCallBack:NavigationCallback){
        this.nCallBack = navigationCallBack
    }


    fun jump(context: Context, block: RouterConfigBuilder.() -> Unit) {
        Log.e("msg", "...context...  $context")
        RouterConfigBuilder(context, null).apply(block).builder().jump()
    }

    fun jump(fragment: Fragment, block: RouterConfigBuilder.() -> Unit) {
        RouterConfigBuilder(fragment.requireContext(), fragment).apply(block).builder().jump()
    }


    internal fun doJump(routerConfig: RouterConfig, call: NavigationCallback?) {
        //直接导航跳转
        if (interceptorList.isEmpty()) {
            startActivity(routerConfig, call)
        } else {
            interceptorService?.doInterceptions(routerConfig, object : InterceptorCallback {
                override fun onContinue(routerConfig: RouterConfig) {
                    startActivity(routerConfig, call)
                }

                override fun onInterrupt(exception: Throwable?) {
                    call?.onInterrupt(routerConfig)
                }
            })
        }
    }



    private fun startActivity(routerConfig: RouterConfig, call: NavigationCallback?) {
        // 取出对应的page页面
        val classes = mapPage[routerConfig.target]
        //判断是否存在此页面
        //可以设置默认处理器
        if (classes == null) {
            call?.onFound(routerConfig) ?: nCallBack?.onFound(routerConfig)
        } else {
            try {
                // intent 对象初始化
                val intent = Intent(routerConfig.context, classes)
                //数据类
                routerConfig.bundle?.apply {
                    intent.putExtras(this)
                }
                //设置启动类型
                routerConfig.flags?.apply {
                    forEach {
                        intent.addFlags(it)
                    }
                }
                // 处理对应的bundle数据，以及对应的intentFlag,以及查看是否带有resultCode
                if (routerConfig.requestCode != null) {
                    when {
                        routerConfig.fragment is Fragment -> {
                            routerConfig.fragment.startActivityForResult(intent, routerConfig.requestCode)

                        }
                        routerConfig.context is Activity -> {
                            ActivityCompat.startActivityForResult(
                                routerConfig.context,
                                intent,
                                routerConfig.requestCode,
                                null
                            )
                        }
                        else -> {
                            startToActivity(routerConfig, intent)
                        }
                    }
                } else {
                    startToActivity(routerConfig, intent)
                }
                // 查看是否有跳转动画
                animToActivity(routerConfig)
                call?.onGoDestination(routerConfig) ?: nCallBack?.onGoDestination(routerConfig)
            } catch (e: Exception) {
                e.printStackTrace()
                call?.onInterrupt(routerConfig) ?: nCallBack?.onInterrupt(routerConfig)
            }
        }
    }


    private fun startToActivity(routerConfig: RouterConfig, intent: Intent) {
        ActivityCompat.startActivity(routerConfig.context, intent, null)
    }


    private fun animToActivity(routerConfig: RouterConfig) {
        //        if(routerConfig.context is Activity){
        //            routerConfig.context.overridePendingTransition()
        //        }
    }


    //处理数据
    fun addResumeRouter(routerConfig: RouterConfig) {
        val resumeKey = routerConfig.resumeKey
        resumeKey?.let {
            resumeMap[it] = routerConfig
        }
    }

    //根据原来参数，恢复路由跳转
    private fun resume(routerConfig: RouterConfig?) {
        routerConfig?.let {
            //从恢复路由表中删除已恢复的跳转请求
            resumeMap.remove(it.resumeKey)
            //标记
            doJump(it, it.callBack)
        }
    }

    //如果存在，则直接恢复到被拦截之前的地址
    fun resumeRouter(resumeKey:String?){
        resumeKey?.let {
            val routerConfig = getResumeRouter(it)
            resume(routerConfig)
        }
    }

    //获取路由配置
   private fun getResumeRouter(resumeKey: String): RouterConfig? = resumeMap[resumeKey]

    //退出时要调用
    fun onDestory() {
        mapPage.clear()
        interceptorList.clear()
        resumeMap.clear()
        interceptorService = null
    }

}