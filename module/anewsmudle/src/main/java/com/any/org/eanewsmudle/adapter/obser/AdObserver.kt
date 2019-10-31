package com.any.org.eanewsmudle.adapter.obser

/**
 *
 * @author any
 * @time 2019/10/30 15.00
 * @details
 */
interface AdObserver<T> {

      fun updateData(t:List<T>?,new:Boolean)

}