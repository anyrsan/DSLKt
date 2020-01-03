package com.any.org.onemodule

import com.any.org.ankolibrary.doAsyncTask
import com.any.org.onemodule.extend.DateEx
import com.any.org.onemodule.extend.getTargetDate
import io.reactivex.Observable
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
    fun testFor() {

//        val a = "25.05".toBigDecimal()*(12.toBigDecimal())
//
//        val b = "22.11".toDouble() * 12
//
//
//        val m  = "11.22".toFloat()*1.5f
//        println(m)
//        val n = m.toDouble()
//        println(n)


//        for (m in 0 until 1){
//            run breaking@{
//                (0..10).forEach {
//                    if(it == 5){
//                        return@breaking
//                    }
//                    println(it)
//                }
//            }
//            println(".....ddddd")
//        }


        //  180

        val priceT = 5.20 * 2100

        val result = 0.186 * 2100

        println(priceT)

        println(result)

        val mm = 37.8 * 3
        println(mm.times(1.0))

    }


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


    @Test
    fun testListDate() {

        DateEx.getListDate(-2)

    }


    @Test
    fun testListToMap() {

        val list = mutableListOf<Model>()

        for (i in 0..15) {
            val itemId = if (i % 4 == 0) 555 else i
            val name = "$i 个"
            list.add(Model(itemId, name))
        }
        list.forEach {
            println("item... $it")
        }


        val newList = list.groupBy {
            it.itemId
        }

        val kkkList = newList.values.first()

        val listMap = kkkList.map {
            it.itemId to it
        }.toMap()

        listMap.keys.forEach {
            println(" key = $it...... value = ${listMap[it]}")
        }


//        val map = list.map {
//            it.itemId to it
//        }.toMap()
//
//        map.keys.forEach {
//            println("key.... $it")
//        }
//
//
//        map.values.forEach {
//            println("value...  $it")
//        }

    }


}

data class Model(val itemId: Int, val name: String)
