package com.any.org.dslnetlibrary

/**
 *
 * @author any
 * @time 2019/8/19 09.42
 * @details HttpBaseModel 基础数据类，主要是处理  基础数据类的结构，大体上要保证一致, 如果
 */
abstract class HttpBaseModel {

    abstract fun isSuccess(): Boolean

    abstract fun getHttpCode(): Int

    abstract fun getHttpMessage(): String

}

// 本来可以定义基础类以下结构，由于各公司数据结构定义不一样，现改为抽象接口层处理
//open class HttpBaseModel {
//    var code: String = ""
//    var message: String? = null
//    fun isSuccess(): Boolean {
//        return code == "200"
//    }
//}
