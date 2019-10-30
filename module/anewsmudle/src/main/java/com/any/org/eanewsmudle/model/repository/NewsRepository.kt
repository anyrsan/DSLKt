package com.any.org.eanewsmudle.model.repository

import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.local.NewsLocalProvider
import com.any.org.eanewsmudle.model.remote.NewsNetProvider

/**
 *
 * @author any
 * @time 2019/10/28 10.48
 * @details
 */
class NewsRepository(
    private val netProvider: NewsNetProvider,
    private val localProvider: NewsLocalProvider
) {

    //从网络上获取数据
    fun getList(
        id: Int?
    ) = netProvider.getList(id)


    //test
    fun getDetail(itemModel: NewsItemModel) = netProvider.getNewsDetail(itemModel)

    //从网络加载
    fun getThs(page:Int)= netProvider.getThsList(page)

}