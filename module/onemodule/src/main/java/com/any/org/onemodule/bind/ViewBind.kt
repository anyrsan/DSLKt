package com.any.org.onemodule.bind

import android.view.View
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.any.org.commonlibrary.bitmap.load
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.utils.DensityUtil
import com.any.org.commonlibrary.widget.GridSpacingItemDecorationextend
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.viewevent.LoadRefreshListener
import com.any.org.onemodule.viewevent.LoadScrollListener
import com.any.org.onemodule.viewevent.NDViewClick
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/22 17.30
 * @details view 绑定事件
 */
@JvmOverloads
@BindingAdapter("app:url", "app:isCircle", requireAll = false)
fun bindImageView(img: ImageView, url: String?, @Nullable isCircle: Boolean?) {
    val circle = isCircle ?: false
    url?.let {
        img.load(it, circle)
    }
}

@BindingAdapter("app:bindRefreshListener", requireAll = false)
fun refreshListener(refreshLayout: SwipeRefreshLayout, @Nullable refresh: LoadRefreshListener?) {
    refreshLayout.setOnRefreshListener {
        refresh?.load(true)
    }
}

@JvmOverloads
@BindingAdapter("app:bindAdapter", "app:bindItemDecoration", requireAll = false)
fun <VH : RecyclerView.ViewHolder> bindRecycleViewAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<VH>,
    isItemDt: Boolean?
) {
    val mContext = recyclerView.context
    val space = DensityUtil.dip2px(mContext, 10f)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(mContext)
    if (isItemDt == null) {
        recyclerView.addItemDecoration(VerticalDecoration(mContext, space, 0, space * 3))
    }

}


@BindingAdapter("app:bindLoadListener")
fun bindRecycleViewOnScrollListener(recyclerView: RecyclerView, loadListener: LoadScrollListener) {

    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            val position = when (val layoutManager = recyclerView.layoutManager) {
                is GridLayoutManager -> {
                    layoutManager.findLastVisibleItemPosition()
                }
                is LinearLayoutManager -> {
                    layoutManager.findLastVisibleItemPosition()
                }
                else -> {
                    0
                }
            }
            loadListener.getCurrPosition(position)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager
            val childCount = recyclerView.childCount
            val lastView = recyclerView.getChildAt(childCount - 1)
            val lastBottom = lastView?.bottom ?: 0
            val recycleBottom = recyclerView.bottom - recyclerView.paddingBottom
            val lastPosition = layoutManager?.getPosition(lastView) ?: 0
            val totalItemCount = recyclerView.adapter?.itemCount ?: -1
            KLog.e(
                "scrolled",
                "...$layoutManager... $lastBottom  $recycleBottom   $lastPosition  $totalItemCount    ${recyclerView.adapter?.itemCount}"
            )
            if (lastBottom == recycleBottom && lastPosition == totalItemCount - 1) {
                loadListener.needLoadMore(true)
            }
        }
    })
}


@BindingAdapter("app:bindGridAdapter", requireAll = false)
fun <VH : RecyclerView.ViewHolder> bindRecycleViewGridAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<VH>
) {
    val mContext = recyclerView.context
    val space = DensityUtil.dip2px(mContext, 10f)
    KLog.e("bindRecycleViewGridAdapter ...  ${recyclerView.itemDecorationCount}")
    if (recyclerView.itemDecorationCount > 0) {
        recyclerView.removeItemDecorationAt(0)
    }
    recyclerView.adapter = adapter
    recyclerView.layoutManager = GridLayoutManager(mContext, 2)
    recyclerView.addItemDecoration(GridSpacingItemDecorationextend(2, space, true))
}

@BindingAdapter("app:convertDate")
fun convertDateToValue(textView: TextView, date: String?) {
    date?.let {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val mDate = sdf.parse(it)
        //设置日期
        val calendar = Calendar.getInstance()
        calendar.time = mDate
        // 取日期
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        sdf.applyPattern("MM/yyyy")
        val smallValue = sdf.format(mDate)
        val fontSize = DensityUtil.dip2px(textView.context, 10f)
        textView.text = CateApi.getValue(fontSize, "$day", smallValue)
    }
}

@BindingAdapter("app:onNDClick")
fun addNDClickView(view: View, ndViewClick: NDViewClick) {
    view.viewOnClick {
        ndViewClick.clickView(it)
    }
}
