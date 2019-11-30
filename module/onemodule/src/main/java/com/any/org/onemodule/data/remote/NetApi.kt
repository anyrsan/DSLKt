package com.any.org.onemodule.data.remote

import com.any.org.onemodule.model.OneDataModel
import com.any.org.onemodule.model.OneMonthModel
import com.any.org.onemodule.net.ARTICLE_DETAIL_URL
import com.any.org.onemodule.net.ONE_DATA_URL
import com.any.org.onemodule.net.ONE_MONTH_URL
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author any
 * @time 2019/11/11 17.51
 * @details
 */

interface NetApi {


    @GET(ONE_DATA_URL)
    fun getOneData(@Path("date") date: String, @Path("address") address: String): Observable<OneDataModel>


    @GET(ARTICLE_DETAIL_URL)
    fun < T> getOneArticleDetail(@Path("type") cateEnType:String,@Path("itemId") itemId:String):Observable<T>

    @GET(ONE_MONTH_URL)
    fun getMonthData(@Path("month") month:String):Single<OneMonthModel>

}