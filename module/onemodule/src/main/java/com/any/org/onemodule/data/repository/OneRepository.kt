package com.any.org.onemodule.data.repository

import com.any.org.onemodule.data.local.LocalProvider
import com.any.org.onemodule.data.remote.NetProvider

/**
 *
 * @author any
 * @time 2019/11/11 18.03
 * @details
 */
class OneRepository(
    private val netProvider: NetProvider,
    private val localProvider: LocalProvider
) {


    fun getOneData(date: String, address: String) =
        kotlin.run {
            netProvider.getOneData(date, address)
        }

    fun getMonthData(month: String) = netProvider.getMonthData(month)

    fun <T> getOneArticleDetail(cateEnType: String, itemId: String) =
        netProvider.getOneArticleDetail<T>(cateEnType, itemId)
}