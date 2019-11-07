package com.any.org.rxviewmodel.model.local

import android.os.SystemClock
import com.any.org.commonlibrary.log.KLog
import io.reactivex.Observable
import io.reactivex.Single

/**
 *
 * @author any
 * @time 2019/11/6 16.55
 * @details
 */
class LocalDataProvider {


    fun testData(): Observable<String> =
        Observable.create<String> {
            SystemClock.sleep(3000)
            for (index in 0..100) {
                it.onNext("生产 $index")
                SystemClock.sleep(100)
                KLog.e("我是事件源，数据还在产生中  $index")
            }
            it.onNext("我是在3s后产生的数据")

            KLog.e("完成事件了。")
            //通知完成
            it.onComplete()
        }


    // error use , but has leaks
    fun test():Single<String> = Single.create{
        SystemClock.sleep(5000)
        for (index in 0..100) {
            it.onSuccess("生产 $index")
            SystemClock.sleep(100)
            KLog.e("single  $index")
        }
        it.onSuccess("single最后事件")
    }
}