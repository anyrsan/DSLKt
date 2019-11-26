package com.any.org.commonlibrary.auto

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentManager

/**
 *
 * @author any
 * @time 2019/11/22 10.20
 * @details
 */
abstract class BaseLifecycleCallback : Application.ActivityLifecycleCallbacks, FragmentManager.FragmentLifecycleCallbacks(){


    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {

    }


    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }
}