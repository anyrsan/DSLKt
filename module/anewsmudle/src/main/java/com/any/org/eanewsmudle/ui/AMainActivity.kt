package com.any.org.eanewsmudle.ui

import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.NewsFragmentAdapter
import kotlinx.android.synthetic.main.a_main_activity.*

/**
 *
 * @author any
 * @time 2019/10/31 17.58
 * @details
 */
class AMainActivity : BaseActivity() {

    private val fragmentAdapter by lazy { NewsFragmentAdapter(supportFragmentManager) }

    override fun getResourceId(): Int = R.layout.a_main_activity

    override fun initView() {
        setStatusBarTransparent(true)
        setTopPadding(topLL)

        viewPager.offscreenPageLimit = fragmentAdapter.count
        viewPager.adapter = fragmentAdapter
    }

    override fun initGetIntent() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun lazyData() {
    }
}