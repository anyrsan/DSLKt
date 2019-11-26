package com.any.org.onemodule.bind

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.any.org.commonlibrary.bitmap.load
import com.any.org.commonlibrary.utils.DensityUtil
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.viewevent.LoadRefreshListener
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

@BindingAdapter("app:bindAdapter")
fun <VH : RecyclerView.ViewHolder> bindRecycleViewAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<VH>
) {
    val mContext = recyclerView.context
    val space = DensityUtil.dip2px(mContext, 10f)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(mContext)
    recyclerView.addItemDecoration(VerticalDecoration(mContext, space, 0, space*10))
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