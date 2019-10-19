package com.any.org.newsmodule

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.commonlibrary.utils.DensityUtil
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.newsmodule.adapter.NewsListAdapter
import com.any.org.newsmodule.model.NewsModel
import com.any.org.newsmodule.presenter.NewsPresenter
import com.any.routerannotation.KRouter
import kotlinx.android.synthetic.main.news_activity.*

/**
 *
 * @author any
 * @time 2019/9/18 20.20
 * @details
 */
@KRouter(NEWS)
class NewsActivity : BaseActivity() {

    private var lastId: Int? = null

    private val newsAdapter by lazy { NewsListAdapter() }

    private lateinit var presenter: NewsPresenter

    override fun getResourceId(): Int = R.layout.news_activity

    override fun initView() {
    }

    override fun initGetIntent() {
        val key = intent.getStringExtra("key")
        Log.e("msg", "输出key  $key")

    }

    override fun initData() {
        presenter = NewsPresenter(this, this)

        val layoutManager = LinearLayoutManager(this)

        recyclerView.addItemDecoration(
            VerticalDecoration(
                applicationContext,
                DensityUtil.dp2px(resources, 5f),
                DensityUtil.dp2px(resources,5f),
                DensityUtil.dp2px(resources,5f)
            )
        )

        recyclerView.layoutManager = layoutManager


        //关联
        recyclerView.adapter = newsAdapter


    }

    override fun initEvent() {
        //点击事件
        newsTv.viewOnClick {
            finish()
        }
        //加载更多
        newsAdapter.setOnLoadMoreListener({
            loadNewsData(lastId, null, true)
        }, recyclerView)

        //刷新
        refreshLayout.setOnRefreshListener {
            lastId = null
            loadNewsData(null, null, false)
        }

    }

    override fun lazyData() {
        refreshLayout.isRefreshing = true
        loadNewsData(null, null)
    }

    //处理数据
    private fun loadNewsData(id: Int?, timestamp: String?, hasMore: Boolean = false) {
        presenter.getList(id, timestamp) { it, error ->
            handlerResultN(it, error, hasMore)
        }
    }


    //处理数据
    private fun handlerResultN(it: NewsModel?, error: Boolean, hasMore: Boolean) {
        //先处理是否为错误
        it?.result?.data?.feed?.list?.let {
            it.forEach { model ->
                KLog.e("输出获取结果 ${model.id}")
            }
            //添加数据
            if (hasMore) {
                newsAdapter.addData(it)
            } else {  //更新数据
                newsAdapter.setNewData(it)
            }
            //获取后一个Id
            lastId = it.last().id
        }
        //加载完成
        if (hasMore) {
            newsAdapter.loadMoreComplete()
        }

        refreshLayout.isRefreshing = false
    }

}