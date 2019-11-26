package com.any.org.onemodule.util

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import com.any.org.commonlibrary.utils.DensityUtil

/**
 *
 * @author any
 * @time 2019/10/8 10.26
 * @details  字体处理类
 */
object SpannableUtils {


    fun getValue(fontSize: Int, bigValue: String, smallValue: String): SpannableString {
        val spannableString = SpannableString("$bigValue $smallValue")
        val index = bigValue.length
        spannableString.setSpan(
            AbsoluteSizeSpan(fontSize),
            index,
            index + smallValue.length - 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }


    fun setValueToTextView(textView: TextView, value: String, mark: String = "¥") {
        val fontSize = DensityUtil.sp2px(textView.context, 10f)
        val text = getTarget("$mark$value", fontSize, mark)
        textView.text = text
    }


    fun setValueToTextView(
        textView: TextView,
        startValue: String,
        endValue: String,
        mark: String = "¥"
    ) {
        val fontSize = DensityUtil.sp2px(textView.context, 10f)
        val text = getTarget("$startValue$mark$endValue", fontSize, mark)
        textView.text = text
    }

    private fun getTarget(value: String, fontSize: Int, mark: String = "¥"): SpannableString {
        val spannableString = SpannableString(value)
        val index = value.indexOf(mark)
        if (index >= 0) {  //AbsoluteSizeSpan   //RelativeSizeSpan
            spannableString.setSpan(
                AbsoluteSizeSpan(fontSize),
                index,
                index + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

}