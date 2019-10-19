package com.any.org.newsmodule.service

import com.any.org.commonlibrary.net.NetManager
import com.any.org.dslnetlibrary.HttpBaseModel
import com.any.org.newsmodule.model.NewsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * news 数据
 */
object NewsService {


    fun getNewsApi():NewsApi = NetManager.create(NewsApi::class.java)


    interface NewsApi{

        @GET
        fun getSiNaNews(@Url url:String,@QueryMap queryMap:Map<String,String>):Observable<NewsModel>

    }

}