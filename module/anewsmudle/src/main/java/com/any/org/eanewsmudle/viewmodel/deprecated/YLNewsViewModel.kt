//package com.any.org.eanewsmudle.viewmodel.deprecated
//
//import com.any.org.eanewsmudle.model.bean.YLNewsModel
//import com.any.org.eanewsmudle.model.repository.ANewsRepository
//import com.any.org.eanewsmudle.viewmodel.BaseViewModel
//import io.reactivex.Observable
//
///**
// *
// * @author any
// * @time 2019/10/31 14.24
// * @details
// */
//class YLNewsViewModel(private val newsRepository: ANewsRepository) :
//    BaseViewModel<YLNewsModel.DataBean, YLNewsModel>() {
//
//    override fun setParams(m: YLNewsModel, isRefresh: Boolean) {
//        cTime = m.req_time
//    }
//
//    private var cTime: Long? = null
//
//    override fun clearParams() {
//        cTime = null
//    }
//
//    override fun isEmpty(m: YLNewsModel): Boolean = m.data.isNullOrEmpty()
//
//    override fun getDataList(m: YLNewsModel): List<YLNewsModel.DataBean>? = m.data
//
//    override fun requestList(): Observable<YLNewsModel> = newsRepository.getYLNews(cTime)
//}