package com.any.org.commonlibrary.auto

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.annotation.NonNull
import kotlin.math.roundToInt

/**
 *
 * @author any
 * @time 2019/11/20 17.02
 * @details
 */
internal object AutoSize {


    fun resetConvertDensity(@NonNull activity: Activity){
        val sysDisplay = Resources.getSystem().displayMetrics
        val actDisplay = activity.resources.displayMetrics
        actDisplay.density = sysDisplay.density
        actDisplay.scaledDensity = sysDisplay.scaledDensity
        actDisplay.densityDpi = sysDisplay.densityDpi
        Log.e(
            "msg",
            "resetConvertDensity  actDisplay  dpi =${actDisplay.densityDpi}  density = ${actDisplay.density}  scaled=${actDisplay.scaledDensity}"
        )
    }


    //
    fun autoConvertDensity(@NonNull pxInDp: Int, @NonNull activity: Activity, @NonNull application: Application) {
        //系统的，不会改变   如果修改 application 后，想恢复，就调用 System Resources 进行 reset
//        val sysDisplay = Resources.getSystem().displayMetrics
//        Log.e(
//            "msg",
//            "系统的  System  sysDisplay  dpi =${sysDisplay.densityDpi}  density = ${sysDisplay.density}  scaled=${sysDisplay.scaledDensity}"
//        )
        //获取全局的 disPlay
        val appDisplay = application.resources.displayMetrics
        val appDensity = appDisplay.density
        val appScaledDensity = appDisplay.scaledDensity
        Log.e(
            "msg",
            "输出 application的消息  appDisplay  dpi = ${appDisplay.densityDpi} density = ${appDisplay.density} scaled = ${appDisplay.scaledDensity}"
        )

        val targetDensity = (appDisplay.widthPixels.toFloat() / pxInDp)
        val targetScaleDensity = (targetDensity * (appScaledDensity / appDensity))
        val targetDensityDpi = (160 * targetDensity).roundToInt()


        // 设置activity的 disPlay
        val actDisplay = activity.resources.displayMetrics
        actDisplay.density = targetDensity.sub()
        actDisplay.scaledDensity = targetScaleDensity.sub()
        actDisplay.densityDpi = targetDensityDpi
        Log.e(
            "msg",
            "输出 activity的消息 actDisplay  dpi = ${actDisplay.densityDpi} density = ${actDisplay.density} scaled = ${actDisplay.scaledDensity}"
        )

        // 通过对比， 发现 activity 中获取的 resources 对象 为同一个
        Log.e("aabb","输出  actDisplay ->   ${actDisplay.hashCode()}")

    }


}

//直接强转
fun Float.sub(): Float = (this * 100).roundToInt()/ 100f