package com.any.org.onemodule.extend

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import com.any.org.commonlibrary.utils.DensityUtil
import com.any.org.onemodule.model.OneDataWeatherModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
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


    fun showTitleDate(date: String, textView: TextView) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val mDate = sdf.parse(date)
        //设置日期
        val calendar = Calendar.getInstance()
        calendar.time = mDate
        // 取日期
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        sdf.applyPattern("MM/yyyy")
        val smallValue = sdf.format(mDate)
        val fontSize = DensityUtil.dip2px(textView.context, 10f)
        textView.text = getValue(fontSize, "$day", smallValue)
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