package com.any.org.onemodule.data

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.model.OneDataWeatherModel
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author any
 * @time 2019/11/11 17.01
 * @details
 */

object CateApi {

    /**
     *  {
    0: "图文",
    1: "阅读",
    2: "连载",
    3: "问答",
    4: "音乐",
    5: "影视",
    8: "电台",
    0: "hp",
    1: "essay",
    2: "serialcontent",
    3: "question",
    4: "music",
    5: "movie",
    8: "radio",
    };
     */
    private fun cateFullData(en: Boolean = true) = lazy {
        val map = hashMapOf<Int, String>()
        map[0] = if (en) "hp" else "图文"
        map[1] = if (en) "essay" else "阅读"
        map[2] = if (en) "serialcontent" else "连载"
        map[3] = if (en) "question" else "问答"
        map[4] = if (en) "music" else "音乐"
        map[5] = if (en) "movie" else "影视"
        map[8] = if (en) "radio" else "电台"
        map
    }

    private val cateMenuZH by cateFullData(false)

    private val cateMenuEN by cateFullData()


    fun getCateEn(cateType: String?): String {
        return try {
            val key = cateType?.toInt() ?: 0
            return "${cateMenuEN[key]}"
        } catch (e: Exception) {
            e.printStackTrace()
            "hp"
        }
    }

    @JvmStatic
    fun getCateTitle(cateType: String?): String {
        return try {
            val key = cateType?.toInt() ?: 0
            return "- ${cateMenuZH[key]} -"
        } catch (e: Exception) {
            e.printStackTrace()
            "- 未知 -"
        }
    }

    @JvmStatic
    fun getMsg(msg: String?): String = if (msg.isNullOrEmpty()) "" else msg


    fun getType(cateType: String?): Int {
        return cateType?.toInt() ?: -1
    }


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


    @JvmStatic
    fun formatWeather(weather: OneDataWeatherModel?) =
        "${weather?.city_name} ${weather?.climate} ${weather?.temperature}°C"


    fun getValue(fontSize: Int, bigValue: String, smallValue: String): SpannableString {
        val spannableString = SpannableString("$bigValue   $smallValue")
        val index = bigValue.length
        spannableString.setSpan(
            AbsoluteSizeSpan(fontSize),
            index,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }


}




