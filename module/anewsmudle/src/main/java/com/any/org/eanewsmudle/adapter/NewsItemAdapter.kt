package com.any.org.eanewsmudle.adapter

import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.databinding.ANewsItemAdapterBinding
import com.any.org.eanewsmudle.model.bean.NewsItemModel

/**
 *
 * @author any
 * @time 2019/10/29 19.52
 * @details
 */
class NewsItemAdapter(list:AdapterDataObserver<NewsItemModel>):
    BaseItemAdapter<NewsItemModel,ANewsItemAdapterBinding>(R.layout.a_news_item_adapter,list)