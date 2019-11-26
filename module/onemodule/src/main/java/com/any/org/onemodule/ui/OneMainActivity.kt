package com.any.org.onemodule.ui

import com.any.org.ankolibrary.get
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.OneAdapter
import com.any.org.onemodule.databinding.OneMainActivityBinding
import com.any.org.onemodule.viewevent.LoadRefreshListener
import com.any.org.onemodule.viewmodel.OneViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/11/25 16.34
 * @details
 */
class OneMainActivity : BaseVBActivity<OneMainActivityBinding>(), IAdjustDensity {

    private val oneVM by viewModel<OneViewModel>()

    private val adapter by lazy { OneAdapter(null) }

    override fun getResourceId(): Int = R.layout.one_main_activity

    private val refreshListener = object : LoadRefreshListener {
        override fun load(refresh: Boolean) {
            oneVM.getOneData(refresh)
        }
    }

    override fun initView() {
        //绑定
        mBinding.vm = oneVM
        mBinding.refreshListener = refreshListener
        mBinding.adapter = adapter
        //设置数据
        get(oneVM.dataModel) {
            adapter.setData(it?.data?.content_list)
            mBinding.date = it?.data?.date
            mBinding.weather = it?.data?.weather
        }
        // 处理数据
        setStatusBarTransparent(true)
        setTopPadding(mBinding.topRl)

    }

    override fun initData() {
        oneVM.getOneData(true)
    }

    override fun initEvent() {
    }

    override fun lazyData() {
    }

    override fun pxInDp(): Int = 360
}