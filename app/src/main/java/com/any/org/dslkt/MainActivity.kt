package com.any.org.dslkt


import com.any.org.commonlibrary.*
import com.any.org.commonlibrary.event.viewOnClick
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.eventbuslibrary.RxNewBus
import com.any.org.eventbuslibrary.ktanno.Subscribe
import com.any.org.eventbuslibrary.ktanno.ThreadModel
import com.any.routercompliecore.Router
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getResourceId(): Int = R.layout.activity_main

    override fun initView() {
    }

    override fun initGetIntent() {
    }

    override fun initData() {

        RxNewBus.registerEvent(this)

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
                requestCode = 1000
                callDefaultBack {
                    KLog.e("我是自己实现用户页面加载判断")
                }
            }
        }

        gotoANew.viewOnClick {
            Router.jump(this){
                target = MVVM
            }
        }

    }

    //延时加载
    override fun lazyData() {

    }


    @Subscribe(threadModel = ThreadModel.MAIN)
    fun getData(userInfo: RxModel) {
        KLog.e("接收来自login的数据回传  $userInfo")
    }

    override fun onDestroy() {
        RxNewBus.unRegisterEvent(this)
        super.onDestroy()
    }
}
