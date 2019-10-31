package com.any.org.commonlibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * @author any
 * @time 2019/10/31 17.46
 * @details
 */
abstract class BaseVBFragment<VB : ViewDataBinding> : BaseFragment() {

    var mBinding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getResourceId(), container, false)
//        mBinding?.lifecycleOwner = this
        return mBinding?.root
    }

}