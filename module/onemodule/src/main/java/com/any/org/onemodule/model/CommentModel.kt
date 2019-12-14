package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/12/13 16.02
 * @details
 */
data class CommentModel(
    val data: CommentSubModel,
    val res: Int
)

data class CommentSubModel(
    val count: Int,
    val data: List<CommentItemModel>
)

data class CommentItemModel(
    val content: String,
    val created_at: String,
    val del_flag: String,
    val device_token: String,
    val id: String,
    val input_date: String,
    val praisenum: Int,
    val quote: String,
    val reviewed: String,
    val touser: Any,
    val type: Int,
    val updated_at: String,
    val user: User,
    val user_info_id: String
)

data class User(
    val user_id: String,
    val user_name: String,
    val web_url: String
)