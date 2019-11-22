package com.any.org.onemodule.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 *
 * @author any
 * @time 2019/11/11 17.03
 * @details
 */
@Dao
interface ArticleDao {


    @Query("SELECT * FROM articles WHERE id = :id")
    fun getArticleData(id: String): Single<ArticleDetailData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleData(data: ArticleDetailData):Completable

    @Query("DELETE FROM articles WHERE id = :id")
    fun deleteArticleDataById(id: String):Completable

    @Query("DELETE FROM articles")
    fun deleteAllArticleData():Completable


}