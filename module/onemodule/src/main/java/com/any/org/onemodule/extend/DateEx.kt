package com.any.org.onemodule.extend

import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.model.OneMonthSubModel
import io.reactivex.Observer
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/11 17.30
 * @details
 */

object DateEx {

    //2019-11-25 06:00:00  //今天，日期
    @JvmStatic
    fun convertDate(date: String?): String {
        try {
            KLog.e("输出日期  $date")
            if (date.isNullOrEmpty()) return "未知日期"
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val targetDate = format.parse(date)
            format.applyPattern("yyyy-MM-dd")
            val now = format.format(Date())
            val target = format.format(targetDate)
            return if (now == target) {
                "今天"
            } else {
                format.applyPattern("MM-dd")
                format.format(targetDate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "异常"
    }



    fun isToday(date: String): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val now = format.format(Date())
        KLog.e("isToday... $date  $now")
        return now == date
    }

    fun convertDate(timeInMillis: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(Date(timeInMillis))
    }


    /**
     * 获取当前月分的前N个月日期
     */
    fun getListDate(diffMonth: Int = 0): List<String> {
        val list = mutableListOf<String>()
        // 获取当前时间
        val now = Calendar.getInstance()
        now.timeInMillis = Date().time
        now.add(Calendar.MONTH, diffMonth)
        val day = now.get(Calendar.DAY_OF_MONTH)
        println("day... $day")
        val maxDay = if (diffMonth != 0) {
            now.getActualMaximum(Calendar.DAY_OF_MONTH)
        } else {
            day
        }
        println("count... $maxDay")
        now.set(Calendar.DAY_OF_MONTH, maxDay)
        for (i in 1..maxDay) {
            list.add(convertDate(now.timeInMillis))
            println("... $i    ${convertDate(now.timeInMillis)}")
            now.add(Calendar.DAY_OF_MONTH, -1)
        }
        return list
    }

}

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


//增加函数
infix fun OneMonthSubModel.getMonthFromDate(date: String?): String {
    try {
        KLog.e("输出日期  $date")
        if (date.isNullOrEmpty()) return "未知日期"
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val targetDate = format.parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = targetDate
        return "${calendar.get(Calendar.MONTH) + 1}月"
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "异常"
}



