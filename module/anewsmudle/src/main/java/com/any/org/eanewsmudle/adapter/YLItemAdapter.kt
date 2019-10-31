package com.any.org.eanewsmudle.adapter

import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.databinding.ANewsYlAdapterBinding
import com.any.org.eanewsmudle.model.bean.YLNewsModel

/**
 *
 * @author any
 * @time 2019/10/30 16.10
 * @details
 */
class YLItemAdapter(list: AdapterDataObserver<YLNewsModel.DataBean>):
BaseItemAdapter<YLNewsModel.DataBean, ANewsYlAdapterBinding>(R.layout.a_news_yl_adapter,list)