package com.any.org.newsmodule.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.ui.BaseFragment
import com.any.org.commonlibrary.widget.SectionItemDecoration
import com.any.org.newsmodule.R
import com.any.org.newsmodule.adapter.NewsListAdapter
import com.any.org.newsmodule.iview.IFragment
import com.any.org.newsmodule.model.NewsModel
import com.any.org.newsmodule.presenter.NewsPresenter
import kotlinx.android.synthetic.main.news_fragment.*

/**
 *
 * @author any
 * @time 2019/10/21 15.37
 * @details  sina 要闻
 */
class SinaFragment : BaseFragment(), IFragment {

    var onScrollListener: ((Boolean) -> Unit)? = null

    override fun getTile(): String = "新浪"

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    companion object {
        val INSTANCE by lazy { SinaFragment() }
    }


    private var lastId: Int? = null

    private val newsAdapter by lazy { NewsListAdapter() }

    private lateinit var presenter: NewsPresenter

    private lateinit var sectionDt: SectionItemDecoration


    override fun getResourceId(): Int = R.layout.news_fragment

    override fun initData() {
        presenter = NewsPresenter(requireContext(), this)

        val layoutManager = LinearLayoutManager(requireContext())
        //recyclerView.addItemDecoration(VerticalDecoration(context=requireContext(),mVerticalSpacing = DensityUtil.dp2px(resources,0.5f)))

        sectionDt = SectionItemDecoration(requireContext(), null)
        //添加横切
        recyclerView.addItemDecoration(sectionDt)

        recyclerView.layoutManager = layoutManager

        //关联
        recyclerView.adapter = newsAdapter


    }


    override fun initEvent() {

        //加载更多
        newsAdapter.setOnLoadMoreListener({
            loadNewsData(lastId, null, true)
        }, recyclerView)

        //刷新
        refreshLayout.setOnRefreshListener {
            lastId = null
            loadNewsData(null, null, false)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dy > 0 时为向上滚动
                //dy < 0 时为向下滚动
                onScrollListener?.invoke(dy <= 0)
            }

        })
    }


    override fun lazyData() {
        refreshLayout.isRefreshing = true
        loadNewsData(null, null)
    }

    //处理数据
    private fun loadNewsData(id: Int?, timestamp: String?, hasMore: Boolean = false) {
        presenter.getList(id) { it, error ->
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
            //同步数据
            sectionDt.setData(newsAdapter.data as List<SectionModel>?)
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