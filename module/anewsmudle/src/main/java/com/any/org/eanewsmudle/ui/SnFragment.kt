package com.any.org.eanewsmudle.ui

import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.adapter.SnItemAdapter
import com.any.org.eanewsmudle.databinding.ANewsSnAdapterBinding
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.viewmodel.BaseViewModel
import com.any.org.eanewsmudle.viewmodel.SnViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/31 19.19
 * @details  YL data
 */
class SnFragment :
    NewsBaseFragment<NewsItemModel, NewsModel, ANewsSnAdapterBinding>() {

    private val vm by viewModel<SnViewModel>()

    private val ad by lazy { SnItemAdapter(vm.mList) }

    override fun getItemAdapter(): BaseItemAdapter<NewsItemModel, ANewsSnAdapterBinding> {
        return ad
    }

    override fun getViewModel(): BaseViewModel<NewsItemModel, NewsModel> {
        return vm
    }


}