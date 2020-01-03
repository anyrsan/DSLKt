package com.any.org.onemodule.adapter

import androidx.databinding.ViewDataBinding
import com.any.org.onemodule.R
import com.any.org.onemodule.BR
import com.any.org.onemodule.model.MenuModel

/**
 *
 * @author any
 * @time 2019/12/31 16.06
 * @details 暂时不用这个
 */
class MenuSerAdapter : BaseAdapter<MenuModel>() {

    override fun getViewType(t: MenuModel): Int = 0

    override fun getLayoutId(viewType: Int): Int = R.layout.menu_layout

    override fun handlerVariable(dataBind: ViewDataBinding, t: MenuModel) {
        dataBind.setVariable(BR.menuM, t)
    }

    override fun onClickItem(t: MenuModel) {

    }

}