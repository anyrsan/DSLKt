package com.any.org.onemodule.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.onemodule.BR
import com.any.org.onemodule.R
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.databinding.OneDtItemAdapterBinding
import com.any.org.onemodule.databinding.OneFxItemAdapterBinding
import com.any.org.onemodule.databinding.OneYyItemAdapterBinding
import com.any.org.onemodule.databinding.OneZxItemAdapterBinding
import com.any.org.onemodule.model.OneDataItemModel

/**
 *
 * @author any
 * @time 2019/11/26 18.51
 * @details
 */
class OneMainAdapter : BaseAdapter<OneDataItemModel>() {

    override fun makeBaseItemView(parent: ViewGroup, viewType: Int): BaseItemView<ViewDataBinding> {
        return when (viewType) {
            0 -> {
                val itemView = makeView(parent, R.layout.one_fx_item_adapter)
                BaseItemView<OneFxItemAdapterBinding>(itemView)
            }
            4 -> {
                val itemView = makeView(parent, R.layout.one_yy_item_adapter)
                BaseItemView<OneYyItemAdapterBinding>(itemView)
            }
            8 -> {
                val itemView = makeView(parent, R.layout.one_dt_item_adapter)
                BaseItemView<OneDtItemAdapterBinding>(itemView)
            }
            else -> {
                val itemView = makeView(parent, R.layout.one_zx_item_adapter)
                BaseItemView<OneZxItemAdapterBinding>(itemView)
            }
        } as BaseItemView<ViewDataBinding>
    }

    override fun handlerVariable(dataBind: ViewDataBinding?, oneData: OneDataItemModel) {
        dataBind?.setVariable(BR.oneDM, oneData)
        dataBind?.setVariable(BR.auther, oneData.author)
    }

    override fun getViewType(t: OneDataItemModel): Int = CateApi.getType(t.category)

    override fun onClickItem(t: OneDataItemModel) {
        RxNewBus.postEvent(t)
    }

}