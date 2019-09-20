package com.any.org.dslkt


import com.any.org.commonlibrary.LOGIN
import com.any.org.commonlibrary.NEWS
import com.any.org.commonlibrary.READ
import com.any.org.commonlibrary.USER
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.routercompliecore.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getResourceId(): Int = R.layout.activity_main

    override fun initView() {
    }

    override fun initGetIntent() {
    }

    override fun initData() {
    }

    override fun initEvent() {

        gotoLogin.viewOnClick {
            Router.jump(this) {
                target = LOGIN
                setParams("key" to 1, "value" to 2)
            }
        }



        gotoNews.viewOnClick {
            Router.jump(this) {
                target = NEWS
            }
        }

        gotoRead.viewOnClick {
            Router.jump(this) {
                target = READ

            }
        }


        gotoUser.viewOnClick {
            Router.jump(this) {
                target = USER
                callDefaultBack {
                    KLog.e("我是自己实现用户页面加载判断")
                }
            }
        }

    }

    //延时加载
    override fun lazyData() {

    }

}
