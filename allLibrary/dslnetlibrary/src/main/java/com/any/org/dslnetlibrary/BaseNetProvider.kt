package com.any.org.dslnetlibrary

import android.content.Context
import com.any.org.dslnetlibrary.HttpBaseModel
import com.any.org.dslnetlibrary.http
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/10/23 17.49
 * @details 处理请求
 */
object BaseNetProvider {


    /***
     * 处理所有请求 明确为 HttpBaseModel子类
     */
    fun <T : HttpBaseModel> httpData(
        requestData: Observable<T>,
        mContext: Context?,
        lProvider: LifecycleProvider<*>?,
        callBack: HttpCallBack<T>
    ) {
        http<T> {
            data = requestData
            context=mContext
            lifecycleProvider= lProvider
            successCallBack {
                callBack.onResult(it,null,0)
            }
            errorCallBack { errorCode, errorMessage ->
                callBack.onResult(null,errorMessage,errorCode)
            }
        }
    }


    /**
     * 普通类型，不具备 HttpBaseModel 规范
     */
    inline fun <reified T> httpCommonData(requestData: Observable<T>,
                                          mContext: Context?,
                                          lProvider: LifecycleProvider<*>?,
                                          callBack: HttpCallBack<T>){
        httpCommon<T> {
            data = requestData
            context=mContext
            lifecycleProvider= lProvider
            successCallBack {
                callBack.onResult(it,null,0)
            }
            errorCallBack { errorCode, errorMessage ->
                callBack.onResult(null,errorMessage,errorCode)
            }
        }
    }



}