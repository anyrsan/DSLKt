package com.any.org.eanewsmudle

import androidx.recyclerview.widget.LinearLayoutManager
import com.any.org.ankolibrary.bindLifecycle
import com.any.org.ankolibrary.subOnlyCode
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseVBActivity
import com.any.org.eanewsmudle.adapter.NewsItemAdapter
import com.any.org.eanewsmudle.adapter.decoration.ObserverItemDecoration
import com.any.org.eanewsmudle.databinding.ANewsActivityBinding
import com.any.org.eanewsmudle.viewcmd.LoadPresenter
import com.any.org.eanewsmudle.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.a_news_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *
 * @author any
 * @time 2019/10/25 19.12
 * @details
 */
class ANewsActivity : BaseVBActivity<ANewsActivityBinding>() {

    //完成注入
    private val newsViewModel by viewModel<NewsViewModel>()

    //自动关联数据
    private val newsAdapter by lazy { NewsItemAdapter(newsViewModel.mList) }
    //截面item
    private val sectionDt by lazy {
        ObserverItemDecoration(
            applicationContext,
            newsViewModel.mList
        )
    }

    override fun getResourceId(): Int = R.layout.a_news_activity

    override fun initView() {
        setStatusBarTransparent(true)
        setTopPadding(topRL)
        //设置
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = newsAdapter
            addItemDecoration(sectionDt)
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
        newsViewModel.getList(isRefresh).bindLifecycle(this)
            .subOnlyCode {
                KLog.e("msg...size... ${newsAdapter.data.size}")
                if (it != 0) {
                    newsViewModel.empty.set(true)
                }
            }
    }

}