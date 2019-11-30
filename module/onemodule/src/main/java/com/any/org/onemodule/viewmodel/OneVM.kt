package com.any.org.onemodule.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.remote.TestApi
import com.any.org.onemodule.extend.getTargetDate
import kotlinx.coroutines.*
import java.util.*
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

/**
 *
 * @author any
 * @time 2019/11/29 18.47
 * @details
 */
class OneVM(private val testApi: TestApi) : BaseCoroutineViewModel() {

    fun getData() {

//        doTask {
//            KLog.e("msg...开始执行任务了。。。")
//            val data = getM()   // 在IO线程人执行完后，直接回到主线程
//            KLog.e("msg... 等等结果 ")
//            KLog.e("msg... ${Thread.currentThread().name}  $data")
//        }
//
//        KLog.e("我是另一个执行函数")
//
//        doDelayTask(2000) {
//            KLog.e("进入延期函数")
//            delay(1000)
//            KLog.e("已结束 执行")
//        }

//        GlobalScope.launch {
//            KLog.e("开始执行协程了，执行在线程---》  ${Thread.currentThread().name}")
//            val resultDeferred = asyncResult()
//            KLog.e("调用了异步结果方法后，直接输出一个代码块的执行线程 ---》  ${Thread.currentThread().name}")
//            val result = resultDeferred.await()
//            KLog.e("调用等待后的输出结果  $result    所在执行线程 ----> ${Thread.currentThread().name}")
//        }
//
//
//        doTask({
//
//            KLog.e("doTask...。。。 $it")
//
//        }) {
//
////            doTask({
////                KLog.e("内部处理协程中函数获取结果异常。。。 $it")
////            }) {
////                KLog.e("doTask... 开始执行...   ${Thread.currentThread().name}")
////                val result = getM()
////                KLog.e("输出执行结果  $result  所在线程---》 ${Thread.currentThread().name} ")
////            }
//
//            doTask {
//                val kkk = resultAsync().await()
//                KLog.e("会执行吗？.... $kkk")
//            }
//        }

//        GlobalScope.launch {
//            testCosp()
//            KLog.e("看看结果")
//        }
//


        doTask {
            KLog.e("viewModel....执行的线程的上下文 线程 ${Thread.currentThread().name}")
            val kkk =  executiveRequest {
                testApi.getMonthDataAsync("")
            }
            KLog.e("会有结果执行？。。。 $kkk")
        }

        doTask {
            KLog.e("msg...开始执行任务了。。。")
            test()
            KLog.e("msg... 等等结果 ")
            KLog.e("msg... ${Thread.currentThread().name}")
        }

    }

    private suspend fun testCosp() {
        coroutineScope {
            KLog.e("coroutineScope ... 协程  ${Thread.currentThread().name}")
            test()
        }
    }


    //
    private suspend fun test() {
        val jmb = GlobalScope.async {
            //            KLog.e("async... ${Thread.currentThread().name}")
            KLog.e("我是返回值 。。。")
            testApi.getOneData(Calendar.getInstance().getTargetDate(), "深圳")
        }
        val result = jmb.await()
        KLog.e("test....  ${Thread.currentThread().name} ")
        KLog.e("result.... $result")
    }


    //      KLog.e("msg.... getM...  ${Thread.currentThread().name}")
    //            delay(1500)
    //            throw Exception("我是故意出异常")
    //            testApi.getOneData(Calendar.getInstance().getTargetDate(), "深圳")

    // 函数执行到此会被挂起，不向后执行
    private suspend fun getM() =
        withContext(Dispatchers.IO) {
             try {
                 testApi.getMonthDataAsync("")
             }catch (e:Exception){
                 throw e
             }
        }


}


