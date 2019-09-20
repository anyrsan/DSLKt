package com.any.org.commonlibrary

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import kotlin.math.abs

/**
 * User: any
 * Date: 2019/4/15
 */
object CustomToast {

    private var mToast: Toast? = null

    private var mTime: Long = 0


    fun showNetMsg(context: Context?, message: String?) {
        context?.let {
            showMsg(it, message)
        }
    }

    fun showNetMsg(context: Context?,@StringRes stringId: Int){
        context?.let {
            showMsg(it,stringId)
        }
    }


    fun showMsg(context: Context, @StringRes stringId: Int) {
        showMsg(context, context.getString(stringId))
    }

    fun showMsg(context: Context, message: String?) {
        message?.let {
            message(false, it, context)
        }
    }

    //Top显示
    fun showTopMsg(context: Context, message: String) {
        message(false, message, context, false)
    }

    private fun message(
        isLong: Boolean,
        message: String,
        context: Context,
        isCenter: Boolean = true
    ) {
        val currTime = System.currentTimeMillis()
        // 短时间内重复显示
        // 短时间内进行切换，就创建新的，如果长时间，则复用
        // 3秒内不复用
        if (checkoutTime(mTime, currTime)) {
            mToast?.cancel()
            mToast = null
        }
        mTime = currTime   //记录时间
        //不需要对同样的文本执行弹框
        if (mToast == null) {
            //加载Toast布局
            val toastRoot =
                LayoutInflater.from(context.applicationContext).inflate(R.layout.view_toast, null)
            //设置透明
            toastRoot.background.alpha = 80
            //初始化布局控件
            mToast = Toast(context.applicationContext)
            mToast?.run {
                view = toastRoot
                duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            }
        }
        mToast?.run {
            view?.findViewById<TextView>(R.id.txt_toast)?.text = message
            //获取屏幕高度
            if (isCenter) {
                setGravity(Gravity.CENTER, 0, 0)
            } else {
                setGravity(Gravity.TOP, 0, dp2px(context.resources, 70f))
            }
            show()
        }
    }


    private fun checkoutTime(mTime: Long, currTime: Long): Boolean =
        abs(mTime - currTime) < 3000

    private fun dp2px(res: Resources, paramFloat: Float): Int {
        return (0.5f + paramFloat * res.displayMetrics.density).toInt()
    }
}
