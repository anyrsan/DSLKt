package com.any.org.onemodule

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.any.org.ankolibrary.doAsyncTask
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.extend.getTargetDate
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun testDate() {

        println(Calendar.getInstance().getTargetDate(1))
        println(Calendar.getInstance().getTargetDate(1, -1))
        println(Calendar.getInstance().getTargetDate(0, 1))
        println(Calendar.getInstance().getTargetDate())

    }


    @Test
    fun formatDate() {

        val date = "2019-11-25 06:00:00"
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val result = sdf.parse(date)
        println(result)

        val a = sdf.format(result)
        println(a)
    }


    fun oneObservable(): Observable<Int> = kotlin.run {
        Observable.create {
            doAsyncTask {
                Thread.sleep(2000)
//                it.onNext(0)
                it.onError(Exception("故意产生异常"))
//                it.onComplete()
            }
        }
    }


    fun twoObservable(): Observable<Int> = kotlin.run {
        Observable.create {
            doAsyncTask {
                Thread.sleep(1000)
                it.onNext(1)
                it.onComplete()
            }
        }

    }

    @Test
    fun testObservable() {

        // 按顺序执行，一次一个
        Observable.concat(oneObservable(), twoObservable()).subscribeOn(Schedulers.io()).subscribe({
            println("接收到了数据  $it")
        }, {
            println("产生异常了")
        }, {

        })

        // 只执行其中一个，不按顺序执行
//        Observable.ambArray(oneObservable(),twoObservable()).subscribeOn(Schedulers.io()).subscribe {
//            println("输出 结果  $it")
//        }

//        Observable.zip(
//            oneObservable(),
//            twoObservable(),
//            BiFunction<Int, Int, String> { t1, t2 -> "$t1 + $t2" }
//        ).subscribe {
//
//            println("输出结果  $it")
//        }


        Thread.sleep(7000)
        println("执行完成")

    }


}