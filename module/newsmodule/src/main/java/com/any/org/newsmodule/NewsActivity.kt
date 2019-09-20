package com.any.org.newsmodule

import android.util.Log
import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.routerannotation.KRouter

/**
 *
 * @author any
 * @time 2019/9/18 20.20
 * @details
 */
@KRouter(NEWS)
class NewsActivity : BaseActivity() {
    override fun getResourceId(): Int = R.layout.news_activity

    override fun initView() {
    }

    override fun initGetIntent() {

        val key = intent.getStringExtra("key")
        Log.e("msg","输出key  $key")

    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun lazyData() {
    }
}