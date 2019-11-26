package com.any.org.eanewsmudle.viewmodel

import com.any.org.eanewsmudle.model.bean.ThsItemModel
import com.any.org.eanewsmudle.model.repository.ANewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/11/1 14.15
 * @details
 */
class  ThsViewModel(private val rep: ANewsRepository) :
    BaseViewModel<ThsItemModel, List<ThsItemModel>>() {


    private var page = 1

    private val map by lazy { mapOf("block" to "getnews", "page" to "$page") }

    override fun clearParams() {
        page = 1
    }

    override fun setParams(m: List<ThsItemModel>, isRefresh: Boolean) {
        page++
    }

    override fun isEmpty(m: List<ThsItemModel>): Boolean = m.isNullOrEmpty()

    override fun getDataList(m: List<ThsItemModel>): List<ThsItemModel>? = m

    override fun requestList(): Observable<List<ThsItemModel>> = rep.thsList(map)
}