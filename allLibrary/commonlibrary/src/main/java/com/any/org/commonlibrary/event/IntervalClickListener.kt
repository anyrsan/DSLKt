package com.any.org.commonlibrary.event

import android.view.View
import java.util.*

/**
 *
 * @author any
 * @time 2019/07/05 09.42
 * @details  间隔时间处理点击事件
 */
open class IntervalClickListener(
    val callBack: (status: Int, view: View) -> Unit,
    private val intervalTime: Int
) :
    View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > intervalTime) {
            lastClickTime = currentTime
            callBack(0, v)
        } else {
            callBack(1, v)
        }
    }
}


fun View.viewOnClick(intervalTime: Int = 10000, nClick: (status: Int, v: View) -> Unit) {
    setOnClickListener(object : IntervalClickListener(nClick, intervalTime) {})
}

