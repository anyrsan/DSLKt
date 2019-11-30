package com.any.org.onemodule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.any.org.ankolibrary.handlerException
import com.any.org.commonlibrary.log.KLog
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author any
 * @time 2019/11/29 17.20
 * @details  协程处理 自动处理关闭 (如果是Fragment中用， 由于onCleared 只在activity中回调，所以不会取消协程任务)
 */
abstract class BaseCoroutineViewModel : ViewModel() {

    fun doTask(throwableHandler: CoroutineException? = null, task: suspend () -> Unit) {
        viewModelScope.safeLaunch(throwableHandler) {
            task.invoke()
        }
    }

    fun doDelayTask(
        throwableHandler: CoroutineException? = null,
        timeMillis: Long,
        task: suspend () -> Unit
    ) {
        viewModelScope.delayLaunch(timeMillis) {
            task()
        }
    }


    //处理结果
    suspend fun <T> executiveRequest(
        throwableHandler: CoroutineException = DefaultHandler,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ): T? =
        withContext(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                KLog.e("处理请求产生的异常")
                throwableHandler.invoke(e)
                null
            }
        }

}

//别名
typealias CoroutineException = (Throwable) -> Unit

//全局默认实现
object DefaultHandler : CoroutineException {
    override fun invoke(p1: Throwable) {
        p1.printStackTrace()
        KLog.e("全局处理 异常  $p1")
    }
}

//可以接收协程异常
private fun coroutineHandlerException(throwableHandler: CoroutineException?): CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
        throwableHandler?.invoke(throwable)
    }

//处理协程context
private fun coroutineScopeContext(throwableHandler: CoroutineException?): CoroutineContext =
    coroutineHandlerException(throwableHandler) + GlobalScope.coroutineContext

//扩展
fun CoroutineScope.safeLaunch(
    throwableHandler: CoroutineException? = DefaultHandler,
    block: suspend () -> Unit
): Job = launch(coroutineScopeContext(throwableHandler)) {
    block.invoke()
}

//扩展
fun CoroutineScope.delayLaunch(
    timeMillis: Long,
    throwableHandler: CoroutineException? = DefaultHandler,
    block: suspend () -> Unit
): Job = launch(coroutineScopeContext(throwableHandler)) {
    delay(timeMillis)
    block.invoke()
}
