package com.any.org.onemodule.nui

import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.log.eLog
import com.any.org.commonlibrary.nui.BaseVBFragmentEx
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.MonthMainAdapter
import com.any.org.onemodule.databinding.OneDateFragmentBinding
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.nviewmodel.MonthViewModel
import com.any.org.onemodule.viewevent.LoadScrollListener
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/26 16.03
 * @details 显示日期
 */
class OneDateFragment : BaseVBFragmentEx<OneDateFragmentBinding>(), IAdjustDensity {

    private val monthViewModel by viewModel<MonthViewModel>()

    private val montAdapter by lazy { MonthMainAdapter() }

    private var diffNum = 0

    private val loadListener = object : LoadScrollListener {

        override fun getCurrPosition(position: Int) {
            KLog.e("获取 position 位置  $position")
            val data = montAdapter.getItemModel(position)
            data?.let {
                mBinding?.dateText = it.date
            }
        }

        override fun needLoadMore(load: Boolean) {
            KLog.e("需要加载 了。。。 $load")
            diffNum--
            loadMonthData(diffNum, true)
        }

    }

    override fun getResourceId(): Int = R.layout.one_date_fragment


    override fun initData() {
        //处理数据
        mBinding?.monthVM = monthViewModel
        mBinding?.monthAdapter = montAdapter
        mBinding?.loadListener = loadListener

    }

    override fun lazyData() {
        diffNum = 0
        //获取数据
        loadMonthData(diffNum)
    }


    private fun loadMonthData(diffNum: Int, isAdd: Boolean = false) {
        monthViewModel.getMonthData(Calendar.getInstance().getTargetDate(1, diffNum), isAdd)
    }


    override fun initEvent() {

    }

    override fun pxInDp(): Int = 360

    override fun onDestroy() {
        super.onDestroy()
        "结束".eLog()
    }

}