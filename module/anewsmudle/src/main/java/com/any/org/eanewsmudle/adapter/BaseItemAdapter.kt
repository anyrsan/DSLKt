package com.any.org.eanewsmudle.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.any.org.commonlibrary.log.KLog
import com.any.org.eanewsmudle.adapter.obser.AdObserver
import com.any.org.eanewsmudle.adapter.obser.AdapterDataObserver
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 *
 * @author any
 * @time 2019/10/30 15.41
 * @details
 */
abstract class BaseItemAdapter<T, V : ViewDataBinding>(@LayoutRes layoutId: Int, list: AdapterDataObserver<T>) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutId) {

    init {
        list.addListener(object : AdObserver<T> {
            override fun updateData(t: List<T>?, new: Boolean) {
                if (new) {
                    setNewData(t)
                    setEnableLoadMore(t?.isNotEmpty() ?: false)
                    KLog.e("刷新数据成功")
                } else {
                    //这里也可以处理出错情况  t为空时，就是加载出错了
                    t?.run {
                        addData(this)
                        loadMoreComplete()
                        //如果不为空，则代表还有下一页，否则不存在更多
                        setEnableLoadMore(isNotEmpty())
                    } ?: loadMoreFail()
                    KLog.e("......添加数据成功")
                }
            }

        })
    }

    override fun convert(helper: BaseViewHolder?, item: T?) {
        helper?.let {
            val binding = DataBindingUtil.bind<V>(it.itemView)
            binding?.setVariable(BR.newsItem, item)
            binding?.executePendingBindings()
        }
    }
}