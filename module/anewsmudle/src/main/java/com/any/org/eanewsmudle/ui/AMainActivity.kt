package com.any.org.eanewsmudle.ui

import com.any.org.ankolibrary.argument
import com.any.org.ankolibrary.set
import com.any.org.commonlibrary.MVVM
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.NewsFragmentAdapter
import com.any.org.eanewsmudle.databinding.AMainActivityBinding
import com.any.org.eanewsmudle.viewmodel.AMainViewModel
import com.any.org.eanewsmudle.viewpresenter.PageChangeListener
import com.any.routerannotation.KRouter
import kotlinx.android.synthetic.main.a_main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/31 17.58
 * @details
 */

@KRouter(MVVM)
class AMainActivity : BaseVBActivity<AMainActivityBinding>() {

    // 注意，viewmodel 都是 延迟加载的
    private val aViewModel by viewModel<AMainViewModel>()

    private val aKey  by argument<String>("aKey")

    private val fragmentAdapter by lazy { NewsFragmentAdapter(supportFragmentManager) }

    override fun getResourceId(): Int = R.layout.a_main_activity


    private val pageChangeListener = object : PageChangeListener {
        override fun onPageSelect(position: Int) {
            val title = fragmentAdapter.getPageTitle(position).toString()
            mBinding.vm?.mTitle?.set(title)
        }
    }

    override fun initView() {
        mBinding.vm = aViewModel
        mBinding.adapter = fragmentAdapter
        mBinding.pageChange = pageChangeListener
        setStatusBarTransparent(true)
        setTopPadding(topLL)
    }

    override fun initGetIntent() {
        KLog.e("aKey is null = $aKey")
    }

    override fun initData() {
        //获取第一次值
        pageChangeListener.onPageSelect(0)
    }

    override fun initEvent() {
    }

    override fun lazyData() {
    }
}