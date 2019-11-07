package com.any.org.rxviewmodel

import com.any.org.ankolibrary.argument
import com.any.org.commonlibrary.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_two.*

/**
 *
 * @author any
 * @time 2019/11/6 20.10
 * @details
 */
class TwoActivity : BaseActivity() {

    private val text by argument<String>("text")

    override fun getResourceId(): Int = R.layout.activity_two

    override fun initView() {

        twoTv.text = "来自MainActivity的 变量 $text"
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