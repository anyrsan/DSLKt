package com.any.org.loginmodule.model

import com.any.org.dslnetlibrary.HttpBaseModel

/**
 *
 * @author any
 * @time 2019/10/19 15.41
 * @details
 */
open class LoginBaseModel<T> : HttpBaseModel() {

    var data: T? = null

    var message: String = ""

    var status: Int = -1

    override fun isSuccess(): Boolean = status == 200

    override fun getHttpCode(): Int = status

    override fun getHttpMessage(): String = message
}