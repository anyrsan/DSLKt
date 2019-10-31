package com.any.org.eanewsmudle.viewmodel

import androidx.databinding.ObservableBoolean
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
class NewsViewModel(private val newsRepository: NewsRepository) :
    BaseViewModel<NewsItemModel, NewsModel>() {



    private var tempId: Int? = null

    override fun clearParams() {
        tempId = null
    }

    override fun setParams(m: NewsModel, isRefresh: Boolean) {
        tempId = m.result?.data?.feed?.list?.last()?.id
    }

    override fun isEmpty(m: NewsModel): Boolean = m.result?.data?.feed?.list.isNullOrEmpty()

    override fun getDataList(m: NewsModel): List<NewsItemModel>? = m.result?.data?.feed?.list

    override fun requestList(): Observable<NewsModel> = newsRepository.getList(tempId)


}