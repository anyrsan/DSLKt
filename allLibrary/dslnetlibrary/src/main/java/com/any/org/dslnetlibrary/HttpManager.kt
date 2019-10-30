package com.any.org.dslnetlibrary

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author any
 * @time 2019/06/27 15.03
 * @details  改变HttpBaseModel 申明结构
 */
class HttpManager<T : HttpBaseModel>(
    private val data: Observable<T>?,
    private val owner: LifecycleOwner?,
    private val context: Context?,
    private val successCallBack: ((t: T) -> Unit)?,
    private val errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)?,
    private val onDoNext: ((t: T) -> Unit)?
) {


    //网络请求
    fun doTask() {
        val lifecycleBool = owner != null
        //定义错误处理
        val onError: (t: Throwable) -> Unit = {
            it.printStackTrace()
            Log.e("error","message:${it.localizedMessage}")
            val responeThrowable = ExceptionHandle.handleException(it, context)
            errorCallBack?.invoke(responeThrowable.code, responeThrowable.msg)
        }
        // 定义正确处理
        val onNext: (t: T) -> Unit = {
            if (it.isSuccess()) {
                successCallBack?.invoke(it)
            } else {
                onError(ExceptionHandle.CustomException(it.getHttpMessage(), it.getHttpCode()))
            }
        }

        val doNextBool = onDoNext != null

        data?.apply {
            subscribeOn(Schedulers.io()).apply {
                if (doNextBool) {
                    doOnNext(onDoNext)
                        .observeOn(AndroidSchedulers.mainThread()).apply {
                            if (lifecycleBool) {
                                compose(AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle())
                                    .subscribe(onNext, onError)
                            } else {
                                subscribe(onNext, onError)
                            }
                        }
                } else {
                    observeOn(AndroidSchedulers.mainThread()).apply {
                        if (lifecycleBool) {
                            compose(AndroidLifecycle.createLifecycleProvider(owner).bindToLifecycle())
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


fun <T : HttpBaseModel> http(block: HttpManagerBuilder<T>.() -> Unit) {
    val httpManager = HttpManagerBuilder<T>().apply(block).build()
    httpManager.doTask()
}

//增加类
fun <T : HttpBaseModel> httpWrap(block: HttpManagerBuilderWrap<T>.() -> Unit) {
    val httpManager = HttpManagerBuilderWrap<T>().apply(block).build()
    httpManager.doTask()
}



open class HttpManagerBuilder<T : HttpBaseModel> {

    var data: Observable<T>? = null

    var owner: LifecycleOwner?=null

    var context: Context? = null

    protected var successCallBack: ((t: T) -> Unit)? = null

    protected var errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)? = null


    fun successCallBack(successCallBack: ((t: T) -> Unit)) {
        this.successCallBack = successCallBack
    }

    fun errorCallBack(errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)) {
        this.errorCallBack = errorCallBack
    }

    open fun build(): HttpManager<T> =
        HttpManager(data, owner, context, successCallBack, errorCallBack, null)

}


class HttpManagerBuilderWrap<T : HttpBaseModel> : HttpManagerBuilder<T>() {

    private var onDoNext: ((t: T) -> Unit)? = null

    fun onDoNext(onDoNext: ((t: T) -> Unit)) {
        this.onDoNext = onDoNext
    }

    override fun build(): HttpManager<T> =
        HttpManager(data, owner, context, successCallBack, errorCallBack, onDoNext)

}




