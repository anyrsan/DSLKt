package com.any.org.onemodule.data.repository

import com.any.org.onemodule.data.local.LocalProvider
import com.any.org.onemodule.data.remote.NetApi
import io.reactivex.Observable
import io.reactivex.Single

/**
 *
 * @author any
 * @time 2019/11/11 18.03
 * @details
 */
class OneRepository(
    private val netProvider: NetApi,
    private val localProvider: LocalProvider
) {

    fun getOneData(date: String, address: String) =
        kotlin.run {
            netProvider.getOneData(date, address)
        }

    fun getMonthData(month: String) = netProvider.getMonthData(month)


    fun getListDate(month:Int) = Observable.create<List<String>> {
        it.onNext(localProvider.getListDate(month))
        it.onComplete()
    }


    //找不到就是-1
    fun getPosition(date:String,listData:List<String>) = kotlin.run{
        val position = listData.indexOf(date)
        Single.just(position)
    }

}