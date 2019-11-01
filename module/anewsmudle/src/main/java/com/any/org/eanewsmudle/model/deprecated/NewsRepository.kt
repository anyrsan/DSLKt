//package com.any.org.eanewsmudle.model.deprecated
//
//import com.any.org.eanewsmudle.model.bean.NewsItemModel
//import com.any.org.eanewsmudle.model.local.ANewsLocalProvider
//
///**
// *
// * @author any
// * @time 2019/10/28 10.48
// * @details
// */
//class NewsRepository(
//    private val netProvider: NewsNetProvider,
//    private val localProvider: ANewsLocalProvider
//) {
//
//    //从网络上获取数据
//    fun getList(
//        id: Int?
//    ) = netProvider.getList(id)
//
//
//    //test
//    fun getDetail(itemModel: NewsItemModel) = netProvider.getNewsDetail(itemModel)
//
//    //从网络加载
//    fun getThs(page:Int)= netProvider.getThsList(page)
//
//
//    //获取
//    fun getYLNews(ctime:Long?=null) = netProvider.getYlNews(ctime)
//
//}