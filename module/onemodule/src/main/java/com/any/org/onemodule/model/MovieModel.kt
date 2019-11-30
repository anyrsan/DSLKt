package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/28 20.04
 * @details
 */
data class MovieModel(val res: Int, val data: MovieSubModel?)

data class MovieSubModel(
    val charge_edt: String,
    val commentnum: Int,
    val detailcover: String,
    val directors: String,
    val directors_id: String,
    val editor_email: String,
    val enable_pay: String,
    val hide_flag: String,
    val id: String,
    val indexcover: String,
    val info: String,
    val keywords: String,
    val last_update_date: String,
    val maketime: String,
    val media_type: String,
    val movie_id: String,
    val next_id: Int,
    val officialstory: String,
    val poster: String,
    val praisenum: Int,
    val previous_id: String,
    val read_num: String,
    val related: String,
    val releasetime: String,
    val review: String,
    val revisedscore: String,
    val score: Any,
    val scoretime: String,
    val servertime: Int,
    val sharenum: Int,
    val sort: String,
    val start_video: String,
    val title: String,
    val verse: String,
    val verse_en: String,
    val video: String,
    val web_url: String
)