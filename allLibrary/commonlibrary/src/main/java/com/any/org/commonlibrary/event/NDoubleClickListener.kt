package com.any.org.commonlibrary.event

import android.view.View
import java.util.*

/**
 *
 * @author any
 * @time 2019/07/05 09.42
 * @details   防止按纽短时间内重复点击
 */
open class NDoubleClickListener(private val diffTime: Int, val callBack: (view: View) -> Unit) :
    View.OnClickListener {
    var lastClickTime = 0L
    override fun onClick(v: View) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > diffTime) {
            lastClickTime = currentTime
            callBack(v)
        }
    }
}


fun View.viewOnClick(nClick: (v: View) -> Unit) {
     viewOnClickDiffTime(500,nClick)
}

fun View.viewOnClickDiffTime(diffTime: Int = 1000,nClick: (v: View) -> Unit){
    setOnClickListener(object :NDoubleClickListener(diffTime,nClick){})
}
