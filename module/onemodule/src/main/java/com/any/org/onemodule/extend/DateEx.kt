package com.any.org.onemodule.extend

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/11 17.30
 * @details
 */


inline fun Calendar.getTargetDate(type: Int = 0, diffNum: Int = 0, date: String? = null): String =
    run {
        //格式化
        val format = when (type) {
            1 -> "yyyy-MM"
            else -> "yyyy-MM-dd"
        }
        val simpleFormat = SimpleDateFormat(format)
        var targetDate = date?.let { simpleFormat.parse(it) } ?: time
        targetDate = if (diffNum != 0) {
            val tDate = Calendar.getInstance()
            tDate.time = targetDate
            if (type == 1) {
                val month = tDate.get(Calendar.MONTH)
                tDate.set(Calendar.MONTH, month + diffNum)
            } else {
                val day = tDate.get(Calendar.DATE)
                tDate.set(Calendar.DATE, day + diffNum)
            }
            tDate.time
        } else targetDate
        simpleFormat.format(targetDate)
    }
