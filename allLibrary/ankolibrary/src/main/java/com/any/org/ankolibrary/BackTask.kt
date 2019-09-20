@file:Suppress("unused")

package com.any.org.ankolibrary

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong

/**
 *
 * @author any
 * @time 2019/07/25 11.03
 * @details
 */

// 处理UI 提切换线程
fun <T> T.runOnUiThread(f: T.() -> Unit) {
    if (Looper.getMainLooper() == Looper.myLooper()) f() else ContextHandler.handler.post { f() }
}

//提供 主线的 handler 对象
private object ContextHandler {
    val handler = Handler(Looper.getMainLooper())
}

//上下文弱引用
class AsyncContextTask<T>(val weak: WeakReference<T>)

// 函数传回值
fun <T> AsyncContextTask<T>.onComplete(f: (t: T?) -> Unit) {
    val ref = weak.get()
    if (Looper.getMainLooper() == Looper.myLooper()) f(ref) else ContextHandler.handler.post { f(ref) }
}

fun <T, R> AsyncContextTask<T>.onCompleteR(f: (t: T?) -> R) {
    val ref = weak.get()
    if (Looper.getMainLooper() == Looper.myLooper()) f(ref) else ContextHandler.handler.post { f(ref) }
}

// 函数调用
fun <T> T.doAsyncTask(task: AsyncContextTask<T>.() -> Unit): Future<Unit> {
    val context = AsyncContextTask(WeakReference(this))
    return BackgroundExecutor.submit {
        return@submit try {
            context.task()
        } catch (e: Throwable) {
            println(e)
            e.printStackTrace()
            throw e
        }
    }
}

// 函数调用
fun <T, R> T.doAsyncResultTask(task: AsyncContextTask<T>.() -> R): Future<R> {
    val context = AsyncContextTask(WeakReference(this))
    return BackgroundExecutor.submit {
        try {
            context.task()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}


fun <T, R> T.doAsyncScheduledDelayTask(
    delay: Long,
    unit: TimeUnit = TimeUnit.SECONDS,
    task: AsyncContextTask<T>.() -> R
): ScheduledFuture<R> {
    val context = AsyncContextTask(WeakReference(this))
    return BackgroundExecutor.scheduledFuture(delay, unit) {
        try {
            context.task()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}


fun <T> T.doAsyncDelayTask(
    delay: Long,
    unit: TimeUnit = TimeUnit.SECONDS,
    task: AsyncContextTask<*>.() -> Unit
): ScheduledFuture<*> {
    val context = AsyncContextTask(WeakReference(this))
    return BackgroundExecutor.schedule(delay, unit) {
        return@schedule try {
            context.task()
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }
}


fun <T> T.doAsyncScheduleAtFixedRateTask(
    delay: Long,
    period: Long,
    unit: TimeUnit = TimeUnit.SECONDS,
    task: AsyncContextTask<*>.() -> Unit
): ScheduledFuture<*> {
    val context = AsyncContextTask(WeakReference(this))
    return BackgroundExecutor.scheduleAtFixedRate(delay, period, unit) {
        return@scheduleAtFixedRate try {
            context.task()
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }
}

fun <T> T.doAsyncScheduleWithFixedDelayTask(
    delay: Long,
    period: Long,
    unit: TimeUnit = TimeUnit.SECONDS,
    task: AsyncContextTask<*>.() -> Unit
): ScheduledFuture<*> {
    val context = AsyncContextTask(WeakReference(this))
    return BackgroundExecutor.scheduleWithFixedDelay(delay, period, unit) {
        return@scheduleWithFixedDelay try {
            context.task()
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }
}


//  线程核心池大小
internal object BackgroundExecutor {
    private val executor: ScheduledExecutorService =
        Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors(),
            MyThreadFactory("BTE")
        )

    fun <T> submit(task: () -> T): Future<T> = executor.submit(task)

    fun <T> scheduledFuture(delay: Long, unit: TimeUnit, callable: () -> T): ScheduledFuture<T> =
        executor.schedule(callable, delay, unit)

    fun schedule(delay: Long, unit: TimeUnit, command: () -> Unit): ScheduledFuture<*> =
        executor.schedule(command, delay, unit)

    //固定频率：每间隔固定时间就执行一次任务。注重频率
    fun scheduleAtFixedRate(initialDelay: Long, period: Long, unit: TimeUnit, command: () -> Unit): ScheduledFuture<*> =
        executor.scheduleAtFixedRate(command, initialDelay, period, unit)

    //固定延迟：任务之间的时间间隔
    fun scheduleWithFixedDelay(
        initialDelay: Long,
        period: Long,
        unit: TimeUnit,
        command: () -> Unit
    ): ScheduledFuture<*> =
        executor.scheduleWithFixedDelay(command, initialDelay, period, unit)

}

internal class MyThreadFactory(private val prefix: String) : ThreadFactory {
    private val atom = AtomicLong()
    override fun newThread(r: Runnable): Thread {
        val value = atom.incrementAndGet()
        val name = StringBuffer().append(prefix).append("-").append(value).toString()
        val thread = Thread(r, name)
        thread.isDaemon = true
        return thread
    }
}

