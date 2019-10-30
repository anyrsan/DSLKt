package com.any.org.dslnetlibrary

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.HttpException


inline fun <reified T> getModel(jsonString: String?): T = GsonBuilder().create().fromJson(jsonString, T::class.java)

inline fun <reified T> getListModel(jsonArray: String?): List<T> =
    GsonBuilder().create().fromJson(jsonArray, object : TypeToken<List<T>>() {}.type)


/**
 *
 * @author any
 * @time 2019/06/29 10.06
 * @details call<ResponseBody> 请求
 */

class HttpCallManager<T : HttpBaseModel>(
    val call: Call<ResponseBody>?,
    val owner: LifecycleOwner?,
    val context: Context?,
    val successCallBack: (((t: T) -> Unit)?),
    val errorCallBack: (((errorCode: Int, errorMessage: String?) -> Unit)?)
) {

    inline fun <reified M : T> doTask() {
        val lifecycleBool = owner != null
        //定义错误处理
        val onError: (t: Throwable) -> Unit = {
            val responeThrowable = ExceptionHandle.handleException(it, context)
            errorCallBack?.invoke(responeThrowable.code, responeThrowable.msg)
        }
        // 定义正确处理
        val onNext: (t: M) -> Unit = {
            if (it.isSuccess()) {
                successCallBack?.invoke(it)
            } else {
                onError(ExceptionHandle.CustomException(it.getHttpMessage(), it.getHttpCode()))
            }
        }
        // 处理数据
        Observable.create<M> {
            //处理网络请求异常
            try {
                val responseBody = call?.execute()
                // 优先处理异常( code!=200)
                val code = responseBody?.code() ?: 0
                Log.e("msg", "code.... $code")
                if (code == 200) {
                    val jsonData = responseBody?.body()?.string()
                    val r: M = getModel(jsonData)
                    it.onNext(r)
                } else {
                    throw HttpException(responseBody)
                }
            } catch (e: Exception) {
                it.onError(e)
                e.printStackTrace()
            } finally {
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).apply {
            if (lifecycleBool) { // todo ??? 此处写法是否
                compose(AndroidLifecycle.createLifecycleProvider(owner!!).bindToLifecycle())
                    .subscribe(onNext, onError)
            } else {
                subscribe(onNext, onError)
            }
        }
    }
}

//处理调用
inline fun <reified T : HttpBaseModel> httpCall(block: HttpCallManagerBuild<T>.() -> Unit): Unit =
    HttpCallManagerBuild<T>().apply(block).build().doTask<T>()


class HttpCallManagerBuild<T : HttpBaseModel> {
    var call: Call<ResponseBody>? = null
    var owner: LifecycleOwner?=null
    var context: Context? = null
    private var successCallBack: (((t: T) -> Unit)?)= null
    private var errorCallBack: (((errorCode: Int, errorMessage: String?) -> Unit)?) = null

    fun successCallBack(successCallBack: ((t: T) -> Unit)) {
        this.successCallBack = successCallBack
    }

    fun errorCallBack(errorCallBack: ((errorCode: Int, errorMessage: String?) -> Unit)) {
        this.errorCallBack = errorCallBack
    }

    fun build(): HttpCallManager<T> = HttpCallManager(call, owner, context, successCallBack, errorCallBack)

}