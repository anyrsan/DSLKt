package com.any.org.onemodule.model

/**
 *
 * @author any
 * @time 2019/12/18 10.30
 * @details 连载 目录
 */

data class SerialMenuModel(
    val data: List<SerialMenuSubModel>,
    val res: Int
)

data class SerialMenuSubModel(
    val author: Author,
    val excerpt: String,
    val has_audio: Boolean,
    val id: String,
    val maketime: String,
    val number: String,
    val read_num: String,
    val serial_id: String,
    val serial_list: List<String>,
    val start_video: String,
    val title: String
)

data class Author(
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
