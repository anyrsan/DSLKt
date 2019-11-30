package com.any.org.onemodule.data.remote

import com.any.org.onemodule.model.OneDataModel
import com.any.org.onemodule.model.OneMonthModel
import com.any.org.onemodule.net.ONE_DATA_URL
import com.any.org.onemodule.net.ONE_MONTH_URL
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author any
 * @time 2019/11/29 18.48
 * @details
 */
interface TestApi {

    @GET(ONE_DATA_URL)
    suspend fun getOneData(@Path("date") date: String, @Path("address") address: String): OneDataModel


    @GET(ONE_MONTH_URL)
    suspend fun getMonthDataAsync(@Path("month") month: String): OneMonthModel
}