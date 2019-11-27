package com.any.org.onemodule.ui

import com.any.org.ankolibrary.get
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBFragment
import com.any.org.commonlibrary.widget.SectionTitleItemDecoration
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.MonthMainAdapter
import com.any.org.onemodule.adapter.MonthSubAdapter
import com.any.org.onemodule.databinding.OneDateFragmentBinding
import com.any.org.onemodule.extend.getTargetDate
import com.any.org.onemodule.model.OneMonthModel
import com.any.org.onemodule.viewevent.LoadScrollListener
import com.any.org.onemodule.viewmodel.OneViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/26 16.03
 * @details
 */
class OneDateFragment : BaseVBFragment<OneDateFragmentBinding>(), IAdjustDensity {

    private val oneVM by viewModel<OneViewModel>()

    private val montAdapter by lazy { MonthMainAdapter() }

    private var diffNum = 0

    private val loadListener = object : LoadScrollListener{

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
            loadMonthData(diffNum,true)
        }

    }

    override fun getResourceId(): Int = R.layout.one_date_fragment

    override fun initData() {
        mBinding?.monthAdapter = montAdapter
        mBinding?.loadListener = loadListener

        //处理数据
        get(oneVM.dataMonths) {
            KLog.e("msg....   数据  ${it?.size}")
            montAdapter.setNewData(it)
        }

    }

    override fun lazyData() {
        diffNum =0
        //获取数据
        loadMonthData(diffNum)

    }


    private fun loadMonthData(diffNum:Int,isAdd:Boolean=false){
        oneVM.getMonthData(Calendar.getInstance().getTargetDate(1,diffNum),isAdd)
    }


    override fun initEvent() {

    }

    override fun pxInDp(): Int = 360
}