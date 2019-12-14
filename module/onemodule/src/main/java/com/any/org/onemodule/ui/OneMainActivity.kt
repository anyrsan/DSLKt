package com.any.org.onemodule.ui

import android.view.View
import com.any.org.ankolibrary.get
import com.any.org.ankolibrary.startActivity
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.*
import com.any.org.commonlibrary.ui.createFragment
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.eventbuslibrary.ktanno.Subscribe
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.OneMainAdapter
import com.any.org.onemodule.databinding.OneMainActivityBinding
import com.any.org.onemodule.model.OneDataItemModel
import com.any.org.onemodule.model.OneMonthSubModel
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
class OneMainActivity : BaseVBActivityEx<OneMainActivityBinding>(), IAdjustDensity {

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
            handlerOneFragment()
        }
    }

    private fun handlerOneFragment() {
        KLog.e("msg...  clickView...")
        if (isAdd) {
            mBinding.triangleLbv.animRotation(false)
//            removeFragment(oneDateFragment)
            hideFragment(oneDateFragment)
        } else {
            mBinding.triangleLbv.animRotation(true)
            //添加fragment
//            addFragment(oneDateFragment, R.id.oneContainerFl)
            addOrShowFragment(oneDateFragment, R.id.oneContainerFl)
        }
        isAdd = !isAdd
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
            //回到顶部
            scrollToTop()
        }
        // 处理数据
        mBinding.topRl.setTopPadding()
        RxNewBus.registerEvent(this)
    }

    private fun scrollToTop() {
        mBinding.oneRecycleView.scrollToPosition(0)
    }

    override fun initData() {

    }

    override fun initEvent() {

    }


    override fun lazyData() {
        oneVM.getOneData(true)
    }

    override fun pxInDp(): Int = 360


    //处理事件
    @Subscribe
    fun loadDate(t: OneMonthSubModel) {
        handlerOneFragment()
        KLog.e("收到事件了。。。 $t")
        oneVM.getOneData(true, t.date)
    }


    //点击事件
    @Subscribe
    fun clickOneItemModel(t: OneDataItemModel) {
        KLog.e("加载数据。。。 $t")
        when (t.category) {
            "0" -> {
                KLog.e("可以不处理")
            }
            "1" -> {
                KLog.e("去阅读")
                startActivity<DetailsActivity>("itemId" to t.item_id, "category" to t.category)

            }
            "2" -> {
                startActivity<DetailsActivity>("itemId" to t.item_id, "category" to t.category)
                KLog.e("连载")
            }
            "3" -> {
                KLog.e("问答")
            }
            "4" -> {
                KLog.e("音乐")
            }
            "5" -> {
                KLog.e("影视")
            }
            "8" -> {
                KLog.e("电台")
            }
        }


    }


    override fun onDestroy() {
        RxNewBus.unRegisterEvent(this)
        super.onDestroy()
    }

}