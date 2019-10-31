package com.any.org.eanewsmudle.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import com.any.org.ankolibrary.bindLifecycle
import com.any.org.ankolibrary.subOnlyCode
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.ui.BaseVBFragment
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.BaseItemAdapter
import com.any.org.eanewsmudle.adapter.decoration.ObserverItemDecoration
import com.any.org.eanewsmudle.databinding.ANewsActivityBinding
import com.any.org.eanewsmudle.databinding.ANewsFragmentBinding
import com.any.org.eanewsmudle.viewmodel.BaseViewModel
import com.any.org.eanewsmudle.viewpresenter.LoadRefreshListener
import com.any.org.eanewsmudle.viewpresenter.NDViewClickListener
import com.any.org.eanewsmudle.viewpresenter.OnScrollListener
import com.any.org.eanewsmudle.viewpresenter.vpresenter.NewPresenter

/**
 *
 * @author any
 * @time 2019/10/31 16.44
 * @details
 */
abstract class NewsBaseFragment<T : SectionModel, M, V : ViewDataBinding> :
    BaseVBFragment<ANewsFragmentBinding>() {

    abstract fun getViewModel(): BaseViewModel<T, M>

    abstract fun getItemAdapter(): BaseItemAdapter<T, V>

    val newsViewModel by lazy { getViewModel() }

    private val newsAdapter by lazy { getItemAdapter() }


    private val sectionDt by lazy {
        ObserverItemDecoration(
            requireContext(),
            newsViewModel.mList
        )
    }

    override fun getResourceId(): Int = R.layout.a_news_fragment

    private val clickListener = object : NDViewClickListener {
        override fun click(v: View) {
            KLog.e("回到顶点")
            mBinding?.recyclerView?.smoothScrollToPosition(0)
        }
    }

    private val loadRefreshListener = object : LoadRefreshListener {
        override fun load(refresh: Boolean) {
            getListData(refresh)
        }
    }

    private val onScrollListener = object : OnScrollListener {
        override fun viewIsGone(gone: Boolean) {
            newsViewModel.isGone.set(gone)
        }
    }


    override fun initData() {
        //设置线条
        mBinding?.decoration = sectionDt
        // adapter
        mBinding?.adapter = newsAdapter
        // viewmodel
        mBinding?.vm = newsViewModel
        // presenter
        mBinding?.presenter = NewPresenter(clickListener, loadRefreshListener, onScrollListener)
    }

    override fun initEvent() {
        //处理加载更多
        newsAdapter.setOnLoadMoreListener({
            KLog.e("loadMore")
            getListData(false)
        }, mBinding?.recyclerView)

    }

    override fun lazyData() {
        getListData(true)
    }

    private fun getListData(isRefresh: Boolean) {
        //通过生命周期关联
        newsViewModel.freshData(isRefresh).bindLifecycle(this)
            .subOnlyCode {
                KLog.e("msg...size... ${newsAdapter.data.size}")
            }
    }
}