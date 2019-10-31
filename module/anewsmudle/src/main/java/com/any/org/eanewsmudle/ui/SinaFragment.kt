package com.any.org.eanewsmudle.ui

import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.adapter.NewsItemAdapter
import com.any.org.eanewsmudle.databinding.ANewsItemAdapterBinding
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.viewmodel.BaseViewModel
import com.any.org.eanewsmudle.viewmodel.NewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/31 19.19
 * @details  YL data
 */
class SinaFragment :
    NewsBaseFragment<NewsItemModel, NewsModel, ANewsItemAdapterBinding>() {

    private val vm by viewModel<NewsViewModel>()

    private val ad by lazy { NewsItemAdapter(vm.mList) }

    override fun getItemAdapter(): BaseItemAdapter<NewsItemModel, ANewsItemAdapterBinding> {
        return ad
    }

    override fun getViewModel(): BaseViewModel<NewsItemModel, NewsModel> {
        return vm
    }

    companion object {
        fun getInstance(): SinaFragment = SinaFragment()
    }

}