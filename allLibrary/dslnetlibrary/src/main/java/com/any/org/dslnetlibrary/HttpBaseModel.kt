package com.any.org.dslnetlibrary

/**
 *
 * @author any
 * @time 2019/8/19 09.42
 * @details HttpBaseModel 基础数据类，主要是处理  基础数据类的结构，大体上要保证一致
 */
open class HttpBaseModel {
    var code: String = ""
        // 可以处理多种code
        get() = if (field == "") status else field
    var status: String = ""

    var message: String? = null
    fun isSuccess(): Boolean {
        return (code == "200") || (status=="200")
    }

}