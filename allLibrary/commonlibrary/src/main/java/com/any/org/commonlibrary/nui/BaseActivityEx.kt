package com.any.org.commonlibrary.nui

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.utils.StatusBarUtils

/**
 *
 * @author any
 * @time 2019/12/11 17.52
 * @details acitivty基础类
 */
abstract class BaseActivityEx : AppCompatActivity() {

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

    open fun onCreate() {
        setContentView(getResourceId())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
        //优化设置
        if (isSetStatus()) setStatusBar(true, Color.WHITE) else setStatusBar(true)
        initView()
        initGetIntent()
        initData()
        initEvent()
        delayLoad(300, ::lazyData)
    }


}


//提供 主线的 handler 对象
private object ContextHandler {
    val handler = Handler(Looper.getMainLooper())
}

fun requestTimeOut(r: Runnable, time: Long = 30000) {
    ContextHandler.handler.postDelayed(r, time)
}

fun cancelTimeOut(r: Runnable) {
    ContextHandler.handler.removeCallbacks(r)
}

/**
 * 运行主线程
 */
fun runUI(block: () -> Unit) {
    ContextHandler.handler.post { block() }
}

/**
 * 延时加载
 */
fun Activity.delayLoad(delayed: Long = 300, block: () -> Unit) {
    ContextHandler.handler.postDelayed({
        if (isFinishing)
            return@postDelayed
        block()
    }, delayed)
}

/**
 *  延时加载
 */
fun Fragment.delayLoad(delayed: Long = 300, block: () -> Unit) {
    ContextHandler.handler.postDelayed({
        if (isDetached)
            return@postDelayed
        block()
    }, delayed)
}


/**
 * 状态栏
 */
fun Activity.setStatusBar(isDark: Boolean, color: Int = Color.TRANSPARENT) {
    StatusBarUtils.setStatusBar(this, isDark, color)
}

/**
 * view padding
 */
fun View.setTopPadding() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setPadding(0, StatusBarUtils.getStatusBarHeight(resources), 0, 0)
    }
}

/***
 * toast
 */
fun Activity.mToast(message: String?) {
    runOnUiThread {
        CustomToast.showMsg(this, message)
    }
}

/**
 *  toast other
 */
fun Activity.mToast(@StringRes stringId: Int) {
    runOnUiThread {
        CustomToast.showMsg(this, stringId)
    }
}

/**
 * toast
 */
fun Fragment.mToast(msg: String) {
    activity?.mToast(msg)
}

/**
 * toast
 */
fun Fragment.mToast(@StringRes stringId: Int) {
    activity?.mToast(stringId)
}


/**
 * 关闭当前窗体
 */
fun Fragment.finish() {
    activity?.finish()
}


/***
 * 创建 fragment 一般是单例 exp:  val baseFg by lazy {createFragment(BaseFragment::class.java.name)}
 */
fun AppCompatActivity.createFragment(className: String): Fragment = run {
    supportFragmentManager.fragmentFactory.instantiate(this.classLoader, className)
}

/***
 * 创建 fragment 一般是单例 exp:  val baseFg by lazy {createFragment(BaseFragment::class.java.name)}
 */
fun FragmentPagerAdapter.createFragment(
    mFragmentManager: FragmentManager,
    className: String
): Fragment = run {
    mFragmentManager.fragmentFactory.instantiate(this.javaClass.classLoader!!, className)
}


/***
 *  提供添加
 */
fun AppCompatActivity.addFragment(fragment: Fragment, @IdRes rId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    fragmentTransaction.add(rId, fragment)
    fragmentTransaction.commitAllowingStateLoss()
}


/***
 *  提供添加
 */
fun AppCompatActivity.addOrShowFragment(fragment: Fragment, @IdRes rId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    if (fragment.isAdded) {
        fragmentTransaction.show(fragment)
    } else {
        fragmentTransaction.add(rId, fragment)
    }
    fragmentTransaction.commitAllowingStateLoss()
}


fun AppCompatActivity.hideFragment(fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    fragmentTransaction.hide(fragment)
    fragmentTransaction.commitAllowingStateLoss()
}


/***
 * 替换
 */
fun AppCompatActivity.replaceFragment(fragment: Fragment, @IdRes rId: Int) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    fragmentTransaction.replace(rId, fragment)
    fragmentTransaction.commitAllowingStateLoss()
}


/***
 * 移除
 */
fun AppCompatActivity.removeFragment(fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
    fragmentTransaction.remove(fragment)
    fragmentTransaction.commitAllowingStateLoss()
}



