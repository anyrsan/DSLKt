package com.any.org.eanewsmudle.ui

import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.adapter.ThsItemAdapter
import com.any.org.eanewsmudle.databinding.ANewsThsAdapterBinding
import com.any.org.eanewsmudle.model.bean.ThsItemModel
import com.any.org.eanewsmudle.viewmodel.BaseViewModel
import com.any.org.eanewsmudle.viewmodel.ThsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/31 19.19
 * @details  YL data
 */
class ThsFragment :
    NewsBaseFragment<ThsItemModel, List<ThsItemModel>, ANewsThsAdapterBinding>() {

    private val vm by viewModel<ThsViewModel>()

    private val ad by lazy { ThsItemAdapter(vm.mList) }

    override fun getViewModel(): BaseViewModel<ThsItemModel, List<ThsItemModel>> {
        return vm
    }

    override fun getItemAdapter(): BaseItemAdapter<ThsItemModel, ANewsThsAdapterBinding> {
        return ad
    }


}