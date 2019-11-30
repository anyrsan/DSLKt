package com.any.org.commonlibrary.bitmap

import android.widget.ImageView
import com.any.org.commonlibrary.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 *
 * @author any
 * @time 2019/10/31 11.43
 * @details
 */

fun ImageView.load(url: String, isCircle: Boolean = false) = kotlin.run {
    if (isCircle) {
        Glide.with(context).load(url).centerCrop().apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.toast_bg).into(this)
    } else {
        Glide.with(context).load(url).centerCrop().placeholder(R.drawable.default_bg)
            .into(this)
    }
}


