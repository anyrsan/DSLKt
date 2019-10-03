package com.any.org.loginmodule

import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.LOGIN
import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.RxModel
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.provide.ShareDataProvide
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.dslnetlibrary.HttpBaseModel
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.loginmodule.presenter.LoginPresenter
import com.any.routerannotation.KRouter
import com.any.routercompliecore.Router
import kotlinx.android.synthetic.main.login_activity.*

/**
 *
 * @author any
 * @time 2019/9/18 16.34
 * @details
 */
@KRouter(LOGIN)
class LoginActivity : BaseActivity() {

    private var rKey: String? = null

    override fun getResourceId(): Int = R.layout.login_activity

    private val loginPresenter by lazy { LoginPresenter(applicationContext,this) }


    override fun initView() {
    }

    override fun initGetIntent() {
        rKey = intent.getStringExtra("rKey")
    }

    override fun initData() {
        //标记已完成登录操作
        ShareDataProvide.getInstance().hasLogin = true
    }

    override fun initEvent() {

        loginTv.viewOnClick {
            val userName ="a"
            val pwd ="b"
//            loginPresenter.doLogin(userName,pwd){it,errer->   //直接展开，但是方法代码量过多时，建议不要展开
//
//            }
            loginPresenter.doLogin(userName,pwd,::onLoginResult)
        }


        gotoNewsTv.viewOnClick {
            Router.jump(this){
                target = NEWS
            }
        }

    }

    override fun lazyData() {

    }

    //结果回调
    private fun onLoginResult(it:HttpBaseModel?,error:Boolean){


        //处理结果
        it?.let {

            CustomToast.showMsg(applicationContext,"登录成功")

        }

        // 尝试恢复对应的页面
        Router.resumeRouter(rKey)

        RxNewBus.postEvent(RxModel("完成了，可以把数据传回注册的地方"))

        finish()
    }



}