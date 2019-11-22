package com.any.org.onemodule.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @author any
 * @time 2019/11/11 17.05
 * @details
 */
@Entity(tableName = "articles")
data class ArticleDetailData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "author_it")
    val authorIt: String,
    @ColumnInfo(name = "author_introduce")
    val authorIntroduce: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "makettime")
    val makettime: String
)