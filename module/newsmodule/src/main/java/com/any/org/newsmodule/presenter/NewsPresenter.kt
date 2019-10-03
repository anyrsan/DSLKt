package com.any.org.newsmodule.presenter

import android.content.Context
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.net.url.LIST_URL
import com.any.org.dslnetlibrary.HttpBaseModel
import com.any.org.dslnetlibrary.http
import com.any.org.newsmodule.service.NewsService
import com.trello.rxlifecycle3.LifecycleProvider

/**
 *
 * @author any
 * @time 2019/9/20 13.50
 * @details
 */
class NewsPresenter (private val mContext: Context, private  val lProvider: LifecycleProvider<*>){

    private val serviceApi by lazy { NewsService.getNewsApi() }

    fun getList(
        token:String,
        onResult: (it: HttpBaseModel?, error: Boolean) -> Unit
    ) {
        val requestData = serviceApi.getNews(token, LIST_URL)
        http<HttpBaseModel> {
            data = requestData
            context = mContext
            lifecycleProvider = lProvider
            successCallBack {
                onResult.invoke(it, false)
            }
            errorCallBack { errorCode, errorMessage ->
                onResult.invoke(null, true)
                CustomToast.showNetMsg(mContext, errorMessage)
            }

        }


    }


}