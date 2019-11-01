package com.any.org.eanewsmudle.viewmodel

import com.any.org.eanewsmudle.model.bean.YLNewsModel
import com.any.org.eanewsmudle.model.repository.ANewsRepository
import io.reactivex.Observable

/**
 *
 * @author any
 * @time 2019/11/1 14.27
 * @details
 */
class YlViewModel(private val rep: ANewsRepository) :
    BaseViewModel<YLNewsModel.DataBean, YLNewsModel>() {

    private val map by lazy {
        mutableMapOf(
            "cateid" to "1Q",
            "cre" to "tianyi",
            "mod" to "pcent",
            "merge" to "3",
            "statics" to "1",
            "length" to "15",
            "up" to "0",
            "down" to "0"
        )
    }

    override fun setParams(m: YLNewsModel, isRefresh: Boolean) {
        cTime = m.req_time
        cTime?.let {
            map["tm"] = "$it"
        }
    }

    private var cTime: Long? = null

    override fun clearParams() {
        cTime = null
        map.remove("tm")
    }

    override fun isEmpty(m: YLNewsModel): Boolean = m.data.isNullOrEmpty()

    override fun getDataList(m: YLNewsModel): List<YLNewsModel.DataBean>? = m.data

    override fun requestList(): Observable<YLNewsModel> = rep.ylList(map)
}