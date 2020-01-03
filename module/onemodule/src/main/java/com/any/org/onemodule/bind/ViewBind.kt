package com.any.org.onemodule.bind

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.any.org.commonlibrary.bitmap.load
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.utils.DensityUtil
import com.any.org.commonlibrary.widget.GridSpacingItemDecorationextend
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.onemodule.adapter.BaseAdapter
import com.any.org.onemodule.adapter.obser.BaseLoadAdapter
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.extend.StringEx
import com.any.org.onemodule.manager.VoicePlayerManager
import com.any.org.onemodule.viewevent.LoadRefreshListener
import com.any.org.onemodule.viewevent.LoadScrollListener
import com.any.org.onemodule.viewevent.NDViewClick
import com.any.org.onemodule.widget.ChatAnimImageView
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/22 17.30
 * @details view 绑定事件  ,多个属性时，建议写多个方法，如果合在一起，一定要注意方法重复调用问题
 *
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
@BindingAdapter("app:bindAdapter", "app:bindItemDecoration", "app:bindData", requireAll = false)
fun <T> bindRecycleViewAdapter(
    recyclerView: RecyclerView,
    adapter: BaseAdapter<T>?,
    isItemDt: Boolean?,
    data: List<T>?
) {

    val tempAdapter = recyclerView.adapter
    val mContext = recyclerView.context.applicationContext
    if (tempAdapter == null) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(mContext)
    }
    val itemDtSize = recyclerView.itemDecorationCount
    if (itemDtSize == 0) {
        val space = DensityUtil.dip2px(mContext, 10f)
        if (isItemDt == null || isItemDt) {
            recyclerView.addItemDecoration(VerticalDecoration(mContext, space, 0, space * 3))
        }
    }
    data?.let {
        adapter?.setNewData(it)
    }
}


@BindingAdapter("app:bindLoadAdapter", requireAll = false)
fun <M, T : ViewDataBinding> bindRecycleViewAdapter(
    view: RecyclerView,
    adapter: BaseLoadAdapter<M, T>?
) {
    view.apply {
        val mContext = view.context.applicationContext
        val space = DensityUtil.dip2px(mContext, 1f)
        layoutManager = LinearLayoutManager(mContext)
        this.adapter = adapter
        isNestedScrollingEnabled = false
        addItemDecoration(VerticalDecoration(mContext, space, 0, 0))
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
    val mContext = recyclerView.context.applicationContext
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
        textView.text = StringEx.getValue(fontSize, "$day", smallValue)
    }
    KLog.e("有数据 来了。。。 $date")
}

@BindingAdapter("app:onNDClick",requireAll = false)
fun addNDClickView(view: View, ndViewClick: NDViewClick?) {
    view.viewOnClick {
        ndViewClick?.clickView(it)
    }
}

@BindingAdapter("app:bindHtml", requireAll = false)
fun bindWebHtml(webView: WebView, data: String?) {
    data?.let {
        webView.loadData(it, "text/html", "utf-8")
    }
}

@BindingAdapter("app:bindAnim")
fun bindAnimView(anim: ChatAnimImageView, play: Boolean) {
    if (play) anim.startAnim() else anim.stopAnim()
}

@BindingAdapter("app:scrollTop", requireAll = false)
fun bindScrollToTop(recyclerView: RecyclerView, isTop: Boolean?) {
//     if (isTop == true) recyclerView.scrollToPosition(0)
}


@BindingAdapter("app:bindVpAdapter", requireAll = false)
fun bindVpAdapter(
    viewPager2: ViewPager2,
    fAdapter: FragmentStateAdapter
) {
    viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    viewPager2.adapter = fAdapter
}


@BindingAdapter("app:bindLoadListener", requireAll = false)
fun bindVpListener(viewPager2: ViewPager2, loadListener: LoadScrollListener?) {
    viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            loadListener?.getCurrPosition(position)
            val lastPosition = viewPager2.adapter?.itemCount ?: 0
            //最后一个时，要加载数据
            loadListener?.needLoadMore(position == lastPosition - 1)
        }
    })
}


@BindingAdapter("app:bindToPosition", requireAll = false)
fun bindVpToPosition(viewPager2: ViewPager2, position: Int) {
    viewPager2.setCurrentItem(position, true)
}


@BindingAdapter("app:bindVpAdapter", requireAll = false)
fun bindVpAdapter(
    viewPager: ViewPager,
    fAdapter: FragmentPagerAdapter
) {
    viewPager.adapter = fAdapter
    viewPager.offscreenPageLimit = 3
}


@BindingAdapter("app:bindLoadListener", requireAll = false)
fun bindVpListener(viewPager: ViewPager, loadListener: LoadScrollListener?) {

    viewPager.clearOnPageChangeListeners()
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            loadListener?.getCurrPosition(position)
            val lastPosition = viewPager.adapter?.count ?: 0
            //最后一个时，要加载数据
            loadListener?.needLoadMore(position == lastPosition - 1)

        }

    })
}


@BindingAdapter("app:bindToPosition", requireAll = false)
fun bindVpToPosition(viewPager: ViewPager, position: Int) {
    viewPager.setCurrentItem(position, true)
}