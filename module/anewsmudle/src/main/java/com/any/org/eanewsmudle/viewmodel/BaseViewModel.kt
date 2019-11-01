package com.any.org.eanewsmudle.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.any.org.ankolibrary.async
import com.any.org.commonlibrary.log.KLog
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/10/25 19.46
 * @details
 */
abstract class BaseViewModel<T, M> : ViewModel() {

    val mList = AdapterDataObserver<T>()

    val empty = ObservableBoolean(false)

    val isLoading = ObservableBoolean(true)

    val isGone = ObservableBoolean(false)

    abstract fun clearParams()

    abstract fun setParams(m: M,isRefresh:Boolean)

    abstract fun isEmpty(m: M): Boolean

    abstract fun getDataList(m: M): List<T>?

    abstract fun requestList(): Observable<M>

    fun freshData(isRefresh: Boolean = true): Observable<M> = kotlin.run {
        if (isRefresh) {
            clearParams()
            isLoading.set(true)
        }
        requestList().async(200).doOnNext {
            //刷新数据
            if (isRefresh) {
                //检查是否为空
                empty.set(isEmpty(it))
                //关闭刷新
                isLoading.set(false)
            }
            setParams(it,isRefresh)
            mList.updateData(getDataList(it), isRefresh)
            KLog.e("ooommm  load=${isLoading.get()}")
        }.doOnError {
            if (isRefresh) {
                isLoading.set(false)
                empty.set(true)
            }
            mList.updateData(null, isRefresh)
            KLog.e("出错了，哈哈")
        }
    }


    override fun onCleared() {
        super.onCleared()
        mList.onCleared()
        KLog.e("viewmodel 失效")
    }

}