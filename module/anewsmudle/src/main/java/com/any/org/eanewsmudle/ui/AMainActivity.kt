package com.any.org.eanewsmudle.ui

import com.any.org.ankolibrary.argument
import com.any.org.commonlibrary.MVVM
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.NewsFragmentAdapter
import com.any.org.eanewsmudle.databinding.AMainActivityBinding
import com.any.org.eanewsmudle.viewmodel.AMainViewModel
import com.any.routerannotation.KRouter
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * @author any
 * @time 2019/10/31 17.58
 * @details
 */

@KRouter(MVVM)
class AMainActivity : BaseVBActivity<AMainActivityBinding>() {

    // 注意，viewmodel 都是 延迟加载的
    private val aViewModel by viewModel<AMainViewModel>()

    private val aKey by argument<String>("aKey")

    private val fragmentAdapter by lazy { NewsFragmentAdapter(supportFragmentManager) }

    override fun getResourceId(): Int = R.layout.a_main_activity


    override fun initView() {
        mBinding.vm = aViewModel
        mBinding.adapter = fragmentAdapter
        setStatusBarTransparent(true)
        setTopPadding(mBinding.topLL)
        //主要是生产一次默认值
        aViewModel.onPageChangeListener.getTitle(fragmentAdapter.getPageTitle(0))
    }

    override fun initGetIntent() {
        KLog.e("aKey is null = $aKey")
    }

    override fun initData() {

    }

    override fun initEvent() {
    }

    override fun lazyData() {

    }
}