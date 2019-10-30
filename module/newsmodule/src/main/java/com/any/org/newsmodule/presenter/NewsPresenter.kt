package com.any.org.newsmodule.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.net.url.SINA_NEWS
import com.any.org.commonlibrary.net.url.THS_NEWS
import com.any.org.dslnetlibrary.http
import com.any.org.dslnetlibrary.httpCommon
import com.any.org.newsmodule.model.NewsModel
import com.any.org.newsmodule.model.ThsItemModel
import com.any.org.newsmodule.service.NewsService
import com.trello.rxlifecycle3.LifecycleProvider

//别名回调
typealias onListResult = (it: NewsModel?, error: Boolean) -> Unit


typealias onThsListResult = (it: List<ThsItemModel>?) -> Unit

/**
 *
 * @author any
 * @time 2019/9/20 13.50
 * @details
 */
class NewsPresenter(private val mContext: Context, private val tOwner: LifecycleOwner) {

    private val serviceApi by lazy { NewsService.getNewsApi() }

    fun getList(
        id: Int?,
        onResult: onListResult
    ) {
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
        val requestData = serviceApi.getSiNaNews(SINA_NEWS, map)
        http<NewsModel> {
            data = requestData
            context = mContext
            owner = tOwner
            successCallBack {
                onResult.invoke(it, false)
            }
            errorCallBack { _, errorMessage ->
                onResult.invoke(null, true)
                CustomToast.showNetMsg(mContext, errorMessage)
            }

        }
    }


    //只需要传入此变量
    fun getThsList(page: Int = 1, onResult: onThsListResult) {
        val map = mutableMapOf("block" to "getnews", "page" to "$page")
        val requestData = serviceApi.getThsNews(THS_NEWS, map)
        httpCommon<List<ThsItemModel>> {
            data = requestData
            context = mContext
            owner = tOwner
            successCallBack {
                onResult.invoke(it)
            }
            errorCallBack { _, errorMessage ->
                onResult.invoke(null)
                CustomToast.showNetMsg(mContext, errorMessage)
            }

        }
    }


}