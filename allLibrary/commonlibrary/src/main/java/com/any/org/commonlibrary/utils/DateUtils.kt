package com.any.org.commonlibrary.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 * User: any
 * Date: 2019/3/18
 */
object DateUtils {


    //  1,2,,3,4,5,6,7
    private val WEEK = arrayOf("日", "一", "二", "三", "四", "五", "六")

    /**
     * 2分钟超时
     */
    fun isTimeOut(timeMillis: Long,timeOut:Int=2):Boolean{
        val diffTime = System.currentTimeMillis() - timeMillis
        val mT = timeOut * 60 * 1000
        return diffTime > mT
    }

    //获取下标
    fun getIndex(timeMillis: Long): Array<Int> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        var h = calendar.get(Calendar.HOUR_OF_DAY)
        val m = calendar.get(Calendar.MINUTE)
        return arrayOf(h, m)
    }

    //获取下标
    fun getIndex(date: String): Array<Int> {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date)
        var h = calendar.get(Calendar.HOUR_OF_DAY)
        val m = calendar.get(Calendar.MINUTE)
        val b = h > 12
        val ap = if (b) 1 else 0
        h = if (b) h - 12 else h    //下标是从0开始的
        return arrayOf(ap, h, m)
    }

    fun formatDate(date: String, pattern: String = "yyyy-MM-dd"): String {
        val timeMillis = getTimeMillis(date)
        val week = getWeekIndex(timeMillis) - 1
        val wks = " 周" + WEEK[week]
        var format = SimpleDateFormat("HH:mm")
        var hhmm = format.format(Date(timeMillis))
        val ampm = if (hhmm.split(":")[0].toInt() > 12) "下午" else "上午"
        format = SimpleDateFormat("hh:mm")
        hhmm = format.format(Date(timeMillis))
        val dt = SimpleDateFormat(pattern).format(Date(timeMillis))
        return "$dt $wks $ampm $hhmm"
    }


    // 推荐用这个
    fun formatDate(timeMillis: Long, pattern: String = "yyyy-MM-dd",showWeek:Boolean=true): String {
        val format = SimpleDateFormat(pattern)
        val dt = format.format(Date(timeMillis))
        val week = getWeekIndex(timeMillis) - 1
        return if(showWeek){
            val wks = "周" + WEEK[week]
            "$dt:$wks"
        }else{
            "$dt"
        }
    }

    fun formatDateNew(timeMillis:Long, pattern: String = "yyyy-MM-dd",showWeek:Boolean=true): String {
        val format = SimpleDateFormat(pattern)
        val dt = format.format(timeMillis)
        val week = getWeekIndex(timeMillis) - 1
        return if(showWeek){
            val wks = "周" + WEEK[week]
            "$dt:$wks"
        }else{
            "$dt"
        }
    }

    fun formatDateNew(date:String, pattern: String = "yyyy-MM-dd",showWeek:Boolean=true): String {
        val format = SimpleDateFormat(pattern)
        val timeMillis = convertDateToTimeMillis(date)
        val dt = format.format(timeMillis)
        val week = getWeekIndex(timeMillis) - 1
        return if(showWeek){
            val wks = "周" + WEEK[week]
            "$dt:$wks"
        }else{
            "$dt"
        }
    }

    private fun convertDateToTimeMillis(date: String,pattern: String="yyyy-MM-dd HH:mm:ss"):Long{
        val myFormatter = SimpleDateFormat(pattern)
        return myFormatter.parse(date).time
    }


    // 2019-03-12:周二:7:30
    fun formatStringToDate(str: String): String {
        val strList = str.split(":")
        var hour = strList[2].toInt()
        var apm = "上午"
        if (hour > 12) {
            apm = "下午"
            hour -= 12
        }
        return strList[0] + " " + strList[1] + " " + apm + " " + hour + ":" + strList[3]
    }

    // 2019-03-12:周二:17:30
    fun convertStrToDate(str: String): String {
        val strList = str.split(":")
        val tstr = strList[0] + " " + strList[2] + ":" + strList[3]
        val dt = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(tstr)
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dt)
    }


    fun formatTime(time: Long, isCheckYesterday: Boolean = true): String {
        return when {
            isSameDay(time) -> "今天 ${SimpleDateFormat("HH:mm").format(Date(time))}"
            isCheckYesterday && isYesterday(time) -> "昨天 ${SimpleDateFormat("HH:mm").format(Date(time))}"
            isTommorrow(time) -> "明天 ${SimpleDateFormat("HH:mm").format(Date(time))}"
            else -> "${SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(time))}"
        }
    }


    fun isSameDay(var0: Long): Boolean {
        val var2 = getTodayStartAndEndTime()
        return var0 >= var2.first && var0 < var2.second
    }

    private fun isYesterday(var0: Long): Boolean {
        val var2 = getYesterdayStartAndEndTime()
        return var0 >= var2.first && var0 < var2.second
    }

    private fun isTommorrow(var0: Long): Boolean {
        val var2 = getTomorrowStartAndEndTime()
        return var0 >= var2.first && var0 < var2.second
    }

    private fun getYesterdayStartAndEndTime(): Pair<Long, Long> {
        val var0 = Calendar.getInstance()
        var0.add(Calendar.DAY_OF_MONTH, -1)
        var0.set(Calendar.HOUR_OF_DAY, 0)
        var0.set(Calendar.MINUTE, 0)
        var0.set(Calendar.SECOND, 0)
        var0.set(Calendar.MILLISECOND, 0)
        val var1 = var0.time
        val var2 = var1.time
        val var4 = Calendar.getInstance()
        var4.add(Calendar.DAY_OF_MONTH, -1)
        var4.set(Calendar.HOUR_OF_DAY, 23)
        var4.set(Calendar.MINUTE, 59)
        var4.set(Calendar.SECOND, 59)
        var4.set(Calendar.MILLISECOND, 999)
        val var5 = var4.time
        val var6 = var5.time
        return Pair(var2, var6)
    }

    private fun getTodayStartAndEndTime(): Pair<Long, Long> {
        val var0 = Calendar.getInstance()
        var0.set(Calendar.HOUR_OF_DAY, 0)
        var0.set(Calendar.MINUTE, 0)
        var0.set(Calendar.SECOND, 0)
        var0.set(Calendar.MILLISECOND, 0)
        val var1 = var0.time
        val var2 = var1.time
        val var4 = Calendar.getInstance()
        var4.set(Calendar.HOUR_OF_DAY, 23)
        var4.set(Calendar.MINUTE, 59)
        var4.set(Calendar.SECOND, 59)
        var4.set(Calendar.MILLISECOND, 999)
        val var5 = var4.time
        val var6 = var5.time
        return Pair(var2, var6)
    }


    //明天的起始时间与结束时间
    private fun getTomorrowStartAndEndTime(): Pair<Long, Long> {
        val var0 = Calendar.getInstance()
        var0.add(Calendar.DAY_OF_MONTH, +1)
        var0.set(Calendar.HOUR_OF_DAY, 0)
        var0.set(Calendar.MINUTE, 0)
        var0.set(Calendar.SECOND, 0)
        var0.set(Calendar.MILLISECOND, 0)
        val var1 = var0.time
        val var2 = var1.time
        val var4 = Calendar.getInstance()
        var4.add(Calendar.DAY_OF_MONTH, +1)
        var4.set(Calendar.HOUR_OF_DAY, 23)
        var4.set(Calendar.MINUTE, 59)
        var4.set(Calendar.SECOND, 59)
        var4.set(Calendar.MILLISECOND, 999)
        val var5 = var4.time
        val var6 = var5.time
        return Pair(var2, var6)
    }


    //**获取指定日期相差 n 天的时间单位
    fun getDateByNDay(timeMillis: Long, n: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val day = calendar.get(Calendar.DATE)
        calendar.set(Calendar.DATE, day + n)
        return calendar.timeInMillis
    }

    //两个日期相差几天
    fun getBetweenDay(timeMillis: Long, timeMillis2: Long, pattern: String = "yyyy-MM-dd"): Long {
        val myFormat = SimpleDateFormat(pattern)
        val d1 = myFormat.format(Date(timeMillis))
        val d2 = myFormat.format(Date(timeMillis2))
        val t1 = myFormat.parse(d1).time
        val t2 = myFormat.parse(d2).time
        val between = t1 - t2
        return Math.abs(between) / (1000 * 3600 * 24)
    }


    //截止当前的天数
    fun getBetweenDay(timeMillis: Long, timeMillis2: Long = System.currentTimeMillis()): Long {
        val between = timeMillis2 - timeMillis
        return abs(between) / (1000 * 3600 * 24)
    }


    //截止当前的天数
    fun getBetweenDayAndHour(
        timeMillis: Long,
        timeMillis2: Long = System.currentTimeMillis()
    ): Triple<Long, Long, Long> {
        val between = timeMillis2 - timeMillis

        val dayM = 1000 * 3600 * 24

        val hourM = 1000 * 60 * 60

        val minuteM = 1000 * 60

        val day = abs(between) / dayM

        val hour = abs(between) % dayM / hourM

        val minute = abs(between) % dayM % hourM / minuteM

        return Triple(day, hour, minute)
    }

    /***
     * 指定日期的相差N天的日期
     */
    fun getDateByNDay(date: String, n: Int, pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
        val myFormatter = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        calendar.time = myFormatter.parse(date)
        val day = calendar.get(Calendar.DATE)
        calendar.set(Calendar.DATE, day + n)
        return myFormatter.format(calendar.time)
    }

    //当前时间之后 的N 天
    fun getCurrTimeByNDay(date: String, n: Int, pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
        val myFormatter = SimpleDateFormat(pattern)
        val mDate = myFormatter.parse(date)
        val tempCalendar = Calendar.getInstance()
        tempCalendar.time = mDate

        //当前时间的N天后
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val day = calendar.get(Calendar.DATE)
        calendar.set(Calendar.DATE, day + n)

        tempCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        tempCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        tempCalendar.set(Calendar.DATE, calendar.get(Calendar.DATE))
        return myFormatter.format(tempCalendar.time)
    }


    //获取当前时间并显示 周几
    fun getTimeAndWeek(timeMillis: Long = System.currentTimeMillis()): Pair<String, String> {
        //获取当前时间
        val time = SimpleDateFormat("MM-dd").format(Date(timeMillis))

        val week = getWeekIndex(timeMillis) - 1

        return Pair(time, "周${WEEK[week]}")
    }


    //获取指定时间星期几  下标从 1 开始  1 星期天  7 星期六
    fun getWeekIndex(time: Long): Int {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        return c.get(Calendar.DAY_OF_WEEK)
    }


    /**
     * 2019-03-26T18:22:32
     */
    fun getTimeMillis(date: String, pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): Long {
        val myFormatter = SimpleDateFormat(pattern)
        return myFormatter.parse(date).time
    }

    fun getDate(timeMillis: Long, pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): String {
        val myFormatter = SimpleDateFormat(pattern)
        return myFormatter.format(Date(timeMillis))
    }


    fun isExceedTime(date: String): Boolean {
        val time = getTimeMillis(date)
        return isExceedTime(time)
    }

    fun isExceedTime(time: Long): Boolean {
        val currTime = System.currentTimeMillis()
        return time < currTime
    }

    // [1,2,3,4]
    fun weekMsg(cycle: List<String>?): String? {
        cycle?.let {
            return with(StringBuilder()) {
                if (it.size == 7) {
                    return "每天"
                }
                it.forEach {
                    append("周${WEEK[it.toInt() - 1]} ")
                }
                toString()
            }
        }
        return null
    }


//    /**
//     * 获取指定月份的第一天跟最后一天
//     */
//    fun getMonthFandL(date: String, pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): Pair<Long, Long> {
//        val myFormatter = SimpleDateFormat(pattern)
//        val date = myFormatter.parse(date)
//        val calendar = Calendar.getInstance()
//        calendar.time = date
//
//        val firstDay = calendar.getMinimum(Calendar.DATE)
//        calendar.set(Calendar.DAY_OF_MONTH, firstDay)
//        val fT = calendar.timeInMillis
//
//        val lastDay = calendar.getMaximum(Calendar.DATE)
//        calendar.set(Calendar.DAY_OF_MONTH, lastDay)
//        val lT = calendar.timeInMillis
//        return Pair(fT, lT)
//    }


    /**
     * 获取指定日期内的日期以及周几
     */
    fun getPerDaysByStartAndEndDate(
        startTime: Long,
        endTime: Long,
        dateFormat: String = "MM月dd日"
    ): Pair<List<String>, List<Long>> {


        var format = SimpleDateFormat(dateFormat)
        val res = mutableListOf<String>()
        val res2 = mutableListOf<Long>()
        try {
            var start = startTime
            var end = endTime
            if (start > end) return Pair(res, res2)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = start
            while (end >= start) {
                val wk = calendar.get(Calendar.DAY_OF_WEEK)
                if (isSameDay(calendar.timeInMillis)) {
                    res.add("今天")
                } else {
                    res.add(format.format(calendar.time) + " 周${WEEK[wk - 1]}")
                }
                res2.add(calendar.timeInMillis)
                calendar.add(Calendar.DAY_OF_MONTH, +1)
                start = calendar.timeInMillis
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(res, res2)
    }


}