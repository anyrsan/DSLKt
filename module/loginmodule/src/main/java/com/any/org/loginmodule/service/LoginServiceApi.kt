package com.any.org.loginmodule.service

import com.any.org.commonlibrary.net.NetManager
import com.any.org.commonlibrary.net.url.LOGIN_URL
import com.any.org.dslnetlibrary.HttpBaseModel
import io.reactivex.Observable
import retrofit2.http.*

/**
 *
 * @author any
 * @time 2019/9/20 11.56
 * @details
 */
object LoginServiceApi {


    fun getServiceApi(): ServiceApi = NetManager.create(ServiceApi::class.java)


    interface ServiceApi {


        @FormUrlEncoded
        @POST
        fun doLogin(@Url url: String, @FieldMap map: Map<String, String>): Observable<HttpBaseModel>


        @FormUrlEncoded
        @POST(LOGIN_URL)
        fun doLogin(@FieldMap map: Map<String, String>): Observable<HttpBaseModel>


        @GET("xxx")
        fun getUserInfo(@Header("token") token:String):Observable<HttpBaseModel>

        @GET
        fun getUserInfo(@Header("token")token:String,@Url url:String):Observable<HttpBaseModel>
    }


}