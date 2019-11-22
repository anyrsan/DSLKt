package com.any.org.rxviewmodel.viewmodel

import androidx.lifecycle.MutableLiveData
import com.any.org.ankolibrary.async
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.log.KLog
import com.any.org.rxviewmodel.model.repository.NewRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author any
 * @time 2019/11/6 19.48
 * @details
 */
class DataViewModel(private val rep: NewRepository) : RxBaseViewModel() {

    val mLd = MutableLiveData<String>()

    val mOther = MutableLiveData<String>()

    val close = MutableLiveData<Boolean>()

    fun getData() {
        launch {
            KLog.e("开始执行数据加载")
            rep.getData().async().subscribe({
                KLog.e("会获取到数据是吧  $it")
                mLd.set(it)
            }, {
                KLog.e("有错误来了哦  $it")
            }, {
                KLog.e("事件结束了")
                close.set(true)
            })
        }
    }


    fun getOtherData() {
        launch {
            rep.getData().async().subscribe({
                mOther.set("我是  $it")
            }, {

            },{
                close.set(true)
            })
        }


        launch {
//            rep.test().async(1000).doAfterSuccess{
//                KLog.e("single","single 事件的值  $it")
//            }.subscribe {s:String->
//
//            }

            rep.test().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doAfterSuccess {
                KLog.e("single","single 事件的值  $it")
            }.doOnError {
                KLog.e("有错误来了哦  $it")
            }.subscribe { s:String->

            }
        }
    }


    fun testRxLifecycle(): Observable<String> = rep.getData().async().doAfterNext {
        KLog.e("会获取到数据是吧  $it")
        mLd.set(it)
    }.doOnError {
        KLog.e("有错误来了哦  $it")
    }

    //滴滴方案
    fun testAuto() =
        rep.test().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doAfterSuccess {
            mLd.set(it)
            close.set(true)
        }.doOnError {
            KLog.e("有错误来了哦  $it")
        }

}