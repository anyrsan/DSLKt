package com.any.org.commonlibrary.provide

/**
 *
 * @author any
 * @time 2019/9/18 19.42
 * @details
 */
class ShareDataProvide private constructor() {


    var hasLogin = false

    companion object {

        private val provide by lazy { ShareDataProvide() }

        fun getInstance(): ShareDataProvide = provide

    }


}