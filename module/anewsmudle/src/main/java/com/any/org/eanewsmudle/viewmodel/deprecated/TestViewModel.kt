//package com.any.org.eanewsmudle.viewmodel.deprecated
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import com.any.org.eanewsmudle.model.bean.NewsItemModel
//import com.any.org.eanewsmudle.model.deprecated.NewsRepository
//
///**
// *
// * @author any
// * @time 2019/10/29 17.05
// * @details
// */
//class TestViewModel(
//    application: Application,
//    private val newsItemModel: NewsItemModel,
//    private val newsRepository: NewsRepository
//) :
//    AndroidViewModel(application) {
//
//    //获取详情，test 请求
//    fun getDetails() = newsRepository.getDetail(newsItemModel)
//
//
//}