package com.any.org.eanewsmudle.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.any.org.ankolibrary.bindLifecycle
import com.any.org.ankolibrary.subOnlyCode
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.eanewsmudle.R
import com.any.org.eanewsmudle.adapter.NewsItemAdapter
import com.any.org.eanewsmudle.adapter.YLItemAdapter
import com.any.org.eanewsmudle.adapter.decoration.ObserverItemDecoration
import com.any.org.eanewsmudle.databinding.ANewsActivityBinding
import com.any.org.eanewsmudle.databinding.AYlActivityBinding
import com.any.org.eanewsmudle.viewpresenter.LoadPresenter
import com.any.org.eanewsmudle.viewmodel.NewsViewModel
import com.any.org.eanewsmudle.viewmodel.YLViewModel
import kotlinx.android.synthetic.main.a_news_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/25 19.12
 * @details
 */
class YLActivity : BaseVBActivity<AYlActivityBinding>() {

    //完成注入
    private val newsViewModel by viewModel<YLViewModel>()

    //自动关联数据
    private val newsAdapter by lazy { YLItemAdapter(newsViewModel.mList) }
    //截面item
    private val sectionDt by lazy {
        ObserverItemDecoration(
            applicationContext,
            newsViewModel.mList
        )
    }

    override fun getResourceId(): Int = R.layout.a_yl_activity

    override fun initView() {
        setStatusBarTransparent(true)
        setTopPadding(topRL)
        //设置
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = newsAdapter
            addItemDecoration(sectionDt)
            addItemDecoration(VerticalDecoration(applicationContext,mVerticalSpacing = 2),1)
        }
        mBinding.nViewModel = newsViewModel
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

        //刷新
        mBinding.loadPresenter = object : LoadPresenter {
            override fun load(refresh: Boolean) {
                getListData(refresh)
            }
        }

    }

    override fun lazyData() {
        getListData(true)
    }

    private fun getListData(isRefresh: Boolean) {
        //通过生命周期关联
        newsViewModel.getYLNews(isRefresh).bindLifecycle(this)
            .subOnlyCode {
                KLog.e("msg...size... ${newsAdapter.data.size}")
            }
    }

}