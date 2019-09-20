package com.any.org.commonlibrary.log

import android.util.Log
import com.any.org.commonlibrary.BuildConfig
/**
 *
 * @author any
 * @time 2019/9/19 18.10
 * @details
 */
object KLog {

    private const val TAG = "msg"

    private const val DEBUG = BuildConfig.DEBUG_LOG

    fun e(message: String?, tag: String = TAG) {
        if(DEBUG)
        Log.e(tag, message)
    }

    fun w(message: String?, tag: String = TAG) {
        if(DEBUG)
        Log.w(tag, message)
    }

    fun d(message: String?, tag: String = TAG) {
        if(DEBUG)
        Log.d(tag, message)
    }


}