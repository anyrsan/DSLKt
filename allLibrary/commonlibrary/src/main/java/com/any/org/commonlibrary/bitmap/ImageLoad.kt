package com.any.org.commonlibrary.bitmap

import android.widget.ImageView
import com.any.org.commonlibrary.R
import com.bumptech.glide.Glide

/**
 *
 * @author any
 * @time 2019/10/31 11.43
 * @details
 */

fun ImageView.load(url:String) = kotlin.run {
    Glide.with(context).load(url).centerCrop().placeholder(R.drawable.ic_arrow_back_black_24dp).into(this)
}