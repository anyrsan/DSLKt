package com.any.org.onemodule.nui

import android.view.View
import com.any.org.ankolibrary.get
import com.any.org.ankolibrary.startActivity
import com.any.org.commonlibrary.auto.IAdjustDensity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.nui.BaseVBActivityEx
import com.any.org.commonlibrary.nui.addOrShowFragment
import com.any.org.commonlibrary.nui.hideFragment
import com.any.org.commonlibrary.nui.setTopPadding
import com.any.org.commonlibrary.ui.createFragment
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.eventbuslibrary.ktanno.Subscribe
import com.any.org.onemodule.R
import com.any.org.onemodule.adapter.ViewPagerAdapter
import com.any.org.onemodule.databinding.OneVpMainActivityBinding
import com.any.org.onemodule.extend.StringEx
import com.any.org.onemodule.model.OneDataItemModel
import com.any.org.onemodule.model.OneMonthSubModel
import com.any.org.onemodule.nviewmodel.OneVpNViewModel
import com.any.org.onemodule.viewevent.LoadScrollListener
import com.any.org.onemodule.viewevent.NDViewClick
import org.koin.android.viewmodel.ext.android.viewModel


/**
 *
 * @author any
 * @time 2019/12/16 17.34
 * @details app主页
 */
class OneVPMainActivity : BaseVBActivityEx<OneVpMainActivityBinding>(), IAdjustDensity {


    private val listData = mutableListOf<String>()

    private val oneVpVM by viewModel<OneVpNViewModel>()

    //建议用viewpager, 发现viewpager2事件解决不太好，滑动有冲突
    private val vpAdapter by lazy { ViewPagerAdapter(listData, supportFragmentManager) }

    private var currMonth = 0

    private var isAdd = false

    private val oneDateFragment by lazy { createFragment(OneDateFragment::class.java.name) }


    private val onLoadListener = object : LoadScrollListener {
        override fun getCurrPosition(position: Int) {
            val title = vpAdapter.getTitle(position)
            StringEx.showTitleDate(title, mBinding.oneDateTv)
        }

        override fun needLoadMore(load: Boolean) {
            if (load) {
                currMonth--
                getListData()
            }
            KLog.e("need load more ...   $load")
        }
    }

    private val ndViewClick = object : NDViewClick {
        override fun clickView(view: View) {
            handlerOneFragment()
        }
    }


    private fun handlerOneFragment() {
        KLog.e("msg...  clickView...")
        if (isAdd) {
            mBinding.triangleLbv.animRotation(false)
            hideFragment(oneDateFragment)
        } else {
            mBinding.triangleLbv.animRotation(true)
            //添加fragment
            addOrShowFragment(oneDateFragment, R.id.oneVpContainerFl)
        }
        isAdd = !isAdd
    }

    override fun getResourceId(): Int = R.layout.one_vp_main_activity

    override fun initView() {
        mBinding.vm = oneVpVM
        mBinding.adapter = vpAdapter
        mBinding.loadListener = onLoadListener
        mBinding.ndClick = ndViewClick
        mBinding.topRl.setTopPadding()
        //更新数据
        get(oneVpVM.listDate) {
            it?.let { list ->
                listData.addAll(list)
                vpAdapter.notifyDataSetChanged()
                KLog.e("msg.... ... ... $list")
            }
        }
        KLog.e("initView.... $oneVpVM")
    }

    override fun initData() {
        RxNewBus.registerEvent(this)
    }

    override fun initEvent() {
    }

    override fun lazyData() {
        currMonth = 0
        oneVpVM.weaterData()
        getListData()
    }


    private fun getListData() {
        oneVpVM.getListDate(currMonth)
    }

    override fun pxInDp(): Int = 360



    //处理事件
    @Subscribe
    fun loadDate(t: OneMonthSubModel) {
        handlerOneFragment()
        KLog.e("收到事件了。。。 $t")
//        oneVM.getOneData(true, t.date)
        // 找出下标，然后滑动到指定的位置，找不到说明集合列表没有加载到指定位置，导致的原因
        // 可以对比，加载出来，还有一种由于api的设计原因，代码的实现可以换其它方式
        oneVpVM.gotoPositionPage(t.date)
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
                startActivity<NDetailsActivity>("itemId" to t.item_id, "category" to t.category)
            }
            "2" -> {
                startActivity<NDetailsActivity>("itemId" to t.item_id, "category" to t.category)
                KLog.e("连载")
            }
            "3" -> {
                startActivity<NDetailsActivity>("itemId" to t.item_id, "category" to t.category)
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