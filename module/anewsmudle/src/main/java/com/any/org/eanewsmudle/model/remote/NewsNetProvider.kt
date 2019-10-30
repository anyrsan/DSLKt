package com.any.org.eanewsmudle.model.remote

import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.net.url.SINA_NEWS
import com.any.org.commonlibrary.net.url.THS_NEWS
import com.any.org.dslnetlibrary.BaseNetProvider
import com.any.org.dslnetlibrary.HttpCallBack
import com.any.org.dslnetlibrary.httpCommon
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.model.bean.ThsItemModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 *
 * @author any
 * @time 2019/10/28 10.21
 * @details
 */
class NewsNetProvider(private val api: NewsApi) {

    fun getList(
        id: Int?
    ): Observable<NewsModel> = kotlin.run {
        val map = mutableMapOf(
            "page" to "1",
            "zhibo_id" to "152",
            "tag_id" to "0",
            "dire" to "f",
            "dpc" to "1",
            "page_size" to "10",
            "type" to "1"
        )
        id?.let {
            map["id"] = "$it"
        }
        KLog.e("map =   $map")
        api.getSiNaNews(SINA_NEWS, map)
    }


    //test
    fun getNewsDetail(item: NewsItemModel): Observable<NewsModel> =
        api.getNewsDetail(SINA_NEWS, item)


    //只需要传入此变量
    fun getThsList(page: Int = 1) = kotlin.run {
        val map = mutableMapOf("block" to "getnews", "page" to "$page")
        api.getThsNews(THS_NEWS, map)
    }


    interface NewsApi {

        @GET
        fun getSiNaNews(@Url url: String, @QueryMap queryMap: Map<String, String>): Observable<NewsModel>


        @GET  // test api
        fun getNewsDetail(@Url url: String, @Body item: NewsItemModel): Observable<NewsModel>

        @GET
        fun getThsNews(@Url url: String, @QueryMap queryMap: Map<String, String>): Observable<List<ThsItemModel>>
    }

}