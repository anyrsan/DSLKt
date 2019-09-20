package com.any.org.commonlibrary.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.any.org.commonlibrary.CustomToast
import com.any.org.commonlibrary.utils.StatusBarUtils
import com.trello.rxlifecycle3.components.support.RxFragment

/**
 *
 * @author any
 * @time 2019/9/18 16.44
 * @details
 */
abstract class BaseFargment : RxFragment() {


    abstract fun getResourceId(): Int

    abstract fun initData()

    //懒加载数据
    abstract fun lazyData()

    abstract fun initEvent()

    protected val handler by lazy { Handler() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getResourceId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        delayedLoad(::lazyData)
        initEvent()
    }


    /**
     * 关闭当前窗体
     */
    protected fun finish() {
        activity?.finish()
    }


    //设置顶部的值
    protected fun setTopPadding(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setPadding(0, StatusBarUtils.getStatusBarHeight(context), 0, 0)
        }
    }


    fun requestTimeOut(r: Runnable, time: Long = 30000) {
        handler.postDelayed(r, time)
    }

    fun cancelTimeOut(r: Runnable) {
        handler.removeCallbacks(r)
    }

    /**
     * 延时执行任务
     */
    fun delayDoTask(time: Long = 100, block: () -> Unit) {
        handler.postDelayed(block, time)
    }

    //处理
    private fun delayedLoad(block: () -> Unit, delayed: Long = 300) {
        handler.postDelayed({
            activity?.apply {
                if (isFinishing)
                    return@postDelayed
            }
            block()
        }, delayed)
    }


    protected fun runUI(block: () -> Unit) {
        handler.post(block)
    }

}



fun Fragment.mToast(msg: String) {
    activity?.mToast(msg)
}

fun Fragment.mToast(@StringRes stringId: Int) {
    activity?.mToast(stringId)
}
