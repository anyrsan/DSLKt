package com.any.org.commonlibrary.auto

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * @author any
 * @time 2019/11/21 19.42
 * @details 管理类
 */
class AutoSizeManager(private val application: Application) :
    BaseLifecycleCallback() {

    //注册
    fun registerLifecycleCallbacks() {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is IAdjustDensity) {
            AutoSize.autoConvertDensity(activity.pxInDp(), activity, application)
        } else {
            AutoSize.resetConvertDensity(activity)
        }
        //单独处理内部分发
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(this, true)
        }

    }


    //其实这里要注意，如果多个fragment被添加到同一activity时。对activity 的 比例操作
    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        Log.e("msg", "....///////  onFragmentCreated ")
        if (f is IAdjustDensity) {
            AutoSize.autoConvertDensity(f.pxInDp(), f.requireActivity(), application)
        } else {
            AutoSize.resetConvertDensity(f.requireActivity())
        }
    }


}