package com.any.org.eventbuslibrary


import com.any.org.ankolibrary.doAsyncTask
import com.any.org.ankolibrary.runOnUiThread
import com.any.org.eventbuslibrary.ktanno.Subscribe
import com.any.org.eventbuslibrary.ktanno.ThreadModel
import com.any.org.eventbuslibrary.reflect.MethodDataInfo
import java.lang.Exception
/**
 *
 * @author any
 * @time 2019/07/30 17.29
 * @details  可以通过APT,优先对代码进行装载扫描，对注解框架进行优先存储，然后在调用时，执行相关操作
 */
object RxNewBus {

    //// 处理类解析
    private val subscribeClassMap by lazy { mutableMapOf<Any, List<MethodDataInfo>>() }

    ////// 处理
    fun registerEvent(subscribe: Any) {
        if (subscribeClassMap.containsKey(subscribe)) {
            throw Exception("不应该重复注册相同的key")
        }
        // 获取当前类的所有方法 私有不处理
        val clazz = subscribe.javaClass
        val methods = clazz.methods
        // 获取方法的
        val listMethods = ArrayList<MethodDataInfo>()
        // 遍历方法
        methods.forEach {
            val funTypes = it.parameterTypes
            val annotation = it.getAnnotation(Subscribe::class.java)
            if (annotation != null && funTypes.size == 1) {  //满足条件 ，则可以进行参数获取
                val typeClass = funTypes[0]
                val methodInfo = MethodDataInfo(it, typeClass, annotation.priority, annotation.threadModel)
                listMethods.add(methodInfo)
            }
        }
        subscribeClassMap[subscribe] = listMethods
//        listMethods.forEach {
//            Log.e("bus","msg.... ${it.method}")
//        }
    }


    /**
     * 反射可以处理 继承关系的方法，如果是apt技术，这块要考虑如何获取，后续优化改为APT时会有针对性的调整
     */
    fun postEvent(any: Any) {
        val clazz = any.javaClass
        subscribeClassMap.keys.forEach {
            val listMethods = subscribeClassMap[it]
            // 如果在不同线程间切换，并不能保证执行顺序，如果都是同步线程，那就会在同一类中注册的会顺序执行
            // 后续优化方案，对不同类注册的方法，也严格按照顺序执行
            listMethods?.sortedByDescending { md ->
                md.priority
            }?.forEach { md ->
                if (md.argType == clazz) {
                    when (md.threadModel) {
                        ThreadModel.MAIN -> {
                            // 调用函数
                            runOnUiThread {
                                md.method.invoke(it, any)
                            }
                        }
                        ThreadModel.BACKGROUND -> {
                            doAsyncTask {
                                md.method.invoke(it, any)
                            }
                        }
                        ThreadModel.CURRENT -> {
                            md.method.invoke(it, any)
                        }
                    }
                }
            }
        }
    }


    //反注消
    fun unRegisterEvent(any: Any) {
        subscribeClassMap.remove(any)
    }

}