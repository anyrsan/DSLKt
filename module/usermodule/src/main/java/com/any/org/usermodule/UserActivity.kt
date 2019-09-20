package com.any.org.usermodule

import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.USER
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.routerannotation.KRouter
import com.any.routercompliecore.Router
import kotlinx.android.synthetic.main.user_activity.*

/**
 *
 * @author any
 * @time 2019/9/18 20.23
 * @details
 */
@KRouter(USER)
class UserActivity :BaseActivity(){

    override fun getResourceId(): Int =R.layout.user_activity

    override fun initView() {
    }

    override fun initGetIntent() {
    }

    override fun initData() {
    }

    override fun initEvent() {

        gotoNewsTv.viewOnClick {
            Router.jump(this){
                target = NEWS
                setParams("key" to "来自用户界面")
            }
        }

    }

    override fun lazyData() {
    }

}