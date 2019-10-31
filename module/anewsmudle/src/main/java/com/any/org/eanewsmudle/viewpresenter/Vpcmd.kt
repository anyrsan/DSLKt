package com.any.org.eanewsmudle.viewpresenter

import android.view.View

/**
 *
 * @author any
 * @time 2019/10/23 13.47
 * @details 指令
 */
interface LoadRefreshListener {
    fun load(refresh: Boolean)
}

interface DoubleClickListener {
    fun doubleClick(v: View)
}

interface OnScrollListener {
    fun viewIsGone(gone: Boolean)
}

interface NDViewClickListener {
    fun click(v: View)
}