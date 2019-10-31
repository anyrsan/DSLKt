package com.any.org.commonlibrary.event

import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 *
 * @author any
 * @time 2019/07/05 09.42
 * @details   防止按纽短时间内重复点击
 */
open class DoubleClickListener(private val diffTime: Int, val callBack: (view: View) -> Unit) :
    View.OnTouchListener {
    private var downTime = 0L
    private var touchDelegate = false
    override fun onTouch(v: View, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val currentTime = Calendar.getInstance().timeInMillis
            touchDelegate = false
            downTime = if (currentTime - downTime < diffTime) {
//                KLog.e("click","click... 执行事件成功 时间置0")
                callBack(v)
                touchDelegate = true
//                v.postDelayed ({callBack(v)},400)
                0
            } else {
//                KLog.w("click","click...  输出无效，记录时间 $currentTime")
                currentTime
            }
        }
        return touchDelegate
    }
}


fun View.viewDoubleClick(nClick: (v: View) -> Unit) {
    viewDoubleClickDiffTime(500, nClick)
}

fun View.viewDoubleClickDiffTime(diffTime: Int = 400, nClick: (v: View) -> Unit) {
    setOnTouchListener(object : DoubleClickListener(diffTime, nClick) {})
}
