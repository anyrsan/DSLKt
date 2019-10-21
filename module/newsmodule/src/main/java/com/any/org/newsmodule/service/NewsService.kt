package com.any.org.newsmodule.service

import com.any.org.commonlibrary.net.NetManager
import com.any.org.newsmodule.model.NewsModel
import com.any.org.newsmodule.model.ThsItemModel
import io.reactivex.Observable
import retrofit2.http.GET
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

        @GET
        fun getThsNews(@Url url:String,@QueryMap queryMap: Map<String, String>):Observable<List<ThsItemModel>>

    }

}