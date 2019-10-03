package com.any.org.newsmodule

import android.util.Log
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.ui.BaseActivity
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

    private lateinit var presenter: NewsPresenter

    override fun getResourceId(): Int = R.layout.news_activity

    override fun initView() {
    }

    override fun initGetIntent() {

        val key = intent.getStringExtra("key")
        Log.e("msg","输出key  $key")


    }

    override fun initData() {
        presenter = NewsPresenter(this,this)
    }

    override fun initEvent() {



        getNewsListTv.viewOnClick {
            val token =""
            presenter.getList(token){it,error->
                //处理逻辑
                CustomToast.showMsg(applicationContext,"请求列表成功")

            }
        }

    }

    override fun lazyData() {
    }
}