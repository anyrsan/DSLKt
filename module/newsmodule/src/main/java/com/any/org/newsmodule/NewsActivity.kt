package com.any.org.newsmodule

import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.model.SectionModel
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.commonlibrary.widget.SectionItemDecoration
import com.any.org.commonlibrary.widget.VerticalDecoration
import com.any.org.newsmodule.adapter.NewsFragmentAdapter
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

    private val fragmentAdapter by lazy { NewsFragmentAdapter(supportFragmentManager) }

    override fun getResourceId(): Int = R.layout.news_activity


    override fun lazyData() {}
    override fun initView() {}
    override fun initGetIntent() {
        val key = intent.getStringExtra("key")
        Log.e("msg", "输出key  $key")

    }

    override fun initData() {
        setStatusBarTransparent(true)
        setTopPadding(topRL)

        viewPager.adapter = fragmentAdapter


        // 设置tab背景色
        pagerTab.setBackgroundColor(Color.WHITE)
        // 设置下划线颜色
        pagerTab.tabIndicatorColor = resources.getColor(R.color.color_ffffff)
        pagerTab.setTextColor(resources.getColor(R.color.color_e50000))
        pagerTab.drawFullUnderline = false
    }

    override fun initEvent() {
        //点击事件
        newsTv.viewOnClick {
            finish()
        }

        //返回顶部
        backTopTv.viewOnClick {
            fragmentAdapter.scrollTop()
        }
    }


}