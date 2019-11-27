package com.any.org.onemodule.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.BR
import com.any.org.onemodule.R
import com.any.org.onemodule.databinding.OneMonthItemMainAdapterBinding
import com.any.org.onemodule.model.OneMonthModel

/**
 *
 * @author any
 * @time 2019/11/27 10.54
 * @details
 */
class MonthMainAdapter : BaseAdapter<OneMonthModel>() {

//    private val adapter by lazy { MonthSubAdapter() }

    override fun getViewType(t: OneMonthModel): Int = 0

    override fun makeBaseItemView(parent: ViewGroup, viewType: Int): BaseItemView<ViewDataBinding> {
        val itemView = makeView(parent, R.layout.one_month_item_main_adapter)
        return BaseItemView<OneMonthItemMainAdapterBinding>(itemView) as BaseItemView<ViewDataBinding>
    }

    override fun handlerVariable(dataBind: ViewDataBinding?, t: OneMonthModel) {
        val adapter = MonthSubAdapter()
        //处理
        dataBind?.setVariable(BR.monthSubAdapter, adapter)
        //再绑定
        dataBind?.setVariable(BR.oneMM, t)
        //执行
        adapter?.setNewData(t.data)
        KLog.e("msg  handler MonthMainAdapter....  $adapter")
    }
}