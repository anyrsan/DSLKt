package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/11/28 19.58
 * @details 资讯阅读
 */
data class EssayModel (val res: Int, val data: EssaySubModel?)

data class EssaySubModel(
    val anchor: String,
    val audio: String,
    val audio_duration: String,
    val auth_it: String,
    val commentnum: Int,
    val content_id: String,
    val copyright: String,
    val cover: String,
    val editor_email: String,
    val enable_pay: String,
    val guide_word: String,
    val hide_flag: String,
    val hp_author: String,
    val hp_author_introduce: String,
    val hp_content: String,
    val hp_makettime: String,
    val hp_title: String,
    val last_update_date: String,
    val maketime: String,
    val next_id: Int,
    val praisenum: Int,
    val previous_id: String,
    val sharenum: Int,
    val start_video: String,
    val sub_title: String,
    val top_media_file: String,
    val top_media_image: String,
    val top_media_type: String,
    val wb_img_url: String,
    val wb_name: String,
    val web_url: String
)
