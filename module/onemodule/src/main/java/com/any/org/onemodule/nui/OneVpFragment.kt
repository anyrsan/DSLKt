package com.any.org.onemodule.nui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.BaseVBFragmentEx
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.OneMainAdapter
import com.any.org.onemodule.databinding.OneVpFragmentBinding
import com.any.org.onemodule.nviewmodel.OneVpNViewModel
import com.any.org.onemodule.viewevent.LoadRefreshListener
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/12/16 17.47
 * @details 日期
 */
class OneVpFragment : BaseVBFragmentEx<OneVpFragmentBinding>() {

    private val vm: OneVpNViewModel by viewModel()

    private val adapter by lazy { OneMainAdapter() }

    private var mDate: String? = null

    companion object {
        fun getInstance(date: String): Fragment {
            val bundle = Bundle()
            bundle.putString("date", date)
            val f = OneVpFragment()
            f.arguments = bundle
            KLog.e("init.... $date")
            return f
        }
    }

    private val refreshListener = object : LoadRefreshListener {
        override fun load(refresh: Boolean) {
            loadOneData(refresh)
        }

    }


    override fun getResourceId(): Int = R.layout.one_vp_fragment

    override fun initData() {
        mDate = arguments?.getString("date")
        KLog.e("date...$arguments   $mDate    $vm")
        mBinding?.let {
            it.mDate = mDate
            it.vm = vm
            it.refreshListener = refreshListener
            it.adapter = adapter
        }
    }

    override fun lazyData() {
        loadOneData(true)
    }

    override fun initEvent() {

    }

    private fun loadOneData(isRefresh: Boolean) {
        mDate?.let {
            vm.getDefaultData(isRefresh, it)
        }
    }


    override fun onDestroyView() {
        //断开关系
        mBinding?.adapter = null
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        KLog.e("页面分离了")
    }

    override fun onDestroy() {
        super.onDestroy()
        KLog.e("页面销毁了")
    }
}