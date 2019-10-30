package com.any.org.eanewsmudle.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.any.org.ankolibrary.*
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.ThsItemAdapter
import com.any.org.eanewsmudle.adapter.decoration.ObserverItemDecoration
import com.any.org.eanewsmudle.databinding.AThsActivityBinding
import com.any.org.eanewsmudle.viewmodel.ThsViewModel
import kotlinx.android.synthetic.main.a_news_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/29 17.19
 * @details
 */
class ThsActivity : BaseVBActivity<AThsActivityBinding>() {


    private val thsViewModel by viewModel<ThsViewModel>()

    //自动关联数据
    private val thsAdapter by lazy { ThsItemAdapter(thsViewModel.mList) }
    //截面item
    private val sectionDt by lazy {
        ObserverItemDecoration(
            applicationContext,
            thsViewModel.mList
        )
    }

    override fun getResourceId(): Int = R.layout.a_ths_activity

    override fun initView() {
        setStatusBarTransparent(true)
        setTopPadding(topRL)
        //设置
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = thsAdapter
//            addItemDecoration(sectionDt)
        }
        mBinding.thsVM = thsViewModel
    }

    override fun initGetIntent() {

    }

    override fun initData() {

    }


    override fun initEvent() {
        //处理加载更多
        thsAdapter.setOnLoadMoreListener({
            KLog.e("loadMore")
            getListData(false)
        }, mBinding.recyclerView)

        //处理
        mBinding.refreshLayout.setOnRefreshListener {
            getListData(true)
        }
    }

    override fun lazyData() {
        getListData(true)
    }

    private fun getListData(isRefresh: Boolean) {
        //通过生命周期关联
        thsViewModel.getList(isRefresh).bindLifecycle(this)
            .subOnlyCode {

            }
    }


}