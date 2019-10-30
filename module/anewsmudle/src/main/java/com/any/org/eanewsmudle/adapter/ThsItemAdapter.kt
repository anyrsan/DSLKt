package com.any.org.eanewsmudle.adapter

import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.any.org.eanewsmudle.databinding.ANewsThsAdapterBinding
import com.any.org.eanewsmudle.model.bean.ThsItemModel

/**
 *
 * @author any
 * @time 2019/10/30 16.10
 * @details
 */
class ThsItemAdapter(list: AdapterDataObserver<ThsItemModel>):
BaseItemAdapter<ThsItemModel, ANewsThsAdapterBinding>(R.layout.a_news_ths_adapter,list)