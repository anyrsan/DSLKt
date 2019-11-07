package com.any.org.rxviewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.any.org.ankolibrary.startActivity
import com.any.org.commonlibrary.log.KLog
import com.any.org.commonlibrary.ui.BaseActivity
import com.any.org.rxviewmodel.viewmodel.DataViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val dataVM by viewModel<DataViewModel>()

    override fun getResourceId(): Int = R.layout.activity_main

    override fun initView() {

        //获取权限
        RxPermissions(this).request("android.permission.WRITE_EXTERNAL_STORAGE").subscribe {

            KLog.e("is ok $it")

        }

    }

    override fun initGetIntent() {
    }

    override fun initData() {
        dataVM.mLd.observe(this, Observer {
            //            KLog.e("获取到了值 ，我在Activity中监听  $it")
            tempTv.text = "来自数据  $it"
        })

        dataVM.mOther.observe(this, Observer {
            otherTv.text = it
        })

        dataVM.close.observe(this, Observer {
            startActivity<TwoActivity>("text" to "Hello")
            finish()
        })

        // 基础 方案， 也不会产生问题
        dataVM.getData()

        delayedPost(1500) {
            //另一个产生
            dataVM.getOtherData()
        }


        // rxLifecycle方案，不会产生问题
//        dataVM.testRxLifecycle().bindLifecycle(this).subResult { t, message, errorCode ->
//           KLog.e("数据来了。。。")
//        }


        //滴滴方案，会产生泄露
//        dataVM.testAuto()
//            .`as`(
//                AutoDispose.autoDisposable(
//                AndroidLifecycleScopeProvider.from(this,
//                Lifecycle.Event.ON_DESTROY)))
//            .subscribe { s: String ->
//                KLog.e("获取的test  $s")
//            }



    }

    override fun initEvent() {

    }

    override fun lazyData() {
        KLog.e("我是延时执行")
//        delayedPost(2500) {
//            KLog.e("1s后，我就关闭了当前界面")
//            startActivity<TwoActivity>("text" to "Hello")
//            finish()
//        }
    }


}
