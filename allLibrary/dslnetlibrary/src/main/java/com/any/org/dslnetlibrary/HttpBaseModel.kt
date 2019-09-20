package com.any.org.dslnetlibrary

/**
 *
 * @author any
 * @time 2019/8/19 09.42
 * @details HttpBaseModel 基础数据类，主要是处理
 */
open class HttpBaseModel {
    var code: String = ""
        // 可以处理多种code
//        get() = if(field == "") "" else field

    var message: String? = null
    fun isSuccess(): Boolean {
        return code == "200"
    }

}