package com.any.org.commonlibrary.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @author any
 * @time 2019/10/25 19.16
 * @details
 */
abstract class BaseVBActivity<VB : ViewDataBinding> : BaseActivity() {

    val mBinding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getResourceId()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.lifecycleOwner = this  //绑定生命周期
    }


    override fun initGetIntent() {

    }

    override fun initEvent() {

    }


    override fun onDestroy() {
        mBinding.unbind()
        super.onDestroy()
    }

}