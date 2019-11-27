package com.any.org.onemodule.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.onemodule.BR
import com.any.org.onemodule.R
import com.any.org.onemodule.databinding.OneMonthItemSubAdapterBinding
import com.any.org.onemodule.model.OneMonthSubModel

/**
 *
 * @author any
 * @time 2019/11/27 10.51
 * @details  月份内部适配器
 */
class MonthSubAdapter : BaseAdapter<OneMonthSubModel>() {

    override fun getViewType(t: OneMonthSubModel): Int = 0

    override fun makeBaseItemView(parent: ViewGroup, viewType: Int): BaseItemView<ViewDataBinding> {
        val itemView = makeView(parent, R.layout.one_month_item_sub_adapter)
        return BaseItemView<OneMonthItemSubAdapterBinding>(itemView) as BaseItemView<ViewDataBinding>
    }

    override fun handlerVariable(dataBind: ViewDataBinding?, t: OneMonthSubModel) {
        dataBind?.setVariable(BR.monthDM, t)
    }

    override fun onClickItem(t: OneMonthSubModel) {
        RxNewBus.postEvent(t)
    }

}