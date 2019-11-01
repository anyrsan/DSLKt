package com.any.org.eanewsmudle.ui

import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.adapter.YlItemAdapter
import com.any.org.eanewsmudle.databinding.ANewsYlAdapterBinding
import com.any.org.eanewsmudle.model.bean.YLNewsModel
import com.any.org.eanewsmudle.viewmodel.BaseViewModel
import com.any.org.eanewsmudle.viewmodel.YlViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/31 19.19
 * @details  YL data
 */
class YlFragment :
    NewsBaseFragment<YLNewsModel.DataBean, YLNewsModel, ANewsYlAdapterBinding>() {

    private val vm by viewModel<YlViewModel>()

    private val ad by lazy { YlItemAdapter(vm.mList) }

    override fun getViewModel(): BaseViewModel<YLNewsModel.DataBean, YLNewsModel> {
        return vm
    }

    override fun getItemAdapter(): BaseItemAdapter<YLNewsModel.DataBean, ANewsYlAdapterBinding> {
        return ad
    }

}