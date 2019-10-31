package com.any.org.eanewsmudle.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.any.org.ankolibrary.async
import com.any.org.commonlibrary.log.KLog
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.model.repository.NewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/10/25 19.46
 * @details
 */
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {


    val mList = AdapterDataObserver<NewsItemModel>()

    val empty = ObservableBoolean(false)

    val isLoading = ObservableBoolean(true)

    private var tempId: Int? = null

    fun getList(isRefresh: Boolean = true): Observable<NewsModel> = kotlin.run {
        if (isRefresh) {
            tempId = null
            isLoading.set(true)
        }
        newsRepository.getList(tempId).async(500).doOnNext {
            //刷新数据
            if (isRefresh) {
                //检查是否为空
                empty.set(it.result?.data?.feed?.list.isNullOrEmpty())
                //关闭刷新
                isLoading.set(false)
            }
            it?.result?.data?.feed?.list.let { data ->
                tempId = if (data?.isNotEmpty() == true) data.last().id else null
                mList.updateData(data, isRefresh)
            }
            KLog.e("ooommm  load=${isLoading.get()}")
        }.doOnError {

        }
    }


    override fun onCleared() {
        super.onCleared()
        mList.onCleared()
        KLog.e("viewmodel 失效")
    }

}