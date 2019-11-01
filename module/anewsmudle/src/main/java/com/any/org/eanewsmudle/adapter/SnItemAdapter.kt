package com.any.org.eanewsmudle.adapter

import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.databinding.ANewsSnAdapterBinding
import com.any.org.eanewsmudle.model.bean.NewsItemModel

/**
 *
 * @author any
 * @time 2019/10/29 19.52
 * @details
 */
class SnItemAdapter(list:AdapterDataObserver<NewsItemModel>):
    BaseItemAdapter<NewsItemModel,ANewsSnAdapterBinding>(R.layout.a_news_sn_adapter,list)