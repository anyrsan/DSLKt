package com.any.org.commonlibrary.nui

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @author any
 * @time 2019/12/11 18.05
 * @details
 */
abstract class BaseVBActivityEx<VB : ViewDataBinding> : BaseActivityEx(){

    lateinit var mBinding: VB

    override fun onCreate() {
        mBinding = DataBindingUtil.setContentView(this,getResourceId())
        mBinding.lifecycleOwner = this
    }

    override fun initGetIntent() {

    }

    override fun onDestroy() {
        mBinding.unbind()
        super.onDestroy()
    }


}