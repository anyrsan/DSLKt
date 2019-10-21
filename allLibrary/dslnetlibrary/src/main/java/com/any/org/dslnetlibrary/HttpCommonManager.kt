package com.any.org.dslnetlibrary

import android.content.Context
import android.util.Log
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author any
 * @time 2019/06/27 15.03
 * @details  改变HttpBaseModel 申明结构
 */
class HttpCommonManager<T>(
    private val data: Observable<T>?,
    private val lifecycleProvider: LifecycleProvider<*>?,
    private val context: Context?,
    private val successCallBack: ((t: T) -> Unit)?,
    private val errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)?,
    private val onDoNext: ((t: T) -> Unit)?
) {


    //网络请求
    fun doTask() {
        val lifecycleBool = lifecycleProvider != null
        //定义错误处理
        val onError: (t: Throwable) -> Unit = {
            it.printStackTrace()
            Log.e("error","message:${it.localizedMessage}")
            val responeThrowable = ExceptionHandle.handleException(it, context)
            errorCallBack?.invoke(responeThrowable.code, responeThrowable.msg)
        }
        // 定义正确处理
        val onNext: (t: T) -> Unit = {
            successCallBack?.invoke(it)
        }

        val doNextBool = onDoNext != null

        data?.apply {
            subscribeOn(Schedulers.io()).apply {
                if (doNextBool) {
                    doOnNext(onDoNext)
                        .observeOn(AndroidSchedulers.mainThread()).apply {
                            if (lifecycleBool) {
                                compose(lifecycleProvider!!.bindToLifecycle())
                                    .subscribe(onNext, onError)
                            } else {
                                subscribe(onNext, onError)
                            }
                        }
                } else {
                    observeOn(AndroidSchedulers.mainThread()).apply {
                        if (lifecycleBool) {
                            compose(lifecycleProvider!!.bindToLifecycle())
                                .subscribe(onNext, onError)
                        } else {
                            subscribe(onNext, onError)
                        }
                    }
                }
            }

        }
    }
}


fun <T > httpCommon(block: HttpCommonManagerBuilder<T>.() -> Unit) {
    val httpManager = HttpCommonManagerBuilder<T>().apply(block).build()
    httpManager.doTask()
}

//增加类
fun <T > httpCommonWrap(block: HttpCommonManagerBuilder<T>.() -> Unit) {
    val httpManager = HttpCommonManagerBuilder<T>().apply(block).build()
    httpManager.doTask()
}



open class HttpCommonManagerBuilder<T> {

    var data: Observable<T>? = null

    var lifecycleProvider: LifecycleProvider<*>? = null

    var context: Context? = null

    protected var successCallBack: ((t: T) -> Unit)? = null

    protected var errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)? = null


    fun successCallBack(successCallBack: ((t: T) -> Unit)) {
        this.successCallBack = successCallBack
    }

    fun errorCallBack(errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)) {
        this.errorCallBack = errorCallBack
    }

    open fun build(): HttpCommonManager<T> =
        HttpCommonManager(data, lifecycleProvider, context, successCallBack, errorCallBack, null)

}


class HttpCommonManagerBuilderWrap<T> : HttpCommonManagerBuilder<T>() {

    private var onDoNext: ((t: T) -> Unit)? = null

    fun onDoNext(onDoNext: ((t: T) -> Unit)) {
        this.onDoNext = onDoNext
    }

    override fun build(): HttpCommonManager<T> =
        HttpCommonManager(data, lifecycleProvider, context, successCallBack, errorCallBack, onDoNext)

}




