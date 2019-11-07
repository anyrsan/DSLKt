package com.any.org.ankolibrary

import android.net.ParseException
import org.json.JSONException
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

// 网络异常
const val NET_ERROR_CODE = -200
// 解析异常
const val JSON_ERROR_CODE = -201
// 连接异常
const val CONNECT_ERROR_CODE = -202
// https异常
const val SSL_ERROR_CODE = -203
// 未知异常
const val UNKNOWN_ERROR_CODE = -100


/**
 * 处理异常
 */
fun handlerException(t: Throwable): CustomException {
    return when (t) {
        is HttpException -> {
            val netMessage = if (t.code() == 401) {
                "登录过期"
                throw Exception("估计抛出一个全局异常")
            } else {
                "服务器内部错误"
            }
            CustomException(netMessage, t.code())
        }
        is UnknownHostException -> {
            CustomException("主机异常，一般是没有网络", NET_ERROR_CODE)
        }
        is JSONException, is ParseException -> {
            CustomException("解析异常", JSON_ERROR_CODE)
        }

        is ConnectException, is SocketTimeoutException-> {
            CustomException("连接异常", CONNECT_ERROR_CODE)
        }

        is SSLHandshakeException -> {
            CustomException("证书异常", SSL_ERROR_CODE)
        }
        else -> {
            CustomException("未知异常", UNKNOWN_ERROR_CODE)
        }

    }


}


/**
 *
 * @author any
 * @time 2019/10/28 16.50
 * @details
 */
class CustomException(val netMessage: String?, val errorCode: Int) : Exception()