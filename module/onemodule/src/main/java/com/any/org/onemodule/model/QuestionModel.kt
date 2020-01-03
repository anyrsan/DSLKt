package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/28 20.00
 * @details 问答
 */
data class QuestionModel(val res: Int, val data: QuestionSubModel?)

data class QuestionSubModel(
    val anchor: String,
    val answer_content: String,
    val answer_title: String,
    val answerer: Answerer,
    val asker: Asker,
    val charge_edt: String,
    val charge_email: String,
    val content_bgcolor: String,
    val copyright: String,
    val cover: String,
    val cover_media_file: String,
    val cover_media_type: String,
    val enable_pay: String,
    val guide_word: String,
    val last_update_date: String,
    val next_id: Int,
    val previous_id: String,
    val question_content: String,
    val question_id: String,
    val question_makettime: String,
    val question_title: String,
    val read_num: String,
    val recommend_flag: String,
    val start_video: String,
    val web_url: String
) : ContentDetailsModel()

data class Answerer(
    val desc: String,
    val fans_total: String,
    val is_settled: String,
    val settled_type: String,
    val summary: String,
    val user_id: String,
    val user_name: String,
    val wb_name: String,
    val web_url: String
)

data class Asker(
    val desc: String,
    val fans_total: String,
    val is_settled: String,
    val settled_type: String,
    val summary: String,
    val user_id: String,
    val user_name: String,
    val wb_name: String,
    val web_url: String
)