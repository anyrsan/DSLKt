package com.any.org.eanewsmudle.ui

import com.any.org.ankolibrary.*
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.databinding.ATestActivityBinding
import com.any.org.eanewsmudle.model.bean.NewsItemModel
import com.any.org.eanewsmudle.model.bean.NewsModel
import com.any.org.eanewsmudle.viewmodel.TestViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 *
 * @author any
 * @time 2019/10/29 17.19
 * @details
 */
class TestActivity : BaseVBActivity<ATestActivityBinding>() {


    //获取 item
    private val itemModel: NewsItemModel by argument("item")
    // 进行初始化
    private val testViewModel by viewModel<TestViewModel> { parametersOf(application,itemModel) }

    override fun getResourceId(): Int = R.layout.a_test_activity


    override fun initData() {
        //完成订阅关系
        testViewModel.getDetails().async(1000).bindLifecycle(this).subResult(::onResult)
    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun lazyData() {

    }

    private fun onResult(t:NewsModel?,message:String?,erroCode:Int){

    }


}