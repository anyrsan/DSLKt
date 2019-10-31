package com.any.org.eanewsmudle.ui

import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.eanewsmudle.R

/**
 *
 * @author any
 * @time 2019/10/31 17.58
 * @details
 */
class AMainActivity : BaseActivity() {

    override fun getResourceId(): Int =R.layout.a_main_activity

    override fun initView() {
        addFragment(AMainFragment(),R.id.mainContainerRl)
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