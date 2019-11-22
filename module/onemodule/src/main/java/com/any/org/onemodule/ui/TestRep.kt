package com.any.org.onemodule.ui

import com.any.org.ankolibrary.get
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.onemodule.R
import com.any.org.onemodule.databinding.TestActivityBinding
import com.any.org.onemodule.viewmodel.TestViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/11/16 14.20
 * @details
 */
class TestRep : BaseVBActivity<TestActivityBinding>() {

    private val vm by viewModel<TestViewModel>()

    override fun getResourceId(): Int = R.layout.test_activity

    override fun initView() {

        mBinding.vm = vm


        //
        get(vm.articleData){
          KLog.e("msg","${it?.data?.hp_title}")
        }

        get(vm.oneData){
            KLog.e("msg","${it?.data?.content_list?.get(0)?.title}")
        }

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