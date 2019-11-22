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

const val ONE_DATA_URL = "channel/one/{date}/{address}"

const val ARTICLE_DETAIL_URL="{type}/detail/{itemId}?channel=cool"

object OneNetManager : RetrofitBaseApi(BASE_URL) {
    override fun rLog(message: String) {
        KLog.e("OneNet", message)
    }
}