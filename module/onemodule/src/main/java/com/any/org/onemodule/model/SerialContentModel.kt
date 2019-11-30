package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/28 19.40
 * @details  连载文章
 */
data class SerialContentModel(val res: Int, val data: SerialContentSubModel?)

data class SerialContentSubModel(
    val audio_duration: String,
    val author: OneAuthorModel,
    val charge_edt: String,
    val commentnum: Int,
    val content: String,
    val copyright: String,
    val cover: String,
    val enable_pay: String,
    val excerpt: String,
    val id: String,
    val last_update_date: String,
    val lastid: String,
    val maketime: String,
    val nextid: Int,
    val number: String,
    val praisenum: Int,
    val read_num: String,
    val serial_id: String,
    val sharenum: Int,
    val title: String
)