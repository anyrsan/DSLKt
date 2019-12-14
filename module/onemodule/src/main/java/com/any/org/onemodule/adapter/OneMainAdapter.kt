package com.any.org.onemodule.adapter

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.onemodule.BR
import com.any.org.onemodule.R
import com.any.org.onemodule.data.CateApi
import com.any.org.onemodule.model.OneDataItemModel

/**
 *
 * @author any
 * @time 2019/11/26 18.51
 * @details
 */
class OneMainAdapter : BaseAdapter<OneDataItemModel>() {


    override fun getLayoutId(viewType: Int): Int {
        return when (viewType) {
            0 -> {
                R.layout.one_fx_item_adapter
            }
            4 -> {
                R.layout.one_yy_item_adapter
            }
            8 -> {
                R.layout.one_dt_item_adapter
            }
            else -> {
                R.layout.one_zx_item_adapter
            }
        }
    }


    override fun handlerVariable(dataBind: ViewDataBinding, oneData: OneDataItemModel) {
        dataBind.setVariable(BR.oneDM, oneData)
        dataBind.setVariable(BR.auther, oneData.author)
    }

    override fun getViewType(t: OneDataItemModel): Int = CateApi.getType(t.category)

    override fun onClickItem(t: OneDataItemModel) {
        RxNewBus.postEvent(t)
    }

}