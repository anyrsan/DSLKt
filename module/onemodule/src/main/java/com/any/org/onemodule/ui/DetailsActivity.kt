package com.any.org.onemodule.ui

import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.BaseVBActivityEx
import com.any.org.commonlibrary.nui.addFragment
import com.any.org.commonlibrary.nui.setTopPadding
import com.any.org.commonlibrary.ui.createFragment
import com.any.org.onemodule.R
import com.any.org.onemodule.databinding.MonthActivityBinding

/**
 *
 * @author any
 * @time 2019/12/11 14.21
 * @details
 */
class DetailsActivity : BaseVBActivityEx<MonthActivityBinding>(),IAdjustDensity {

    override fun getResourceId(): Int = R.layout.month_activity

    private val oneDateFragment by lazy { createFragment(DetailsContentFragment::class.java.name) }

    override fun initView() {
        addFragment(oneDateFragment, R.id.monthContainerLl)
        mBinding.monthContainerLl.setTopPadding()
        KLog.e("msg ...  initView...")
    }

    override fun initGetIntent() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun lazyData() {
    }

    override fun pxInDp(): Int  = 360
}