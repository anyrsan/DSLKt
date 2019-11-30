package com.any.org.onemodule.ui

import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.onemodule.R
import com.any.org.onemodule.databinding.TestActivityBinding
import com.any.org.onemodule.viewmodel.OneVM
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/11/29 18.57
 * @details
 */
class TestActivity : BaseVBActivity<TestActivityBinding>() {

    private val oneVm  by viewModel<OneVM>()

    override fun getResourceId(): Int = R.layout.test_activity

    override fun initView() {
    }

    override fun initData() {
    }

    override fun lazyData() {

        oneVm.getData()

    }
}