package com.any.org.eanewsmudle.ui.deprecated

import android.view.View
import androidx.lifecycle.Observer
import com.any.org.ankolibrary.bindLifecycle
import com.any.org.ankolibrary.subOnlyCode
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.YlItemAdapter
import com.any.org.eanewsmudle.adapter.decoration.ObserverItemDecoration
import com.any.org.eanewsmudle.databinding.ANewsActivityBinding
import com.any.org.eanewsmudle.model.remote.ARemoteProvider
import com.any.org.eanewsmudle.model.repository.ANewsRepository
import com.any.org.eanewsmudle.viewmodel.RxYlViewModel
import com.any.org.eanewsmudle.viewmodel.YlViewModel
import com.any.org.eanewsmudle.viewpresenter.LoadRefreshListener
import com.any.org.eanewsmudle.viewpresenter.NDViewClickListener
import com.any.org.eanewsmudle.viewpresenter.OnScrollListener
import com.any.org.eanewsmudle.viewpresenter.vpresenter.NewPresenter
import kotlinx.android.synthetic.main.a_news_activity.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/25 19.12
 * @details
 */
class ANewsActivity : BaseVBActivity<ANewsActivityBinding>() {

    //完成注入 sina
//    private val newsViewModel by viewModel<NewsViewModel>()
//    private val newsAdapter by lazy { SnItemAdapter(newsViewModel.mList) }

    private lateinit var api: ARemoteProvider.NewsApi

    private val apiT by inject<ARemoteProvider.NewsApi> ()

//
//    private val rep by inject<ANewsRepository> ()
//    private val rxYlVM by lazy { RxYlViewModel(rep) }


    //完成注入 yL
    private val newsViewModel by viewModel<YlViewModel>()
    private val newsAdapter by lazy { YlItemAdapter(newsViewModel.mList) }

    //自动关联数据
    //截面item
    private val sectionDt by lazy {
        ObserverItemDecoration(
            applicationContext,
            newsViewModel.mList
        )
    }

    override fun getResourceId(): Int = R.layout.a_news_activity

    private val clickListener=object :NDViewClickListener{
        override fun click(v: View) {
            KLog.e("回到顶点")
            mBinding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private val loadRefreshListener = object :LoadRefreshListener{
        override fun load(refresh: Boolean) {
            getListData(refresh)
        }
    }

    private val onScrollListener = object :OnScrollListener{
        override fun viewIsGone(gone: Boolean) {
            newsViewModel.isGone.set(gone)
        }
    }

    override fun initView() {

        api = get()

        //设置线条
        mBinding.decoration= sectionDt
        // adapter
        mBinding.adapter = newsAdapter
        // viewmodel
        mBinding.vm = newsViewModel
        // presenter
        mBinding.presenter = NewPresenter(clickListener,loadRefreshListener,onScrollListener)
        setStatusBarTransparent(true)
        setTopPadding(topRL)

//
//        //
//        rxYlVM.dataL.observe(this, Observer{
//
//        })

    }

    override fun initGetIntent() {

    }

    override fun initData() {

    }

    override fun initEvent() {
        //处理加载更多
        newsAdapter.setOnLoadMoreListener({
            KLog.e("loadMore")
            getListData(false)
        }, mBinding.recyclerView)


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