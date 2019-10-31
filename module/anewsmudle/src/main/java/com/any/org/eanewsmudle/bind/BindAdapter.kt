package com.any.org.eanewsmudle.bind

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.any.org.commonlibrary.bitmap.load
import com.any.org.commonlibrary.event.viewDoubleClick
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.viewpresenter.*

/**
 *
 * @author any
 * @time 2019/10/25 19.35
 * @details 主要处理一些控件绑定事件等,事件写到最后 ，保存一些公共事件，可以组合成一个presenter类
 */
@BindingAdapter("app:onRefreshEvent", requireAll = false)
fun bindSwipeRefreshListener(refreshView: SwipeRefreshLayout, loadPresenter: LoadRefreshListener?) {
    refreshView.setOnRefreshListener {
        loadPresenter?.load(true)
    }
}


@BindingAdapter("app:url", requireAll = false)
fun bindImageView(img: ImageView, url: String?) {
    url?.let {
        img.load(it)
    }
}

@BindingAdapter("app:onDoubleClick", requireAll = false)
fun bindDoubleToView(view: View, dPresenter: DoubleClickListener?) {
    //双击不好使，改成单击事件
    view.viewDoubleClick {
        dPresenter?.doubleClick(it)
    }
}

@BindingAdapter("app:onViewClick", requireAll = false)
fun bindClickToView(view: View, dPresenter: NDViewClickListener?) {
    view.viewOnClick {
        dPresenter?.click(it)
    }
}


@BindingAdapter("app:scrollListener", requireAll = false)
fun bindRecycleViewScrollListener(view: RecyclerView, onsls: OnScrollListener?) {
    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //dy > 0 时为向上滚动
            //dy < 0 时为向下滚动
            onsls?.viewIsGone(dy > 0)
        }
    })
}

@BindingAdapter("app:bindAdapter", "app:bindDecoration", requireAll = false)
fun <M, T : ViewDataBinding> bindRecycleViewAdapter(
    view: RecyclerView,
    adapter: BaseItemAdapter<M, T>,
    decoration: RecyclerView.ItemDecoration
) {
    view.apply {
        layoutManager = LinearLayoutManager(view.context)
        this.adapter = adapter
        addItemDecoration(decoration)
    }
}