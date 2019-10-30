package com.any.org.eanewsmudle.model.bean

import com.any.org.dslnetlibrary.HttpBaseModel

/**
 *
 * @author any
 * @time 2019/10/19 15.52
 * @details  由于sina接口数据返回很特别，所以这里直接写成这样了
 */
open class NewsBaseModel<T> : HttpBaseModel() {

    var result:T?=null

    override fun isSuccess(): Boolean = result!=null

    override fun getHttpCode(): Int = 200

    override fun getHttpMessage(): String = ""
}