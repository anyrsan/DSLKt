package com.any.org.eanewsmudle.bind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.any.org.commonlibrary.bitmap.load
import com.any.org.eanewsmudle.viewpresenter.LoadPresenter

/**
 *
 * @author any
 * @time 2019/10/25 19.35
 * @details 主要处理一些控件绑定事件等
 */
@BindingAdapter("app:onRefreshEvent", requireAll = false)
fun bindSwipeRefreshListener(refreshView: SwipeRefreshLayout, loadPresenter: LoadPresenter) {
    refreshView.setOnRefreshListener {
        loadPresenter.load(true)
    }
}


@BindingAdapter("app:url", requireAll = false)
fun bindImageView(img: ImageView, url: String?) {
    url?.let {
        img.load(it)
    }
}
