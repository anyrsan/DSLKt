package com.any.org.onemodule.net

import com.any.org.commonlibrary.log.KLog
import com.any.org.dslnetlibrary.RetrofitBaseApi

/**
 *
 * @author any
 * @time 2019/11/11 17.53
 * @details
 */

const val BASE_URL = "http://v3.wufazhuce.com:8000/api/"
//oneMainURL
const val ONE_DATA_URL = "channel/one/{date}/{address}"
//详情
const val ARTICLE_DETAIL_URL = "{type}/detail/{itemId}?channel=cool"
// 月分
const val ONE_MONTH_URL = "feeds/list/{month}?channel=cool"
//评论 支持分页
const val COMMENT_DATA_URL = "comment/praiseandtime/{type}/{contentId}/{commentId}?channel=cool"

//指定月分连载
const val SERIAL_MONTH_URL ="serialcontent/bymonth/{yearMonth}"

object OneNetManager : RetrofitBaseApi(BASE_URL) {
    override fun rLog(message: String) {
        KLog.e("OneNet", message)
    }
}