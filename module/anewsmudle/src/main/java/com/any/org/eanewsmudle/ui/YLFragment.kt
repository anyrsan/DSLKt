package com.any.org.eanewsmudle.ui

import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.adapter.YLItemAdapter
import com.any.org.eanewsmudle.databinding.ANewsYlAdapterBinding
import com.any.org.eanewsmudle.model.bean.YLNewsModel
import com.any.org.eanewsmudle.viewmodel.BaseViewModel
import com.any.org.eanewsmudle.viewmodel.YLNewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/31 19.19
 * @details  YL data
 */
class YLFragment :
    NewsBaseFragment<YLNewsModel.DataBean, YLNewsModel, ANewsYlAdapterBinding>() {

    private val vm by viewModel<YLNewsViewModel>()

    private val ad by lazy { YLItemAdapter(vm.mList) }

    override fun getViewModel(): BaseViewModel<YLNewsModel.DataBean, YLNewsModel> {
        return vm
    }

    override fun getItemAdapter(): BaseItemAdapter<YLNewsModel.DataBean, ANewsYlAdapterBinding> {
        return ad
    }

    companion object {
        fun getInstance(): YLFragment = YLFragment()
    }

}