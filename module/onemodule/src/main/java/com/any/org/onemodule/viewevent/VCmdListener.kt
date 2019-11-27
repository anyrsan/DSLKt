package com.any.org.onemodule.viewevent

import android.view.View

/**
 *
 * @author any
 * @time 2019/11/25 10.15
 * @details
 */

/**
 * 刷新监听
 */
interface LoadRefreshListener {
    fun load(refresh: Boolean)
}

interface NDViewClick {
    fun clickView(view: View)
}

interface LoadScrollListener {
    fun getCurrPosition(position: Int)

    fun needLoadMore(load: Boolean)
}