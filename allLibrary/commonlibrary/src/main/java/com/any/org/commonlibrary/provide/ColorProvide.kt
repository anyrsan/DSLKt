package com.any.org.commonlibrary.provide

import android.view.View

/**
 *
 * @author any
 * @time 2019/10/21 17.37
 * @details
 */
object ColorProvide {
    private val colors = arrayOf("#ffcdd2","#e1bee7","#c5cae9","#b3e5fc","#b2dfdb","#dcedc8","#fff9c4","#ffe0b2","#d7ccc8","#cfd8dc")
    //处理值
   fun getColor(position:Int):String = colors[(position% colors.size)]
}