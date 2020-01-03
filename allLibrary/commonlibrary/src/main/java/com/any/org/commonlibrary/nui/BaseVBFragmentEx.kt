package com.any.org.commonlibrary.nui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.any.org.commonlibrary.log.KLog

/**
 *
 * @author any
 * @time 2019/10/31 17.46
 * @details  onCreateView vs onDestroyView
 */
abstract class BaseVBFragmentEx<VB : ViewDataBinding> : BaseFragmentEx() {

    var mBinding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        KLog.e("onCreateView.... ...  $mBinding")
        mBinding = DataBindingUtil.inflate(inflater, getResourceId(), container, false)
        mBinding?.lifecycleOwner = this
        return mBinding?.root
    }


    override fun onDestroyView() {
        // 如果不这样写，存在adapter context 关联 泄漏风险
        // 主要是因为viewModel 生命周期强于 fragment
        mBinding?.unbind()
        val rootView = mBinding?.root as? ViewGroup
        rootView?.let {
            it.removeAllViews()
        }
        mBinding = null
        super.onDestroyView()
        KLog.e("onDestroyView.... .... ")
    }

}