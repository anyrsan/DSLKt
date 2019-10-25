package com.any.org.dslnetlibrary

/**
 *
 * @author any
 * @time 2019/10/23 19.10
 * @details
 */
interface HttpCallBack<T> {
    fun onResult(t: T?, message: String?, errorCode: Int)
}