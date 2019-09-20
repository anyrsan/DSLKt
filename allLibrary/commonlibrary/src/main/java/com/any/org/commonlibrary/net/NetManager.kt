package com.any.org.commonlibrary.net

import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.net.url.BASE_URL
import com.any.org.dslnetlibrary.RetrofitBaseApi

/**
 *
 * @author any
 * @time 2019/9/20 11.38
 * @details
 */
object NetManager : RetrofitBaseApi(BASE_URL) {

    override fun rLog(message: String) {
        KLog.e(message,"NetM")
    }
}