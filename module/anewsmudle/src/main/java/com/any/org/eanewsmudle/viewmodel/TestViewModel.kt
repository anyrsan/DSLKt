package com.any.org.eanewsmudle.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.any.org.ankolibrary.bindLifecycle
import com.any.org.commonlibrary.CustomToast
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.repository.NewsRepository

/**
 *
 * @author any
 * @time 2019/10/29 17.05
 * @details
 */
class TestViewModel(
    private val newsItemModel: NewsItemModel,
    application: Application,
    private val newsRepository: NewsRepository
) :
    AndroidViewModel(application) {

    //获取详情，test 请求
    fun getDetails() = newsRepository.getDetail(newsItemModel)


}