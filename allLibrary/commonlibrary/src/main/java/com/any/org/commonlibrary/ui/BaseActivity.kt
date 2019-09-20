package com.any.org.commonlibrary.ui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.utils.StatusBarUtils
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

/**
 *
 * @author any
 * @time 2019/9/18 16.35
 * @details
 */
abstract class BaseActivity : RxAppCompatActivity() {

    @LayoutRes
    abstract fun getResourceId(): Int

    //设置view
    abstract fun initView()

    //处理initent
    abstract fun initGetIntent()

    //初始化数据
    abstract fun initData()

    //设置View事件
    abstract fun initEvent()

    //懒加载数据
    abstract fun lazyData()

    open fun isSetStatus(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //优化设置
        if (isSetStatus()) setStatusBar(true, Color.WHITE)
        setContentView(getResourceId())
        initView()
        initGetIntent()
        initData()
        initEvent()
        delayedLoad(::lazyData)
    }


    /***
     *  提供添加
     */
    protected fun addFragment(fragment: Fragment, @IdRes rId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(rId, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }


    /***
     * 替换
     */
    protected fun replaceFragment(fragment: Fragment, @IdRes rId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(rId, fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }


    /***
     * 移除
     */
    protected fun removeFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commitAllowingStateLoss()
    }


    protected fun setStatusBar(isDark: Boolean, color: Int) {
        StatusBarUtils.setStatusBar(this, isDark, color)
    }

    protected fun setStatusBarTransparent(isDark: Boolean) {
        setStatusBar(isDark, Color.TRANSPARENT)
    }


    //设置顶部的值
    protected fun setTopPadding(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setPadding(0, StatusBarUtils.getStatusBarHeight(this), 0, 0)
        }
    }

    protected val handler by lazy { Handler() }

    //延时处理任务
    protected fun delayedPost(delayed: Long = 100, block: () -> Unit) {
        delayedLoad(block, delayed)
    }

    private fun delayedLoad(block: () -> Unit, delayed: Long = 300) {
        handler.postDelayed({
            if (isFinishing)
                return@postDelayed
            block()
        }, delayed)
    }


    protected fun runUI(block: () -> Unit) {
        handler.post(block)
    }

}

//处理
fun Activity.mToast(message: String?) {
    runOnUiThread {
        CustomToast.showMsg(this, message)
    }
}

//扩展toast函数
fun Activity.mToast(@StringRes stringId: Int) {
    runOnUiThread {
        CustomToast.showMsg(this, stringId)
    }
}