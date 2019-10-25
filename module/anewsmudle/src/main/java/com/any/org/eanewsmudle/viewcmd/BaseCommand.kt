package com.any.org.newsmodule.viewcmd

/**
 *
 * @author any
 * @time 2019/10/23 13.47
 * @details 指令
 */
interface BaseCommand<T> {

    fun cmdCallTask(flag:Int): T?  //执行任务

}