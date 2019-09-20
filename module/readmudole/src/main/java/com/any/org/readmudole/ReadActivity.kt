package com.any.org.readmudole

import com.any.org.commonlibrary.READ
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.routerannotation.KRouter

/**
 *
 * @author any
 * @time 2019/9/18 20.21
 * @details
 */
@KRouter(READ)
class ReadActivity :BaseActivity(){
    override fun getResourceId(): Int = R.layout.read_activity

    override fun initView() {
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