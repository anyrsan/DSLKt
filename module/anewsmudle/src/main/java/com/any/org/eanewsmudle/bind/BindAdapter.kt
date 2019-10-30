package com.any.org.eanewsmudle.bind

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.any.org.eanewsmudle.viewcmd.LoadPresenter

/**
 *
 * @author any
 * @time 2019/10/25 19.35
 * @details
 */
//@BindingAdapter("app:bind_swipeRefreshLayout_refreshing")
//fun setSwipeRefreshLayoutRefreshing(refreshView: SwipeRefreshLayout, refreshing: Boolean) {
////   if(refreshing != refreshView.isRefreshing){
////       refreshView.isRefreshing = refreshing
////   }
////    refreshView.isRefreshing = refreshing
//    KLog.e("msg....   bindSwipeRefreshState  $refreshing")
//}
//
//@InverseBindingAdapter(
//    attribute = "app:bind_swipeRefreshLayout_refreshing",
//    event = "app:bind_swipeRefreshLayout_refreshingAttrChanged"
//)
//fun isSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean =
//    swipeRefreshLayout.isRefreshing



@BindingAdapter("app:onRefreshEvent",requireAll = false)
fun bindSwipeRefreshListener(refreshView: SwipeRefreshLayout, loadPresenter: LoadPresenter) {
    refreshView.setOnRefreshListener {
        loadPresenter.load(true)
    }
}

