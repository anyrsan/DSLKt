package com.any.org.onemodule.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
 * @author any
 * @time 2019/11/11 19.59
 * @details
 */
@Database(entities = [ArticleDetailData::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

}