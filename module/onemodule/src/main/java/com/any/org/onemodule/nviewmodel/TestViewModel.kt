package com.any.org.onemodule.nviewmodel

import com.any.org.commonlibrary.log.KLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 *
 * @author any
 * @time 2020/1/11 14.25
 * @details
 */
class TestViewModel : NBaseCoroutineViewModel() {


    fun doOneTask() {
        doTask {
            printlnNum()
        }
    }


   private suspend fun printlnNum() {
        withContext(Dispatchers.IO) {
            var doTask = true
            while (doTask) {
                KLog.e("每隔0.5s输出一次   ${this@TestViewModel}")
                delay(500)
            }
        }
    }




}