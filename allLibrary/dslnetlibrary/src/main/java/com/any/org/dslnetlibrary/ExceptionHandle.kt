package com.any.org.dslnetlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ParseException
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

/**
 * @author any
 * @date 2017/11/30
 */
class ExceptionHandle {

    inner class ServerException(val msg: String?, val code: Int) : RuntimeException()
    //异常类
    open class ResponeThrowable(var msg: String?, var code: Int) : Exception()

    //自定义异常
    class CustomException(msg: String?, code: Int) : ResponeThrowable(msg, code)

    companion object {

        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val METHED_NOT_ALLOWED = 405
        private const val REQUEST_TIMEOUT = 408
        private const val INTERNAL_SERVER_ERROR = 500
        private const val BAD_GATEWAY = 502
        private const val SERVICE_UNAVAILABLE = 503
        private const val GATEWAY_TIMEOUT = 504
        private const val ACTION_LOGIN = "com.need.login"

        /**
         * 获取当前是否联网
         *
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        fun isNetWorkAvailable(context: Context): Boolean {
            var isAvailable = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.isAvailable) {   //??? todo isAvailable
                isAvailable = true
            }
            return isAvailable
        }


        fun handleException(e: Throwable, context: Context?): ResponeThrowable {
            val ex: ResponeThrowable
            if (context != null && !isNetWorkAvailable(context)) {
                ex = ResponeThrowable("当前手机无网络", ERROR.NONET)
                return ex
            } else if (e is CustomException) {  //自定义异常
                Log.e("msg","...用户主动处理的非正确数据异常。。。。")
                return ResponeThrowable(e.msg, e.code)
            } else if (e is HttpException) {
                ex = ResponeThrowable("", ERROR.HTTP_ERROR)
                when (e.code()) {
                    UNAUTHORIZED -> {
                        ex.code = ERROR.TOKENLOSE
                        ex.msg = "登录过期"
                        context?.let {
                            LocalBroadcastManager.getInstance(it).sendBroadcast(Intent(ACTION_LOGIN))
                        }
                    }
                    FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE, METHED_NOT_ALLOWED -> ex.msg =
                        "服务器开小差了，稍后重试"
                    else -> ex.msg = "服务器开小差了，稍后重试"
                }
                return ex
            } else if (e is ServerException) {
                ex = ResponeThrowable(e.message, e.code)
                return ex
            } else if (e is JSONException || e is ParseException) {
                ex = ResponeThrowable("解析错误", ERROR.PARSE_ERROR)
                ex.msg = "解析错误"
                return ex
            } else if (e is ConnectException) {
                ex = ResponeThrowable("连接失败", ERROR.NETWORD_ERROR)
                ex.msg = "连接失败"
                return ex
            } else if (e is SSLHandshakeException) {
                ex = ResponeThrowable("证书验证失败", ERROR.SSL_ERROR)
                ex.msg = "证书验证失败"
                return ex
            } else if (e is ConnectTimeoutException) {
                ex = ResponeThrowable("连接超时", ERROR.TIMEOUT_ERROR)
                ex.msg = "连接超时"
                return ex
            } else if (e is SocketTimeoutException) {
                ex = ResponeThrowable("连接超时", ERROR.TIMEOUT_ERROR)
                ex.msg = "连接超时"
                return ex
            } else {
                ex = ResponeThrowable("未知错误", ERROR.UNKNOWN)
                ex.msg = "未知错误"
                ex.printStackTrace()
                return ex
            }
        }
    }


    /**
     * 约定异常
     */
    object ERROR {
        /**
         * 未知错误
         */
        const val UNKNOWN = 10000
        /**
         * 解析错误
         */
        const val PARSE_ERROR = 10001
        /**
         * 网络错误
         */
        const val NETWORD_ERROR = 10002
        /**
         * 协议出错
         */
        const val HTTP_ERROR = 10003

        /**
         * 证书出错
         */
        const val SSL_ERROR = 10005

        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 10006

        /**
         * 无网络
         */
        const val NONET = 10007

        //toke失效
        const val TOKENLOSE = 10008
    }
}

