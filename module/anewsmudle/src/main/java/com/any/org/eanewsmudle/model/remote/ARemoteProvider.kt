package com.any.org.eanewsmudle.model.remote

import com.any.org.commonlibrary.net.url.SINA_NEWS
import com.any.org.commonlibrary.net.url.SINA_YL
import com.any.org.commonlibrary.net.url.THS_NEWS
import com.any.org.commonlibrary.net.url.业务_URL
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.model.bean.ThsItemModel
import com.any.org.eanewsmudle.model.bean.YLNewsModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 *
 * @author any
 * @time 2019/10/28 10.21
 * @details 重构后写法
 */
class ARemoteProvider(private val api: NewsApi) {


    fun sinaList(map:Map<String,String>) = api.getSiNaNews(SINA_NEWS,map)

    fun thsList(map:Map<String,String>) = api.getThsNews(THS_NEWS,map)

    fun ylList(map:Map<String,String>) = api.getYLNews(SINA_YL,map)


    interface NewsApi {

        @GET
        fun getSiNaNews(@Url url: String, @QueryMap queryMap: Map<String, String>): Observable<NewsModel>


        @GET
        fun getThsNews(@Url url: String, @QueryMap queryMap: Map<String, String>): Observable<List<ThsItemModel>>


        @GET
        fun getYLNews(@Url url: String, @QueryMap queryMap: Map<String, String>): Observable<YLNewsModel>


        //#######################################  以下是测试api #######################################
        @GET  // test api
        fun getNewsDetail(@Url url: String, @Body item: NewsItemModel): Observable<NewsModel>

        @GET(业务_URL)
        fun getstApi(@QueryMap queryMap: Map<String, String>):Observable<NewsModel>
    }

}