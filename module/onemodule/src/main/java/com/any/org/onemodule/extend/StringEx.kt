package com.any.org.onemodule.extend

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import com.any.org.onemodule.model.OneDataWeatherModel
import java.lang.Exception
import kotlin.math.roundToInt

/**
 *
 * @author any
 * @time 2019/12/11 11.48
 * @details
 */
object StringEx {


    @JvmStatic
    fun formatWeather(weather: OneDataWeatherModel?) = if (weather == null) "" else
        "${weather?.city_name} ${weather?.climate} ${weather?.temperature}°C"


    @JvmStatic
    fun getMsg(msg: String?): String = if (msg.isNullOrEmpty()) "" else msg


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


    @JvmStatic
    fun formatTime(duration: String?): String {
        try {
            val msg = duration?.toInt() ?: 0
            val mm = msg / 60
            val ss = msg % 60
            val mt = if (mm >= 10) mm.toString() else "0$mm"
            val st = if (ss >= 10) ss.toString() else "0$ss"
            return "$mt:$st"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "error"
    }




}