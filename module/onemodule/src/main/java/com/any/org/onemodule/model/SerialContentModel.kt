package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/28 19.40
 * @details  连载文章
 */
data class SerialContentModel(val res: Int, val data: SerialContentSubModel?)

data class SerialContentSubModel(
    val author: OneAuthorModel,
    val charge_edt: String,
    val content: String,
    val copyright: String,
    val cover: String,
    val editor_email: String,
    val enable_pay: String,
    val excerpt: String,
    val hide_flag: String,
    val id: String,
    val input_name: String,
    val last_update_date: String,
    val last_update_name: String,
    val lastid: String,
    val maketime: String,
    val nextid: Int,
    val number: String,
    val read_num: String,
    val serial_id: String,
    val start_video: String,
    val title: String,
    val top_media_file: String,
    val top_media_image: String,
    val top_media_type: String,
    val anchor: String,
    val web_url: String
) : ContentDetailsModel()