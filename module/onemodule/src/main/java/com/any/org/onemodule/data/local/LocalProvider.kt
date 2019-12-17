package com.any.org.onemodule.data.local

import com.any.org.commonlibrary.log.KLog
import com.any.org.onemodule.data.room.ArticleDao
import com.any.org.onemodule.extend.DateEx
import com.any.org.onemodule.model.ArticleDetailDataModel
import com.any.org.onemodule.model.ArticleDetailModel
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.Single

/**
 *
 * @author any
 * @time 2019/11/11 20.05
 * @details
 */
class LocalProvider(private val artDao:ArticleDao) {


    fun getOneArticleDetail(id:String): Single<ArticleDetailModel> {
        return artDao.getArticleData(id).map {
            val articleDetailModel = ArticleDetailModel()
            articleDetailModel.res=0
            articleDetailModel.data = ArticleDetailDataModel()
            ConvertData.convertToDataModel(articleDetailModel.data,it)
            articleDetailModel
        }.doOnError {
            KLog.e("在这里有错了  $it")
        }
    }


    fun saveArticleDetail(articleDetailModel: ArticleDetailModel) = run{
        val data = ConvertData.convertToData(articleDetailModel.data)
       artDao.insertArticleData(data)
    }


    fun deleteAll() = artDao.deleteAllArticleData()

    fun getArticle(id:String) = artDao.getArticleData(id)


    //获取指定月分的日期
    fun getListDate(month:Int) = DateEx.getListDate(month)




}