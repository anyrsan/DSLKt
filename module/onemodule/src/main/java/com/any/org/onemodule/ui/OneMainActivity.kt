package com.any.org.onemodule.ui

import android.view.View
import com.any.org.ankolibrary.get
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.commonlibrary.ui.createFragment
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.OneMainAdapter
import com.any.org.onemodule.databinding.OneMainActivityBinding
import com.any.org.onemodule.viewevent.LoadRefreshListener
import com.any.org.onemodule.viewevent.NDViewClick
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

    private val adapter by lazy { OneMainAdapter() }

    override fun getResourceId(): Int = R.layout.one_main_activity

    private var isAdd = false

    private val oneDateFragment by lazy { createFragment(OneDateFragment::class.java.name) }

    private val refreshListener = object : LoadRefreshListener {
        override fun load(refresh: Boolean) {
            oneVM.getOneData(refresh)
        }
    }


    private val ndClick = object : NDViewClick {
        override fun clickView(view: View) {
            KLog.e("msg...  clickView...")
            if (isAdd) {
                mBinding.triangleLbv.animRotation(false)
                removeFragment(oneDateFragment)
            } else {
                mBinding.triangleLbv.animRotation(true)
                //添加fragment
                replaceFragment(oneDateFragment, R.id.oneContainerFl)
            }
            isAdd = !isAdd
        }
    }

    override fun initView() {
        //绑定
        mBinding.vm = oneVM
        mBinding.refreshListener = refreshListener
        mBinding.ndClick = ndClick
        mBinding.adapter = adapter
        //设置数据
        get(oneVM.dataModel) {
            adapter.setNewData(it?.data?.content_list)
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


    override fun lazyData() {

    }

    override fun pxInDp(): Int = 360
}