package com.any.org.newsmodule.adapter

import com.any.org.commonlibrary.utils.DateUtils
import com.any.org.newsmodule.R
import com.any.org.newsmodule.model.NewsItemModel
import com.any.org.newsmodule.model.ThsItemModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 *
 * @author any
 * @time 2019/10/19 16.46
 * @details  显示view
 */
class NewsThsAdapter :BaseQuickAdapter<ThsItemModel,BaseViewHolder>(R.layout.news_item_adapter){

    override fun convert(helper: BaseViewHolder?, item: ThsItemModel?) {

        //处理
        item?.ctime?.let {
            val cTime = DateUtils.formatDateNew(it,"HH:mm",false)
            helper?.setText(R.id.timeLabTv,cTime)
        }
        helper?.setText(R.id.contentTv,item?.content)

    }
}