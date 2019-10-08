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

    fun e(tag: String?, msg: String) {
        val mTag = tag?.let { it } ?: TAG
        if (DEBUG) {
            Log.e(mTag, msg)
        }
    }

    fun w(tag: String?, msg: String) {
        val mTag = tag?.let { it } ?: TAG
        if (DEBUG) {
            Log.w(mTag, msg)
        }
    }

    fun e(msg: String) {
        e(TAG, msg)
    }

    fun w(msg: String) {
        w(TAG, msg)
    }



}