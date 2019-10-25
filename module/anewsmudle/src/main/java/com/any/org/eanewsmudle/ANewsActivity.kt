package com.any.org.eanewsmudle

import com.any.org.commonlibrary.ui.BaseActivity
import kotlinx.android.synthetic.main.a_news_activity.*

/**
 *
 * @author any
 * @time 2019/10/25 19.12
 * @details
 */
class ANewsActivity :BaseActivity(){

    override fun getResourceId(): Int = R.layout.a_news_activity

    override fun initView() {
        setStatusBarTransparent(true)
        setTopPadding(topRL)
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