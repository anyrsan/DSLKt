package com.any.routercompliecore

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

class RouterConfig(
    val context: Context,  // 上下文 Activity
    val fragment: Fragment?, //  fragment 上下文
    val target: String,   //导航路由地址
    val callBack: NavigationCallback?,// 导航回调
    val requestCode: Int?,  //请求结果码
    val bundle: Bundle?, // 页面数据，传数据
    var resumeKey: String?, // 重定向路由地址
    var flags: IntArray?
) {

    internal fun jump() {
        Router.doJump(this, callBack)
    }
}

class RouterParamConfig(val param: Pair<String, Any?>?)

class RouterParamConfigBuilder {
    var param: Pair<String, Any?>? = null
    internal fun build(): RouterParamConfig = RouterParamConfig(param)
}

class RouterConfigBuilder(private val context: Context, private val fragment: Fragment?) {

    var target = ""

    var resumeKey: String? = null

    private var flags: IntArray? = null

    var requestCode: Int? = null

    private var bundle: Bundle? = null

    private var callBack: NavigationCallback? = null


    private var param: Array<Pair<String, Any?>>? = null


    fun callBack(block: NavigationCallback) {
        this.callBack = block
    }

    fun callDefaultBack(block: (code: Int) -> Unit) {
        this.callBack = DefaultNavigationCallBack(block)
    }

    //设置flag
    fun addFlags(vararg flags: Int) {
        this.flags = flags
    }

    //设置参数
    fun setParams(vararg params: Pair<String, Any?>) {
        if (params.isNotEmpty()) bundle = fillIntentArguments(params)
    }


    //不推荐调用此方法
    @Deprecated("removed")
    fun addParam(block: RouterParamConfigBuilder.() -> Unit) {
        val routerCB = RouterParamConfigBuilder().apply(block).build()
        routerCB.param?.let {
            // 合并多个
            this.bundle = fillIntentArguments(arrayOf(it), bundle)
        }
    }

    internal fun builder(): RouterConfig =
        RouterConfig(context, fragment, target, callBack, requestCode, bundle, resumeKey, flags)

    //传换参数
    private fun fillIntentArguments(
        params: Array<out Pair<String, Any?>>,
        mBundle: Bundle? = null
    ): Bundle {
        val bundle = if (mBundle == null) Bundle() else Bundle(mBundle)
        params.forEach {
            when (val value = it.second) {
                null -> bundle.putSerializable(it.first, null as Serializable?)
                is Int -> bundle.putInt(it.first, value)
                is Long -> bundle.putLong(it.first, value)
                is CharSequence -> bundle.putCharSequence(it.first, value)
                is String -> bundle.putString(it.first, value)
                is Float -> bundle.putFloat(it.first, value)
                is Double -> bundle.putDouble(it.first, value)
                is Char -> bundle.putChar(it.first, value)
                is Short -> bundle.putShort(it.first, value)
                is Boolean -> bundle.putBoolean(it.first, value)
                is Serializable -> bundle.putSerializable(it.first, value)
                is Bundle -> bundle.putBundle(it.first, value)
                is Parcelable -> bundle.putParcelable(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> bundle.putCharSequenceArray(
                        it.first,
                        value as Array<out CharSequence>?
                    )
                    value.isArrayOf<String>() -> bundle.putStringArray(
                        it.first,
                        value as Array<out String>?
                    )
                    value.isArrayOf<Parcelable>() -> bundle.putParcelableArray(
                        it.first,
                        value as Array<out Parcelable>?
                    )
                }
                is IntArray -> bundle.putIntArray(it.first, value)
                is LongArray -> bundle.putLongArray(it.first, value)
                is FloatArray -> bundle.putFloatArray(it.first, value)
                is DoubleArray -> bundle.putDoubleArray(it.first, value)
                is CharArray -> bundle.putCharArray(it.first, value)
                is ShortArray -> bundle.putShortArray(it.first, value)
                is BooleanArray -> bundle.putBooleanArray(it.first, value)
            }
        }
        return bundle
    }
}