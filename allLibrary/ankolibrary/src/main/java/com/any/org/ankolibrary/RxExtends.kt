@file:Suppress("unused")

package com.any.org.ankolibrary

import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *
 * @author any
 * @time 2019/10/28 15.29
 * @details  rx扩展库
 */


fun <T> Observable<T>.bindLifecycle(owner: LifecycleOwner): Observable<T> = this.compose(
    AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle()
)

//异步请求
fun <T> Observable<T>.async(withDelay: Long = 0): Observable<T> =
    this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(
        AndroidSchedulers.mainThread()
    )

//异步请求
fun <T> Observable<T>.async(): Observable<T> =
    this.subscribeOn(Schedulers.io()).observeOn(
        AndroidSchedulers.mainThread()
    )


fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
    this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(
        AndroidSchedulers.mainThread()
    )

fun <T> Single<T>.async(): Single<T> = this.subscribeOn(Schedulers.io()).observeOn(
    AndroidSchedulers.mainThread()
)

//订阅
fun <T> Observable<T>.subResult(onResult: ((t: T?, message: String?, errorCode: Int) -> Unit)? = null) {
    // 定义正确处理
    val onNext: (t: T) -> Unit = {
        onResult?.invoke(it, null, 0)
    }
    //处理错误
    val onError: (t: Throwable) -> Unit = {
        Log.e("error", "message: onError $it")
        it.printStackTrace()
        val customException = handlerException(it)
        Log.e("error", "message:${customException.netMessage}")
        onResult?.invoke(null, customException.message, customException.errorCode)
    }
    this.subscribe(onNext, onError)
}


fun <T> Observable<T>.subOnlyCode(onResult: ((errorCode: Int) -> Unit)? = null) {
    // 定义正确处理
    val onNext: (t: T) -> Unit = {
        onResult?.invoke(0)
    }
    //处理错误
    val onError: (t: Throwable) -> Unit = {
        Log.e("error", "message: onError $it")
        it.printStackTrace()
        val customException = handlerException(it)
        Log.e("error", "message:${customException.netMessage}")
        onResult?.invoke(customException.errorCode)
    }
    this.subscribe(onNext, onError)
}


//如果不是主线程，就切换到主线程，否则直接 setValue
fun <T> MutableLiveData<T>.set(t: T?) = kotlin.run {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        this.value = t
    } else {
        this.postValue(t)
    }

}

fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.initData(t: T?) = MutableLiveData<T>().apply {
    postValue(t)
}

fun <T> AppCompatActivity.get(t: MutableLiveData<T>, value: (t: T?) -> Unit) =
    t.observe(this, Observer {
        value.invoke(it)
    })

fun <T> Fragment.get(t: MutableLiveData<T>, value: (t: T?) -> Unit) = t.observe(this, Observer {
    value.invoke(it)
})