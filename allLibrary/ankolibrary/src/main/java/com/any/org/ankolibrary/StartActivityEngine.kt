package com.any.org.ankolibrary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.Serializable
import com.any.org.ankolibrary.argument as argument1


/**
 *
 * @author any
 * @time 2019/05/18 15.02
 * @details 启动activity
 */
object StartActivityEngine {
    fun internalStartActivity(
        ctx: Context,
        activity: Class<out Activity>,
        params: Array<out Pair<String, Any?>>
    ) {
        ctx.startActivity(
            createIntent(
                ctx,
                activity,
                params
            )
        )
    }

    fun internalStartActivityForResult(
        act: Activity,
        activity: Class<out Activity>,
        requestCode: Int,
        params: Array<out Pair<String, Any?>>
    ) {
        act.startActivityForResult(
            createIntent(
                act,
                activity,
                params
            ), requestCode
        )
    }


    fun internalStartActivityForResult(
        fragment: Fragment,
        activity: Class<out Activity>,
        requestCode: Int,
        params: Array<out Pair<String, Any?>>
    ) {
        fragment.startActivityForResult(
            createIntent(
                fragment.requireContext(),
                activity,
                params
            ), requestCode
        )
    }


    fun <T> createIntent(
        ctx: Context,
        clazz: Class<out T>,
        params: Array<out Pair<String, Any?>>
    ): Intent {
        val intent = Intent(ctx, clazz)
        if (params.isNotEmpty()) fillIntentArguments(
            intent,
            params
        )
        return intent
    }


    private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
        params.forEach {
            val value = it.second
            when (value) {
                null -> intent.putExtra(it.first, null as Serializable?)
                is Int -> intent.putExtra(it.first, value)
                is Long -> intent.putExtra(it.first, value)
                is CharSequence -> intent.putExtra(it.first, value)
                is String -> intent.putExtra(it.first, value)
                is Float -> intent.putExtra(it.first, value)
                is Double -> intent.putExtra(it.first, value)
                is Char -> intent.putExtra(it.first, value)
                is Short -> intent.putExtra(it.first, value)
                is Boolean -> intent.putExtra(it.first, value)
                is Serializable -> intent.putExtra(it.first, value)
                is Bundle -> intent.putExtra(it.first, value)
                is Parcelable -> intent.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                }
                is IntArray -> intent.putExtra(it.first, value)
                is LongArray -> intent.putExtra(it.first, value)
                is FloatArray -> intent.putExtra(it.first, value)
                is DoubleArray -> intent.putExtra(it.first, value)
                is CharArray -> intent.putExtra(it.first, value)
                is ShortArray -> intent.putExtra(it.first, value)
                is BooleanArray -> intent.putExtra(it.first, value)
            }
            return@forEach
        }
    }
}

inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) =
    StartActivityEngine.internalStartActivity(this, T::class.java, params)

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) =
    StartActivityEngine.internalStartActivityForResult(
        this,
        T::class.java,
        requestCode,
        params
    )

//支持 fragment的跳转回会传
inline fun <reified T : Activity> Fragment.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) =
    StartActivityEngine.internalStartActivityForResult(
        this,
        T::class.java,
        requestCode,
        params
    )


inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
    StartActivityEngine.createIntent(this, T::class.java, params)


//获取对应的参数值
inline fun <reified T : Any> FragmentActivity.argument(key: String) =
    lazy {
        intent?.extras?.get(key) as? T ?: null
    }

//获取对应的参数值
inline fun <reified T : Any> Fragment.argument(key: String) = lazy {
    activity?.intent?.extras?.get(key) as? T ?: null
}

//直接取值 ，不存在
inline fun <reified T : Any> FragmentActivity.autoFill(key: String, default: T? = null): T? {
    return intent?.extras?.get(key)?.let {
        if (it is T) it else default
    } ?: default
}


inline fun <reified T : Any> Fragment.autoFill(key: String, default: T? = null): T? {
    return activity?.intent?.extras?.get(key)?.let {
        if (it is T) it else default
    } ?: default
}