package com.any.org.newsmodule.adapter

import com.any.org.newsmodule.R
import com.any.org.newsmodule.model.NewsItemModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @author any
 * @time 2019/10/19 16.46
 * @details  显示view
 */
class NewsListAdapter :BaseQuickAdapter<NewsItemModel,BaseViewHolder>(R.layout.news_item_adapter){

    override fun convert(helper: BaseViewHolder?, item: NewsItemModel?) {

        helper?.setText(R.id.timeLabTv,item?.create_time)

        helper?.setText(R.id.contentTv,item?.rich_text)

    }
}