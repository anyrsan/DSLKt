package com.any.org.newsmodule.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.ui.BaseFargment
import com.any.org.commonlibrary.utils.DensityUtil
import com.any.org.commonlibrary.widget.SectionItemDecoration
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.newsmodule.R
import com.any.org.newsmodule.adapter.NewsThsAdapter
import com.any.org.newsmodule.iview.IFragment
import com.any.org.newsmodule.model.ThsItemModel
import com.any.org.newsmodule.presenter.NewsPresenter
import kotlinx.android.synthetic.main.news_fragment.*

/**
 *
 * @author any
 * @time 2019/10/21 15.37
 * @details  ths 要闻
 */
class ThsFragment : BaseFargment(), IFragment {

    var onScrollListener: ((Boolean) -> Unit)? = null

    override fun getTile(): String = "同花顺"

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }
    companion object{
        val INSTANCE by lazy { ThsFragment() }
    }


    private var currPage: Int= 1

    private val newsAdapter by lazy { NewsThsAdapter() }

    private lateinit var presenter: NewsPresenter

    private lateinit var sectionDt: SectionItemDecoration


    override fun getResourceId(): Int = R.layout.news_fragment

    override fun initData() {
        presenter = NewsPresenter(requireContext(), this)

        val layoutManager = LinearLayoutManager(requireContext())


  //      recyclerView.addItemDecoration(VerticalDecoration(context=requireContext(),mVerticalSpacing = DensityUtil.dp2px(resources,0.5f)))
        sectionDt = SectionItemDecoration(requireContext(),null)
        //添加横切
        recyclerView.addItemDecoration(sectionDt)


        recyclerView.layoutManager = layoutManager

        //关联
        recyclerView.adapter = newsAdapter
    }


    override fun initEvent() {

        //加载更多
        newsAdapter.setOnLoadMoreListener({
            currPage++
            loadNewsData(currPage, true)
        }, recyclerView)

        //刷新
        refreshLayout.setOnRefreshListener {
            currPage = 1
            loadNewsData(currPage,  false)
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
        loadNewsData(currPage)
    }

    //处理数据
    private fun loadNewsData(page: Int, hasMore: Boolean = false) {
        presenter.getThsList(page) {
            handlerResultN(it,hasMore)
        }
    }


    //处理数据
    private fun handlerResultN(it: List<ThsItemModel>?, hasMore: Boolean) {
        //先处理是否为错误
        it?.let {
            //添加数据
            if (hasMore) {
                newsAdapter.addData(it)
            } else {  //更新数据
                newsAdapter.setNewData(it)
            }
            //同步数据
            sectionDt.setData(newsAdapter.data as List<SectionModel>?)
            //获取后一个Id
        }
        //加载完成
        if (hasMore) {
            newsAdapter.loadMoreComplete()
        }
        refreshLayout.isRefreshing = false
    }



}