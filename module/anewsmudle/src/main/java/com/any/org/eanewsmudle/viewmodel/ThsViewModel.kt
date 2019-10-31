package com.any.org.eanewsmudle.viewmodel

import com.any.org.eanewsmudle.model.bean.ThsItemModel
import com.any.org.eanewsmudle.model.repository.NewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/10/25 19.46
 * @details
 */
class ThsViewModel(private val newsRepository: NewsRepository) :
    BaseViewModel<ThsItemModel, List<ThsItemModel>>() {

    private var page: Int = 1

    override fun clearParams() {
        page = 1
    }


    override fun setParams(m: List<ThsItemModel>, isRefresh: Boolean) {
        page++
    }

    override fun isEmpty(m: List<ThsItemModel>): Boolean = m.isNullOrEmpty()

    override fun getDataList(m: List<ThsItemModel>): List<ThsItemModel>? = m

    override fun requestList(): Observable<List<ThsItemModel>> =
        newsRepository.getThs(page)

}