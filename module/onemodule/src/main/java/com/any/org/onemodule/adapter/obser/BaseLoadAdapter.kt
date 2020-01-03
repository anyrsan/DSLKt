package com.any.org.onemodule.adapter.obser

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.any.org.commonlibrary.log.KLog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 *
 * @author any
 * @time 2019/10/30 15.41
 * @details 封装适配器，自动完成数据装载
 */
abstract class BaseLoadAdapter<T, V : ViewDataBinding>(@LayoutRes layoutId: Int,  list: AdapterDataObserver<T>) :
    BaseQuickAdapter<T, BaseViewHolder>(layoutId) {

    private val pageNum = 16

    var loadMoreData = false
        private set

    private val observer = object : AdObserver<T> {
        override fun updateData(t: List<T>?, new: Boolean) {
            loadMoreData = !(t.isNullOrEmpty() || (t.size < pageNum)  || t.isEmpty())
            if (new) {
                setNewData(t)
                setEnableLoadMore(loadMoreData)
                if (!loadMoreData) loadMoreEnd()
                KLog.e("AAA","刷新数据成功   $loadMoreData")
            } else {
                //这里也可以处理出错情况  t为空时，就是加载出错了
                t?.run {
                    loadMoreComplete()
                    addData(this)
                    //如果不为空，则代表还有下一页，否则不存在更多
                    if (!loadMoreData) loadMoreEnd()
                    KLog.e("有没有更多数据 。。。  ${isEmpty()}")
                } ?: loadMoreFail()
                KLog.e("......添加数据成功  $loadMoreData")
            }
        }
    }

    init {
        list.addListener(observer)
    }

    abstract fun handlerData(binding: ViewDataBinding?, item: T?)

    override fun convert(helper: BaseViewHolder?, item: T?) {
        helper?.let {
            val binding = DataBindingUtil.bind<V>(it.itemView)
            handlerData(binding, item)
            binding?.executePendingBindings()
        }
    }

}