package com.any.org.onemodule.data.repository

import com.any.org.onemodule.data.local.LocalProvider
import com.any.org.onemodule.data.remote.NetApi

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

}