package com.any.org.eanewsmudle.viewmodel

import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.model.repository.ANewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/11/1 14.20
 * @details
 */
class SnViewModel(private val rep: ANewsRepository) : BaseViewModel<NewsItemModel, NewsModel>() {

    private var tempId: Int? = null

    private val map by lazy {
        mutableMapOf(
            "page" to "1",
            "zhibo_id" to "152",
            "tag_id" to "0",
            "dire" to "f",
            "dpc" to "1",
            "page_size" to "10",
            "type" to "1"
        )
    }

    //下拉刷新时，一定会调用到这里
    override fun clearParams() {
        tempId = null
        map.remove("id")
    }

    override fun setParams(m: NewsModel, isRefresh: Boolean) {
        tempId = m.result?.data?.feed?.list?.last()?.id
        tempId?.let {
            map["id"] = "$it"
        }
    }

    override fun isEmpty(m: NewsModel): Boolean = m.result?.data?.feed?.list.isNullOrEmpty()

    override fun getDataList(m: NewsModel): List<NewsItemModel>? = m.result?.data?.feed?.list

    override fun requestList(): Observable<NewsModel> =  rep.sinaList(map)
}